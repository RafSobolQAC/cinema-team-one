package controllers
import javax.inject._
import play.api.mvc._


class ScreensController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport {


  def screens() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.screens("Screens"))
  }
}
