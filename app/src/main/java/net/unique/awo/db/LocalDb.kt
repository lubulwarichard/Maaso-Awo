package net.unique.awo.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import net.unique.awo.db.posts.PostsDao
import net.unique.awo.db.user.UserDao
import net.unique.awo.model.LoginCreds
import net.unique.awo.model.Post

@Database(entities = [Post::class, LoginCreds::class], version = 2, exportSchema = false)
abstract class LocalDb : RoomDatabase() {

    abstract fun postsDao(): PostsDao

    abstract fun userDao(): UserDao

}