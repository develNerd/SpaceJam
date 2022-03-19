package jc.iakakpo.spacejam.utils

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.exception.ApolloHttpException
import java.io.IOException
import java.net.UnknownHostException

/**
 * @author Isaac Akakpo
 * Created on 3/5/2022 4:54 PM
 */
suspend fun <T : Operation.Data> makeClientRequest(call: ApolloCall<T>): ClientResult<ApolloResponse<T>> {
  return try {
    ClientResult.Success(call.execute())
  } catch (throwable: Exception) {
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