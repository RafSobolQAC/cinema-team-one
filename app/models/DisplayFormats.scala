package models

object DisplayFormats {
  def screeningDisplay(scr: String) = {
    val spl = scr.split(",")
    spl(0)+"-"+spl(1)+"-"+spl(2)+" "+spl(3)+":"+spl(4)
  }

  def commentFormat(comment: Commends) = {

  }
}
