package models

import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime, LocalTime, ZoneOffset}
import java.util.Date

import reactivemongo.bson.{BSONObjectID, BSONDateTime}
import play.api.libs.json.{Json, _}
import reactivemongo.play.json.BSONFormats._

object JsonFormats {




  implicit val dateTimeFormat: OFormat[DateTime] = Json.format[DateTime]

//  implicit val movieFormat: OFormat[Movie] = Json.format[Movie]
  implicit val upcomingMovieWithIDFormatOld: OFormat[UpcomingMovieWithID] = Json.format[UpcomingMovieWithID]
  implicit val releasedMovieWithIDFormatOld: OFormat[ReleasedMovieWithID] = Json.format[ReleasedMovieWithID]

  implicit val bookingFormat: OFormat[Booking] = Json.format[Booking]
}
