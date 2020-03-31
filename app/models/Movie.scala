package models

import akka.http.scaladsl.model.DateTime

class Movie(id: Int, title: String, description: String,posterUrl: String, actors: List[String], director: String
//            ,screenings: List[DateTime]
           ){
  def getId(): Int = id
  def getTitle(): String = title
  def getDescription(): String = description
  def getPosterUrl(): String = posterUrl
  def getActors(): List[String] = actors
  def getDirector(): String = director
//  def getscreenings():List[DateTime] = screenings

}


