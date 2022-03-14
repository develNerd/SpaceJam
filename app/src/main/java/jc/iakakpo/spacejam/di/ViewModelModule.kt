package jc.iakakpo.spacejam.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import jc.iakakpo.spacejam.repository.SpaceJamRepository
import jc.iakakpo.spacejam.repository.SpaceJamRepositoryImpl

/**
 * @author Isaac Akakpo
 * Created on 3/5/2022 4:01 PM
 *
 * ViewModel Module Scoped to only ViewModels for
 * Constructor Injection
 *
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {


    /**
     * Bind spaceJam repository with it's implementation
     * using [@Binds] annotation from Dagger-Hilt
     *
     *
     * @param spaceJamRepository -> [SpaceJamRepositoryImpl]
     * @return [SpaceJamRepository]
     */
    @Binds
    abstract fun bindSpaceJamRepository(
        spaceJamRepository: SpaceJamRepositoryImpl
    ): SpaceJamRepository

}