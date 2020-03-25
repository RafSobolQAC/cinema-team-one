package controllers

import models.Movie
import play.api._
import play.api.mvc._

object movieDatabase {

  val allFilmsList :List[Movie] = List[Movie](
    new Movie(1,"Anchorman 1", "Comedy", "https://images-na.ssl-images-amazon.com/images/I/91TXzLjVV-L._SL1500_.jpg", List("Will Ferrel", "John"),
      "Tarantino" ),
    new Movie(2,"Anchorman 2", "Comedy", "https://images-na.ssl-images-amazon.com/images/I/81qxP6kFOeL._SY445_.jpg", List("Jim Carrey", "John"),
      "Tarantino" ),
    new Movie(3,"BruceAllMighty", "Comedy", "https://resizing.flixster.com/8GO-zLY5jL4_peZ_bvGUZkpC3i0=/206x305/v1.bTsxMTE3NjUyNDtqOzE4NDQ0OzEyMDA7ODAwOzEyMDA", List("Jennifer Anniston"),
      "Tarantino" )

  )

}
