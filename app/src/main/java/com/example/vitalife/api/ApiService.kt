import com.example.vitalife.model.LoginRequest
import com.example.vitalife.model.LoginResponse
import com.example.vitalife.model.RegisterResponse
import com.example.vitalife.model.UserProfileResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    // Endpoint para registro
    @POST("api/auth/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    // Endpoint para login
    @POST("api/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    // Endpoint para obtener el perfil de usuario
    @GET("api/user/profile")
    fun getUserProfile(@Query("userId") userId: Int): Call<UserProfileResponse>
}