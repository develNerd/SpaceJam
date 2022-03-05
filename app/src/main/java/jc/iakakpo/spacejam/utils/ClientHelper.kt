package jc.iakakpo.spacejam.utils

import com.apollographql.apollo3.exception.ApolloHttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception

/**
 * @author Isaac Akakpo
 * Created on 3/5/2022 4:54 PM
 */
suspend fun <T> makeClientRequest(call: suspend () -> T): Flow<ClientResult<T>> {
    return flow{
        try {
            emit(ClientResult.InProgress)
            emit(ClientResult.Success(call.invoke()))
        }catch (throwable:Exception){
            when (throwable) {
                is ApolloHttpException -> emit(ClientResult.HttpError(throwable.statusCode, throwable,throwable.message))
                is IOException -> emit(ClientResult.NoInternet(throwable))
                else -> emit(ClientResult.Error(throwable))
            }
        }
    }
}