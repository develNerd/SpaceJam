package jc.iakakpo.spacejam.repository

import com.apollographql.apollo3.api.ApolloResponse
import jc.iakakpo.spacejam.PastLaunchesQuery
import jc.iakakpo.spacejam.utils.ClientResult
import kotlinx.coroutines.flow.Flow

/**
 * @author Isaac Akakpo
 * Created on 3/5/2022 3:13 PM
 */
interface SpaceJamRepository {
    suspend fun getLaunchesPast(limit:Int): Flow<ClientResult<ApolloResponse<PastLaunchesQuery.Data>>>


}