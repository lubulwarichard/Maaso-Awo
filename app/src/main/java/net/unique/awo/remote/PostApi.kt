package net.unique.awo.remote

import io.reactivex.Observable
import net.unique.awo.BuildConfig
import net.unique.awo.model.Post
import net.unique.awo.model.payment.*
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

/**
 * The interface which provides methods to get result of webservices
 */
interface PostApi {

    /**
     * Get the list of the posts from the API
     */
    @GET("/posts")
    fun getPosts(): Observable<List<Post>>

    /**
     * Create a user
     */
    @Headers(
        "Ocp-Apim-Subscription-Key: ${BuildConfig.MomoApiSubscriptionKey}",
        "Content-Type: application/json"
    )
    @POST("/v1_0/apiuser")
    fun createUser(
        @Header("X-Reference-Id") referenceId: String,
        @Body params: RequestBody
    ): Observable<Response<Void>>

    /**
     * Get API Key
     */
    @Headers(
        "Ocp-Apim-Subscription-Key: ${BuildConfig.MomoApiSubscriptionKey}"
    )
    @POST("/v1_0/apiuser/{referenceId}/apikey")
    fun getApiKey(
        @Path("referenceId") referenceId: String
    ): Observable<Response<MomoApiResponse>>

    /**
     * request a payment from a consumer (Payer).
     */
    @Headers(
        "X-Reference-Id: application/json",
        "X-Target-Environment: Your-App-Name",
        "Ocp-Apim-Subscription-Key: max-age=640000",
        "Content-Type: application/json"
    )
    @POST("/collection/v1_0/requesttopay")
    fun requestPayment(
        @Body requestPayment: RequestPayment
    ): Observable<Response<String>>

    /**
     *  Validate status of initiated transaction.
     */
    @GET("/collection/v1_0/requesttopay/<resourceId>")
    fun getRequestPaymentStatus(): Observable<Response<RequestPayment>>

    /**
     *  Get an access token which can then be used to authorize and authenticate towards the other end-points
     */
    @Headers(
        "Ocp-Apim-Subscription-Key: ${BuildConfig.MomoApiSubscriptionKey}"
    )
    @POST("/collection/token")
    fun getToken(
        @Header("Authorization") authorization: String
    ): Observable<Response<MomoApiResponse.TokenResponse>>

    @POST("/collection/v1_0/account/balance")
    fun getAccountBalance(): Observable<Response<Balance>>

    /**
     *  Check if an account holder is registered and active in the system.
     */
    @GET("/collection/v1_0/accountholder/{accountHolderIdType}/{accountHolderId}/active")
    fun checkIfActive(): Observable<Response<UserActiveStatus>>

}


/**
 * Instructions
 * 1. First create a user using a UUID generator
 * 2. Use the UUID to get the api key
 * 3. Use the api key as password and uuid as user name under authorization when getting a token
 * 4. use token in all other requests
 *
 * To retry a request
 * Do step 1 then add it to a request
 */