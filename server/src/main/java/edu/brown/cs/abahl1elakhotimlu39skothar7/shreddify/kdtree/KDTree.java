package edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.kdtree;
import java.util.*;

/**
 * Represents a KdTree of any dimension and contains KDNode objects.
 * @param <N> a KDNode of any type
 */
public class KDTree<N extends KDNode> {
  private int dimensions;
  private N root;
  private KDTree<N> left;
  private KDTree<N> right;

  /**
   * Constructor for KDTree.
   *
   * @param d     the number of dimensions of each node
   * @param nodes an ArrayList of KDNode objects
   */
  public KDTree(List<N> nodes, int d) {
    this.buildTree(nodes, d);
  }

  public KDTree() {
    this.root = null;
    this.right = null;
    this.left = null;
    this.dimensions = 0;
  }

  /**
   * Builds a balanced KDTree from the provided list of nodes.
   *
   * @param nodes an ArrayList of KDNode objects to be added to the tree
   * @param curDim the current depth of the tree that KDNodes are being added to
   */
  public void buildTree(List<N> nodes, int curDim) throws RuntimeException {
    boolean validList;
    if (!nodes.isEmpty()) {
      validList = true;
      dimensions = nodes.get(0).getDim();
      for (int i = 0; i < nodes.size(); i++) {
        validList = validList && (nodes.get(i).getDim() == dimensions);
      }
      if (validList) {
        nodes.sort(new NodeDimComparator<N>(curDim));
        if (nodes.size() == 1) {
          this.root = nodes.get(0);
          this.left = new KDTree();
          this.right = new KDTree();
        } else if (nodes.size() > 1) {
          int curMid = (nodes.size() - 1) / 2;
          this.root = nodes.get(curMid);
          List<N> leftList = nodes.subList(0, curMid);
          List<N> rightList = nodes.subList(curMid + 1, nodes.size());
          if (leftList.isEmpty()) {
            this.left = new KDTree();
          } else {
            this.left = new KDTree<N>(leftList, curDim + 1);
          }
          if (rightList.isEmpty()) {
            this.right = new KDTree();
          } else {
            this.right = new KDTree<N>(rightList, curDim + 1);
          }
        }
      } else {
        throw new RuntimeException("ERROR: the list of KDNodes passed to the KDTree was invalid");
      }
    } else {
      this.root = null;
      this.dimensions = 0;
      this.left = null;
      this.right = null;
    }

  }

  /**
   * Finds the numNeighbors nearest KDNodes to target and updates nearNeighbors to contain them.
   *
   * @param target the KDNode that we want to find the closest neighbors to
   * @param nearNeighbors the current nearest KDNodes to the target
   * @param numNeighbors the desired number of neighbors to be found
   * @param furthestDistance the furthest distance that a KDNOde in nearNeighbors is from target
   * @param dim the current depth of the tree being searched
   */
  public void neighborsHelper(
          N target,
          PriorityQueue<N> nearNeighbors,
          int numNeighbors,
          double furthestDistance,
          int dim) {
    // checks that curTree isn't a leaf node
    if (this.root != null) {
      try {
        // tries to calculate distance between target and curTree's root
        double curDistance = target.calcDistance(this.root);
        // if nearNeighbors doesn't have the correct number of neighbors,
        // current root is added to priority queue automatically
        if (nearNeighbors.size() < numNeighbors) {
          nearNeighbors.add(this.root);
        } else {
          // furthest distance set to distance between target and nearNeighbors head
          furthestDistance = target.calcDistance(nearNeighbors.peek());
          Random rand = new Random();
          // randomChance equals either 0 or 1, equal chance it could be either
          int randomChance = rand.nextInt(2);
          // if random chance is 1 or curDistance is less than furthestDistance,
          // current root is added to priority queue
          if ((curDistance < furthestDistance)
                  || ((randomChance == 1) && (curDistance == furthestDistance))) {
            nearNeighbors.poll();
            nearNeighbors.add(this.root);
          }
        }
        int curDim = dim % dimensions;
        double axisDistance = Math.abs(target.getMetric(curDim) - this.root.getMetric(curDim));
        // if axisDistance less than or equal to furthestDistance, possible nearer neighbor exists
        // both children must be searched
        if (axisDistance <= furthestDistance) {
          this.left.neighborsHelper(
                  target,
                  nearNeighbors,
                  numNeighbors,
                  furthestDistance,
                  dim + 1);
          this.right.neighborsHelper(
                  target,
                  nearNeighbors,
                  numNeighbors,
                  furthestDistance,
                  dim + 1);
          // else we just need to search the branch that target point would be on
        } else if (target.getMetric(curDim) > this.root.getMetric(curDim)) {
          this.right.neighborsHelper(
                  target,
                  nearNeighbors,
                  numNeighbors,
                  furthestDistance,
                  dim + 1);
        } else if (target.getMetric(curDim) < this.root.getMetric(curDim)) {
          this.left.neighborsHelper(
                  target,
                  nearNeighbors,
                  numNeighbors,
                  furthestDistance,
                  dim + 1);
        }
      } catch (Exception e) {
        throw new RuntimeException(
                "ERROR: Dimensions of the target point don't match the dimensions of the KD Tree");
      }
    }
  }

