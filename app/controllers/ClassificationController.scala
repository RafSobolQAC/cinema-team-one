package controllers
import javax.inject._
import play.api.mvc._

 class ClassificationController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {


    def classifications() = Action {
      Ok(views.html.classifications("Movie Classifications"))
    }

}
