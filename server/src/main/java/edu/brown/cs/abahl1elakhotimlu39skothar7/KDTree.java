package edu.brown.cs.abahl1elakhotimlu39skothar7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a KdTree of any dimension and contains KDNode objects.
 *
 * @param <T> Object implementing KDNode (a Workout in this case)
 */
public class KDTree<T extends KDNode> {
  private int dimensions;
  private T root;
  private List<T> neighbors;

  /**
   * Constructor for KDTree.
   *
   * @param d     the number of dimensions of each node
   * @param nodes an ArrayList of KDNode objects
   */
  public KDTree(int d, List<T> nodes) {
    dimensions = d;
    root = this.buildTree(nodes, d, 0, nodes.size());
    neighbors = new ArrayList<>();
  }

  /**
   * Recursively builds tree with given nodes.
   *
   * @param nodes nodes to build subtree with at current level of recursion
   * @param dim   dimension to sort nodes on at this level
   * @param left  left half of node list to build next left subtree with
   * @param right right half of node list to build next right subtree with
   * @return root of whole tree
   */
  public T buildTree(List<T> nodes, int dim, int left, int right) {
    //no more nodes left to build subtree with
    if (left >= right) {
      return null;
    }
    //sort nodes list based on current dimension
    Collections.sort(nodes.subList(left, right), new NodeDimComparator<>(dim));

    int m = (left + right - 1) / 2; //median index in current section, right one if even number
    T subRoot = nodes.get(m); //median root at this level based on current dimension

    dim = (dim + 1) % dimensions; //rotate which dimension to be compared in next level

    //recursively build left and right subtrees of median root
    subRoot.setLeft(buildTree(nodes, dim, left, m));
    subRoot.setRight(buildTree(nodes, dim, m + 1, right));

    return subRoot;
  }

  /**
   * Searches through KdTree to return a list of k nearest neighbors.
   *
   * @param target   the node from which to find the nearest neighbors
   * @param currRoot the current tree node during each level of the traversal
   * @param dim      the current axis to be compared during each level of the traversal
   * @param k        the number of nearest neighbors to return
   * @return an ArrayList of nodes representing the nearest neighbors
   */
  public List<T> kNearestNeighbors(T target, T currRoot, int dim, int k) {
    return null;
  }

  //public void add(KDNode n) {}
  //public void remove(KDNode n) {}
  public KDNode getRoot() {
    return root;
  }
}
