package edu.brown.cs.abahl1elakhotimlu39skothar7;

import java.util.HashMap;

public interface KDNode {

  //gets number of dimensions in node?
  // current dimension of node? (time, equip, difficulty, % abs, % arms, etc.)
  int getDim();

  //gets overall metric of node? (...hashmap? ab-->20%, arms: 30%, ....difficult: 8...)
  double getMetric(int dimLevel);

  //gets overall metric of node's attributes (...hashmap? ab->20%, arms->30%, difficulty->8...)
  HashMap<String, Double> getAllMetrics();

  // boolean getFlexibility();

  /*
  //Stuff likely needed for KDTree building and searching
   */
  //sets root of left subtree
  void setLeft(KDNode n);

  //sets root of right subtree
  void setRight(KDNode n);

  //gets root of left subtree, possibly null
  KDNode getLeft();

  //gets root of right subtree, possibly null
  KDNode getRight();

  //calculates aggregate "closeness"/similarity with provided workout node using the Metrics
  double calcDistance(KDNode n);
}
