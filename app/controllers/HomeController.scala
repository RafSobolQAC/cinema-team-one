package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index("Home"))
  }

  def placeholder = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.placeholder("PLACEHOLDER"))
  }
}
