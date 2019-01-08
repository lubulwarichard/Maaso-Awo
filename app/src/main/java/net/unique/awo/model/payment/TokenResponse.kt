package net.unique.awo.model.payment

data class TokenResponse (
    val access_token: String,
    val token_type: String,
    val expires_in: String
)
