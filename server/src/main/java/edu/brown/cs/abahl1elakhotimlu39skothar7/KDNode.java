package edu.brown.cs.abahl1elakhotimlu39skothar7;

public interface KDNode {

  //gets overall metric of node's attributes (...hashmap? ab->20%, arms->30%, difficulty->8...)
  double getMetric();

  boolean getFlexibility();

  /*
  //Stuff likely needed for KDTree building and searching
   */
  //sets root of left subtree
  void setLeft(KDNode n);
  //sets root of right subtree
  void setRight(KDNode n);
  //gets value of provided dimension (value of time, equip, or difficulty, or %abs etc.)
  double getDim(int dim);
  //gets root of left subtree, possibly null
  KDNode getLeft();
  //gets root of right subtree, possibly null
  KDNode getRight();
  //calculates aggregate "closeness"/similarity with provided workout node using the Metrics
  double calcDistance(KDNode n);
}
