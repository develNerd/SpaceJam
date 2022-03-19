package jc.iakakpo.spacejam.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloHttpException
import jc.iakakpo.spacejam.PastLaunchesQuery
import jc.iakakpo.spacejam.models.Links
import jc.iakakpo.spacejam.models.PastLaunchesData
import jc.iakakpo.spacejam.models.Ship
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * @author Isaac Akakpo
 * Created on 3/7/2022 8:08 PM
 */
class PaginatedPastLaunchesRepository @Inject constructor(private val client: ApolloClient) :
  PagingSource<Int, PastLaunchesData>() {
  override fun getRefreshKey(state: PagingState<Int, PastLaunchesData>): Int? {
    // Try to find the page key of the closest page to anchorPosition, from
    // either the prevKey or the nextKey, but you need to handle nullability
    // here:
    //  * prevKey == null -> anchorPage is the first page.
    //  * nextKey == null -> anchorPage is the last page.
    //  * both prevKey and nextKey null -> anchorPage is the initial page, so
    //    just return null.
    return state.anchorPosition?.let { anchorPosition ->
      val anchorPage = state.closestPageToPosition(anchorPosition)
      anchorPage?.prevKey?.plus(15) ?: anchorPage?.nextKey?.minus(15)
    }
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PastLaunchesData> {
    return try {
      val offSet = params.key?.plus(15) ?: 0
      val query = client.query(
        PastLaunchesQuery(
          Optional.presentIfNotNull(15),
          Optional.presentIfNotNull(offSet)
        )
      ).execute()
      val pastLaunches = query.data?.launchesPast?.map {
        PastLaunchesData(
          it?.launch_year, it?.mission_name, it?.details,
          Links(
            it?.links?.flickr_images ?: emptyList<String>(),
            it?.links?.article_link ?: emptyList<String>(),
            it?.links?.video_link
          ), it?.ships?.map { ship -> Ship(ship?.name, ship?.model) }
        )
      } ?: emptyList()

      if (pastLaunches.isNotEmpty())
        pastLaunches.let {
          LoadResult.Page(
            data = it,
            prevKey = null, // Only paging forward.
            nextKey = offSet
          )
        }
      else
        LoadResult.Page(data = emptyList(), null, null)
    } catch (e: Exception) {
      Timber.e(e)
      LoadResult.Error(e)
    } catch (e: IOException) {
      Timber.e(e)
      LoadResult.Error(e)
    } catch (e: ApolloHttpException) {
      Timber.e(e)
      LoadResult.Error(e)
    }
  }


}