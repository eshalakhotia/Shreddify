package edu.brown.cs.abahl1elakhotimlu39skothar7;

import java.util.*;

import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents.Exercise;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents.Workout;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExerciseTest {

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
    _bicycleCrunches = new Exercise("bicycleCrunches40", "40 bicycle crunches", 60, 60, 40, "reps", targetAreas1, new HashSet<>(), "");
    _jumpingJacks = new Exercise("jumpingJacks100", "100 jumping jacks", 88, 300, 100,"reps", targetAreas2, new HashSet<>(), "");
    _wallSit = new Exercise("wallSit60", "60 sec wall sit", 85, 60, 1,"time", targetAreas3, equipment1, "");
    _toeTouches = new Exercise("toeTouches60", "60 sec toe touches", 32, 60, 1,"time", targetAreas4, new HashSet<>(), "");
    _pushUps = new Exercise("pushUps20", "20 push ups", 46, 70, 20,"reps", targetAreas5, new HashSet<>(), "");
    _russianTwists = new Exercise("russianTwists40", "40 push ups", 56, 70, 40,"reps", targetAreas1, equipment2, "");
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
    assertEquals("pushUps20", _pushUps.getExerciseId());
    assertEquals("wallSit60", _wallSit.getExerciseId());
    assertEquals("russianTwists40", _russianTwists.getExerciseId());
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


}
