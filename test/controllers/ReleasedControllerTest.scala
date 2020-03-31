package controllers

import akka.actor.ActorRefFactory
import akka.stream.ActorMaterializer
import models.MovieWithID
import org.scalatestplus.play._
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONObjectID
import services.{BookingServices, ReleasedServices}


class ReleasedControllerTest extends PlaySpec with Results with MockitoSugar {
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  "The get-movies method" should {
    "return all movies " in {
      val mockedMongo = mock[ReactiveMongoApi]
      val releasedService = mock[ReleasedServices]
      val controller = new ReleasedController(Helpers.stubControllerComponents(),mockedMongo,releasedService)
      val filmList = Future {
        List[MovieWithID](
          MovieWithID(BSONObjectID.generate(), "image", "title", "director", "desc", List("act1", "act2"), List("morning", "evening")),
          MovieWithID(BSONObjectID.generate(), "image2", "title3", "director", "descaaa", List("act3", "act2"), List("morning", "evening"))
        )
      }
      when(releasedService.getMovies).thenReturn(filmList)
      val result: Future[Result] = controller.getMovies.apply(FakeRequest())
      contentType(result) mustBe Some("text/plain")
      filmList.map{
        value => contentAsString(result) must contain("descaaa")
      }
    }

  }

  "The get titles and screenings method" should {
    "return only titles and screenings" in {
      val mockedMongo = mock[ReactiveMongoApi]
      val releasedService = mock[ReleasedServices]
      val controller = new ReleasedController(Helpers.stubControllerComponents(),mockedMongo,releasedService)
      val filmList = Future {
        List[MovieWithID](
          MovieWithID(BSONObjectID.generate(), "image", "title", "director", "desc", List("act1", "act2"), List("morning", "evening")),
          MovieWithID(BSONObjectID.generate(), "image2", "title3", "director", "descaaa", List("act3", "act2"), List("morning", "evening"))
        )
      }
      when(releasedService.getMovies).thenReturn(filmList)
      val result: Future[Result] = controller.getOnlyMoviesAndScreenings.apply(FakeRequest())
      contentType(result) mustBe Some("text/plain")
      filmList.map{
        value =>
          {
            contentAsString(result) must not contain value.headOption.getOrElse(MovieWithID(BSONObjectID.generate(), "animage", "atitle", "adirector", "description2", List(), List())).description
          }
      }

    }
  }

}
