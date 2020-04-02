package controllers
import javax.inject._
import play.api.mvc._

 class ClassificationController @Inject()(cc: ControllerComponents) extends AbstractController(cc)  with play.api.i18n.I18nSupport{


    def classifications() = Action { implicit request: Request[AnyContent] =>
      Ok(views.html.classifications("Movie Classifications"))
    }

}
