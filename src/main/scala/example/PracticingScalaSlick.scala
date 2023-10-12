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

    val dbConfig = DatabaseConfig.forConfig[JdbcProfile]("movie-db")
    val movieRepo = new MovieRepo(dbConfig)

    val movies = Seq(
      Movie(None, "Justice League"),
      Movie(None, "Man of Steel"),
      Movie(None, "Avengers"),
      Movie(None, "Batman"),
      Movie(None, "Constantine"),
      Movie(None, "Watchmen"),
      Movie(None, "Green Lantern"),
      Movie(None, "Suicide Squad"),
      Movie(None, "Aquaman"),
      Movie(None, "Shazam"),
      Movie(None, "Joker"),
      Movie(None, "The Flash"),
      Movie(None, "Blue Beetle"),
      Movie(None, "Black Adam"),
      Movie(None, "Batman v Superman"),
      Movie(None, "Avengers End Game"),
      Movie(None, "Wonder Woman"),
      Movie(None, "Captain America"),
      Movie(None, "Spider Man"),
      Movie(None, "Iron Man"),
      Movie(None, "Black Widow"),
      Movie(None, "Doctor Strange"),
      Movie(None, "Thor"),
      Movie(None, "Ant Man"),
      Movie(None, "Black Panter"),
      Movie(None, "Captain Marvel"),
      Movie(None, "Guardians of Galaxy")
    )

    /** inserting a seq of movies */

    val created = Await.result(
      movieRepo
        .create(movies),
      Duration.Inf
    )

    println(created)

    /** listing all movies */
    val list = Await.result(movieRepo.list(), Duration.Inf)

    list fold (println, fb => { fb.foreach(println) })

    /** remove */

  }

}
