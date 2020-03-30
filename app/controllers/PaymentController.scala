package controllers
import javax.inject._
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws._
import play.api.mvc._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.sys.process._
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class PaymentController @Inject()(template:views.html.payment,ws:WSClient,cc: ControllerComponents) extends AbstractController(cc) {
  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`,.
   */
    def getAccessToken(): String ={
      val response:String="curl -v https://api.sandbox.paypal.com/v1/oauth2/token  -H \"Accept: application/json\" -H \"Accept-Language: en_US\" -u \"AU2PsgPZmOKjOX6uFScyLxX6Y_3TyQYuaIiQJjZW7amomHDUNAIeHbLHDFFUuuZpeTz-XmIcIyaFW0Gn:ELoyt2BXJOFH5sxbjrwoX8w28e98oJQ4UBTo6gU3IIQFfRjk3wDpget9gxI3rkpX7GsqV0eKtk2d8Khk\" -d \"grant_type=client_credentials\"" !!
      val json: JsValue = Json.parse(response)
      val accessToken: String = (json \ "access_token").as[String]
      accessToken
    }
  def createOrder() ={
    val json: JsValue = Json.parse("""
  {
    "application_context":{
        "return_url":"http://localhost:9099/capturePayment"
    },
    "intent": "CAPTURE",
    "purchase_units": [
    {
      "reference_id": "TICKET",
      "amount": {
        "currency_code": "GBP",
        "value": "69.00"
      }
    }
  ]
  }
  """)
    val token="Bearer "+getAccessToken()
      Await.result(ws.url("https://api.sandbox.paypal.com/v2/checkout/orders").
      addHttpHeaders("Content-Type"->"application/json").
      addHttpHeaders("Authorization"->token).
      post(json),Duration(5,"seconds")).json




  }


  def capturePayment(orderID:String)=Action{
    val token="Bearer "+getAccessToken()
//   val response=Await.result(ws.url("https://api.sandbox.paypal.com/v2/checkout/orders/"+orderID+"/authorize").
//      addHttpHeaders("Content-Type"->"application/json").
//      addHttpHeaders("Authorization"->token).get
//      ,Duration(5,"seconds"))
    val response="curl -v -X POST https://api.sandbox.paypal.com/v2/checkout/orders/"+orderID +"/capture -H \"Content-Type: application/json\" -H \"Authorization: "+token+"\""!!

    Ok(response)
  }

  def index = Action {
    //Ok(createOrder().toString())
    val json=createOrder()
    val url=(json\"links"\1\\"href").head.toString.replace("\"","")
    println("2")
    val orderID=(json\"id").get.toString.replace("\"","")
    Ok(template(url,orderID))
   // Redirect(createOrder())
   //   Ok("Hello world")
  }


}
