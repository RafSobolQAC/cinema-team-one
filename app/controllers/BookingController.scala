package controllers

import javax.inject.{Inject, _}
import models.{Booking, DateTime}
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import models.JsonFormats._
import play.api.libs.json.{JsValue, Json}
import reactivemongo.play.json.collection.JSONCollection
import services.{BookingServices, ReleasedServices}

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.io.Source

class BookingController @Inject()(
                                   components: ControllerComponents,
                                   val reactiveMongoApi: ReactiveMongoApi,
                                   val bookingServices: BookingServices,
                                   val releasedServices: ReleasedServices,
                                   val paymentController: PaymentController
                                 ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = components.executionContext

  def showForm = Action.async { implicit request: Request[AnyContent] =>
    titlesAndScreenings.map { titles =>
      Ok(views.html.bookingfilm(Booking.getTitleForm, titles))
    }
  }


  def submitSelectFilmFormSubmit = Action.async { implicit request: Request[AnyContent] =>
    Booking.getTitleForm.bindFromRequest.fold({ formWithErrors =>
      Future.successful(BadRequest(views.html.bookingfilm(formWithErrors, List())))
    }, { film =>
      titlesAndScreenings.map { films =>
        val innerFilms = films.find { case ((titleOfFilm, screenings))  =>
          titleOfFilm == film}
          .getOrElse(("None", List()))
        val filmFromInner = innerFilms match {
          case (_, screenings) => screenings
        }
        val urlAndOrdId = paymentController.makeIndex
        Ok(views.html.booking(Booking.createBookingForm,
          film, filmFromInner)(urlAndOrdId._1, urlAndOrdId._2
//          films.find(movie =>
//            movie._1 == film).getOrElse(("None", List()))._2))
          ))
      }
      //      Future.successful(Redirect(routes.BookingController.submitSelectFilmForm(film)))
      //      Ok(viwes.html.booking(Booking.createBookingForm, ))
    })
  }


  def submitForm /*()(implicit titles: List[(String, List[DateTime])])*/ = Action.async { implicit request: Request[AnyContent] =>

    Booking.createBookingForm.bindFromRequest.fold({ formWithErrors =>
      println("This is the form with errors!")
      println(formWithErrors)
      Future.successful(BadRequest("Bad!"))
    }, { booking =>
      bookingServices.createBooking(booking).map(_ => {
        Redirect(routes.HomeController.index()).flashing("success" -> "Made a booking!")
        //        Redirect(routes.BookingController.showForm).flashing("success" -> "Successfully created!")
        //        Ok(views.html.booking(Booking.createBookingForm)).flashing("success" -> "Created booking!")
      })
    })
  }


  def titlesAndScreenings = {
    releasedServices.getMovies.map {
      movies =>
        movies.map {
          movie => movie.title -> movie.screenings
        }
    }
  }
}
