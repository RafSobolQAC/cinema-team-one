package services

import javax.inject.{Inject, Singleton}
import models.ReleasedMovieWithID

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import javax.inject.Inject
import models.{Movie, MovieWithID, Rating}
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

  def updateMovieRating(id: String, rating: Rating) = {
    collection.flatMap(_.update(false).one(
      Json.obj(
        {
          "_id" -> BSONObjectID.parse(id).get
        }
      ),
        "_rating" -> rating
    ))
  }

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
