package models

import java.time.LocalDateTime

case class Movie(image: String,
                 title: String,
                 director: String,
                 actors: List[String],
                 description: String,
                 var screenings: List[LocalDateTime])

