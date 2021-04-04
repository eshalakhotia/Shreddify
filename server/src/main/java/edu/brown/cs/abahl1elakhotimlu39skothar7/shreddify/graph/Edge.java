package edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph;
/**
 * Interface that represents all edges of a graph.
 * @param <E> Edge of graph
 * @param <V> Vertex of graph
 */
public interface Edge<E extends Edge<E, V>, V extends Vertex<E, V>> {
  /**
   * @return Vertex edge is pointing from
   */
  V getStart();

  /**
   * @return Vertex edge is pointing to
   */
  V getEnd();

  /**
   * @return Edge weight
   */
  double getWeight();

  /**
   * Returns the heuristic (not necassarily along an edge) distance between two nodes.
   * @param start an N that extends Node, represents the start from which heuristic is calculated.
   * @param end an N that extends Node, represents the end where the heuristic is calculated to.
   * @return a double that equals the heuristic distance.
   */
  double heuristic(V start, V end);

}
