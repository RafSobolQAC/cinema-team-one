package services

import javax.inject.Inject
import models.{Movie, MovieWithID}
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.play.json._
import collection._
import models.JsonFormats._
import play.api.libs.json.Json
import reactivemongo.api.Cursor
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class UpcomingServices @Inject()(
                                  val reactiveMongoApi: ReactiveMongoApi
                                ) extends ReactiveMongoComponents {

    def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("upcomingfilms"))

    def getMovies = {
      val cursor: Future[Cursor[MovieWithID]] = collection.map {
        _.find(Json.obj())
          .cursor[MovieWithID]()
      }
      cursor.flatMap(
        _.collect[List](
          -1,
          Cursor.FailOnError[List[MovieWithID]]()
        )
      )
    }




}
