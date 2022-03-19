package jc.iakakpo.spacejam.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import jc.iakakpo.spacejam.BuildConfig
import timber.log.Timber

/**
 * @author Isaac Akakpo
 * Created on 3/5/2022 2:07 PM
 *
 * SpaceJam Application Class.
 */
@HiltAndroidApp
class SpaceJamApp : Application() {
  init {
    initLogger()
  }

  private fun initLogger() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }
}


