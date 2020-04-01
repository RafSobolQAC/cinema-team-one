package views

import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.mvc._
import play.api.test.Helpers._
import views.html.main

class MainViewTest extends PlaySpec with Results with MockitoSugar {
  "The Home view" should {
    "render the home template" in {
      val html =  views.html.main("Main")(Ok())
      contentAsString(html) must include("Home")
      contentAsString(html) must include("Upcoming Movies")
      contentAsString(html) must include("Released Movies")
      contentAsString(html) must include("Classifications")
      contentAsString(html) must include("About us")
      contentAsString(html) must include("Contact Us")
    }
  }
}