package edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.kdtree;

import java.util.Comparator;

/**
 * Comparator for values of provided dimension in two Nodes.
 *
 * @param <T> a HasCoordinates object contained in the KdNode
 */
public final class NodeDimComparator<T extends KDNode> implements Comparator<T> {
  private int dim;

  /**
   * Constructor for NodeAxisComparator.
   *
   * @param d int representing dimension of KDNode to compare
   */
  public NodeDimComparator(int d) {
    dim = d;
  }

  /**
   * @param a first KDNode object to compare
   * @param b second KDNode object to compare
   * @return -1 if Node a's dimension value is less than Node b's, 1 if vice versa, 0 if equal
   */
  @Override
  public int compare(T a, T b) {
    if (a.getMetric(dim) < b.getMetric(dim)) {
      return -1;
    } else if (a.getMetric(dim) > b.getMetric(dim)) {
      return 1;
    }
    return 0;
  }
}
