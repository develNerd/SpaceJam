package jc.iakakpo.spacejam.models

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.google.gson.Gson
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.asJdbcDriver
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
class SpaceJamDb()  {

  private val headQuartersAdapter = object : ColumnAdapter<CompanyQuery.Headquarters, String> {
    override fun decode(databaseValue: String): CompanyQuery.Headquarters {
      return Gson().fromJson(databaseValue,CompanyQuery.Headquarters::class.java)
    }

    override fun encode(value: CompanyQuery.Headquarters): String {
      return Gson().toJson(value)
    }

  }

  private val linksAdapter = object : ColumnAdapter<CompanyQuery.Links, String> {
    override fun decode(databaseValue: String): CompanyQuery.Links {
      return  Gson().fromJson(databaseValue,CompanyQuery.Links::class.java)
    }

    override fun encode(value: CompanyQuery.Links): String {
      return Gson().toJson(value)
    }

  }



  fun spaceJamDb(context: Context):SpaceJamDatabase {
    val driver = AndroidSqliteDriver(SpaceJamDatabase.Schema, context, "SpaceJamDatabase.db")

   return SpaceJamDatabase(driver = driver, CompanyDetailsAdapter = CompanyDetails.Adapter(headQuartersAdapter,linksAdapter))
  }




}