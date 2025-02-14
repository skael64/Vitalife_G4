import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field  // 🔹 Import necesario
import kotlinx.coroutines.tasks.await

suspend fun getDailySteps(context: Context): Int {
    val account = GoogleSignIn.getLastSignedInAccount(context)
    if (account != null) {
        val response = Fitness.getHistoryClient(context, account)
            .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
            .await()

        return response.dataPoints.firstOrNull()?.getValue(Field.FIELD_STEPS)?.asInt() ?: 0
    }
    return 0
}
