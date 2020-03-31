package models

import java.time.LocalDateTime

import play.api.data.Form
import play.api.data.Forms._

case class Booking(film: String, screening: String, name: String, seats: List[Int])
object Booking {
  val createBookingForm: Form[Booking] = Form(
    mapping(
      "film" -> nonEmptyText,
      "screening" -> text,
      "name" -> nonEmptyText,
      "seats" -> list(number)

    )(Booking.apply)(Booking.unapply)
  )

  val getTitleForm: Form[String] = Form(
    "title" -> nonEmptyText
  )

//  val bookingWithStringForm = Form(
//    mapping(
//      "film" -> nonEmptyText,
//      "screening" -> mapping(
//        "datetime" -> text
//      )(DateTime.apply)(DateTime.unapply),
//      "name" -> nonEmptyText,
//      "seats" -> list(number)
//
//    )(Booking.apply)(Booking.unapply)
//
//  )

}
