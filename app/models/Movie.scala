package models

import play.api.data.Form
import play.api.data.Forms._
import reactivemongo.bson.BSONObjectID

abstract class Movie(val _id: BSONObjectID,
                     val image: String,
                     val title: String,
                     val director: String,
                     val description: String,
                     val actors: List[String]
                    )

object Movie{
  def searchForFilm: Form[String] = Form{
    "title" -> text
  }
}