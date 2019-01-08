package net.unique.awo.model

import android.arch.persistence.room.Entity
import net.unique.awo.utils.Constants
import java.io.Serializable

@Entity(tableName = Constants.USER_TABLE_NAME)
data class LoginCreds (
    val countryCode: String,
    val countryName: String,
    val phoneNumber: String
) : Serializable
