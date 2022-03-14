package jc.iakakpo.spacejam.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import jc.iakakpo.spacejam.PastLaunchesQuery
import jc.iakakpo.spacejam.utils.makeClientRequest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author Isaac Akakpo
 * Created on 3/5/2022 3:29 PM
 *
 *
 */
class SpaceJamRepositoryImpl @Inject constructor(private val client:ApolloClient) : SpaceJamRepository {
    override suspend fun getLaunchesPast(limit:Int) = flow {
        emit( makeClientRequest(client.query(PastLaunchesQuery(Optional.presentIfNotNull(limit)))))
    }


}