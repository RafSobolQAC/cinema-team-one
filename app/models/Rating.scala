package models

import play.api.data.Form
import play.api.data.Forms.mapping

case class Rating(starOne: Boolean, starTwo: Boolean, starThree: Boolean)

object Rating {
  val createRating: Form[Rating] = Form(
    mapping(
      "starOne" -> Boolean,
      "starTwo" -> Boolean,
      "starThree" -> Boolean

    )(Rating.apply)(Rating.unapply)
  )

}
