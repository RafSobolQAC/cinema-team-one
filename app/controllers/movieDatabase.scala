package controllers

import models.Movie
import play.api._
import play.api.mvc._

object movieDatabase {

  val allFilmsList :List[Movie] = List[Movie](
    new Movie(1,"Anchorman 1", "Comedy", "https://upload.wikimedia.org/wikipedia/en/c/c3/Jaws_3d.jpg", List("Will Ferrel", "John"),
      "Tarantino" ),
    new Movie(1,"Anchorman 2", "Comedy", "https://upload.wikimedia.org/wikipedia/en/c/c3/Jaws_3d.jpg", List("Jim Carrey", "John"),
      "Tarantino" ),
    new Movie(1,"BruceAllMighty", "Comedy", "https://upload.wikimedia.org/wikipedia/en/c/c3/Jaws_3d.jpg", List("Jennifer Anniston"),
      "Tarantino" )


  )

}
