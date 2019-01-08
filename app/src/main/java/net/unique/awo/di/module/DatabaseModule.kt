package net.unique.awo.di.module

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import net.unique.awo.db.LocalDb
import net.unique.awo.db.posts.PostsDao
import net.unique.awo.db.posts.PostsLocalRepo
import net.unique.awo.db.user.UserDao
import net.unique.awo.db.user.UserLocalRepo
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    internal fun provideLocalDb(context: Context): LocalDb {
        val localDb = Room.databaseBuilder(context, LocalDb::class.java, "FeedMeDb")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
        return localDb
    }

    @Singleton
    @Provides
    internal fun providePostsDao(db: LocalDb): PostsDao {
        return db.postsDao()
    }

    @Singleton
    @Provides
    internal fun provideUserDao(db: LocalDb): UserDao {
        return db.userDao()
    }

    @Singleton
    @Provides
    internal fun providePostsLocalRepo(postsDao: PostsDao): PostsLocalRepo {
        return PostsLocalRepo(postsDao)
    }

    @Singleton
    @Provides
    internal fun provideUserLocalRepo(userDao: UserDao): UserLocalRepo {
        return UserLocalRepo(userDao)
    }

}