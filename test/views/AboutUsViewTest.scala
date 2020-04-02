package views

import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.mvc._
import play.api.test.Helpers._


class AboutUsViewTest extends PlaySpec with Results with MockitoSugar {
  "The aboutus view" should {
    "render the aboutus template" in {
      val html = views.html.aboutus("About")

      contentAsString(html) must include("Raf Sobol")
      contentAsString(html) must include("Evi Nikolaidou")
      contentAsString(html) must include("John Pooler")
      contentAsString(html) must include("Nathan Rees")
      contentAsString(html) must include("Malik Bensalem")
      contentAsString(html) must include("Project Lead")
      contentAsString(html) must include("Design Lead")
      contentAsString(html) must include("Developer")
      contentAsString(html) must include("Contact")
    }
  }
}