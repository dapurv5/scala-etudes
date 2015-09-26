package com.github.dapurv5.spark.etudes

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

/**
 * @author dapurv5
 */
object SimpleApp {
  def main(args: Array[String]) {
    val logFile = "/home/dapurv5/MyCode/anahata/src/main/resources/macbeth.txt";
    val conf = new SparkConf().setAppName("Simple Application").setMaster("spark://xps:7077");
    val sc = new SparkContext(conf);
    val logData = sc.textFile(logFile, 2).cache();
    val numMacbeth = logData.filter(line => line.contains("macbeth")).count();
    println(numMacbeth);
  }
}