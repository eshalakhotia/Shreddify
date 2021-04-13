package edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph;

import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;


public class DatabaseConn {
  private static Connection conn;
  private List<String> usersList;
  private Map<String, User> users;
  private Map<String, Workout> workouts;
  private Map<String, Exercise> exercises;
  private OutEdgeCache cache = new OutEdgeCache();
  
  public DatabaseConn() throws SQLException, ClassNotFoundException {
    conn = null;
    this.loadDatabase("data/databaseShreddify.sqlite3");
  }
  public void loadDatabase(String filename) throws SQLException, ClassNotFoundException {
    try {
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:" + filename;
      conn = DriverManager.getConnection(urlToDB);
      exercises = getAllExercises();
      workouts = getAllWorkouts(exercises);
      users = getAllUsers(workouts);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  public Map<String, Exercise> getExercises() {
    return new HashMap<String, Exercise>(exercises);
  }

  public Map<String, Workout> getWorkouts() {
    return new HashMap<String, Workout>(workouts);
  }

  public Map<String, User> getUsers() {
    return new HashMap<String, User>(users);
  }

  public Connection getConn() {
    return conn;
  }

  /*
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
  */

  public Map<String, Exercise> getAllExercises() throws SQLException {
    Map<String, Exercise> exercises = new HashMap<String, Exercise>();
    PreparedStatement exerciseInfo = conn.prepareStatement(
            "SELECT * FROM exercises;");
    ResultSet resulting = exerciseInfo.executeQuery();
    while (resulting.next()) {
      String newExerciseName = resulting.getString(1);
      String newExerciseID = resulting.getString(2);
      double newExerciseDifficulty = resulting.getDouble(3);
      int newExerciseTime = resulting.getInt(4);
      int newExerciseReps = resulting.getInt(5);
      if (newExerciseReps == 0) {
        newExerciseReps = 1;
      }
      String[] targetAreasColumns = new String[]{"Cardio", "Abs", "Legs", "Arms", "Glutes", "Back", "Chest"};
      Set<String> targetAreas = new HashSet<String>();
      for (int i = 6; i < 13; i++) {
        if (resulting.getDouble(i) != 0) {
          targetAreas.add(targetAreasColumns[i - 6].toLowerCase(Locale.ROOT));
        }
      }
      // equipment field is a comma delimited string which we split and put into a HashSet
      Set<String> equip;
      if (resulting.getString(13) == null) {
        equip = new HashSet<>();
      } else {
        String[] equipmentList = resulting.getString(13).split(",");
        equip = new HashSet<String>(Arrays.asList(equipmentList));
        Exercise newExercise = new Exercise(newExerciseID, newExerciseName, newExerciseDifficulty, newExerciseTime, newExerciseReps, targetAreas, equip);
        exercises.put(newExerciseID, newExercise);
      }
    }
    return exercises;
  }

  public Map<String, Workout> getAllWorkouts(Map<String, Exercise> allExercises) throws SQLException {
    Map<String, Workout> workouts = new HashMap<String, Workout>();
    PreparedStatement workoutInfo = conn.prepareStatement(
            "SELECT * FROM workouts;");
    ResultSet resulting = workoutInfo.executeQuery();
    while (resulting.next()) {
      String newWorkoutID = resulting.getString(1);
      String newWorkoutName = resulting.getString(2);
      int newWorkoutCycles = resulting.getInt(3);
      String newWorkoutExerciseIDList = resulting.getString(4);
      String[] newWorkoutExerciseIDArray = newWorkoutExerciseIDList.split(",");
      List<Exercise> newWorkoutExercises = new ArrayList<Exercise>();
      for (int i = 0; i < newWorkoutExerciseIDArray.length; i++) {
        Exercise newExercise = allExercises.get(newWorkoutExerciseIDArray[i]);
        if (newExercise != null) {
          newWorkoutExercises.add(newExercise);
        }
      }
      Workout newWorkout = new Workout(newWorkoutID, newWorkoutName, newWorkoutCycles, newWorkoutExercises, cache);
      workouts.put(newWorkoutID, newWorkout);
    }
    return workouts;
  }

  public Map<String, User> getAllUsers(Map<String, Workout> allWorkouts) throws SQLException {
    Map<String, User> users = new HashMap<String, User>();
    PreparedStatement userInfo = conn.prepareStatement(
            "SELECT * FROM users;");
    ResultSet resulting = userInfo.executeQuery();
    while (resulting.next()) {
      String newUserName = resulting.getString(1);
      int newUserPassword = resulting.getInt(2);
      double newUserOFL = resulting.getDouble(3);
      int newUserNumWorkouts = resulting.getInt(4);
      String lastWorkoutAsString = resulting.getString(5);
      String[] lastWorkoutAsArray = lastWorkoutAsString.split(",");
      LocalDateTime newUserLastWorkout;
      if (lastWorkoutAsArray.length == 7) {
        newUserLastWorkout = LocalDateTime.of(
                Integer.parseInt(lastWorkoutAsArray[0]),
                Integer.parseInt(lastWorkoutAsArray[1]),
                Integer.parseInt(lastWorkoutAsArray[2]),
                Integer.parseInt(lastWorkoutAsArray[3]),
                Integer.parseInt(lastWorkoutAsArray[4]),
                Integer.parseInt(lastWorkoutAsArray[5]),
                Integer.parseInt(lastWorkoutAsArray[6]));
      } else {
        newUserLastWorkout = null;
      }
      String pastWorkoutIDsString = resulting.getString(5);
      String[] pastWorkoutIDsArray = pastWorkoutIDsString.split(",");
      List<String> newUserPastWorkoutIDs = new ArrayList<>();
      for (int i = 0; i < pastWorkoutIDsArray.length; i++) {
        newUserPastWorkoutIDs.add(pastWorkoutIDsArray[i]);
      }
      int newUserStreak = resulting.getInt(6);
      // allWorkouts must be adjusted
      User newUser = new User(newUserName, newUserPassword, newUserOFL, newUserNumWorkouts, newUserStreak, newUserPastWorkoutIDs, newUserLastWorkout, allWorkouts);
      users.put(newUserName, newUser);
    }
    return users;
  }


}
