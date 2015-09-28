package com.github.dapurv5.spark.etudes

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

/**
 * @author dapurv5
 * Most spark operations work on RDDs containing any kind of object,
 * a few special operations are available on RDDs of key-value pairs
 * The most common ones are distributed shuffle operations, eg.
 * grouping or aggregating the elements by a key
 * 
 * In scala these operations are automatically available on RDDs containing
 * Tuple2 objects created by simply writing (a,b)
 */
object KeyValuePairs {
  def main(args: Array[String]) {
    val logFile = "/home/dapurv5/MyCode/anahata/src/main/resources/macbeth.txt";
    val conf = new SparkConf().setAppName("Simple Application")
        .setMaster("local[4]")
    val sc = new SparkContext(conf);
    val lines = sc.textFile(logFile, 2).cache();
    val pairs = lines.map { s => (s,1) }
    val counts = pairs.reduceByKey((a,b) => a+b);
    counts.foreach(println);
  }
}