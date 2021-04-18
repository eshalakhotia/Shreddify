package edu.brown.cs.abahl1elakhotimlu39skothar7;

import java.util.*;

import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents.Exercise;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.OutEdgeCache;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents.Workout;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents.WorkoutConnection;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkoutAndConnectionTest {

  private Workout _workout1;
  private Workout _workout2;
  private Workout _workout3;
  private Exercise _bicycleCrunches;
  private Exercise _jumpingJacks;
  private Exercise _wallSit;
  private Exercise _toeTouches;
  private Exercise _pushUps;
  private Exercise _russianTwists;

  /**
   * Sets up the Points using a few sample coordinates.
   */
  @Before
  public void setUp() {
    Set<String> targetAreas1 = new HashSet<>();
    targetAreas1.add("abs");
    Set<String> targetAreas2 = new HashSet<>();
    targetAreas2.add("cardio");
    targetAreas2.add("legs");
    targetAreas2.add("arms");
    Set<String> targetAreas3 = new HashSet<>();
    targetAreas3.add("glutes");
    Set<String> equipment1 = new HashSet<>();
    equipment1.add("flat wall");
    Set<String> targetAreas4 = new HashSet<>();
    targetAreas4.add("cardio");
    targetAreas4.add("legs");
    Set<String> targetAreas5 = new HashSet<>();
    targetAreas5.add("arms");
    Set<String> equipment2 = new HashSet<>();
    equipment2.add("5 lb dumbbell");
    _bicycleCrunches = new Exercise("bicycleCrunches40", "40 bicycle crunches", 60, 60, 40, "reps", targetAreas1, new HashSet<>(), "");
    _jumpingJacks = new Exercise("jumpingJacks100", "100 jumping jacks", 88, 300, 100,"reps", targetAreas2, new HashSet<>(), "");
    _wallSit = new Exercise("wallSit60", "60 sec wall sit", 85, 60, 1,"time", targetAreas3, equipment1, "");
    _toeTouches = new Exercise("toeTouches60", "60 sec toe touches", 32, 60, 1,"time", targetAreas4, new HashSet<>(), "");
    _pushUps = new Exercise("pushUps20", "20 push ups", 46, 70, 20,"reps", targetAreas5, new HashSet<>(), "");
    _russianTwists = new Exercise("russianTwists40", "40 push ups", 56, 70, 40,"reps", targetAreas1, equipment2, "");
    List<Exercise> exerciseList1 = new ArrayList<Exercise>();
    exerciseList1.add(_jumpingJacks);
    exerciseList1.add( _pushUps);
    List<Exercise> exerciseList2 = new ArrayList<Exercise>();
    exerciseList2.add(_wallSit);
    exerciseList2.add( _bicycleCrunches);
    exerciseList2.add(_toeTouches);
    exerciseList2.add( _pushUps);
    exerciseList2.add( _russianTwists);
    exerciseList2.add( _jumpingJacks);
    List<Exercise> exerciseList3 = new ArrayList<Exercise>();
    exerciseList3.add(_bicycleCrunches);
    exerciseList3.add(_toeTouches);
    exerciseList3.add(_russianTwists);
    exerciseList3.add(_jumpingJacks);
    OutEdgeCache cache1 = new OutEdgeCache<WorkoutConnection, Workout>();
    OutEdgeCache cache2 = new OutEdgeCache<WorkoutConnection, Workout>();
    OutEdgeCache cache3 = new OutEdgeCache<WorkoutConnection, Workout>();
    _workout1 = new Workout("new workout 1", "skajdhfalsd", 10, exerciseList1, cache1);
    _workout2 = new Workout("new workout 2", "asdfkjhasdlf", 1, exerciseList2, cache2);
    _workout3 = new Workout("new workout 3", "iequowrhjfk", 4, exerciseList3, cache3);
  }

  /**
   * Resets the Points.
   */
  @After
  public void tearDown() {
    _bicycleCrunches = null;
    _jumpingJacks = null;
    _wallSit = null;
    _toeTouches = null;
    _pushUps = null;
    _russianTwists = null;
    _workout1 = null;
    _workout2 = null;
  }

  /**
   ** Tests the getID method in Workout objects
   */
  @Test
  public void testWorkoutGetID() {
    setUp();
    assertEquals("skajdhfalsd", _workout1.getID());
    assertEquals("asdfkjhasdlf", _workout2.getID());
    tearDown();
  }

  /**
   ** Tests the getName method in Workout objects
   */
  @Test
  public void testWorkoutGetName() {
    setUp();
    assertEquals("new workout 1", _workout1.getName());
    assertEquals("new workout 2", _workout2.getName());
    tearDown();
  }

  /**
   ** Tests that the getDim method and the getAllMetrics method works in Workout objects
   */
  @Test
  public void testGetDimAndGetAllMetrics() {
    setUp();
    assertEquals(9, _workout1.getDim());
    assertEquals(9, _workout2.getDim());
    assertEquals(9, _workout1.getAllMetrics().size());
    tearDown();
  }

  /**
   ** Tests the getMetric method in Workout objects
   */
  @Test
  public void testWorkoutGetMetric() {
    setUp();
    assertEquals(620, _workout2.getMetric(0), 0.1);
    assertEquals(78.3, _workout2.getMetric(1), 0.1);
    assertEquals(20, _workout2.getMetric(2), 1);
    assertEquals(20, _workout2.getMetric(3), 1);
    assertEquals(20, _workout2.getMetric(4), 1);
    assertEquals(27, _workout2.getMetric(5), 1);
    assertEquals(9, _workout2.getMetric(6), 1);
    assertEquals(3700, _workout1.getMetric(0), 1);
    assertEquals(620, _workout2.getMetric("time"), 0.1);
    assertEquals(78.3, _workout2.getMetric("difficulty"), 0.1);
    assertEquals(20, _workout2.getMetric("cardio"), 1);
    assertEquals(20, _workout2.getMetric("abs"), 1);
    assertEquals(20, _workout2.getMetric("legs"), 1);
    assertEquals(27, _workout2.getMetric("arms"), 1);
    assertEquals(9, _workout2.getMetric("glutes"), 1);
    assertEquals(3700, _workout1.getMetric(0), 0.1);
    assertEquals(96.8, _workout1.getMetric(1), 0.1);
    assertEquals(27, _workout1.getMetric(2), 1);
    assertEquals(0, _workout1.getMetric(3), 1);
    assertEquals(27, _workout1.getMetric(4), 1);
    assertEquals(45, _workout1.getMetric(5), 1);
    assertEquals(0, _workout1.getMetric(6), 1);
    tearDown();
  }

  /**
   ** Tests that the parameterToUpdate field has valid get and set methods.
   */
  @Test
  public void testParameterToUpdate() {
    setUp();
    assertEquals(50, _workout1.getParameterToUpdate(), 0.0001);
    assertEquals(50, _workout2.getParameterToUpdate(), 0.0001);
    _workout1.setParameterToUpdate(99);
    _workout2.setParameterToUpdate(1);
    assertEquals(99, _workout1.getParameterToUpdate(), 0.0001);
    assertEquals(1, _workout2.getParameterToUpdate(), 0.0001);
    tearDown();
  }

  /**
   ** Tests that the WorkoutConnection object has valid get methods for start and end.
   */
  @Test
  public void testWorkoutConnectionGetStartGetEnd() {
    setUp();
    WorkoutConnection w1w2 = new WorkoutConnection(_workout1, _workout2);
    WorkoutConnection w2w3 = new WorkoutConnection(_workout2, _workout3);
    WorkoutConnection w3w1 = new WorkoutConnection(_workout3, _workout1);
    boolean validStart1 = true;
    boolean validStart2 = true;
    boolean validEnd1 = true;
    boolean validEnd2 = true;
    Workout compareStart1 = w1w2.getStart();
    Workout compareEnd1 = w3w1.getEnd();
    Workout compareStart2 = w2w3.getStart();
    Workout compareEnd2 = w1w2.getEnd();
    for (int i = 0; i < _workout1.getDim(); i++) {
      validStart1 = validStart1 && (compareStart1.getMetric(i) == _workout1.getMetric(i));
      validEnd1 = validEnd1 && (compareEnd1.getMetric(i) == _workout1.getMetric(i));
    }
    for (int i = 0; i < _workout2.getDim(); i++) {
      validStart2 = validStart2 && (compareStart2.getMetric(i) == _workout2.getMetric(i));
      validEnd2 = validEnd2 && (compareEnd2.getMetric(i) == _workout2.getMetric(i));
    }
    assertTrue(validStart1);
    assertTrue(validStart2);
    assertTrue(validEnd1);
    assertTrue(validEnd2);
  }

  /**
   ** Tests the getWeight method for the WorkoutConnectionClass
   */
  @Test
  public void testWorkoutConnectionGetWeight() {
    setUp();
    WorkoutConnection w1w2 = new WorkoutConnection(_workout1, _workout2);
    WorkoutConnection w2w1 = new WorkoutConnection(_workout2, _workout1);
    WorkoutConnection w1w3 = new WorkoutConnection(_workout1, _workout3);
    WorkoutConnection w2w3 = new WorkoutConnection(_workout2, _workout3);
    WorkoutConnection w2w2 = new WorkoutConnection(_workout2, _workout2);
    WorkoutConnection w3w1 = new WorkoutConnection(_workout3, _workout1);
    assertEquals(0, w2w2.getWeight(), 0.01);
    assertEquals(w1w3.getWeight(), w3w1.getWeight(), 0.01);
    assertEquals(w1w3.getWeight(), w1w3.heuristic(w1w3.getStart(), w1w3.getEnd()), 0.01);
    assertEquals(w1w3.getWeight(), _workout1.calcDistance(_workout3), 0.01);
    assertEquals(9, w1w3.getWeight(), 1);
    assertEquals(w2w1.getWeight(), w1w2.getWeight(), 0.01);
    assertEquals(w2w1.getWeight(), w2w1.heuristic(w2w1.getStart(), w2w1.getEnd()), 0.01);
    assertEquals(w2w1.getWeight(), _workout2.calcDistance(_workout1), 0.01);
    assertEquals(14.5, w2w1.getWeight(), 1);
    assertEquals(8.3, w2w3.getWeight(), 1);
  }


}
