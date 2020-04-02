package models

import play.api.libs.json.{Json, OFormat}

case class bought(
                        order_id: String,
                        purchased: Boolean,
                      )
object bought {
  def apply(orderID: String, purchase: Boolean) = {
    new bought(orderID, purchase)
  }
  implicit val boughtFormat: OFormat[bought] = Json.format[bought]

}
  //implicit val boughtFormat: OFormat[bought] = Json.format[bought]
