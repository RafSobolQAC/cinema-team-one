package models

import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime, LocalTime, ZoneOffset}
import java.util.Date


object JsonFormats {

  import reactivemongo.bson.{BSONObjectID, BSONDateTime}
  import play.api.libs.json.{Json, _}
  import reactivemongo.play.json.BSONFormats._



  implicit val customDateFormat: Format[LocalDateTime] = Format(
    Reads(js => JsSuccess(Instant.parse(js.as[String]).atZone(ZoneOffset.UTC).toLocalDateTime)),
    Writes(d => JsString(d.atZone(ZoneOffset.UTC).toInstant.toString))
  )

  implicit val movieFormat: OFormat[Movie] = Json.format[Movie]
  implicit val movieWithIDFormatOld: OFormat[MovieWithID] = Json.format[MovieWithID]

  implicit val bookingFormat: OFormat[Booking] = Json.format[Booking]
}
