package controllers

import javax.inject.{Inject, _}
import models.Booking
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import models.JsonFormats._
import play.api.libs.json.{JsValue, Json}
import reactivemongo.play.json.collection.JSONCollection
import services.BookingServices

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.io.Source

class BookingController @Inject()(
                                 components: ControllerComponents,
                                 val reactiveMongoApi: ReactiveMongoApi,
                                 val bookingServices: BookingServices
                               ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = components.executionContext

  def showForm = Action {implicit request: Request[AnyContent] =>
    Ok(views.html.booking(Booking.createBookingForm))
  }

  def submitForm = Action.async {implicit request: Request[AnyContent] =>
    Booking.createBookingForm.bindFromRequest.fold({formWithErrors =>
      println("This is the form with errors!")
      println(formWithErrors)
      Future.successful(BadRequest(views.html.booking(formWithErrors)))
    }, {booking =>
      bookingServices.createBooking(booking).map(_ => {
        Redirect(routes.BookingController.showForm).flashing("success" -> "Successfully created!")
//        Ok(views.html.booking(Booking.createBookingForm)).flashing("success" -> "Created booking!")
      })
    })
  }
}
