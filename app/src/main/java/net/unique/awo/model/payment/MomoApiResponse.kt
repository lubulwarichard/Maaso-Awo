package net.unique.awo.model.payment

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MomoApiResponse(
    val apiKey: String
): Serializable {

    data class TokenResponse (
        val access_token: String,
        val token_type: String,
        val expires_in: String,
        val error: String
    ) {
        override fun toString(): String {
            return "TokenResponse(access_token='$access_token', token_type='$token_type', expires_in='$expires_in', error='$error')"
        }
    }

}