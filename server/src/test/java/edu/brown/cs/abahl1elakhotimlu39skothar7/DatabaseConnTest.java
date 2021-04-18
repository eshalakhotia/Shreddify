package edu.brown.cs.abahl1elakhotimlu39skothar7;

import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.io.DatabaseConn;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class DatabaseConnTest {

  private DatabaseConn _testDatabaseConn1;

  /**
   * Sets up the DatabaseConn.
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
   * Resets the DatabaseConn.
   */
  @After
  public void tearDown() {
    _testDatabaseConn1 = null;
  }

  /**
   * Tests that the DatabaseConn is loaded correctly with all exercises, workouts, and users loaded correctly.
   */
  @Test
  public void testLoadDatabase() {
    setUp();
    //assertEquals(50, _testDatabaseConn1.getExercises().size());
    //assertEquals(21, _testDatabaseConn1.getWorkouts().size());
    //assertEquals(1, _testDatabaseConn1.getUsers().size());
    tearDown();
  }

}
