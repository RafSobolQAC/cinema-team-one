package controllers
import javax.inject._
import models.bought
import play.api.libs.json.{JsResultException, JsValue, Json}
import play.api.libs.ws._
import play.api.mvc._
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.play.json.ImplicitBSONHandlers._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.sys.process._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class PaymentController @Inject()(val reactiveMongoApi: ReactiveMongoApi,ws: WSClient, cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport with ReactiveMongoComponents{

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`,.
   */
  def getAccessToken(): String = { //gets a static value for verification with api requests
    val response: String = "curl -v https://api.sandbox.paypal.com/v1/oauth2/token  -H \"Accept: application/json\" -H \"Accept-Language: en_US\" -u \"AU2PsgPZmOKjOX6uFScyLxX6Y_3TyQYuaIiQJjZW7amomHDUNAIeHbLHDFFUuuZpeTz-XmIcIyaFW0Gn:ELoyt2BXJOFH5sxbjrwoX8w28e98oJQ4UBTo6gU3IIQFfRjk3wDpget9gxI3rkpX7GsqV0eKtk2d8Khk\" -d \"grant_type=client_credentials\"" !!
    val json: JsValue = Json.parse(response)
    val accessToken: String = (json \ "access_token").as[String]
    accessToken
  }

  def createOrder(ticketCost: Float, redirectUrl: String): (String, String) = { //creates order with paypal.returns a url (go to it to make the user pay) (the return address is where the user is re directed after paying)
    val postData: JsValue = Json.obj(
      "application_context" -> Json.obj("return_url" -> redirectUrl),
      "intent" -> "CAPTURE",
      "purchase_units" -> Json.arr(
        Json.obj(
          "reference_id" -> "TICKET",
          "amount" -> Json.obj("currency_code" -> "GBP", "value" -> ticketCost)
        )
      )
    )
    val token: String = "Bearer " + getAccessToken()
    val json2 = Await.result(ws.url("https://api.sandbox.paypal.com/v2/checkout/orders").addHttpHeaders("Content-Type" -> "application/json").addHttpHeaders("Authorization" -> token).post(postData), Duration(5, "seconds")).json

    val orderID = (json2 \ "id").as[String]
    val url = (json2 \ "links" \ 1 \\ "href") (0).as[String]
    (url, orderID)
  }

  def capturePayment(orderID: String) = Action { implicit request: Request[AnyContent] => //after user has paid, this needs to happen so it can capture payment
    val token = "Bearer " + getAccessToken()
    val response = "curl -v -X POST https://api.sandbox.paypal.com/v2/checkout/orders/" + orderID + "/capture -H \"Content-Type: application/json\" -H \"Authorization: " + token + "\"" !!

//add new document with orderID and purchased =true

    if (checkCapture(response)) {
      //addPurchase(orderID)
      Ok("Order completed!")

    } else {
      Ok("something went HORRIBLY wrong")
    }
  }


  def addPurchase(orderID:String): Unit ={
    val order= bought.apply(orderID, true)
    def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("bought"))
    collection.flatMap(_.insert.one(order))
  }


  def checkCapture(capturePaymentResponse:String) = { //returns true if the capture payment response was successful (payment taken)
    try {
      (Json.parse(capturePaymentResponse) \ "status").as[String] == "COMPLETED"
    } catch {
      case e: JsResultException => false
    }
  }

  def index = Action { implicit request: Request[AnyContent] => //does it all
    val tuple = createOrder(69f, "http://localhost:9000/capturePayment")
    val url = tuple._1
    val orderID = tuple._2
    //capturePayment(orderID)
    Ok(views.html.payment(url))
  }


}
