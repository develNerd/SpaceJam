package jc.iakakpo.spacejam.utils

/**
 * @author Isaac Akakpo
 * Created on 3/7/2022 11:18 AM
 */
sealed class UIState<out T> {
  object Loading : UIState<Nothing>()
  data class DataLoaded<T>(val response: T) : UIState<T>()
  class NoInternet(val error: Exception) : UIState<Nothing>()
  class SomethingWentWrong(val error: Exception) : UIState<Nothing>()
}