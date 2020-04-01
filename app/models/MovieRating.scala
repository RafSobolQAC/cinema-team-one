package models

import play.api.data.Form
import play.api.data.Forms._

case class MovieRating(Stars: Int)

object MovieRating {

  val createMovieRatingForm: Form[MovieRating] = Form (
    mapping(
      "stars" -> number
    )(MovieRating.apply)(MovieRating.unapply)
  )

}
