package com.github.dapurv5.scala.etudes

object MapsExample {
  
  def mapExample() = {
    println("begin mapExample")
    //A map form (str, str) -> Map[String -> String]
    var mm = collection.mutable.Map[(String, String), Map[String, String]]()
    mm.put(("apurv", "verma"), Map("physics" -> "98", "mathematics" -> "100"))
    println(mm)
    var reportCard = mm.getOrElse(("apurv", "verma"), Map())
    println(reportCard)
  }

  def main(args: Array[String]): Unit = {
    mapExample()
    println("maps example")
  }

}