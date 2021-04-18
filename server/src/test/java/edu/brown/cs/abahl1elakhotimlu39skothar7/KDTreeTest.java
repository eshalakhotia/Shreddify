package edu.brown.cs.abahl1elakhotimlu39skothar7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.kdtree.KDNode;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.kdtree.KDTree;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class KDTreeTest {

  private KDTree _tree1;
  private KDTree _tree2;
  private KDTree _tree3;
  private Point saturn = new Point(new double[] {43,36,15});
  private Point mars = new Point(new double[] {29,16,63});
  private Point venus = new Point(new double[]{23,24,61});
  private Point jupiterMoon = new Point(new double[] {42,14,27});
  private Point pluto = new Point(new double[] {576,201,3002});

  public class Point implements KDNode<Point> {
    public double [] coordinates;

    public Point(double[] coordinates) {
      this.coordinates = coordinates;
    }

    @Override
    public int getDim() {
      return coordinates.length;
    }

    @Override
    public double getMetric(int i) {
      return coordinates[i % getDim()];
    }

    public HashMap<String, Double> getAllMetrics() {
      HashMap<String, Double> allMetrics = new HashMap<String, Double>();
      for (int i = 0; i < getDim(); i++) {
        allMetrics.put(String.valueOf(i), coordinates[i]);
      }
      return allMetrics;
    }

    public double calcDistance(Point p) {
      if (p.getDim() == getDim()) {
        double totalDistance = 0;
        for (int i = 0; i < getDim(); i++) {
          totalDistance += Math.pow(p.getMetric(i) - getMetric(i), 2);
        }
        return Math.sqrt(totalDistance);
      } else {
        return -1;
      }
    }

  }

  /**
   * Sets up the Points using a few sample coordinates.
   */
  @Before
  public void setUp() {
    _tree1 = new KDTree(new ArrayList<>(),2);
    List<Point> tree2List = new ArrayList<>();
    tree2List.add(saturn);
    List<Point> tree3List = new ArrayList<>();
    tree3List.add(saturn);
    tree3List.add(mars);
    tree3List.add(venus);
    tree3List.add(jupiterMoon);
    tree3List.add(pluto);
    _tree2 = new KDTree(tree2List, 3);
    _tree3 = new KDTree(tree3List, 3);
  }

  /**
   * Resets the Points.
   */
  @After
  public void tearDown() {
    _tree1 = null;
    _tree2 = null;
    _tree3 = null;
  }

  /**
   ** Tests whether the KDTree contains new elements after the add() method has been applied.
   */
  @Test
  public void testAdd() {
    setUp();
    _tree2.add(mars, 0);
    _tree2.add(venus, 0);
    _tree2.add(jupiterMoon, 0);
    _tree2.add(pluto, 0);
    assertTrue(_tree2.getLeft().getRoot().getMetric(0) == 29);
    assertTrue(_tree2.getLeft().getRoot().getMetric(1) == 16);
    assertTrue(_tree2.getLeft().getRoot().getMetric(2) == 63);
    assertTrue(_tree2.getRight().getRoot().getMetric(0) == 576);
    assertTrue(_tree2.getRight().getRoot().getMetric(1) == 201);
    assertTrue(_tree2.getRight().getRoot().getMetric(2) == 3002);
    assertTrue(_tree2.getLeft().getLeft().getRoot().getMetric(0) == 42);
    assertTrue(_tree2.getLeft().getLeft().getRoot().getMetric(1) == 14);
    assertTrue(_tree2.getLeft().getLeft().getRoot().getMetric(2) == 27);
    assertTrue(_tree2.getLeft().getRight().getRoot().getMetric(0) == 23);
    assertTrue(_tree2.getLeft().getRight().getRoot().getMetric(1) == 24);
    assertTrue(_tree2.getLeft().getRight().getRoot().getMetric(2) == 61);
    assertTrue(_tree2.getRight().getLeft().getRoot() == null);
    assertTrue(_tree2.getRight().getRight().getRoot() == null);
    assertTrue(_tree2.getLeft().getLeft().getLeft().getRoot() == null);
    assertTrue(_tree2.getLeft().getLeft().getRight().getRoot() == null);
    assertTrue(_tree2.getLeft().getRight().getLeft().getRoot() == null);
    assertTrue(_tree2.getLeft().getRight().getRight().getRoot() == null);
    _tree3.add(new Point(new double[] {577, 202, 3003}), 0);
    assertTrue(_tree3.getRight().getRight().getRight().getRoot().getMetric(0) == 577);
    assertTrue(_tree3.getRight().getRight().getRight().getRoot().getMetric(1) == 202);
    assertTrue(_tree3.getRight().getRight().getRight().getRoot().getMetric(2) == 3003);
    _tree3.add(new Point(new double[] {575, 200, 3001}), 0);
    assertTrue(_tree3.getRight().getRight().getLeft().getRoot().getMetric(0) == 575);
    assertTrue(_tree3.getRight().getRight().getLeft().getRoot().getMetric(1) == 200);
    assertTrue(_tree3.getRight().getRight().getLeft().getRoot().getMetric(2) == 3001);
    tearDown();
  }


  /**
   ** Tests whether PointAndOther with the min coordinate in the min dimension is returned.
   */
  @Test
  public void findMin() {
    setUp();
    assertTrue(_tree1.findMin(saturn, 0).getMetric(0) == 43);
    assertTrue(_tree1.findMin(saturn, 0).getMetric(1) == 36);
    assertTrue(_tree1.findMin(saturn, 0).getMetric(2) == 15);
    assertTrue(_tree3.findMin(saturn, 0).getMetric(0) == 23);
    assertTrue(_tree3.findMin(saturn, 0).getMetric(1) == 24);
    assertTrue(_tree3.findMin(saturn, 0).getMetric(2) == 61);
    assertTrue(_tree3.findMin(venus, 1).getMetric(0) == 42);
    assertTrue(_tree3.findMin(venus, 1).getMetric(1) == 14);
    assertTrue(_tree3.findMin(venus, 1).getMetric(2) == 27);
    tearDown();
  }
  /**
   ** Tests whether the relevant node is removed from the KDTree after remove() is called.
   */
  @Test
  public void testRemove() {
    setUp();
    _tree2.remove(saturn, 0);
    assertTrue(_tree2.getRoot() == null);
    _tree3.remove(mars, 0);
    assertTrue(_tree3.getRoot().getMetric(0) == 42);
    assertTrue(_tree3.getRoot().getMetric(1) == 14);
    assertTrue(_tree3.getRoot().getMetric(2) == 27);
    assertTrue(_tree3.getLeft().getRoot().getMetric(0) == 23);
    assertTrue(_tree3.getLeft().getRoot().getMetric(1) == 24);
    assertTrue(_tree3.getLeft().getRoot().getMetric(2) == 61);
    assertTrue(_tree3.getRight().getRoot().getMetric(0) == 43);
    assertTrue(_tree3.getRight().getRoot().getMetric(1) == 36);
    assertTrue(_tree3.getRight().getRoot().getMetric(2) == 15);
    assertTrue(_tree3.getRight().getRight().getRoot().getMetric(0) == 576);
    assertTrue(_tree3.getRight().getRight().getRoot().getMetric(1) == 201);
    assertTrue(_tree3.getRight().getRight().getRoot().getMetric(2) == 3002);
    assertTrue(_tree3.getLeft().getLeft().getRoot() == null);
    assertTrue(_tree3.getLeft().getRight().getRoot() == null);
    assertTrue(_tree3.getRight().getLeft().getRoot() == null);
    tearDown();
    setUp();
    _tree3.remove(jupiterMoon, 0);
    assertTrue(_tree3.getRoot().getMetric(0) == 43);
    assertTrue(_tree3.getRoot().getMetric(1) == 36);
    assertTrue(_tree3.getRoot().getMetric(2) == 15);
    assertTrue(_tree3.getLeft().getRoot().getMetric(0) == 29);
    assertTrue(_tree3.getLeft().getRoot().getMetric(1) == 16);
    assertTrue(_tree3.getLeft().getRoot().getMetric(2) == 63);
    assertTrue(_tree3.getLeft().getRight().getRoot().getMetric(0) == 23);
    assertTrue(_tree3.getLeft().getRight().getRoot().getMetric(1) == 24);
    assertTrue(_tree3.getLeft().getRight().getRoot().getMetric(2) == 61);
    assertTrue(_tree3.getLeft().getLeft().getRoot() == null);
    assertTrue(_tree3.getRight().getRoot().getMetric(0) == 576);
    assertTrue(_tree3.getRight().getRoot().getMetric(1) == 201);
    assertTrue(_tree3.getRight().getRoot().getMetric(2) == 3002);
    assertTrue(_tree3.getRight().getLeft().getRoot() == null);
    assertTrue(_tree3.getRight().getRight().getRoot() == null);
    _tree3.remove(pluto, 0);
    assertTrue(_tree3.getRoot().getMetric(0) == 43);
    assertTrue(_tree3.getRoot().getMetric(1) == 36);
    assertTrue(_tree3.getRoot().getMetric(2) == 15);
    assertTrue(_tree3.getLeft().getRoot().getMetric(0) == 29);
    assertTrue(_tree3.getLeft().getRoot().getMetric(1) == 16);
    assertTrue(_tree3.getLeft().getRoot().getMetric(2) == 63);
    assertTrue(_tree3.getLeft().getRight().getRoot().getMetric(0) == 23);
    assertTrue(_tree3.getLeft().getRight().getRoot().getMetric(1) == 24);
    assertTrue(_tree3.getLeft().getRight().getRoot().getMetric(2) == 61);
    assertTrue(_tree3.getLeft().getLeft().getRoot() == null);
    assertTrue(_tree3.getRight().getRoot() == null);
    _tree3.remove(saturn, 0);
    assertTrue(_tree3.getRoot().getMetric(0) == 23);
    assertTrue(_tree3.getRoot().getMetric(1) == 24);
    assertTrue(_tree3.getRoot().getMetric(2) == 61);
    assertTrue(_tree3.getRight().getRoot().getMetric(0) == 29);
    assertTrue(_tree3.getRight().getRoot().getMetric(1) == 16);
    assertTrue(_tree3.getRight().getRoot().getMetric(2) == 63);
    assertTrue(_tree3.getLeft().getRoot() == null);
    assertTrue(_tree3.getRight().getRight().getRoot() == null);
    assertTrue(_tree3.getRight().getLeft().getRoot() == null);
    tearDown();
  }

  /**
   ** Tests whether the nearest neighbors are returned by the nearest neighbors algorithm.
   */
  @Test
  public void testNearestNeighbors() {
    setUp();
    List<Point> resultList1 = _tree3.kNearestNeighbors(
            new Point(new double[] {10000, 10000, 1000}), 1);
    assertEquals(resultList1.size(), 1, 0.5);
    assertTrue(resultList1.get(0).getMetric(0) == 576);
    assertTrue(resultList1.get(0).getMetric(1) == 201);
    assertTrue(resultList1.get(0).getMetric(2) == 3002);
    List<Point> resultList2 = _tree3.kNearestNeighbors(
            new Point(new double[] {26.1, 19.5, 62.0}), 2);
    assertEquals(resultList2.size(), 2, 0.5);
    assertTrue(resultList2.get(0).getMetric(0) == 29);
    assertTrue(resultList2.get(0).getMetric(1) == 16);
    assertTrue(resultList2.get(0).getMetric(2) == 63);
    assertTrue(resultList2.get(1).getMetric(0) == 23);
    assertTrue(resultList2.get(1).getMetric(1) == 24);
    assertTrue(resultList2.get(1).getMetric(2) == 61);
    List<Point> resultList3 = _tree3.kNearestNeighbors(
            new Point(new double[] {-1000, 22, 25.953645}), 3);
    assertEquals(resultList3.size(), 3, 0.5);
    assertTrue(resultList3.get(0).getMetric(0) == 23);
    assertTrue(resultList3.get(0).getMetric(1) == 24);
    assertTrue(resultList3.get(0).getMetric(2) == 61);
    assertTrue(resultList3.get(1).getMetric(0) == 29);
    assertTrue(resultList3.get(1).getMetric(1) == 16);
    assertTrue(resultList3.get(1).getMetric(2) == 63);
    assertTrue(resultList3.get(2).getMetric(0) == 42);
    assertTrue(resultList3.get(2).getMetric(1) == 14);
    assertTrue(resultList3.get(2).getMetric(2) == 27);
    tearDown();
  }
}
