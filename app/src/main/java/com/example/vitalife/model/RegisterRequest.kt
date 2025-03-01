data class RegisterRequest(
    val nombres: String,
    val apellidos: String,
    val email: String,
    val password: String,
    val fechaNacimiento: String? = null,
    val peso: Double? = null,
    val talla: Double? = null,
    val genero: String? = null,
    val nivelActividad: String? = null
)