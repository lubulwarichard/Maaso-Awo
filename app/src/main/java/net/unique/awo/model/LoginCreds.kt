package net.unique.awo.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import net.unique.awo.helpers.Constants
import java.io.Serializable

@Entity(tableName = Constants.USER_TABLE_NAME)
data class LoginCreds (
    val countryCode: String,
    val countryName: String,
    var userReferenceId: String,
    var userApiKey: String,

    @PrimaryKey(autoGenerate = false)
    val phoneNumber: String
) : Serializable {
    override fun toString(): String {
        return "LoginCreds(countryCode='$countryCode', countryName='$countryName', userReferenceId='$userReferenceId', userApiKey='$userApiKey', phoneNumber='$phoneNumber')"
    }
}
