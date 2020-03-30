package controllers

import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.libs.ws.WSClient
import play.api.mvc._
import play.api.test.Helpers

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


}
