package jc.iakakpo.spacejam.utils

import com.apollographql.apollo3.exception.ApolloHttpException
import java.io.IOException

/**
 * @author Isaac Akakpo
 * Created on 3/5/2022 4:54 PM
 */
suspend fun <T> makeClientRequest(call: suspend () -> T): ClientResult<T> {
    return try {
        ClientResult.Success(call.invoke())
    } catch (throwable: Exception) {
        return when (throwable) {
            is ApolloHttpException -> ClientResult.HttpError(throwable.statusCode, throwable,throwable.message)
            is IOException -> ClientResult.NoInternet(throwable)
            else -> ClientResult.Error(throwable)
        }
    }
}