package services

import javax.inject.Inject
import models.Booking
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json.collection.JSONCollection
import models.JsonFormats._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ReleasedServices @Inject()(
                                 val reactiveMongoApi: ReactiveMongoApi
                               ) extends ReactiveMongoComponents {
  def collection: Future[JSONCollection]= reactiveMongoApi.database.map(_.collection[JSONCollection]("bookings"))

  def getFilms = {

  }
//  def getFilms = Action.async {
//    val cursor: Future[Cursor[Film]] = collection.map {
//      _.find(filter)
//        .cursor[Film]()
//    }
//    cursor.flatMap(
//      _.collect[List](
//        -1,
//        Cursor.FailOnError[List[Film]]()
//      ))
//  }

}
