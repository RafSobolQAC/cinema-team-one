package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class AboutUsController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport{

  def aboutUs = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.aboutus("aboutus"))
  }
}
