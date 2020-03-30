package controllers

import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.mvc._
import play.api.test._
import scala.concurrent.Future


class ContactControllerTest extends PlaySpec with Results{
  "ContactController" should {
    "Show the Contact page" in {
      val controller= new ContactController(Helpers.stubControllerComponents())
      val result: Future[Result] = controller.contact.apply(FakeRequest())
      contentType(result) mustBe Some("text/html")

    }
  }
}
