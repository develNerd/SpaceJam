package jc.iakakpo.spacejam.models

import jc.iakakpo.spacejam.MissionsQuery

/**
 * @author Isaac Akakpo
 * Created on 3/20/2022 8:47 PM
 */
data class MissionLocal(
  var id: String?,
  var name: String?,
  var description: String?,
  var website: String?,
  var twitter: String?,
  var manufacturers: List<String?>?,
  var wikipedia: String?,
  var url:String? = ""
)

fun MissionsQuery.Mission.toMissionLocal(url: String?):MissionLocal{
  return MissionLocal(
    id = id,
    name = name,
    description = description,
    website = website,
    twitter = twitter,
    manufacturers = manufacturers,
    wikipedia = wikipedia,
    url = url  )
}