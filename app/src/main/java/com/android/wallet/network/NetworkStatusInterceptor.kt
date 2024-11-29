package com.android.wallet.network

import android.content.Context
import com.android.wallet.network.NetworkConstants.ApiCode.SESSION_EXCEPTION
import com.android.wallet.network.NetworkConstants.ApiCode.SESSION_TIMEOUT_EXCEPTION
import com.android.wallet.utils.AppConstants
import com.android.wallet.utils.AppUtils
import com.android.wallet.utils.AppUtils.isNetwork
import com.android.wallet.utils.PrefUtils
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import java.net.SocketException
import java.net.SocketTimeoutException

class NetworkStatusInterceptor(private val context: Context, private val sharedPref: PrefUtils) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return onOnIntercept(chain)
    }

    private fun onOnIntercept(chain: Interceptor.Chain): Response = runBlocking {

        try {
            val originalRequest = chain.request()
            val modifiedRequest = originalRequest.newBuilder()
                .header("AUTHORIZATION", "BEARER " + sharedPref.getString(AppConstants.TOKEN))
                .header("Accept", "application/json").build()
            AppUtils.logI("Headers", CommonProvider.gson().toJson(modifiedRequest.headers))
            chain.proceed(modifiedRequest)
        } catch (exception: SocketTimeoutException) {
            val msg =
                if (isNetwork(context)) exception.message.toString() else NetworkConstants.ErrorMsg.NO_NETWORK
            generateDummyResponse(
                chain, msg, SESSION_TIMEOUT_EXCEPTION
            )
        } catch (exception: SocketException) {
            val msg =
                if (isNetwork(context)) exception.message.toString() else NetworkConstants.ErrorMsg.NO_NETWORK
            generateDummyResponse(
                chain, msg, SESSION_EXCEPTION
            )
        } catch (exception: Exception) {
            val msg =
                if (isNetwork(context)) exception.message.toString() else NetworkConstants.ErrorMsg.NO_NETWORK
            generateDummyResponse(chain, msg)
        }
    }

    private fun generateDummyResponse(
        chain: Interceptor.Chain,
        exception: String,
        code: Int = NetworkConstants.ApiCode.INTERNAL_ERROR_CODE
    ): Response {

        val builder: Response.Builder = Response.Builder()
        builder.code(code)
        builder.request(chain.request())
        builder.protocol(Protocol.HTTP_2)
        builder.message(exception)
        return builder.build()
    }
}