package controllers
import javax.inject._
import play.api.mvc._

class ScreensController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {


  def screens() = Action {
    Ok(views.html.screens("Screens"))
  }

}
