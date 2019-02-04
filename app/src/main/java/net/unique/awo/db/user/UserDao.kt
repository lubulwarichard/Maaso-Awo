package net.unique.awo.db.user

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import net.unique.awo.model.LoginCreds
import net.unique.awo.helpers.Constants

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserCreds(user: LoginCreds)

    @Query("SELECT * FROM " + Constants.USER_TABLE_NAME)
    fun getUserCreds(): LoginCreds

}