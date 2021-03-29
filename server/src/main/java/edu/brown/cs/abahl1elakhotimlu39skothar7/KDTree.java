package edu.brown.cs.abahl1elakhotimlu39skothar7;
import java.util.List;
import java.util.ArrayList;


/**
 * Represents a KdTree of any dimension and contains KDNode objects.
 */
public class KDTree {
  private int dimensions;
  private KDNode root;
  private KDTree left;
  private KDTree right;
  private List<KDNode> neighbors;

  /**
   * Constructor for KDTree.
   *
   * @param d     the number of dimensions of each node
   * @param nodes an ArrayList of KDNode objects
   */
  public KDTree(int d, List<KDNode> nodes) {
    this.buildTree(nodes, d);
    neighbors = new ArrayList<>();
  }

  public void buildTree(List<KDNode> nodes, int curDim) throws RuntimeException {
    boolean validList = true;
    int numDim = nodes.get(0).getDim();
    for (int i = 0; i < nodes.size(); i++) {
      validList = validList && (nodes.get(i).getDim() == numDim);
    }
    if (validList) {
      nodes.sort(new NodeDimComparator<KDNode>(numDim));
      if (nodes.size() == 1) {
        this.root = nodes.get(0);
        this.left = null;
        this.right = null;
      } else if (nodes.size() > 1) {
        int curMid = (nodes.size() - 1) / 2;
        this.root = nodes.get(curMid);
        this.left.buildTree(nodes.subList(0, curMid), curDim + 1);
        this.right.buildTree(nodes.subList(curMid + 1, nodes.size()), curDim + 1);
      }
    } else {
      throw new RuntimeException("ERROR: the list of KDNodes passed to the KDTree was invalid");
    }
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
  public List<KDNode> kNearestNeighbors(KDNode target, KDNode currRoot, int dim, int k) {
    return null;
  }

  public void add(KDNode n, int curDim) {
    if (this.root == null) {
      this.root = n;
    } else {
      if (n.getMetric(curDim) < this.root.getMetric(curDim)) {
        this.left.add(n, curDim + 1);
      } else {
        this.right.add(n, curDim + 1);
      }
    }
  }

  public void remove(KDNode n) { }

  public KDNode getRoot() {
    return root;
  }

}
