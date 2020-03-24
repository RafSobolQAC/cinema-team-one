package controllers
import javax.inject._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

import scala.sys.process._
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class PaymentController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`,.
   */
    def getAccessToken(): String ={
      val response:String="curl -v https://api.sandbox.paypal.com/v1/oauth2/token  -H \"Accept: application/json\" -H \"Accept-Language: en_US\" -u \"AU2PsgPZmOKjOX6uFScyLxX6Y_3TyQYuaIiQJjZW7amomHDUNAIeHbLHDFFUuuZpeTz-XmIcIyaFW0Gn:ELoyt2BXJOFH5sxbjrwoX8w28e98oJQ4UBTo6gU3IIQFfRjk3wDpget9gxI3rkpX7GsqV0eKtk2d8Khk\" -d \"grant_type=client_credentials\"" !!
      val jsonn: JsValue = Json.parse(response)
      val accessToken: String = (jsonn \ "access_token").as[String]
      accessToken
    }

  def index = Action {
   // Ok(views.html.index(getAccessToken()))

    //Ok(getAccessToken())
    Ok(views.html.payment())
  }

}
