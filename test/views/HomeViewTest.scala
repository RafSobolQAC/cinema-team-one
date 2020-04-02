package views

import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.mvc._
import play.api.test.Helpers._

class HomeViewTest extends PlaySpec with Results with MockitoSugar {
  "The Home view" should {
    "render the home template" in {
      val html =  views.html.index("home")
      contentAsString(html) must include("Released Movies")
      contentAsString(html) must include("Upcoming Movies")
    }
  }
}