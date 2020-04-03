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

  def allMovies = {
    val movies = new ListBuffer[Movie]

    //    releasedServices.getMovies.flatMap { el1 =>
    //
    //      movies ++= el1
    //
    //    }
    //    upcomingServices.getMovies.flatMap { el2 =>
    //      movies ++= el2
    //
    //    }

    val combFut = (releasedServices.getMovies, upcomingServices.getMovies)
    combFut
  }

  def getAllMovies = Action.async { implicit request: Request[AnyContent] =>
    val combFut = allMovies
    val relM = combFut._1
    val upcM = combFut._2
    val combList = joinFutureLists(relM, upcM)
    combList.map { movies =>
      Ok(views.html.soughtmovies(movies))
    }
  }

  def joinFutureLists[T](futureListOne: Future[List[T]], futureListTwo: Future[List[T]]) = {
    for {
      thisOne <- futureListOne
      thisTwo <- futureListTwo
    } yield thisOne ::: thisTwo
  }

  def getSelectedFilm = Action.async { implicit request: Request[AnyContent] =>
    Movie.searchForFilm.bindFromRequest.fold(
      {_ =>
        Future.successful(BadRequest("Bad search!"))
      },
      {string =>
        val combList = joinFutureLists(allMovies._1, allMovies._2)
        combList.map { movies =>
          Ok(views.html.soughtmovies(movies.filter(movie => movie.title.toLowerCase.contains(string.toLowerCase))))
        }

      }
    )
  }
}
