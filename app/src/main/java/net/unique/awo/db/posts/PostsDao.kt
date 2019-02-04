package net.unique.awo.db.posts

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import net.unique.awo.model.Post
import net.unique.awo.helpers.Constants

@Dao
interface PostsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<Post>)

    @Query("SELECT * FROM " + Constants.POSTS_TABLE_NAME)
    fun getAll(): List<Post>

}