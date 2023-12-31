/** MovieRepo.scala Practicing Slick
  *
  * @author
  *   Gerardo Jaramillo
  * @version 0.0.1
  */

package example

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import scala.concurrent.Future
import scala.concurrent.ExecutionContext

sealed trait MovieApi[F[_]] {
  def create(movie: Movie): F[Either[Throwable, Option[Long]]]
  def create(movies: Seq[Movie]): F[Either[Throwable, Option[Int]]]
  def list(): F[Either[Throwable, Seq[Movie]]]
  def merge(movie: Movie): F[Int]
  def remove(movieid: Option[Long]): F[Int]
  def findByName(name: Option[String]): F[Option[Movie]]
}

class MovieRepo(val dbConfig: DatabaseConfig[JdbcProfile])(implicit
    ec: ExecutionContext
) extends MovieApi[Future] {

  import dbConfig._
  import profile.api._

  protected class MovieTable(tag: Tag) extends Table[Movie](tag, "movie") {
    def movieid = column[Option[Long]]("movieid", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def * = (movieid, name) <> ((Movie.apply _).tupled, Movie.unapply)
  }

  protected val movies = TableQuery[MovieTable]

  def list() = db
    .run {
      for {
        result <- movies.sortBy(_.name).result
      } yield Right(result)
    }
    .recover { case e: Exception =>
      Left(e)
    }

  def remove(movieid: Option[Long]) = db.run {
    movies.filter(_.movieid === movieid).delete
  }

  def merge(movie: Movie) = db.run {
    ???
  }

  def create(movie: Movie) = db
    .run {
      for {
        result <- movies returning movies.map(_.movieid) += movie
      } yield Right(result)
    }
    .recover { case e: Exception =>
      Left(e)
    }

  def create(newMovies: Seq[Movie]) = db
    .run {
      for {
        result <- movies ++= newMovies
      } yield Right(result)
    }
    .recover { case e: Exception =>
      Left(e)
    }

  def findByName(name: Option[String]) = db.run {
    movies.filter(_.name === name).result.headOption
  }

}
