package models

import java.time.LocalDateTime

import akka.http.javadsl.model.DateTime
import play.api.libs.json.OFormat
import reactivemongo.bson.BSONObjectID

case class UpcomingMovieWithID(
                                override val _id: BSONObjectID,
                                override val image: String,
                                override val title: String,
                                override val director: String,
                                override val description: String,
                                override val actors: List[String],
                                var screenings: List[String],
                                var releaseDate: String,
                              ) extends Movie(_id, image, title, director, description, actors)
