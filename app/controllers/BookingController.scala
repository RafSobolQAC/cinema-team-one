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
                                 val paymentcontroller:PaymentController,
                                 val releasedServices: ReleasedServices
                               ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = components.executionContext

  def showForm = Action {implicit request: Request[AnyContent] =>
    Ok(views.html.booking(Booking.createBookingForm,paymentcontroller.createOrder(5,"")._1))
  }


  def submitForm = Action.async {implicit request: Request[AnyContent] =>
    Booking.createBookingForm.bindFromRequest.fold({formWithErrors =>
      println("This is the form with errors!")
      println(formWithErrors)
      Future.successful(BadRequest(views.html.booking(formWithErrors,null)))
    }, {booking =>
      bookingServices.createBooking(booking).map(_ => {
        Redirect(routes.BookingController.showForm).flashing("success" -> "Successfully created!")
//        Ok(views.html.booking(Booking.createBookingForm)).flashing("success" -> "Created booking!")
      })
    })
  }
}
