package models
import play.api.libs.json._


object JsonFormats {

  implicit val movieFormat: OFormat[Movie] = Json.format[Movie]
  implicit val bookingFormat: OFormat[Booking] = Json.format[Booking]
}
