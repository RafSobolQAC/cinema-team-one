package models

case class DateTime(year: Int, month: Int, day: Int, hour: Int, minute: Int)






object DateTime {
  def apply(year: Int, month: Int, day: Int, hour: Int, minute: Int) = {
    new DateTime(year, month, day, hour, minute)
  }
  def apply(string: String) = {
    val split = string.replace("DateTime(","").replace(")","").split(",").map(stringInt => stringInt.toInt)
    new DateTime(split(0),split(1),split(2),split(3),split(4))
  }
}
/*
val myDate = DateTime(2021,12,11,15,30)
JsonFormats(myDate) =>
{
  year: 2021,
  month: 12,
  day: 11,
  hour: 15,
  minute: 30
}

 */