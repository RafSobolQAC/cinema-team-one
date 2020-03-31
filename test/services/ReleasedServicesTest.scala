package services

import akka.actor.ActorRefFactory
import akka.stream.ActorMaterializer
import models.{Booking, MovieWithID}
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
import reactivemongo.play.json.collection.JSONCollection

class ReleasedServicesTest extends PlaySpec with Results with MockitoSugar {
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  val mockedMongo: ReactiveMongoApi = mock[ReactiveMongoApi]
  val mockFutureJSONColl: Future[JSONCollection] = mock[Future[JSONCollection]]
  //      doReturn(mockFutureJSONColl).when(mockedMongo).database
  var bookService: BookingServices = mock[BookingServices]
  var releasedService: ReleasedServices = mock[ReleasedServices]


  "The get-movies function" should {
    "throw an exception if collection is empty/inaccessible" in {


      when(releasedService.collection).thenReturn(mockFutureJSONColl)
      when(releasedService.getMovies).thenCallRealMethod()

//      val result: Future[List[MovieWithID]] = releasedService.getMovies
      assertThrows[NullPointerException](releasedService.getMovies)
    }
  }
}
