package edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.kdtree;

import java.util.Map;

/**
 * Interface for nodes in KD Tree
 * @param <N> - any class type that extensa KDNode
 */
public interface KDNode<N extends KDNode> {

  //gets number of dimensions in node?
  // current dimension of node? (time, equip, difficulty, % abs, % arms, etc.)
  int getDim();

  //gets overall metric of node? (...hashmap? ab-->20%, arms: 30%, ....difficult: 8...)
  double getMetric(int dimLevel);

  //gets overall metric of node's attributes (...hashmap? ab->20%, arms->30%, difficulty->8...)
  Map<String, Double> getAllMetrics();

  // boolean getFlexibility();

  /**
   * calculates aggregate "closeness"/similarity with provided workout node using the Metrics
   * @param n - node from which to calculate distance
   * @return - numerical distance
   */
  double calcDistance(N n);
}
