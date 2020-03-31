package controllers

import akka.actor.ActorRefFactory
import akka.stream.ActorMaterializer
import models.MovieWithID
import org.scalatestplus.play._
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import play.api.http.Status
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONObjectID
import services.{BookingServices, ReleasedServices}


class BookingControllerTest extends PlaySpec with Results with MockitoSugar {
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  "The show-form method" should {
    "give a html body " in {
      val mockedMongo = mock[ReactiveMongoApi]
      val releasedService = mock[ReleasedServices]
      val bookingService = mock[BookingServices]
      val controller = new BookingController(Helpers.stubControllerComponents(), mockedMongo, bookingService, releasedService)
      val spiedCont = spy(controller)
      val titAndScrList = Future {
        List[(String, List[String])](
          ("title1", List("morning", "evening")),
          ("title2", List("afternoon", "night"))
        )
      }
      when(releasedService.getMovies).thenReturn(Future(List()))
      when(spiedCont.titlesAndScreenings).thenReturn(titAndScrList)
      val result: Future[Result] = spiedCont.showForm.apply(FakeRequest())
      contentType(result) mustBe Some("text/html")

    }

  }
//  "The submit-select-film form" should {
//    "give a html body " in {
//      val mockedMongo = mock[ReactiveMongoApi]
//      val releasedService = mock[ReleasedServices]
//      val bookingService = mock[BookingServices]
//      val controller = new BookingController(Helpers.stubControllerComponents(), mockedMongo, bookingService, releasedService)
//      val titAndScrList = Future {
//        List[MovieWithID](
//          MovieWithID(BSONObjectID.generate(), "image", "title", "director", "desc", List("act1", "act2"), List("morning", "evening")),
//          MovieWithID(BSONObjectID.generate(), "image2", "title3", "director", "descaaa", List("act3", "act2"), List("morning", "evening"))
//        )
//      }
//      when(releasedService.getMovies).thenReturn(titAndScrList)
//      val result:  Future[Result] = controller.submitSelectFilmForm("title3").apply(FakeRequest())
//      contentType(result) mustBe Some("text/html")
//
//    }
//
//  }
  "The submit select film form submit" should {
    "give a bad request if bad form is provided" in {
      val mockedMongo = mock[ReactiveMongoApi]
      val releasedService = mock[ReleasedServices]
      val bookingService = mock[BookingServices]

      val controller = new BookingController(Helpers.stubControllerComponents(), mockedMongo, bookingService, releasedService)
      val request = FakeRequest(routes.BookingController.submitSelectFilmFormSubmit())
        .withFormUrlEncodedBody("bad" -> "bad")
      val result = controller.submitSelectFilmFormSubmit.apply(request)
      Status(status(result)) mustBe Status(400)
    }
  }

}
