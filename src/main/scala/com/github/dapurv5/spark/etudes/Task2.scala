package com.github.dapurv5.spark.etudes

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object Task2 {
  def main(args: Array[String]) {
    val sc = new SparkContext(new SparkConf().setAppName("Task2").setMaster("local[2]"))

    // read the file
    //val file = sc.textFile("hdfs://localhost:8020" + args(0))
    val file = sc.textFile("/home/dapurv5/MyCode/private-projects/cs6242-assignments/HW3/graph1.tsv")
    
    val lines = file.cache();
    val filteredLines = lines.filter { s => !s.split("\t")(2).equals("1")}
    val pairs = filteredLines.flatMap { 
      s => {
        val arr = s.split("\t");
        val src = arr(0);
        val dst = arr(1);
        val score = arr(2).toFloat;
        List((src, 0.8*score), (dst, 0.2*score));
      } 
    }
    
    val counts = pairs.reduceByKey((a,b) => a+b);
    counts.collect();

    // store output on given HDFS path.
    // YOU NEED TO CHANGE THIS
    counts.saveAsTextFile("/home/dapurv5/Desktop/hdfs-output/sparkout")// + args(1))
  }
}