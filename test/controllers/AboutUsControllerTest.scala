package controllers


package controllers

import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.mvc._
import play.api.test._
import scala.concurrent.Future


class AboutUsControllerTest extends PlaySpec with Results{
  "AboutController" should {
    "Show the about page" in {
      val controller= new AboutUsController(Helpers.stubControllerComponents())
      val result: Future[Result] = controller.aboutUs.apply(FakeRequest())
      contentType(result) mustBe Some("text/html")
    }
  }
}
