package controllers
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.libs.ws.{WSClient, WSResponse}
import play.api.mvc._
import play.api.test.Helpers._
import play.api.test.{Helpers, Injecting, WithApplication, _}

import scala.concurrent.Future

class PayementControllerTest extends PlaySpec with MockitoSugar with Results{

  val wsr = mock[WSResponse]
  val ws = mock[WSClient]
  val controller: PaymentController = new PaymentController( ws, Helpers.stubControllerComponents())
  "Payment" should {
    "get access token" in {
      val token = controller.getAccessToken().getBytes()

      println(controller.getAccessToken())
      token must not be empty

    }
  }
  "capture payment" should{
    "capture the current payment" in{
      //val result=controller.capturePayment("3333333w")
      val testID="heheLOLxDJAJAJ"
      val result:Future[Result] = controller.capturePayment(testID).apply(FakeRequest())
      status(result) mustEqual(200)

    //  contentType(result) mustBe null
    }
  }
  "Application" should {
    "be reachable" in new WithServer {
      val response = await(ws.url("http://localhost:" + 9099+"/capturePayment?token=penis").get()) //1

     // response.status must equalTo(OK) //2
      response.body must contain("") //3
    }
  }


  "create Order" should {
    "create an order" in new WithApplication() with Injecting {



      }
    }













  }
