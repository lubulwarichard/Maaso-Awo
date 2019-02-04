package net.unique.awo.ui.post

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import net.unique.awo.base.BasePresenter
import net.unique.awo.db.posts.PostsLocalRepo
import net.unique.awo.model.Post
import net.unique.awo.remote.PostApi
import javax.inject.Inject


/**
 * The Presenter that will present the Post view.
 * @param postView the Post view to be presented by the presenter
 * @property postApi the API interface implementation
 * @property context the context in which the application is running
 * @property subscription the subscription to the API call
 */
class PostPresenter(postView: PostView) : BasePresenter<PostView>(postView) {

    @Inject
    lateinit var postsLocalRepo: PostsLocalRepo

    private lateinit var disposable: Disposable

    override fun onViewCreated() {
        loadPosts()
    }

    /**
     * Loads the posts from the API and presents them in the view when retrieved, or shows error if
     * any.
     */
    private fun loadPosts() {
        view.showLoading()

        disposable = findPosts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { view.hideLoading() }
            .subscribeWith(object : DisposableObserver<List<Post>>() {
                override fun onComplete() {
                }

                override fun onNext(postList: List<Post>) {
                    view.updatePosts(postList)
                }

                override fun onError(errorMessage: Throwable) {
                    errorMessage.printStackTrace()
                    view.showError(errorMessage.localizedMessage)
                }

            })

    }

    private fun findPosts(): Observable<List<Post>> {
        return Observable.mergeDelayError(
            postApi.getPosts().doOnNext {
                    posts -> postsLocalRepo.addPosts(posts)
            }.subscribeOn(Schedulers.io()),

            postsLocalRepo.getAllPosts().subscribeOn(Schedulers.io()))
    }

    override fun onViewDestroyed() {
        disposable.dispose()
    }

}