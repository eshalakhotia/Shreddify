package edu.brown.cs.abahl1elakhotimlu39skothar7;

import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.OutEdgeCache;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.io.DatabaseConn;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents.Exercise;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents.User;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents.Workout;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents.WorkoutConnection;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DatabaseConnTest {

  private DatabaseConn _testDatabaseConn1;
  private User _user1;
  private User _user2;
  private Workout workout1;
  private Workout workout2;
  private Workout workout3;
  private Workout workout4;
  private Workout workout5;
  private Workout workout6;

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
    Exercise bicycleCrunches = new Exercise("bicycleCrunches40", "40 bicycle crunches", 60, 60, 40, "reps", targetAreas1, new HashSet<>(), "");
    Exercise jumpingJacks = new Exercise("jumpingJacks100", "100 jumping jacks", 88, 300, 100,"reps", targetAreas2, new HashSet<>(), "");
    Exercise wallSit = new Exercise("wallSit60", "60 sec wall sit", 85, 60, 1,"time", targetAreas3, equipment1, "");
    Exercise toeTouches = new Exercise("toeTouches60", "60 sec toe touches", 32, 60, 1,"time", targetAreas4, new HashSet<>(), "");
    Exercise pushUps = new Exercise("pushUps20", "20 push ups", 46, 70, 20,"reps", targetAreas5, new HashSet<>(), "");
    Exercise russianTwists = new Exercise("russianTwists40", "40 push ups", 56, 70, 40,"reps", targetAreas1, equipment2, "");
    List<Exercise> exerciseList1 = new ArrayList<Exercise>();
    exerciseList1.add(jumpingJacks);
    exerciseList1.add(pushUps);
    List<Exercise> exerciseList2 = new ArrayList<Exercise>();
    exerciseList2.add(wallSit);
    exerciseList2.add( bicycleCrunches);
    exerciseList2.add(toeTouches);
    exerciseList2.add(pushUps);
    exerciseList2.add(russianTwists);
    exerciseList2.add(jumpingJacks);
    List<Exercise> exerciseList3 = new ArrayList<Exercise>();
    exerciseList3.add(bicycleCrunches);
    exerciseList3.add(toeTouches);
    exerciseList3.add(russianTwists);
    exerciseList3.add(jumpingJacks);
    List<Exercise> exerciseList4 = new ArrayList<Exercise>();
    exerciseList4.add(toeTouches);
    exerciseList4.add(jumpingJacks);
    List<Exercise> exerciseList5 = new ArrayList<Exercise>();
    exerciseList5.add(wallSit);
    exerciseList5.add(bicycleCrunches);
    exerciseList5.add(pushUps);
    List<Exercise> exerciseList6 = new ArrayList<Exercise>();
    exerciseList6.add(jumpingJacks);
    exerciseList6.add(bicycleCrunches);
    exerciseList6.add(jumpingJacks);
    exerciseList6.add(pushUps);
    exerciseList6.add(jumpingJacks);
    exerciseList6.add(wallSit);
    exerciseList6.add(jumpingJacks);
    exerciseList6.add(russianTwists);
    exerciseList6.add(jumpingJacks);
    OutEdgeCache cache = new OutEdgeCache<WorkoutConnection, Workout>();
    workout1 = new Workout("new workout 1", "skajdhfalsd", 10, exerciseList1, cache);
    workout2 = new Workout("new workout 2", "asdfkjhasdlf", 1, exerciseList2, cache);
    workout3 = new Workout("new workout 3", "iequowrhjfk", 4, exerciseList3, cache);
    workout4 = new Workout("new workout 4", "asdjfhqwe", 15, exerciseList4, cache);
    workout5 = new Workout("new workout 5", "34253p9o", 6, exerciseList5, cache);
    workout6 = new Workout("new workout 6", "jkbmnvsa", 1, exerciseList6, cache);
    Map<String, Workout> allWorkouts = new HashMap<String, Workout>();
    allWorkouts.put(workout1.getID(), workout1);
    allWorkouts.put(workout2.getID(), workout2);
    allWorkouts.put(workout3.getID(), workout3);
    allWorkouts.put(workout4.getID(), workout4);
    allWorkouts.put(workout5.getID(), workout5);
    allWorkouts.put(workout6.getID(), workout6);
    List<String> user1PastWorkouts = new ArrayList<>();
    for (int i = 0; i < 27; i++) {
      user1PastWorkouts.add("jkbmnvsa");
    }
    _user1 = new User(
        "firstlast",
        234541,
        99,
        27,
        14,
        user1PastWorkouts,
        LocalDateTime.of(
            2021,
            Month.APRIL,
            11,
            19,
            30,
            40),
        allWorkouts);
    _user2 = new User(
        "random",
        "password",
        56,
        allWorkouts);
  }

  /**
   * Resets the DatabaseConn.
   */
  @After
  public void tearDown() {



    _testDatabaseConn1 = null;
    _user1 = null;
    _user2 = null;

  }

  /**
   * Tests that the DatabaseConn is loaded correctly with all exercises, workouts, and users loaded correctly.
   */
  @Test
  public void testLoadDatabase() {
    setUp();
    assertTrue(176 <= _testDatabaseConn1.getExercises().size());
    assertTrue(91 <= _testDatabaseConn1.getWorkouts().size());
    assertTrue(10 <= _testDatabaseConn1.getUsers().size());
    tearDown();
  }

  @Test
  public void testAddUser(){
    try {
      _testDatabaseConn1.addUser(_user1);
      assertTrue(10 <= _testDatabaseConn1.getUsers().size());
      _testDatabaseConn1.addUser(_user2);
      assertTrue(10 <= _testDatabaseConn1.getUsers().size());


    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    try {
      _testDatabaseConn1.deleteUser("firstlast");
      _testDatabaseConn1.deleteUser("random");
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }


}
