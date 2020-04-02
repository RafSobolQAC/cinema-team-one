package models

import reactivemongo.bson.BSONObjectID

abstract class Movie(val _id: BSONObjectID,
                     val image: String,
                     val title: String,
                     val director: String,
                     val description: String,
                     val actors: List[String]
                    )