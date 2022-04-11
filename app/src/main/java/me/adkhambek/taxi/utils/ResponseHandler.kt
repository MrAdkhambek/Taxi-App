package me.adkhambek.taxi.utils

import me.adkhambek.taxi.R
import me.adkhambek.taxi.datasource.models.BaseResponse
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.CancellationException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ResponseHandler @Inject constructor() {

    suspend fun <T> proceed(
        body: suspend () -> Response<BaseResponse<T>>
    ): Try<T> {
        return try {
            val response: Response<BaseResponse<T>> = body()
            convertResourceUI(response)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Timber.d(e.toString())
            handleException(e)
        }
    }


    private fun <T> convertResourceUI(
        response: Response<BaseResponse<T>>,
    ): Try<T> {
        val body: BaseResponse<T>? = response.body()
        val data: T? = body?.data

        return when {
            data != null -> Try.Success(data)
            else -> {
                val text: Text = handleError(response.errorBody())
                Try.Failure(FailureMessage.ServerException(text))
            }
        }
    }

    private fun handleError(
        body: ResponseBody?,
    ): Text = try {
        val byteArray: String? = body?.bytes()?.let(::String)
        val jsonObject = JSONObject(requireNotNull(byteArray))
        val message: String = jsonObject.getString("display_message")
        Text.PlainText(message)
    } catch (e: JSONException) {
        Text.ResText(R.string.unknown_server_error)
    }

    @Suppress("UNUSED_EXPRESSION")
    private fun handleException(
        throwable: Throwable,
    ): Try<Nothing> {
        val failureMessage = when (throwable) {
            else -> {
                val text = Text.ResText(R.string.unknown_server_error)
                FailureMessage.UnknownException(text)
            }
        }
        return Try.Failure(failureMessage)
    }
}