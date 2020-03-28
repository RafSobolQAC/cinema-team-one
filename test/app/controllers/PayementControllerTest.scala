package controllers

import org.scalatestplus.play._
import play.api.mvc._
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.Future

class PayementControllerTest extends PlaySpec with Results {

  "Example Page#index" should {
    "should be valid" in {
      val controller = new HomeController(Helpers.stub,Helpers.stubControllerComponents())
      val result: Future[Result] = controller.index().apply(FakeRequest())
      contentType(result) mustBe Some("text/html")
    }
  }

}