  /**
   * Searches through KdTree to return a list of k nearest neighbors.
   *
   * @param target   the node from which to find the nearest neighbors
   * @param numNeighbors        the number of nearest neighbors to return
   * @return an ArrayList of nodes representing the nearest neighbors
   */
  public List<N> kNearestNeighbors(N target, int numNeighbors) {
    // creates list to be returned
    List<N> neighbors = new ArrayList<N>();
    // creates PriorityQueue to sort KDNodes based on distance from target in descending order
    PriorityQueue<N> neighborsPQ = new PriorityQueue<N>(new Comparator<N>() {
      @Override
      public int compare(N kdn1, N kdn2) {
        if (target.calcDistance(kdn1) > target.calcDistance(kdn2)) {
          return -1;
        } else if (target.calcDistance(kdn1) < target.calcDistance(kdn2)) {
          return 1;
        } else {
          return 0;
        }
      }
    });
    neighborsHelper(target, neighborsPQ, numNeighbors, Integer.MAX_VALUE, 0);
    for (int i = 0; i < numNeighbors; i++) {

      //TEMPORARY FIX -- null workouts are being added to nearest neighbors list
      if (neighborsPQ.peek() != null) {
        neighbors.add(0, neighborsPQ.poll());
      }
    }
    return neighbors;
  }

  /**
   * Adds the given KDNode to KDTree, parsed through using relevant dimension comparisons.
   * @param n the KDNode to be added to the KDTree.
   * @param curDim the current dimension to be compared as based on the current depth of KDTree.
   */
  public void add(N n, int curDim) {
    if (this.root == null) {
      this.root = n;
      this.right = new KDTree<>();
      this.left = new KDTree<>();
    } else {
      if (n.getMetric(curDim) < this.root.getMetric(curDim)) {
        this.left.add(n, curDim + 1);
      } else {
        this.right.add(n, curDim + 1);
      }
    }
  }

  /**
   * Finds KDNode that has the min metric at the given dim.
   * @param curMin the current KDNode with the min metric at the given dim.
   * @param dim the current dimension to be compared as based on the current depth of KDTree.
   * @return KDNode with the min metric at the given dim.
   */
  public N findMin(N curMin, int dim) {
    // if the KDTree's data attribute is null, this is a leaf node, so the curMin is returned.
    if (this.root == null) {
      return curMin;
    } else {
      // if a leaf node has not yet been reached
      // usableDim determines which coordinate in the points need to be compared
      int usableDim = dim % dimensions;
      if (curMin.getMetric(usableDim) > this.root.getMetric(usableDim)) {
        curMin = this.root;
      }
      return this.right.findMin(this.left.findMin(curMin, dim), dim);
    }
  }

  /**
   * Rebalances the tree by finding the node with the root with the minimum ith coordinate,
   * where i = given dim, changing this node to the root of the tree,
   * and then removing it from its previous position.
   * (this ends up eliminating the current root from the tree)
   * @param dim the dimension that we want to find the minimum coordinate in.
   */
  public void rebalance(int dim) {
    if (this.right.getRoot() == null) {
      if (this.left.getRoot() == null) {
        // if both children are null, the root is set to null
        // THE PROBLEM IS HERE ON LINE 269
        this.root = null;
        this.left = null;
        this.right = null;
      } else {
        // if right child KDTree is null but left child KDTree is not
        // min at given dim is found in the left child KDTree
        N newMin = this.left.findMin(this.left.root, dim);
        // this min is set to current KDTree's root
        this.root = newMin;
        // the min is removed from its previous position in the left child KDTree
        this.left.remove(newMin, dim + 1);
        // right child KDTree is replaced with left child KDTree
        this.right = this.left;
        // left child KDTree is set to an empty KDTree with same dimensions as current KDTree
        this.left = new KDTree();
      }
    } else {
      // if right child KDTree is not null
      // min at given dim is found in the right child KDTree
      N newMin = this.right.findMin(this.right.root, dim);
      // this min is set to current KDTree's root
      this.root = newMin;
      // the min is removed from its previous position in the right child KDTree
      this.right.remove(newMin, dim + 1);
    }
  }

  /**
   * Removes given KDNode from KDTree, parsed through using relevant dimension comparisons.
   * @param n the KDNode to be removed from the KDTree.
   * @param dim the current dimension to be compared as based on the current depth of KDTree.
   */
  public void remove(N n, int dim) {
    // when the current root is the KDNode to be removed, rebalance() is called
    if (this.root != null) {
      boolean sameMetrics = (this.root.getAllMetrics().size() == n.getAllMetrics().size());
      Set<String> thisKeys = this.root.getAllMetrics().keySet();
      Iterator iterate = thisKeys.iterator();
      while (iterate.hasNext()) {
        Object curKey = iterate.next();
        sameMetrics = sameMetrics
                && (this.root.getAllMetrics().get(curKey).equals(n.getAllMetrics().get(curKey)));
      }
      if (sameMetrics) {
        this.rebalance(dim);
      } else {
        // iterates down the tree to find the place that KDNode will be if it is in the KDTree
        int curDim = dim % dimensions;
        if (n.getMetric(curDim) < this.root.getMetric(curDim)) {
          if (this.left != null) {
            this.left.remove(n, dim + 1);
          }
        } else {
          if (this.right != null) {
            this.right.remove(n, dim + 1);
          }
        }
      }
    }
  }

  /**
   * @return the root KDNode of the current KDTree
   */
  public N getRoot() {
    return root;
  }

  /**
   * @return the left subchild of the current KDTree
   */
  public KDTree<N> getLeft() {
    return this.left;
  }

  /**
   * @return the right subchild of the current KDTree
   */
  public KDTree<N> getRight() {
    return this.right;
  }

}
