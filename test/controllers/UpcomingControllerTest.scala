package controllers



import org.scalatestplus.play._
import play.api.mvc._
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.Future



class UpcomingControllerTest extends PlaySpec with Results{
  "Upcoming page controller" should {
    "Show the upcoming movies page" in {
      val controller= new UpcomingController(Helpers.stubControllerComponents(),null,null)
      val result: Future[Result] = controller.getMovies.apply(FakeRequest())
      contentType(result) mustBe Some("text/html")
    }
  }
}




