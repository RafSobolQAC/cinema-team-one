package controllers

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import controllers.movieDatabase
import play.api._
import play.mvc.Controller
import play.mvc.Action

class MoviesGallery extends Controller{
  def allMovies = Action {

    Ok(views.html.allMoviesGallery(movieDatabase.allFilmsList))
  }
}
