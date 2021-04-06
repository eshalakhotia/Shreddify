package edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * The Node interface includes methods that set and get the Edges that are traversable from it.
 * There is also a method to get the Node's ID/Name.
 *  @param <V> Type that extends Node, represents the Nodes that this node is connected to.
 *  @param <E> Type that extends Edge, represents Edges that connect this Node to others.
 */
public interface Vertex<E extends Edge<E, V>, V extends Vertex<E, V>> {

  /**
   * Returns all of the traversable Edges from this Node.
   * @param graph the graph from which we want to see the traversable edges from.
   * @return List of Edges that have their start at this Node
   */
  List<E> getEdgesFromNode(Graph<E, V> graph);

  /**
   * Returns the ID or name of this Node.
   * @return String that represents the ID or name of this
   */
  String getID();

  String getName();

  void setParameterToUpdate(double newPreference);

  double getParameterToUpdate();

}
