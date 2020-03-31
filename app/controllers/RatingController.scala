package controllers

import javax.inject.Inject
import models.Rating
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import services.ReleasedServices
import models.JsonFormats

import scala.concurrent.{ExecutionContext, Future}

class RatingController  @Inject()(
                                   components: ControllerComponents,
                                   val reactiveMongoApi: ReactiveMongoApi,
                                   val releasedServices: ReleasedServices
                                 ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = components.executionContext

  def getRating = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.rating(Rating.createRatingForm))
  }

  def submitRating(id: String) = Action.async { implicit request: Request[AnyContent] =>
    Rating.createRatingForm.bindFromRequest.fold({formWithErrors =>
      println("This is the form with errors!")
      println(formWithErrors)
      Future.successful(BadRequest(views.html.rating(formWithErrors)))
    }, {rating =>
      releasedServices.updateMovieRating(id, rating).map(_ => {
        Ok("Rating submitted successfully")
      })
    })

  }

}
