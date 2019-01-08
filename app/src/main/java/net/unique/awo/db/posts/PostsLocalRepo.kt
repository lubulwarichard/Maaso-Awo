package net.unique.awo.db.posts

import io.reactivex.Observable
import net.unique.awo.model.Post
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PostsLocalRepo @Inject constructor(var postsDao: PostsDao) {


    fun getAllPosts(): Observable<List<Post>> {
        return Observable.fromCallable { postsDao.getAll() }
    }

    fun addPosts(posts: List<Post>) {
        postsDao.insertAll(posts)
    }

}