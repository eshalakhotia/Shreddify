package edu.brown.cs.abahl1elakhotimlu39skothar7;

public interface KDNode {

  //gets number of dimensions in node?
  // current dimension of node? (time, equip, difficulty, % abs, % arms, etc.)
  int getDim();

  //gets overall metric of node? (...hashmap? ab-->20%, arms: 30%, ....difficult: 8...)
  double getMetric(int dimLevel);

  boolean getFlexibility(int dimLevel);

}
