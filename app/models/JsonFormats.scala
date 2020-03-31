package models

import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime, LocalTime, ZoneOffset}
import java.util.Date

import reactivemongo.bson.{BSONObjectID, BSONDateTime}
import play.api.libs.json.{Json, _}
import reactivemongo.play.json.BSONFormats._

object JsonFormats {


  implicit val dateTimeFormat: OFormat[DateTime] = Json.format[DateTime]

  implicit val movieWithIDFormatOld: OFormat[MovieWithID] = Json.format[MovieWithID]

  implicit val bookingFormat: OFormat[Booking] = Json.format[Booking]

  implicit val commendsFormat: OFormat[Commends] = Json.format[Commends]

}
