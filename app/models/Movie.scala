package models

import java.time.LocalDateTime

import akka.http.javadsl.model.DateTime
import play.api.libs.json.OFormat
import reactivemongo.bson.BSONObjectID


case class Movie(
                  var image: String,
                  var title: String,
                  var director: String,
                  var description: String,
                  var actors: List[String],
                 var screenings: List[DateTime])

case class MovieWithID(
                      var _id: BSONObjectID,
                      var image: String,
                      var title: String,
                      var director: String,
                      var description: String,
                      var actors: List[String],
                        var screenings: List[DateTime]
                      )










//{
//  def getId(): Int = _id
//  def getTitle(): String = title
//  def getDescription(): String = description
//  def getPosterUrl(): String = image
//  def getActors(): List[String] = actors
//  def getDirector(): String = director
//  def getScreenings():List[LocalDateTime] = screenings
//
//}



