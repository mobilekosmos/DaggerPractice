package com.example.daggerpractice.ui.main.posts;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.daggerpractice.SessionManager;
import com.example.daggerpractice.models.Post;
import com.example.daggerpractice.network.main.MainApi;
import com.example.daggerpractice.ui.main.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PostsViewModel extends ViewModel {
    private static final String TAG = "PostsViewModel";

    // inject
    private final SessionManager mSessionManager;
    private final MainApi mMainApi;

    private MediatorLiveData<Resource<List<Post>>> mPosts;

    @Inject
    public PostsViewModel(final SessionManager sessionManager, final MainApi mainApi) {
        mSessionManager = sessionManager;
        mMainApi = mainApi;
        Log.d(TAG, "PostViewModel: view model is working");
    }

    public LiveData<Resource<List<Post>>> observePosts() {
        if (mPosts == null) {
            mPosts = new MediatorLiveData<>();
            mPosts.setValue(Resource.loading((List<Post>) null));

            final LiveData<Resource<List<Post>>> source = LiveDataReactiveStreams.fromPublisher(mMainApi.getPostsFromUser(mSessionManager.getAuthUser().getValue().data.getId())
                    .onErrorReturn(new Function<Throwable, List<Post>>() {
                        @Override
                        public List<Post> apply(Throwable throwable) throws Exception {
                            Log.e(TAG, "apply: ", throwable);
                            final Post post = new Post();
                            post.setId(-1);
                            final ArrayList<Post> posts = new ArrayList<>();
                            posts.add(post);
                            return posts;
                        }
                    })

                    .map(new Function<List<Post>, Resource<List<Post>>>() {
                        @Override
                        public Resource<List<Post>> apply(List<Post> posts) throws Exception {
                            if (posts.size() > 0) {
                                if (posts.get(0).getId() == -1) {
                                    return Resource.error("Something went wrong", null);
                                }
                            }
                            return Resource.success(posts);
                        }
                    })

                    .subscribeOn(Schedulers.io())
            );
            mPosts.addSource(source, new Observer<Resource<List<Post>>>() {
                @Override
                public void onChanged(Resource<List<Post>> listResource) {
                    mPosts.setValue(listResource);
                    mPosts.removeSource(source);
                }
            });
        }
        return mPosts;
    }
}
