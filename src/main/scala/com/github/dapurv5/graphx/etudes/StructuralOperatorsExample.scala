package com.github.dapurv5.graphx.etudes

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.graphx.Edge
import org.apache.spark.graphx.Graph


/**
 * @author dapurv5
 * demonstrates graph construction
 * usage of triplets
 * creating subgraphs
 */
object StructuralOperatorsExample {

	def main(args: Array[String]) {
		type VertexId = Long;

		val conf = new SparkConf().setAppName("Structural Operators Example")
				.setMaster("local[4]")
				val sc = new SparkContext(conf);

		// Create an RDD for the vertices 
		val users: RDD[(VertexId, (String, String))] = sc.parallelize(Array((3L, ("rxin", "student")),
				(7L, ("jgonzal", "postdoc")), (5L, ("franklin", "prof")),
				(2L, ("istoica", "prof")), (4L, ("peter", "student"))));

		// Create an RDD for edges
		val relationships: RDD[Edge[String]] =
				sc.parallelize(Array(Edge(3L, 7L, "collab"),    Edge(5L, 3L, "advisor"),
						Edge(2L, 5L, "colleague"), Edge(5L, 7L, "pi"),
						Edge(4L, 0L, "student"),   Edge(5L, 0L, "colleague")));

		// Define a default user in case there are relationship with missing user
		val defaultUser = ("John Doe", "Missing");

		// Build the initial Graph
		val graph = Graph(users, relationships, defaultUser)
				// Notice that there is a user 0 (for which we have no information) connected to users
				// 4 (peter) and 5 (franklin).

		graph.triplets.map(
				triplet => triplet.srcAttr._1 + " is the " + triplet.attr + " of " + triplet.dstAttr._1
				).collect.foreach(println(_));
    
		// Remove missing vertices as well as the edges to connected to them
		val validGraph = graph.subgraph(vpred = (id, attr) => attr._2 != "Missing")
		// The valid subgraph will disconnect users 4 and 5 by removing user 0
		validGraph.vertices.collect.foreach(println(_))
		validGraph.triplets.map(
				triplet => triplet.srcAttr._1 + " is the " + triplet.attr + " of " + triplet.dstAttr._1
				).collect.foreach(println(_))   
	}
  
}