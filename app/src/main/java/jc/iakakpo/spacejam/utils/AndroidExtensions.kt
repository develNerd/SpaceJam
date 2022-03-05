package jc.iakakpo.spacejam.utils

import android.content.Context
import android.content.Intent

/**
 * @author Isaac Akakpo
 * Created on 2/28/2022 9:19 AM
 */



/*
* Extended Generic function to easily launch an
* intent to another activity. we use reified with inline
* to easily cast the specified generic type as a class.
*
* */
inline fun <reified T> Context.gotoActivity(
    clearStack: Boolean = false,
    intentPacking: ((Intent) -> Unit) = {},
) {
    val intent = Intent(this, T::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

    if (clearStack) {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    intentPacking(intent)
    intentPacking.invoke(intent)
    startActivity(intent)
}
