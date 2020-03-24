package controllers

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import controllers.movieDatabase
import javax.inject.Inject
import play.api._
import play.api.mvc.{AbstractController, ControllerComponents}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import play.mvc.Controller
import play.mvc.Action

class MoviesGallery @Inject()(
                               components: ControllerComponents,
                               val reactiveMongoApi: ReactiveMongoApi
                             ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents {











  def allMovies = Action {

    Ok(views.html.allMoviesGallery(movieDatabase.allFilmsList))
  }
}
