package models

import java.time.LocalDateTime

import akka.http.scaladsl.model.DateTime
import play.api.libs.json.{Json, OFormat}
import play.api.data.Form
import play.api.data.Forms._
import reactivemongo.bson.BSONObjectID

case class Booking(film: String, screening: LocalDateTime, name: String, seats: List[Int])

object Booking {
  val createBookingForm: Form[Booking] = Form(
    mapping(
      "film" -> nonEmptyText,
      "screening" -> localDateTime,
      "name" -> nonEmptyText,
      "seats" -> list(number)

    )(Booking.apply)(Booking.unapply)
  )
}

object JsonFormats {
  implicit val bookingFormat: OFormat[Booking] = Json.format[Booking]
}
