package jc.iakakpo.spacejam.models

/**
 * @author Isaac Akakpo
 * Created on 3/7/2022 10:26 PM
 */
data class PastLaunchesData(
  val launch_year: String?, val missionName: String?, val details: String?, val links: Links,
  val ships: List<Any>?
)

