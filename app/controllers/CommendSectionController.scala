package controllers

import javax.inject.Inject
import models.Commends
import models.JsonFormats._
import play.api.data.Form
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, _}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.Cursor
import reactivemongo.api.Cursor.WithOps
import reactivemongo.play.json._
import reactivemongo.play.json.collection.{JSONCollection, _}
import services.ReleasedServices

import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source



class CommendSectionController @Inject()(
                                          components: ControllerComponents,
                                          val reactiveMongoApi: ReactiveMongoApi,
                                          val releasedServices: ReleasedServices,
                                        ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = components.executionContext

  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("comment"))

  implicit var commentsList: List[Commends] = List()

  def makeComments = {
    val cursor: Future[WithOps[Commends]] = collection.map {
      _.find(Json.obj())
        .cursor[Commends]()
    }

    cursor.flatMap(
      _.collect[List](
        -1,
        Cursor.FailOnError[List[Commends]]()
      )
    ).map { comments =>
      commentsList = comments
    }
  }

  def createCommends(comment: Commends) = Action { implicit request: Request[AnyContent] =>
    val futureResult = collection.flatMap(_.insert.one(comment))
    Ok(views.html.commends(Commends.createCommentForm, commentsList))
  }

  def createFromJson: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Commends].map { thing =>
      collection.flatMap(_.insert.one(thing)).map { _ => Ok("Comment is created!")
      }
    }.getOrElse(Future.successful(BadRequest("Invalid Json format!")))
  }

  //  def showCommentForm = Action { implicit request: Request[AnyContent] =>
  //    makeComments
  //    Ok(views.html.commends(Commends.createCommentForm, commentsList))
  //  }

  def serviceSubmitComment(comment: Commends) = {
    collection.flatMap(_.insert.one(comment))
  }

  def formValidation(form:Form[Commends]): Boolean = {
    val source = Source.fromResource("resources/badwords.txt").getLines().toSet
    //val lines = source.getLines().
    val comment = form("comment").value.getOrElse("")
    var hasSwearWord = true
    val splitWodsComments = comment.split(" ")
    splitWodsComments.foreach(word => if (source.contains(word)) {
      hasSwearWord=false
    })
    hasSwearWord

  }

  //todo fix the error with the valitation
//  def submitCommend = Action.async { implicit request: Request[AnyContent] =>
//  Commends.createCommentForm.bindFromRequest.fold({ formWithErrors =>
//    //println(formWithErrors)
//  Future.successful(BadRequest(views.html.commends(formWithErrors, commentsList)))
//  }, { commends =>
//  if (formValidation(Commends.createCommentForm)) {
//    Future.successful(BadRequest("Inappropriate Language"))}
//  else {
//    serviceSubmitComment(commends).map(_ =>
//      Ok(views.html.MessageBoard(Commends.createCommentForm, commentsList)))
//  }
//    }
//    )
//  }

  def submitCommend = Action.async { implicit request: Request[AnyContent] =>
    Commends.createCommentForm.bindFromRequest.fold({ formWithErrors =>
      println(formWithErrors)
      Future.successful(BadRequest("Bad request"))
    }, { commends =>
      if (formValidation(Commends.createCommentForm)) {
        Future.successful(BadRequest("Inappropriate Language"))}
      else {
        serviceSubmitComment(commends).map(_ =>
          Redirect(routes.HomeController.index()).flashing("success" -> "Made a comment!"))
      }
    }
    )
  }

  def findAllComments(filter: Option[(String, Json.JsValueWrapper)] = None): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val cursor: Future[Cursor[Commends]] = collection.map {
      _.find(getOrNothing(filter))
        .cursor[Commends]()
    }
    cursor.flatMap(
      _.collect[List](
        -1,
        Cursor.FailOnError[List[Commends]]()
      )
    ).map { comment =>
      commentsList = comment
      Ok(views.html.commends(Commends.createCommentForm, commentsList))
    }
  }

  def getOrNothing(filter: Option[(String, Json.JsValueWrapper)]) = {
    if (filter.isDefined) Json.obj(filter.get) else Json.obj()
  }

  def showCommentForm = Action.async { implicit request: Request[AnyContent] =>
    makeComments
    movieTitleAndScreening.map{ titles =>
      Ok(views.html.commendsfilm(Commends.createMovieToRateForm, titles))
    }
  }

  def movieTitleAndScreening = {
    releasedServices.getMovies.map { movies =>
      movies.map {
        movie => movie.title -> movie.screenings
      }
    }
  }

  def submitSelectFilmForm = Action.async { implicit request: Request[AnyContent] =>
    Commends.createMovieToRateForm.bindFromRequest.fold({ formWithErrors =>
      Future.successful(BadRequest(views.html.commendsfilm(formWithErrors, List())))
    },{ film =>
      movieTitleAndScreening.map { films =>
        val innerFilms = films.find { case (titleOfFilm) =>
        titleOfFilm == film}
          .getOrElse(("None",List()))
        val filmFromInner = innerFilms match {
          case (_, screenings) => screenings
        }
        Ok(views.html.commends(Commends.createCommentForm, commentsList)
        )
      }

    })

  }



}



