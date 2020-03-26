package models

import play.api.data.Form
import play.api.data.Forms._

case class MessageBoard(name: String, email: String, movieName: String, comment: String)

object MessageBoard {
  val createMessage: Form[MessageBoard] = Form(
    mapping(
      "named" -> nonEmptyText,
      "email" -> email,
      "movieName" -> nonEmptyText,
      "comment" -> nonEmptyText,
//      "rating" -> number(0,5)

    )(MessageBoard.apply)(MessageBoard.unapply)
  ).fill(MessageBoard("","","",""))
}

