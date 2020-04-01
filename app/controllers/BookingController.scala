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
                                   val paymentController:PaymentController,
                                   val releasedServices: ReleasedServices
                               ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {

  val pay=paymentController.createOrder(5,"http://localhost:9099/capturePayment")
  implicit def ec: ExecutionContext = components.executionContext

  def showForm = Action {implicit request: Request[AnyContent] =>
    println(pay._1)
    Ok(views.html.booking(Booking.createBookingForm,pay._1))

  }


  def submitForm = Action.async {implicit request: Request[AnyContent] =>
//    if(paymentController.capturePayment(pay._2)==Ok("Order completed")){
//        Ok("what happens when purchase is verified")
//    }else{
//      Ok("pay the moni")
//    }
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
