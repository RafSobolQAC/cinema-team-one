package controllers

import javax.inject.Inject
import models.{Commends, Rating}
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import services.ReleasedServices

import scala.concurrent.Future

class CommentSectionController  @Inject()(
                                   components: ControllerComponents,
                                   val reactiveMongoApi: ReactiveMongoApi,
                                   val releasedServices: ReleasedServices
                                 ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {

  def getComment = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.commends(Commends.createCommend))
  }

  def submitCommend= Action.async {implicit request: Request[AnyContent] =>
    Commends.createCommend.bindFromRequest.fold({formWithErrors =>
      println("This is the form with errors")
      println(formWithErrors)
      Future.successful(BadRequest(views.html.commends(formWithErrors)))
    }, {rating =>
      releasedServices.getMovies(Commends).map(_ => {
        Redirect(routes.CommentSectionController.submitCommend).flashing("success" -> "Successfully created!")
      })
    })
  }

}
