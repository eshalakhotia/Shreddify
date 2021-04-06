package edu.brown.cs.abahl1elakhotimlu39skothar7;

import java.util.*;

import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.Exercise;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.OutEdgeCache;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.Workout;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.WorkoutConnection;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.kdtree.KDNode;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.kdtree.KDTree;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkoutExerciseTest {

  private Workout _workout1;
  private Workout _workout2;
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
    _bicycleCrunches = new Exercise("bicycleCrunches40", "40 bicycle crunches", 60, 60, 40, targetAreas1, new HashSet<>());
    _jumpingJacks = new Exercise("jumpingJacks100", "100 jumping jacks", 88, 300, 100, targetAreas2, new HashSet<>());
    _wallSit = new Exercise("wallSit60", "60 sec wall sit", 85, 60, 1, targetAreas3, equipment1);
    _toeTouches = new Exercise("toeTouches60", "60 sec toe touches", 32, 60, 1, targetAreas4, new HashSet<>());
    _pushUps = new Exercise("pushUps20", "20 push ups", 46, 70, 20, targetAreas5, new HashSet<>());
    _russianTwists = new Exercise("russianTwists40", "40 push ups", 56, 70, 40, targetAreas1, equipment2);
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
  }

  /**
   ** Tests the get methods in Exercise objects
   */
  @Test
  public void testExerciseGetters() {
    setUp();
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
    assertEquals("40 bicycle crunches", _bicycleCrunches.getExerciseName());
    assertEquals("20 push ups", _pushUps.getExerciseName());
    assertEquals("60 sec wall sit", _wallSit.getExerciseName());
    assertEquals(70, _russianTwists.getExerciseTime());
    assertEquals(60, _bicycleCrunches.getExerciseTime());
    assertEquals(300, _jumpingJacks.getExerciseTime());
    assertEquals(100, _jumpingJacks.getExerciseReps());
    assertEquals(1, _toeTouches.getExerciseReps());
    assertEquals(20, _pushUps.getExerciseReps());
    assertEquals(85, _wallSit.getExerciseDifficulty(), 0.001);
    assertEquals(32, _toeTouches.getExerciseDifficulty(), 0.001);
    assertEquals(56, _russianTwists.getExerciseDifficulty(), 0.001);
    assertTrue(targetAreas4.equals(_toeTouches.getExerciseMuscle()));
    assertTrue(targetAreas5.equals(_pushUps.getExerciseMuscle()));
    assertTrue(targetAreas2.equals(_jumpingJacks.getExerciseMuscle()));
    assertTrue(_bicycleCrunches.getExerciseEquipment().equals(new HashSet<String>()));
    assertTrue(_wallSit.getExerciseEquipment().equals(equipment1));
    assertTrue(_russianTwists.getExerciseEquipment().equals(equipment2));
    tearDown();
  }

  /**
   ** Tests the get methods in Exercise objects
   */
  @Test
  public void testWorkoutConstructor() {
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
    OutEdgeCache cache1 = new OutEdgeCache<WorkoutConnection, Workout>();
    OutEdgeCache cache2 = new OutEdgeCache<WorkoutConnection, Workout>();
    _workout1 = new Workout("new workout 1", "skajdhfalsd", 10, exerciseList1, cache1);
    _workout2 = new Workout("new workout 2", "asdfkjhasdlf", 1, exerciseList2, cache2);
    assertEquals("skajdhfalsd", _workout1.getID());
    assertEquals("asdfkjhasdlf", _workout2.getID());
    assertEquals(7, _workout1.getDim());
    assertEquals(7, _workout2.getDim());
    assertEquals(620, _workout2.getMetric(0), 0.1);
    assertEquals(71.2, _workout2.getMetric(1), 0.1);
    assertEquals(0.20, _workout2.getMetric(2), 0.01);
    assertEquals(0.20, _workout2.getMetric(3), 0.01);
    assertEquals(0.20, _workout2.getMetric(4), 0.01);
    assertEquals(0.27, _workout2.getMetric(5), 0.01);
    assertEquals(0.09, _workout2.getMetric(6), 0.01);
    assertEquals(3700, _workout1.getMetric(0), 0.1);
    assertEquals(620, _workout2.getMetric("time"), 0.1);
    assertEquals(71.2, _workout2.getMetric("difficulty"), 0.1);
    assertEquals(0.20, _workout2.getMetric("cardio"), 0.01);
    assertEquals(0.20, _workout2.getMetric("abs"), 0.01);
    assertEquals(0.20, _workout2.getMetric("legs"), 0.01);
    assertEquals(0.27, _workout2.getMetric("arms"), 0.01);
    assertEquals(0.09, _workout2.getMetric("glutes"), 0.01);
    assertEquals(3700, _workout1.getMetric(0), 0.1);
    assertEquals(80, _workout1.getMetric(1), 0.1);
    assertEquals(0.27, _workout1.getMetric(2), 0.01);
    assertEquals(0, _workout1.getMetric(3), 0.01);
    assertEquals(0.27, _workout1.getMetric(4), 0.01);
    assertEquals(0.45, _workout1.getMetric(5), 0.01);
    assertEquals(0, _workout1.getMetric(6), 0.01);
    assertEquals(7, _workout1.getAllMetrics().size());
    assertEquals(0.5, _workout1.getParameterToUpdate(), 0.0001);
    assertEquals(0.5, _workout2.getParameterToUpdate(), 0.0001);
    _workout1.setParameterToUpdate(0.99);
    _workout2.setParameterToUpdate(0.01);
    assertEquals(0.99, _workout1.getParameterToUpdate(), 0.0001);
    assertEquals(0.01, _workout2.getParameterToUpdate(), 0.0001);
    assertEquals(441, _workout1.calcDistance(_workout2), 1);
    tearDown();
  }


}
