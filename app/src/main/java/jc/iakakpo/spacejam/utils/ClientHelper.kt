package jc.iakakpo.spacejam.utils

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.exception.ApolloHttpException
import jc.iakakpo.spacejam.PastLaunchesQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.internal.wait
import java.io.IOException
import java.lang.Exception
import java.net.UnknownHostException

/**
 * @author Isaac Akakpo
 * Created on 3/5/2022 4:54 PM
 */
suspend fun <T:Operation.Data> makeClientRequest(call: ApolloCall<T>): ClientResult<ApolloResponse<T>> {
    return try {
        ClientResult.Success(call.execute())
    }
    catch (throwable: Exception) {
            when (throwable) {
                is ApolloHttpException ->
                    ClientResult.HttpError(
                        throwable.statusCode,
                        throwable,
                        throwable.message
                    )

                is IOException -> ClientResult.NoInternet(throwable)
                is UnknownHostException -> ClientResult.NoInternet(throwable)
                else -> ClientResult.Error(throwable)
            }
        }

}