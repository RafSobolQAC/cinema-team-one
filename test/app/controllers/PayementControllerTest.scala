package controllers

import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.libs.ws.WSClient
import play.api.mvc._
import play.api.test.Helpers
import play.api.libs.json._
import play.api.mvc._
import play.api.routing.sird._
import play.core.server.Server

class PayementControllerTest extends PlaySpec with MockitoSugar  with Results {

  val template=mock[views.html.payment]
  val ws=mock[WSClient]
  val controller:PaymentController=new PaymentController(template,ws,Helpers.stubControllerComponents())


  "Payment" should {
    "get access token" in {
     // val result:Future[Result]=controller.index().apply(FakeRequest())
      val token=controller.getAccessToken()
      token must not be empty

    }
  }

  "create Order" should{
    "create an order" in{//i will be mocking the wsclient to make sure the requests are working correctly

      Server.withRouterFromComponents() { components =>
        import Results._
        import components.{ defaultActionBuilder => Action }
        {
          case GET(p"/repositories") =>
            Action {
              Ok(Json.arr(Json.obj("full_name" -> "octocat/Hello-World")))
            }
        }


    }
  }


}
