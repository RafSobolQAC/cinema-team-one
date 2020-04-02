package controllers

import javax.inject.Inject
import models.{Movie, ReleasedMovieWithID}
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import services.{BookingServices, ReleasedServices, UpcomingServices}
import views.html.releasedmovie

import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}

class SearchController @Inject()(
                                  components: ControllerComponents,
                                  val reactiveMongoApi: ReactiveMongoApi,
                                  val releasedServices: ReleasedServices,
                                  val upcomingServices: UpcomingServices
                                ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = components.executionContext

  def allMovies = Future {
    val movies = new ListBuffer[Movie]

    releasedServices.getMovies.map(el =>
      movies ++= el
    )

    upcomingServices.getMovies.map(el =>
      movies ++= el
    )

    movies.toList
  }

  def getAllMovies = Action.async { implicit request: Request[AnyContent] =>
    allMovies.map(movies =>
      Ok(views.html.soughtmovies(movies))
    )
  }
}
