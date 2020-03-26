package controllers

import javax.inject.Inject
import models.Rating
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import services.ReleasedServices

import scala.concurrent.Future

class RatingController  @Inject()(
                                   components: ControllerComponents,
                                   val reactiveMongoApi: ReactiveMongoApi,
                                   val releasedServices: ReleasedServices
                                 ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {

  def getRating = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.rating(Rating.createRating))
  }

  def submitRating = Action.async {implicit request: Request[AnyContent] =>
    Rating.createRating.bindFromRequest.fold({formWithErrors =>
      println("This is the form with errors")
      println(formWithErrors)
      Future.successful(BadRequest(views.html.rating(formWithErrors)))
    }, {rating =>
      releasedServices.getMovies(rating).map(_ => {
        Redirect(routes.RatingController.submitRating).flashing("success" -> "Successfully created!")
      })
    })
  }

}
