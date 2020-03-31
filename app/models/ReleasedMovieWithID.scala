package models

import java.time.LocalDateTime

import akka.http.javadsl.model.DateTime
import play.api.libs.json.OFormat
import reactivemongo.bson.BSONObjectID

case class ReleasedMovieWithID(
                      var _id: BSONObjectID,
                      var image: String,
                      var title: String,
                      var director: String,
                      var description: String,
                      var actors: List[String],
                      var screenings: List[String],
                      var ratings: List[Int]
                      )