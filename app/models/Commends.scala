package models

import play.api.data.Form
import play.api.data.Forms._

case class Commends(
                         name: String,
                         email: String,
                         movieName: String,
                         comment: String,
                         rating: Int,
                       )

object Commends {
  val createCommentForm: Form[Commends] = Form(
    mapping(
      "name" -> nonEmptyText,
      "email" -> email,
      "movieName" -> nonEmptyText,
      "comment" -> nonEmptyText,
      "rating" -> number,
    )(Commends.apply)(Commends.unapply)
  ).fill(Commends("","","","",0))

  val createMovieToRateForm: Form[String] = Form(
    "title" -> nonEmptyText
  )





}