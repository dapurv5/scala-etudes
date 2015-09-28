package com.github.dapurv5.spark.etudes

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

/**
 * @author dapurv5
 * 
 * accumulator
 * cache() for persisting results of a transformation
 * collect() for bringing the results to a single machine
 */
object SimpleApp {
	def main(args: Array[String]) {
		val logFile = "/home/dapurv5/MyCode/anahata/src/main/resources/macbeth.txt";
		val conf = new SparkConf().setAppName("Simple Application")
				.setMaster("local[4]")
		val sc = new SparkContext(conf);
		val logData = sc.textFile(logFile, 2).cache();
		val numMacbeth = logData.filter(line => line.contains("Macbeth")).count();
		println(numMacbeth);
	}
}