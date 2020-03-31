package models

import reactivemongo.bson.BSONObjectID


case class Movie(
                  var image: String,
                  var title: String,
                  var director: String,
                  var description: String,
                  var actors: List[String],
                  var screenings: List[String])

case class MovieWithID(
                        _id: BSONObjectID,
                        image: String,
                        title: String,
                        director: String,
                        description: String,
                        actors: List[String],
                        var screenings: List[String]
                      )




