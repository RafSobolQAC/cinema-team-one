package controllers

import java.awt.Cursor

import javax.inject.Inject
import models.Commends
import models.JsonFormats._
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.Cursor
import reactivemongo.play.json._
import reactivemongo.play.json.collection.{JSONCollection, _}
import services.ReleasedServices

import scala.concurrent.{ExecutionContext, Future}

class CommendSectionController @Inject()(
                                          components: ControllerComponents,
                                          val reactiveMongoApi: ReactiveMongoApi,
                                          val releasedServices: ReleasedServices
                                        ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {
  implicit def ec: ExecutionContext = components.executionContext

  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("comment"))

  implicit var commentsList: List[Commends] = List()

  def getCommends = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.commends(Commends.createCommentForm,commentsList))
  }


  def serviceSubmitComment(comment: Commends) = {
    collection.flatMap(_.insert.one(comment))
  }

  def submitCommend = Action.async { implicit request: Request[AnyContent] =>
    Commends.createCommentForm.bindFromRequest.fold({ formWithErrors =>
      //println(formWithErrors)
      Future.successful(BadRequest(views.html.commends(formWithErrors,commentsList)))
    }, {commends =>
      serviceSubmitComment(commends).map(_ =>
        Ok(views.html.MessageBoard(Commends.createCommentForm, commentsList)))
    }
    )
  }

  //todo show the right comment on each movie
//def showComments


}
  //  def showComments(movieName:String) = Action.async {request: Request[AnyContent] =>
//    ReleasedController.findbyName(title).map(movieName =>
//          Ok(views.html.MessageBoard(Commends.createCommentForm)))
//  }


