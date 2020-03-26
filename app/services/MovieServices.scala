package services

import javax.inject.Inject
import models.Movie
import models.JsonFormats._
import play.api.libs.json.Json
import reactivemongo.play.json._

import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.Cursor
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class MovieServices @Inject()(
                             val reactiveMongoApi: ReactiveMongoApi
                             ) extends ReactiveMongoComponents {
  def collection = reactiveMongoApi.database.map(_.collection[JSONCollection]("releasedfilms"))

  def getMovies: Future[List[Movie]] = {
    val cursor = collection.map {
      _.find(Json.obj())
        .cursor[Movie]()
    }
    cursor.flatMap(
      _.collect[List] (
        -1,
        Cursor.FailOnError[List[Movie]]()
      )
    )
  }
}
