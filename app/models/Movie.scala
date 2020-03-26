package models

import java.time.LocalDateTime

import akka.http.javadsl.model.DateTime
import play.api.libs.json.OFormat
import reactivemongo.bson.BSONObjectID


case class Movie(image: String,
                 title: String,
                 director: String,
                 description: String,
                 actors: List[String],
                 var screenings: List[DateTime])

case class MovieWithID(
                        _id: BSONObjectID,
                        image: String,
                        title: String,
                        director: String,
                        description: String,
                        actors: List[String],
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



