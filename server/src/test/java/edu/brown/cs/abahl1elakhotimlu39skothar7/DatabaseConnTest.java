package edu.brown.cs.abahl1elakhotimlu39skothar7;

import java.util.*;

import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.*;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.kdtree.KDNode;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.kdtree.KDTree;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseConnTest {

  private DatabaseConn _testDatabaseConn1;

  /**
   * Sets up the Points using a few sample coordinates.
   */
  @Before
  public void setUp() {
    try {
      _testDatabaseConn1 = new DatabaseConn();
    } catch (Exception E) {
      _testDatabaseConn1 = null;
    }
  }

  /**
   * Resets the Points.
   */
  @After
  public void tearDown() {
    _testDatabaseConn1 = null;
  }

  @Test
  public void testLoadDatabase() {
    setUp();
    assertEquals(50, _testDatabaseConn1.getExercises().size());
    assertEquals(21, _testDatabaseConn1.getWorkouts().size());
    assertEquals(1, _testDatabaseConn1.getUsers().size());
    tearDown();
  }

}
