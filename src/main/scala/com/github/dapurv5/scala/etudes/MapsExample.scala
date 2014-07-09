package com.github.dapurv5.scala.etudes

import com.github.dapurv5.scala.etudes.StringUtils._;
import scala.collection.mutable.{Map => MutableMap}

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
    //mapExample()
    //println("maps example")
    var sqlResult: MutableMap[String, Map[String, String]] = MutableMap[String, Map[String, String]]();
    sqlResult.put("date1", Map("visitors" -> "1", "visits" -> "1", "revenue" -> "1.0"))
    sqlResult.put("date2", Map("visitors" -> "1", "visits" -> "1", "revenue" -> "1.0"))
    sqlResult.put("date3", Map("visitors" -> "1", "visits" -> "1", "revenue" -> "1.0"))
    println(getCumulativeSqlResult(sqlResult.toMap));
  }
  
  
  /**
   * Constructs and returns a cumulative sql result from the given sql result.
   * The current value is a sum over all previous days
   */
  def getCumulativeSqlResult(sqlResult: Map[String, Map[String, String]]): Map[String, Map[String, String]] = {
    var cumulativeSqlResult: Map[String, Map[String, String]] = Map[String, Map[String, String]]()
    var cumulativeSum: MutableMap[String, String] = MutableMap[String, String]();
    for((date, metricsVectorToBeAdded) <- sqlResult.toSeq.sortBy {_._1}) {
      //add the current days data
      for((metric, value) <- metricsVectorToBeAdded) {
        val value_ = value.toDoubleOpt.get;
        val cumulativeSum_ = (cumulativeSum.getOrElse(metric,"0")).toDoubleOpt.get
        cumulativeSum(metric) = (cumulativeSum_ + value_).toString
      }
      cumulativeSqlResult += (date -> cumulativeSum.clone.toMap)
    }
    return cumulativeSqlResult
  }

}