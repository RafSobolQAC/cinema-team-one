package controllers

import javax.inject.Inject
import models.Booking
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import services.{BookingServices, ReleasedServices}

import scala.concurrent.{ExecutionContext, Future}

class BookingController @Inject()(
                                   components: ControllerComponents,
                                   val reactiveMongoApi: ReactiveMongoApi,
                                   val bookingServices: BookingServices,
                                   val releasedServices: ReleasedServices,
                                   val paymentController: PaymentController
                                 ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {

  val pay=paymentController.createOrder(5,"http://localhost:9099/capturePayment")
  implicit def ec: ExecutionContext = components.executionContext

  def showForm = Action.async { implicit request: Request[AnyContent] =>
    titlesAndScreenings.map { titles =>
      Ok(views.html.bookingfilm(Booking.getTitleForm, titles))
    }
  }


//  def submitSelectFilmFormSubmit = Action.async { implicit request: Request[AnyContent] =>
//    Booking.getTitleForm.bindFromRequest.fold({ formWithErrors =>
//      Future.successful(BadRequest(views.html.bookingfilm(formWithErrors, List())))
//    }, { film =>
//      titlesAndScreenings.map { films =>
//        val innerFilms = films.find { case ((titleOfFilm, _))  =>
//          titleOfFilm == film}
//          .getOrElse(("None", List()))
//        val filmFromInner = innerFilms match {
//          case (_, screenings) => screenings
//        }
//        Ok(views.html.booking(Booking.createBookingForm,
//          film, filmFromInner
////          films.find(movie =>
////            movie._1 == film).getOrElse(("None", List()))._2))
//          ))
//      }
//    })
//  }



  def submitForm /*()(implicit titles: List[(String, List[DateTime])])*/ = Action.async { implicit request: Request[AnyContent] =>

//    if(paymentController.capturePayment(pay._2)==Ok("Order completed")){
//        Ok("what happens when purchase is verified")
//    }else{
//      Ok("pay the moni")
//    }
  Booking.createBookingForm.bindFromRequest.fold({formWithErrors =>
      println("This is the form with errors!")
      println(formWithErrors)
      Future.successful(BadRequest(views.html.booking(formWithErrors,"nonono",List("Never"))))
    }, {booking =>
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
