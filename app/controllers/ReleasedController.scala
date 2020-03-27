package controllers

import javax.inject.Inject
import models.Movie
import play.api.mvc._
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
      Ok(movies.toString())
    }
  }

  def getOnlyMoviesAndScreenings: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    releasedServices.getMovies.map { movies =>
      movies.map(movie => movie.title -> movie.screenings)
    }.map { movies =>
      Ok(movies.toString())
    }
  }


}
