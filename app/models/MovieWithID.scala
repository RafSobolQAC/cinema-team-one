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

