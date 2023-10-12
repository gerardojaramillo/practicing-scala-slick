/** PracticingScalaSlick.scala Practicing Slick
  *
  * @author
  *   Gerardo Jaramillo
  * @version 0.0.1
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
    val dbConfig = DatabaseConfig.forConfig[JdbcProfile]("movie-db")
    val movieRepo = new MovieRepo(dbConfig)

    movieRepo.create(Movie(None, "Justice League"))
    movieRepo.create(Movie(None, "Man of Steel"))
    movieRepo.create(Movie(None, "Batman v Superman"))

    val list = Await.ready(movieRepo.list(), Duration.Inf)
    list.foreach(println)

  }

}
