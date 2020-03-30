package models

import play.api.data.Form
import play.api.data.Forms._

case class Rating(starOne: Boolean, starTwo: Boolean, starThree: Boolean)

object Rating {

  object Stars extends Enumeration {
    val starOne, starTwo, starThree = Value
  }

  val createRatingForm: Form[Rating] = Form(
    mapping(
      "starOne" -> boolean,
      "starTwo" -> boolean,
      "starThree" -> boolean
    )(Rating.apply)(Rating.unapply)
  ).fill(Rating(false,false,false))
}

//in the view, make a helper form with 3 radio buttons; submit one true button