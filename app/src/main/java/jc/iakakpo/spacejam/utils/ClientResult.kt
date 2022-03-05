package jc.iakakpo.spacejam.utils

/**
 * @author Isaac Akakpo
 * Created on 3/5/2022 4:44 PM
 */
sealed class ClientResult<out T> {
    data class Success<out T>(val data:T) : ClientResult<T>()
    data class Error(val error:Exception) : ClientResult<Nothing>()
    data class HttpError(val code:Int,val error:Exception,val message:String?) : ClientResult<Nothing>()
    class InProgress<T> : ClientResult<Nothing>()
    class NoInternet(val error:Exception) : ClientResult<Nothing>()
}