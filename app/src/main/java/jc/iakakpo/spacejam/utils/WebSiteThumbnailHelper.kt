package jc.iakakpo.spacejam.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import timber.log.Timber

/**
 * @author Isaac Akakpo
 * Created on 3/7/2022 3:09 PM
 */
object WebSiteThumbnailHelper {


    suspend fun getThumbNail(articleUrl:String,thumbnailUrl:(String) -> Unit){
        withContext(Dispatchers.IO) {
            val doc = async {  Jsoup.connect(articleUrl).get();  }
            try {
                val elements = doc.await().select("meta")
                for (element in elements){
                    //OR more specifically you can check meta property.
                    if(element.attr("property").equals("og:image",ignoreCase = true)){
                        thumbnailUrl(element.attr("content"))
                        break;
                    }
                }

            }catch (e:Exception){
                Timber.e(e)
            }
        }
    }
}