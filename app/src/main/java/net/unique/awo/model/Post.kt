package net.unique.awo.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.Nullable
import net.unique.awo.utils.Constants

/**
 * Class which provides a model for post for both retrofit and local database
 * @constructor Sets all properties of the post
 * @property userId the unique identifier of the author of the post
 * @property id the unique identifier of the post
 * @property title the title of the post
 * @property body the content of the post
 */
@Entity(tableName = Constants.POSTS_TABLE_NAME)
data class Post(

    val userId: Int,

    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val title: String,

    @Nullable
    val body: String

)
