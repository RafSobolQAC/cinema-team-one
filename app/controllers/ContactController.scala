package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class ContactController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def contact = Action {
    Ok(views.html.contact("contact"))
  }
}
