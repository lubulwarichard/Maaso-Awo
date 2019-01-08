package net.unique.awo.remote

import io.reactivex.Observable
import net.unique.awo.model.Post
import net.unique.awo.model.payment.Balance
import net.unique.awo.model.payment.RequestPayment
import net.unique.awo.model.payment.TokenResponse
import net.unique.awo.model.payment.UserActiveStatus
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

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
     * request a payment from a consumer (Payer).
     */
    @POST("/requesttopay")
    fun requestPayment(
        @Body requestPayment: RequestPayment
    ): Observable<Response<String>>

    /**
     *  Validate status of initiated transaction.
     */
    @GET("/requesttopay/<resourceId>")
    fun getRequestPaymentStatus(): Observable<Response<RequestPayment>>

    /**
     *  Create an access token which can then be used to authorize and authenticate towards the other end-points
     */
    @POST("/token")
    fun getToken(): Observable<Response<TokenResponse>>

    @POST("/account/balance")
    fun getAccountBalance(): Observable<Response<Balance>>

    /**
     *  Check if an account holder is registered and active in the system.
     */
    @GET("/accountholder/{accountHolderIdType}/{accountHolderId}/active")
    fun checkIfActive(): Observable<Response<UserActiveStatus>>

}