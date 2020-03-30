package controllers

import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.mvc._
import play.api.test._
import scala.concurrent.Future


class HomeControllerTest extends PlaySpec with Results{
  "Home page controller" should {
    "Show the home page" in {
      val controller= new HomeController(Helpers.stubControllerComponents())
      val result: Future[Result] = controller.index.apply(FakeRequest())
      contentType(result) mustBe Some("text/html")
      contentAsString(result) must include("Currently Showing")
      contentAsString(result) must include("Upcoming Releases")
    }
}
}
