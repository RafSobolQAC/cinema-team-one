package controllers

import javax.inject.Inject
import models.ReleasedMovieWithID
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.bson.BSONObjectID
import services.ReleasedServices

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration.Duration

class ReleasedController @Inject()(
                                    components: ControllerComponents,
                                    val reactiveMongoApi: ReactiveMongoApi,
                                    val releasedServices: ReleasedServices,
                                    val paymentController: PaymentController,
                                    val commentsController: CommendSectionController
                                  ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = components.executionContext

  val pay: (String, String) = paymentController.createOrder(5, "http://localhost:9000/capturePayment")
  val url: String = pay._1

  def getMovies = Action.async { implicit request: Request[AnyContent] =>
    releasedServices.getMovies.map { movies =>
      //   Ok(movies.toString())
      Ok(views.html.releasedmovie(movies))

    }
  }


  def getOnlyMoviesAndScreenings: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    releasedServices.getMovies.map { movies =>
      movies.map(movie => movie.title -> movie.screenings)
    }.map { movies =>
      Ok(movies.toString())
    }
  }


  def releasedMovieInfo(id: String) = Action.async { implicit request: Request[AnyContent] => {
    releasedServices.getMovies.map { movies =>
      val theMovie = movies.find(movie => id == movie._id.stringify).getOrElse(ReleasedMovieWithID(BSONObjectID.generate, "", "","","",List(),List(),List()))
      val comments = Await.result(commentsController.getCommentsForFilm(theMovie.title), Duration.Inf)

      Ok(views.html.releasedmovieInfo(movies.filter(movie => id == movie._id.stringify).head)(url)(comments))

    }
  }
  }
}
