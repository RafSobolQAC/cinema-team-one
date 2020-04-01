package views

import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.mvc._
import play.api.test.Helpers._

class ContactViewTest extends PlaySpec with Results with MockitoSugar {
  "The Contact view" should {
    "render the contact template" in {
      val html =  views.html.contact("contact")

      contentAsString(html) must include("Opening Times")
      contentAsString(html) must include("Monday")
      contentAsString(html) must include("Tuesday")
      contentAsString(html) must include("Wednesday")
      contentAsString(html) must include("Thursday")
      contentAsString(html) must include("Friday")
      contentAsString(html) must include("Saturday")
      contentAsString(html) must include("Sunday")

      contentAsString(html) must include("Visit Us")
      contentAsString(html) must include("Phone")
      contentAsString(html) must include("Email")



    }
  }
}
