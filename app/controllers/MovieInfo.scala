package controllers

import javax.inject.Inject
import models.Movie
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}

class MovieInfo @Inject()(
                             components: ControllerComponents,
                             val reactiveMongoApi: ReactiveMongoApi
                           ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents {


  def movieInfo(id: String) = Action {
    Ok(views.html.movieInformation(movieDatabase.allFilmsList.find(_.getId() == id.toInt).getOrElse(new Movie(1,"error",
      "error", "error", "error", List("error"), "error"))))
  }

}
