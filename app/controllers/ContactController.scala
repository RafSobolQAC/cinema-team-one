package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class ContactController @Inject()(cc: ControllerComponents) extends AbstractController(cc)  with play.api.i18n.I18nSupport{
  def contact = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.contact("contact"))
  }
}
