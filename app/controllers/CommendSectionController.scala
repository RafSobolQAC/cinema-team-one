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

  def createCommends(comment: Commends, title: String) = Action { implicit request: Request[AnyContent] =>
    val futureResult = collection.flatMap(_.insert.one(comment))
    Ok(views.html.commends(Commends.createCommentForm, title, commentsList))
  }

  def createFromJson: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Commends].map { thing =>
      collection.flatMap(_.insert.one(thing)).map { _ => Ok("Comment is created!")
      }
    }.getOrElse(Future.successful(BadRequest("Invalid Json format!")))
  }

  def serviceSubmitComment(comment: Commends) = {
    collection.flatMap(_.insert.one(comment))
  }


  def wordValid(comment: Commends) = {
    val source = Source.fromResource("resources/badwords.txt").getLines().toSet
    var hasSwearWord = false
    val splitWodsComments = comment.comment.split(" ")
    splitWodsComments.foreach(word => if (source.contains(word)) {
      println("Word is " + word)
      hasSwearWord = true
    })
    hasSwearWord

  }

  def submitCommend = Action.async { implicit request: Request[AnyContent] =>
    Commends.createCommentForm.bindFromRequest.fold({ formWithErrors =>
      println(formWithErrors)
      Future.successful(BadRequest("Bad request"))
    }, { commends =>
      if (wordValid(commends)) {
        Future.successful(BadRequest("Inappropriate Language"))
      }
      else {
        serviceSubmitComment(commends).map(_ => {
          Redirect(Call("GET", request.headers("referer")))
          })
      }
    }
    )
  }

  def findAllComments(title: String, filter: Option[(String, Json.JsValueWrapper)] = None): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
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
      Ok(views.html.commends(Commends.createCommentForm, title, commentsList))
    }
  }

  def getOrNothing(filter: Option[(String, Json.JsValueWrapper)]) = {
    if (filter.isDefined) Json.obj(filter.get) else Json.obj()
  }

  def showCommentForm = Action.async { implicit request: Request[AnyContent] =>
    makeComments
    movieTitleAndScreening.map { titles =>
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
  def getCommentsForFilm(title: String) = {
    val cursor: Future[WithOps[Commends]] = collection.map {
      _.find(Json.obj("movieName" -> title))
        .cursor[Commends]()
    }

    cursor.flatMap(
      _.collect[List](
        -1,
        Cursor.FailOnError[List[Commends]]()
      )
    )

  }



  def submitSelectFilmForm = Action.async { implicit request: Request[AnyContent] =>
    Commends.createMovieToRateForm.bindFromRequest.fold({ formWithErrors =>
      Future.successful(BadRequest(views.html.commendsfilm(formWithErrors, List())))
    }, { film =>
      getCommentsForFilm(film).map { comments =>
        Ok(views.html.commends(Commends.createCommentForm, film, comments))
      }

    })

  }

}



