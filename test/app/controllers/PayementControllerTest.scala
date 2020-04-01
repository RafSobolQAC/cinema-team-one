package controllers
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.libs.ws.{WSClient, WSResponse}
import play.api.test.{Helpers, Injecting, WithApplication}
class PayementControllerTest extends PlaySpec with MockitoSugar {

  val template = mock[views.html.payment]
  val wsr = mock[WSResponse]
  val ws = mock[WSClient]
  val controller: PaymentController = new PaymentController(template, ws, Helpers.stubControllerComponents())
  "Payment" should {
    "get access token" in {
      val token = controller.getAccessToken().getBytes()

      println(controller.getAccessToken())
      token must not be empty
      token.toString must be mustEqual("ResultOfBeWordForAny(\"A21AAE2ID7hn7COosFr_WCMVGq8wqozqkKhPrcj2V4ijXeufe1A_aczwKvR3Bf4QZsjYmKN9vFW196FH26lTiAPVYg9AzyE0w\", true)")

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

  "capturePayments" should {
    "be reachable" in new WithServer {
      val response = (ws.url("http://localhost:" + port+"/capturePayment?token=penis")) //1

      // response.status must equalTo(OK) //2
      // response.body must contain("").wait //3
    }
  }


  "create Order" should {
    "create an order" in new WithApplication() with Injecting {



      }
    }













  }
