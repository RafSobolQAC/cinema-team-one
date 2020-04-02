package models

import reactivemongo.bson.BSONObjectID

case class MovieWithID(
                        _id: BSONObjectID,
                        image: String,
                        title: String,
                        director: String,
                        description: String,
                        actors: List[String],
                        var screenings: List[String]
                      )

abstract class Movie(val _id: BSONObjectID,
                     val image: String,
                     val title: String,
                     val director: String,
                     val description: String,
                     val actors: List[String]
                    )