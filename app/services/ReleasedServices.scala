package services


import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import javax.inject.Inject
import models.{Movie, MovieWithID}
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.play.json._
import collection._
import models.JsonFormats._
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent}
import reactivemongo.api.Cursor
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ReleasedServices @Inject()(
                                  val reactiveMongoApi: ReactiveMongoApi
                                ) extends ReactiveMongoComponents {
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

  def getMovieByTitle(title: String): Action[AnyContent] = Action.async {
    val cursor: Future[Cursor[MovieWithID]] = collection.map {
      _.find(Json.obj("title" -> title)).
        sort(Json.obj("title" -> -1)).
        cursor[MovieWithID]()
    }
    val futureMoviesList: Future[List[MovieWithID]] =
      cursor.flatMap(
        _.collect[List](
          -1,
          Cursor.FailOnError[List[MovieWithID]]()
        )
      )

    futureMoviesList.map { movies =>
      Ok(movies.toString)
    }
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
