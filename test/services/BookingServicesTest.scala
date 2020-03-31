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

class BookingServicesTest extends PlaySpec with Results with MockitoSugar {

  "The create-booking method" should {
    "return a Future[WriteResult]" in {
      val mockedMongo = mock[ReactiveMongoApi]
      val mockFutureJSONColl = mock[Future[JSONCollection]]
//      doReturn(mockFutureJSONColl).when(mockedMongo).database
      val service = new BookingServices(mockedMongo)
      val spyService = mock[BookingServices]
      when(spyService.collection).thenReturn(mockFutureJSONColl)
      when(spyService.createBooking(Booking("a","b","c",List()))).thenCallRealMethod()
      spyService.createBooking(Booking("a","b","c",List()))
      verify(spyService, times(1)).createBooking(Booking("a","b","c",List()))
    }
  }
}