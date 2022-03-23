package jc.iakakpo.spacejam.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Isaac Akakpo
 * Created on 3/7/2022 3:09 PM
 */
@Singleton
class WebSiteThumbnailHelper @Inject constructor(){


    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + parentJob)

    fun getThumbNail(articleUrl: String) : Deferred<String?> = coroutineScope.async {
        return@async try {
            Timber.e("Called")
            var m :String = ""
            val doc =  Jsoup.connect(articleUrl).get();

            val elements = doc.select("meta")
            for (element in elements) {
                // OR more specifically you can check meta property.
                if (element.attr("property").equals("og:image", ignoreCase = true)) {
                    m = element.attr("content")
                    break
                }
            }
            m
        }catch (e:IOException){
            Timber.e(e)
            null
        }catch (e:Exception){
            Timber.e(e)
            null
        }
    }


    fun getThumbNailFlow(articleUrl: String): Flow<String?> = flow {
        try {
            val doc =  try {
                Jsoup.connect(articleUrl).get()
            } catch (e: IOException) {
                null
            }
            val elements = doc?.select("meta") ?: return@flow
            for (element in elements) {
                // OR more specifically you can check meta property.
                if (element.attr("property").equals("og:image", ignoreCase = true)) {
                    emit(element.attr("content"))
                    break
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
            emit(null)
        }
    }.flowOn(Dispatchers.IO)
}
