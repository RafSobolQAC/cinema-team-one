package models

import play.api.data.Form
import play.api.data.Forms._

case class Commends(
                         name: String,
                         email: String,
                         movieName: String,
                         comment: String
                       )

object Commends {
  val createCommentForm: Form[Commends] = Form(
    mapping(
      "named" -> nonEmptyText,
      "email" -> email,
      "movieName" -> nonEmptyText,
      "comment" -> nonEmptyText,
    )(Commends.apply)(Commends.unapply)
  ).fill(Commends("","","",""))





}