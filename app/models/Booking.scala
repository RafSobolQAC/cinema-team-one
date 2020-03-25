package models


import java.time.LocalDateTime

import akka.http.scaladsl.model.DateTime
import play.api.libs.json.{Format, JsObject, JsResult, JsValue, Json, OFormat, Reads}
import play.api.data.{Form, format}
import play.api.data.Forms._
import reactivemongo.bson.BSONObjectID
import org.joda.time.DateTimeZone
case class Booking(film: String, screening: LocalDateTime, name: String, seats: List[Int])

object Booking {
  val createBookingForm: Form[Booking] = Form(
    mapping(
      "film" -> nonEmptyText,
      "screening" -> localDateTime("yyyy-MM-dd'T'HH:mm"),
      "name" -> nonEmptyText,
      "seats" -> list(number)

    )(Booking.apply)(Booking.unapply)
  ).fill(Booking("",LocalDateTime.now(), "", List(0,0,0)))
}

object JsonFormats {
  import play.api.libs.json._

//  implicit val localDateTimeRead: Reads[LocalDateTime] =
//    (JsPath \ "$date").read[Long].map { dateTime =>
//      new LocalDateTime(dateTime, DateTimeZone.UTC)
//    }
//
//
//  implicit val localDateTimeWrite: Writes[LocalDateTime] = new Writes[LocalDateTime] {
//    def writes(dateTime: LocalDateTime): JsValue = Json.obj(
//      "$date" -> dateTime.toDateTime(DateTimeZone.UTC).getMillis
//    )
//  }

//  implicit val LocalDateFormat: OFormat[LocalDateTime] =
//    new OFormat[LocalDateTime]() {
//      override def writes(o: LocalDateTime): JsObject = Json.format[LocalDateTime].writes(o).as[JsObject]
//
//      override def reads(json: JsValue): JsResult[LocalDateTime] = Json.format[LocalDateTime].reads(json)
//    }

  implicit val bookingFormat: OFormat[Booking] = Json.format[Booking]
}
