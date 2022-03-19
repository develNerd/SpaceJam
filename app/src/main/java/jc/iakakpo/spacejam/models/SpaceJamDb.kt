package jc.iakakpo.spacejam.models

import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.asJdbcDriver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import jc.iakakpo.spacejam.CompanyDetails
import jc.iakakpo.spacejam.CompanyQuery
import jc.iakakpo.spacejam.SpaceJamDatabase
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


/**
 * @author Isaac Akakpo
 * Created on 3/17/2022 11:04 PM
 */
class SpaceJamDb() {

  private val headQuartersAdapter = object : ColumnAdapter<CompanyQuery.Headquarters, String> {
    override fun decode(databaseValue: String): CompanyQuery.Headquarters {
      return Json.decodeFromString(databaseValue) as CompanyQuery.Headquarters
    }

    override fun encode(value: CompanyQuery.Headquarters): String {
      return Json.encodeToString(value)
    }

  }

  private val linksAdapter = object : ColumnAdapter<CompanyQuery.Links, String> {
    override fun decode(databaseValue: String): CompanyQuery.Links {
      return Json.decodeFromString(databaseValue) as CompanyQuery.Links
    }

    override fun encode(value: CompanyQuery.Links): String {
      return Json.encodeToString(value)
    }

  }

  /**
   * Setup a driver for the spaceJamDatabase
   * */
  private val driver = HikariDataSource().asJdbcDriver()

  fun spaceJamDb() = SpaceJamDatabase(driver = driver, CompanyDetailsAdapter = CompanyDetails.Adapter(headQuartersAdapter,linksAdapter))


}