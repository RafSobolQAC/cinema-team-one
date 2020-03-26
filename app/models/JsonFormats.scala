package models
import play.api.libs.json._
object JsonFormats {
  implicit val movieFormat: OFormat[Movie] = Json.format[Movie]
}
