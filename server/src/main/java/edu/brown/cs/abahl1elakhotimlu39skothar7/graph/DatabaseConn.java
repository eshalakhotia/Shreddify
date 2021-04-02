package edu.brown.cs.abahl1elakhotimlu39skothar7.graph;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class DatabaseConn {
  private static Connection conn;
  private List<String> usersList;
  
  public DatabaseConn() throws SQLException, ClassNotFoundException {
    conn = null;
    this.loadDatabase("databaseShreddify.sqlite3");
  }
  public void loadDatabase(String filename) throws SQLException, ClassNotFoundException {
    try {
      conn.close();
    } catch (Exception ignored) {
    }
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:" + filename;
    conn = DriverManager.getConnection(urlToDB);
  }
  public Connection getConn() {
    return conn;
  }
  public List<String> getAllExerciseAttributes() throws SQLException {
    usersList = new ArrayList<>();
    PreparedStatement idInfo = conn.prepareStatement(
      "SELECT * FROM exercises;");
    ResultSet resulting = idInfo.executeQuery();
    while (resulting.next()) {
      usersList.add(resulting.getString(1));
      usersList.add(resulting.getString(2));
      usersList.add(resulting.getString(3));
      usersList.add(resulting.getString(4));
      usersList.add(resulting.getString(5));
      usersList.add(resulting.getString(6));
      usersList.add(resulting.getString(7));
      usersList.add(resulting.getString(8));
      usersList.add(resulting.getString(9));
      usersList.add(resulting.getString(10));
      usersList.add(resulting.getString(11));
    }
    return usersList;
  }

  public Map<String, Exercise> getAllExercises() throws SQLException {
    Map<String, Exercise> exercises = new HashMap<String, Exercise>();
    PreparedStatement exerciseInfo = conn.prepareStatement(
            "SELECT * FROM exercises;");
    ResultSet resulting = exerciseInfo.executeQuery();
    while (resulting.next()) {
      String newExerciseID = resulting.getString(1);
      String newExerciseName = resulting.getString(2);
      double newExerciseDifficulty = resulting.getDouble(3);
      int newExerciseTime = resulting.getInt(4);
      int newExerciseReps = resulting.getInt(5);
      String[] targetAreasColumns = new String[]{"Cardio", "Abs", "Legs", "Arms", "Glutes", "HIIT"};
      HashMap<String, Double> targetAreas = new HashMap<String, Double>();
      for (int i = 6; i < 11; i++) {
        if (resulting.getDouble(i) != 0) {
          targetAreas.put(targetAreasColumns[i - 6], resulting.getDouble(i));
        }
      }
      // figure out how to express equipment
      Set<String> equip = new HashSet<String>();
      Exercise newExercise = new Exercise(newExerciseID, newExerciseName, newExerciseDifficulty, newExerciseTime, newExerciseReps, targetAreas, equip);
      exercises.put(newExerciseID, newExercise);
    }
    return exercises;
  }

  public Map<String, Workout> getAllWorkouts() throws SQLException {
    Map<String, Workout> workouts = new HashMap<String, Workout>();
    PreparedStatement workoutInfo = conn.prepareStatement(
            "SELECT * FROM workouts;");
    ResultSet resulting = workoutInfo.executeQuery();
    while (resulting.next()) {
      String newWorkoutID = resulting.getString(1);
      String newWorkoutName = resulting.getString(2);
      double newWorkoutCycles = resulting.getInt(3);
      String newWorkoutExerciseIDList = resulting.getString(4);
      String[] newWorkoutExerciseIDArray = newWorkoutExerciseIDList.split(",");
      List<Exercise> newWorkoutExercises = new LinkedList<Exercise>();
      Map<String, Exercise> allExercises = getAllExercises();
      for (int i = 0; i < newWorkoutExerciseIDArray.length; i++) {
        newWorkoutExercises.add(allExercises.get(newWorkoutExerciseIDArray[i]));
      }
      //uncomment when Workout constructor created
      //Workout newWorkout = new Workout(newWorkoutID, newWorkoutName, newWorkoutCycles, newWorkoutExercises);
      //workouts.put(newWorkoutID, newWorkout);
    }
    return workouts;
  }
}
