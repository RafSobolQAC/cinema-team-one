package views


import controllers.HomeController
import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.mvc._
import play.api.test._

import scala.concurrent.Future


class HomeControllerTest extends PlaySpec with Results{
  "HomeController" should {
    "Show the about page" in {
      val controller= new HomeController(Helpers.stubControllerComponents())
      val result: Future[Result] = controller.index.apply(FakeRequest())
      contentType(result) mustBe Some("text/html")
    }
  }
}