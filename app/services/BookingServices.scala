package services

import javax.inject.Inject
import models.Booking
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json.collection.JSONCollection
import models.JsonFormats._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class BookingServices @Inject()(
                                 val reactiveMongoApi: ReactiveMongoApi
                               ) extends ReactiveMongoComponents {

  def collection: Future[JSONCollection]= reactiveMongoApi.database.map(_.collection[JSONCollection]("bookings"))

  def createBooking(booking: Booking) = {
    collection.flatMap(_.insert.one(booking))
  }

}
