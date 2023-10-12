/** PracticingScalaSlick.scala
  */

package example

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import scala.concurrent.Await
import scala.concurrent.duration.Duration

import scala.concurrent.ExecutionContext.Implicits.global

object PracticingScalaSlick {

  def main(args: Array[String]): Unit = {
    println("Practicing Scala Slick !")
    val dbConfig = DatabaseConfig.forConfig[JdbcProfile]("")
    val movieRepo = new MovieRepo(dbConfig)

    val list = Await.ready(movieRepo.list(), Duration.Inf)
    list.foreach(println)

  }

}
