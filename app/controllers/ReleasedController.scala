package controllers

import javax.inject.Inject
import models.ReleasedMovieWithID
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import services.{BookingServices, ReleasedServices}

import scala.concurrent.ExecutionContext

class ReleasedController @Inject()(
                                    components: ControllerComponents,
                                    val reactiveMongoApi: ReactiveMongoApi,
                                    val releasedServices: ReleasedServices
                                  ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = components.executionContext

  def getMovies = Action.async { implicit request: Request[AnyContent] =>
    releasedServices.getMovies.map { movies =>
//   Ok(movies.toString())
      Ok(views.html.movie(movies))

    }
  }

  def ReleasedMovieInfo(id: String) = Action.async { implicit request: Request[AnyContent] =>
    releasedServices.getMovies.map { movies =>


      Ok(views.html.movieInfo(movies.filter(movie => id == movie._id.toString()).head))
    }
  }
}
