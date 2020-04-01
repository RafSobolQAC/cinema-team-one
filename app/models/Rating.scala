package models

import play.api.data.Form
import play.api.data.Forms._

case class Rating(starOne: Boolean, starTwo: Boolean, starThree: Boolean)
object Rating {
  val createRating: Form[Rating] = Form(
    mapping(
      "starOne" -> boolean,
      "starTwo" -> boolean,
      "starThree" -> boolean
    )(Rating.apply)(Rating.unapply)
  )

}
