package edu.brown.cs.abahl1elakhotimlu39skothar7;

import java.util.Comparator;
import java.util.List;

public class KDTree {
  private KDNode root;
  private KDTree left;
  private KDTree right;

  public KDTree() {
    this.root = null;
    this.left = null;
    this.right = null;
  }

  public KDTree(List<KDNode> nodes) {
    this.buildTree(nodes, 0);
  }

  public void buildTree(List<KDNode> nodes, int curDim) throws RuntimeException {
    boolean validList = true;
    int numDim = nodes.get(0).getDim();
    for (int i = 0; i < nodes.size(); i++) {
      validList = validList && (nodes.get(i).getDim() == numDim);
    }
    if (validList) {
      nodes.sort(new Comparator<KDNode>() {
        @Override
        public int compare(KDNode kdn1, KDNode kdn2) {
          if (kdn1.getMetric(curDim % numDim) > kdn2.getMetric(curDim % numDim)) {
            return 1;
          } else if (kdn1.getMetric(curDim % numDim) < kdn2.getMetric(curDim % numDim)) {
            return -1;
          } else {
            return 0;
          }
        }
      });
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

  public void nearest() {
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

  public void remove(KDNode n) {
    
  }
}
