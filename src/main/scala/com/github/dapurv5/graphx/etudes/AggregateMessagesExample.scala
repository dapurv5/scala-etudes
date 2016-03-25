package com.github.dapurv5.graphx.etudes

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.graphx.util.GraphGenerators
import org.apache.spark.graphx.Graph
import org.apache.spark.graphx.VertexRDD

/**
 * @author dapurv5
 */
object AggregateMessagesExample {

	def main(args: Array[String]) {
		type VertexId = Long;

		val conf = new SparkConf().setAppName("Structural Operators Example")
				.setMaster("local[4]");
		val sc = new SparkContext(conf);

		// Create a graph with "age" as the vertex property.  Here we use a random graph for simplicity.
		val graph: Graph[Double, Int] =
				GraphGenerators.logNormalGraph(sc, numVertices = 100).mapVertices( (id, _) => id.toDouble );

		// Compute the number of older followers and their total age
		val olderFollowers: VertexRDD[(Int, Double)] = graph.aggregateMessages[(Int, Double)](
				triplet => { // Map Function
					if (triplet.srcAttr > triplet.dstAttr) {
						// Send message to destination vertex containing counter and age
						triplet.sendToDst(1, triplet.srcAttr)
					}
				},
				// Add counter and age
				(a, b) => (a._1 + b._1, a._2 + b._2) // Reduce Function
				);

		// Divide total age by number of older followers to get average age of older followers
		val avgAgeOfOlderFollowers: VertexRDD[Double] =
				olderFollowers.mapValues( (id, value) => value match { case (count, totalAge) => totalAge / count } )
				// Display the results
				avgAgeOfOlderFollowers.collect.foreach(println(_));   
	}
}