package com.zsc.core.retrofit.exception

import com.google.gson.JsonParseException
import com.zsc.core.retrofit.exception.ApiError.API_EMPTY_MSG_EXCEPTION
import com.zsc.core.retrofit.exception.ApiError.HTTP_EXCEPTION
import com.zsc.core.retrofit.exception.ApiError.NETWORK_EXCEPTION
import com.zsc.core.retrofit.exception.ApiError.PARSE_EXCEPTION
import com.zsc.core.retrofit.exception.ApiError.UNKNOWN_EXCEPTION
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.text.ParseException


/**
 * 异常拦截
 * @author zsc
 * @date 2017/11/15
 */
interface ExceptionEngine {

    /**
     * 异常拦截处理
     * @param e
     * @return
     */
    fun handleException(e: Throwable): ApiException {
        return ApiError.run {
            when (e) {
                is HttpException -> ApiException(HTTP_ERROR, HTTP_EXCEPTION)
                is JsonParseException,
                is JSONException,
                is ParseException -> ApiException(PARSE_ERROR, PARSE_EXCEPTION)
                is ConnectException -> ApiException(ApiError.NETWORK_ERROR, NETWORK_EXCEPTION)
                is ApiException -> e
                else -> ApiException(ApiError.UNKNOWN, UNKNOWN_EXCEPTION)
            }
        }
    }

    /**
     * 异常拦截处理
     * @param e
     * @return
     */
    fun handleErrorMsg(e: Throwable): String {
        return when (e) {
            is HttpException -> HTTP_EXCEPTION
            is JsonParseException,
            is JSONException,
            is ParseException -> PARSE_EXCEPTION
            is ConnectException -> NETWORK_EXCEPTION
            is ApiException -> API_EMPTY_MSG_EXCEPTION
            else -> UNKNOWN_EXCEPTION
        }
    }
}
