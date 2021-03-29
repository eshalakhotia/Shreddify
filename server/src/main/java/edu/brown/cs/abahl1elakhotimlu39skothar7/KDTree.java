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

  public KDNode findMin(KDNode curMin, int dim) {
    // if the KDTree's data attribute is null, this is a leaf node, so the curMin is returned.
    if (this.root == null) {
      return curMin;
    } else {
      // if a leaf node has not yet been reached
      // usableDim determines which coordinate in the points need to be compared
      int usableDim = dim % dimensions;
      if (curMin.getMetric(usableDim) < this.root.getMetric(usableDim)) {
        curMin = this.root;
      }
      // Right child KDTree is searched for min using result of left child KDTree search as curMin
      return this.right.findMin(this.left.findMin(curMin, dim), dim);
    }
  }

  public void rebalance(int dim) {
    if (this.right == null) {
      if (this.left == null) {
        // if both children are null, the data attribute is set to null
        this.root = null;
      } else {
        // if right child KDTree is null but left child KDTree is not
        // min at given coordLevel is found in the left child KDTree
        KDNode newMin = this.left.findMin(this.left.root, dim);
        // this min is set to current KDTree's data attribute
        this.root = newMin;
        // the min is removed from its previous position in the left child KDTree
        this.left.remove(newMin, dim + 1);
        // right child KDTree is replaced with left child KDTree
        this.right = this.left;
        // left child KDTree is set to an empty KDTree with same dimensions as current KDTree
        this.left = new KDTree(dimensions, new ArrayList<>());
      }
    } else {
      // if right child KDTree is not null
      // min at given coordLevel is found in the right child KDTree
      KDNode newMin = this.right.findMin(this.right.root, dim);
      // this min is set to current KDTree's data attribute
      this.root = newMin;
      // the min is removed from its previous position in the right child KDTree
      this.right.remove(newMin, dim + 1);
    }
  }

  public void remove(KDNode n, int dim) {
    // when the current data attribute is the pao to be removed, rebalance() is called
    if (this.root.hashCode() == (n.hashCode())) {
      this.rebalance(dim);
    } else if (this.root == null) {
      // if leaf node is reached without having found the point to be removed, exception is thrown
      throw new RuntimeException("ERROR: The point couldn't be found in this tree");
    } else {
      // iterates down the tree to find the place that pao will be if it is in the KDTree
      int curDim = dim % dimensions;
      if (n.getMetric(curDim) < this.root.getMetric(curDim)) {
        this.left.remove(n, dim + 1);
      } else {
        this.right.remove(n, dim + 1);
      }
    }
  }

  public KDNode getRoot() {
    return root;
  }

}
