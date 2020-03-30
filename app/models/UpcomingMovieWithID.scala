package models

import java.time.LocalDateTime

import akka.http.javadsl.model.DateTime
import play.api.libs.json.OFormat
import reactivemongo.bson.BSONObjectID

case class UpcomingMovieWithID(
                      var _id: BSONObjectID,
                      var image: String,
                      var title: String,
                      var director: String,
                      var description: String,
                      var actors: List[String],
                      var screenings: List[DateTime],
                      var ratings: Object
                      )
