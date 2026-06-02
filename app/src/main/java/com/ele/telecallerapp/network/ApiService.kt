
package com.ele.telecallerapp.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import com.ele.telecallerapp.data.model.PrasadamResponse
interface ApiService {

    /* ---------------- AUTH ---------------- */

    @POST("api/auth/register")
    suspend fun register(@Body body: RegisterRequest): ApiResponse

    @POST("api/auth/login")
    suspend fun login(@Body body: LoginRequest): ApiResponse

    @POST("api/auth/verify-otp")
    suspend fun verifyOtp(@Body body: OtpVerifyRequest): ApiResponse

    @POST("api/auth/forgot-password")
    suspend fun forgotPassword(@Body body: ForgotPasswordRequest): ApiResponse

    @POST("api/auth/reset-password")
    suspend fun resetPassword(@Body body: ResetPasswordRequest): ApiResponse


    /* ---------------- PROFILE ---------------- */

    @GET("api/profile")
    suspend fun getProfile(): ProfileResponse

    @Multipart
    @PUT("api/profile")
    suspend fun updateProfile(
        @Part("name") name: RequestBody,
        @Part("mobile") mobile: RequestBody,
        @Part image: MultipartBody.Part?
    ): UpdateProfileResponse


    /* ---------------- LEADS ---------------- */

    @GET("api/leads")
    suspend fun getLeads(
        @Query("status") status: String? = null,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("search") search: String? = null
    ): LeadListResponse

    @GET("api/leads/{id}")
    suspend fun getLeadById(@Path("id") id: String): LeadResponse

    @POST("api/leads")
    suspend fun createLead(@Body body: CreateLeadRequest): LeadResponse

    @PUT("api/leads/{id}")
    suspend fun updateLead(
        @Path("id") id: String,
        @Body body: UpdateLeadRequest
    ): LeadResponse

    @Multipart
    @POST("api/leads")
    suspend fun createLeadWithImage(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody?,
        @Part("phone") phone: RequestBody,
        @Part("status") status: RequestBody,
        @Part("company") company: RequestBody?,
        @Part("campaign") campaign: RequestBody?,
        @Part("source") source: RequestBody?,
        @Part("priority") priority: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part image: MultipartBody.Part?
    ): LeadResponse

    @Multipart
    @PUT("api/leads/{id}")
    suspend fun updateLeadWithImage(
        @Path("id") id: String,
        @Part("name") name: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("phone") phone: RequestBody?,
        @Part("status") status: RequestBody?,
        @Part("company") company: RequestBody?,
        @Part("campaign") campaign: RequestBody?,
        @Part("source") source: RequestBody?,
        @Part("priority") priority: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part image: MultipartBody.Part?
    ): LeadResponse

    @DELETE("api/leads/{id}")
    suspend fun deleteLead(@Path("id") id: String): Map<String, Any>


    /* ---------------- ACTIVITIES ---------------- */

    @GET("api/leads/{id}/activities")
    suspend fun getLeadActivities(@Path("id") id: String): ActivityListResponse

    @POST("api/leads/{id}/activities")
    suspend fun createActivity(
        @Path("id") id: String,
        @Body body: CreateActivityRequest
    ): ActivityResponse


    /* ---------------- DASHBOARD ---------------- */

    @GET("api/dashboard/stats")
    suspend fun getDashboardStats(): DashboardStatsResponse

    @GET("api/dashboard/activities")
    suspend fun getDashboardActivities(): ActivityListResponse


    /* ---------------- CAMPAIGNS ---------------- */

    @GET("api/campaigns")
    suspend fun getCampaigns(): List<CampaignDto>

    @POST("api/campaigns")
    suspend fun createCampaign(
        @Body body: Map<String, @JvmSuppressWildcards Any>
    ): CampaignDto

    @DELETE("api/campaigns/{id}")
    suspend fun deleteCampaign(@Path("id") id: String)


    /* ---------------- MESSAGE TEMPLATE ---------------- */

    @GET("api/message-templates")
    suspend fun getMessageTemplates(): List<MessageTemplateDto>

    @PUT("api/message-templates/{id}")
    suspend fun updateMessageTemplate(
        @Path("id") id: String,
        @Body body: MessageTemplateDto
    ): MessageTemplateDto


    /* ---------------- DONATIONS ---------------- */

    @POST("api/donations")
    suspend fun createDonation(
        @Body donation: DonationDto
    ): retrofit2.Response<DonationDto>

    @GET("api/donors/my")
    suspend fun getMyDonors(): List<DonorDto>

    @PUT("api/donors/{mobile}")
    suspend fun updateDonor(
        @Path("mobile") mobile: String,
        @Body donor: DonorDto
    ): retrofit2.Response<Unit>

    @GET("api/donations/donor/{mobile}")
    suspend fun getDonationsByDonor(
        @Path("mobile") mobile: String
    ): List<DonationDto>


    /* ---------------- PRASADAM ---------------- */

    @POST("api/prasadam")
    suspend fun createPrasadam(
        @Body body: PrasadamRequest
    ): retrofit2.Response<PrasadamResponse>


    /* ---------------- SETTINGS ---------------- */

    @GET("api/settings")
    suspend fun getSettings(): UserSettings

    @POST("api/settings/update")
    suspend fun updateSettings(@Body settings: UserSettings)

    @POST("api/settings/report")
    suspend fun reportIssue(@Body body: Map<String, String>)


    /* ---------------- USERS / NOTIFICATIONS ---------------- */

    @POST("api/users/save-fcm-token")
    suspend fun saveFcmToken(
        @Body body: Map<String, String>
    )

    @POST("api/users/update-notification-settings")
    suspend fun updateNotificationSettings(
        @Body body: Map<String, Boolean>
    )
}