//package models
//
//import play.api.data.Form
//import play.api.data.Forms._
//
//case class Rating(starOne: Boolean, starTwo: Boolean, starThree: Boolean)
//
//object Rating {
//
//  object Stars extends Enumeration {
//    val one, two, three = Value
//  }
//
//  val createRatingForm: Form[Rating] = Form(
//    mapping(
//      "one" -> boolean,
//      "two" -> boolean,
//      "three" -> boolean
//    )(Rating.apply)(Rating.unapply)
//  )
//
//}
