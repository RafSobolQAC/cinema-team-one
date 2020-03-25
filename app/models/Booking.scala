package models

import java.time.LocalDateTime

import play.api.data.Form
import play.api.data.Forms._
case class Booking(film: String, screening: LocalDateTime, name: String, seats: List[Int])

object Booking {
  val createBookingForm: Form[Booking] = Form(
    mapping(
      "film" -> nonEmptyText,
      "screening" -> localDateTime,
      "name" -> nonEmptyText,
      "seats" -> list(number)

    )(Booking.apply)(Booking.unapply)
  ).fill(Booking("",LocalDateTime.now(), "", List(0,0,0)))
}
