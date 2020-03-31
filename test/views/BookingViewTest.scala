package views

import models.Booking
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._

class BookingViewTest extends PlaySpec with Results with MockitoSugar {

  "The booking view" should {
    "render the booking template" in {
      val html =  views.html.booking(Booking.createBookingForm,"a Film", List("morning","evening"))(Helpers.stubMessagesRequest(), Flash())
      val html2 = views.html.bookingfilm(Booking.getTitleForm, List())(Helpers.stubMessagesRequest())
      contentAsString(html) must include("morning")
      contentAsString(html2) must include("Select film")
    }
  }
}
