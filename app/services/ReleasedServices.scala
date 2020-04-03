package services

import javax.inject.{Inject, Singleton}
import models.ReleasedMovieWithID

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.play.json._
import collection._
import models.JsonFormats._
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent}
import reactivemongo.api.Cursor
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.bson.BSONObjectID

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}


class ReleasedServices @Inject()(
                                  val reactiveMongoApi: ReactiveMongoApi
                                ) extends ReactiveMongoComponents {

  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("releasedfilms"))

  def getMovieByID(id: String): Future[List[ReleasedMovieWithID]] =  {
    val cursor: Future[Cursor[ReleasedMovieWithID]] = collection.map {
      _.find(Json.obj("_id" -> id)).
        cursor[ReleasedMovieWithID]()
    }

    val moviesList: Future[List[ReleasedMovieWithID]] =
      cursor.flatMap(
        _.collect[List](
          -1,
          Cursor.FailOnError[List[ReleasedMovieWithID]]()
        )
      )

    moviesList
  }

//  def updateMovieRating(id: String, rating: Rating) = {
//    getMovieByID(id).map{_ =>
//      collection.map(_.update(false).one(
//        Json.obj(
//          "rating" -> rating
//        ),
//        rating
//      ))
//    }
//  }

  def getMovies = {
    val cursor: Future[Cursor[ReleasedMovieWithID]] = collection.map {
      _.find(Json.obj())
        .cursor[ReleasedMovieWithID]()
    }
    cursor.flatMap(
      _.collect[List] (
        -1,
        Cursor.FailOnError[List[ReleasedMovieWithID]]()
      )
    )
  }

}
