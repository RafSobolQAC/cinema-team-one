package controllers

import javax.inject.Inject

import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}

import services.UpcomingServices



import scala.concurrent.ExecutionContext


class UpcomingController @Inject()(
                               components: ControllerComponents,
                               val reactiveMongoApi: ReactiveMongoApi,
                               val upcomingServices: UpcomingServices
                             ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = components.executionContext

  def getMovies = Action.async { implicit request: Request[AnyContent] =>
    upcomingServices.getMovies.map { movies =>
      Ok(movies.toString())
    }
  }

}
