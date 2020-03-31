package controllers

import akka.http.scaladsl.model.headers.LinkParams.title
import javax.inject.Inject
import models.Commends
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import services.ReleasedServices
import views.html.commends
import models.JsonFormats._
import reactivemongo.play.json.collection.JSONCollection
import services.CommentSectionSerivces
import controllers.ReleasedController
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global

class CommendSectionController @Inject()(
                                          components: ControllerComponents,
                                          val reactiveMongoApi: ReactiveMongoApi,
                                          val releasedServices: ReleasedServices
                                        ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {
  implicit def ec: ExecutionContext = components.executionContext

  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("comment"))


  def getCommends = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.commends(Commends.createCommentForm))
  }

  def serviceSubmitComment(comment: Commends) = {
    collection.flatMap(_.insert.one(comment))
  }

  def submitCommend = Action.async { implicit request: Request[AnyContent] =>
    Commends.createCommentForm.bindFromRequest.fold({ formWithErrors =>
      println(formWithErrors)
      Future.successful(BadRequest(views.html.commends(formWithErrors)))
    }, { Commends =>
      serviceSubmitComment(Commends).map(result =>
        Ok("Well done! Comment created!")
      )
    }
    )
  }

  //todo show the right comment on each movie
//  def CommentsListing(movieName:String) = Action.async {request: Request[AnyContent] =>
//    ReleasedController.findbyName(title).map(movieName =>
//      Ok(views.html.MessageBoard(Commends.createCommentForm)))
//
//  }

}
