package services

import javax.inject.{Inject, Singleton}
import models.MovieWithID
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.play.json._
import collection._
import models.JsonFormats._
import play.api.libs.json.Json
import reactivemongo.api.Cursor
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}

class ReleasedServices @Inject()(
                                  val reactiveMongoApi: ReactiveMongoApi
                                ) extends ReactiveMongoComponents {

  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("releasedfilms"))

  def getMovies = {
    val cursor: Future[Cursor[MovieWithID]] = collection.map {
      _.find(Json.obj())
        .cursor[MovieWithID]()
    }
    cursor.flatMap(
      _.collect[List] (
        -1,
        Cursor.FailOnError[List[MovieWithID]]()
      )
    )
  }

  //  def getFilms = Action.async {
  //    val cursor: Future[Cursor[Film]] = collection.map {
  //      _.find(filter)
  //        .cursor[Film]()
  //    }
  //    cursor.flatMap(
  //      _.collect[List](
  //        -1,
  //        Cursor.FailOnError[List[Film]]()
  //      ))
  //  }

}
