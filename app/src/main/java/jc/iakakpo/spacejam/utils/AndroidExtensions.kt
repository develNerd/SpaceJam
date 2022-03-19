package jc.iakakpo.spacejam.utils

import android.content.Context
import android.content.Intent
import jc.iakakpo.spacejam.CompanyDetails
import jc.iakakpo.spacejam.CompanyQuery
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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

val randoms = listOf<String>(
    "https://cdn.pixabay.com/photo/2018/08/15/13/10/galaxy-3608029_1280.jpg",
    "https://cdn.pixabay.com/photo/2016/10/20/18/35/earth-1756274_1280.jpg",
    "https://cdn.pixabay.com/photo/2016/03/18/15/02/ufo-1265186_1280.jpg"
)

fun Any.toJsonString(): String = Json.encodeToString(this)

fun CompanyQuery.Company.parseToCompanyDetails(): CompanyDetails {
    return CompanyDetails(
        ceo = this.ceo ?: "",
        coo = this.coo ?: "",
        founded = this.founded.toString() ?: "",
        name = this.name ?: "",
        headquarers = this.headquarters,
        links = this.links,
        employees = this.employees?.toLong(),
        this.summary ?: "",
        this.valuation.toString() ?: "",
        this.founder
    )
}

fun CompanyDetails.asMap():Map<String,Any?>{
    val map = mutableMapOf<String,Any?>()
    map["CEO"] = ceo
    map["COO"] = coo
    map["Head Quarters"] = headquarers
    map["Founded"] = founded
    map["Founder"] = founder
    map["Summary"] = summary
    map["employees"] = employees
    map["employees"] = employees
    map["links"] = links
    return map
}

inline fun <reified T> Any.castAs(): T? = this as? T

