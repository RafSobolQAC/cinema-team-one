package services

import javax.inject.Inject
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.Future

class RatingServices @Inject()(
                                val reactiveMongoApi: ReactiveMongoApi
                              ) extends ReactiveMongoComponents {

  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("rating"))


}
