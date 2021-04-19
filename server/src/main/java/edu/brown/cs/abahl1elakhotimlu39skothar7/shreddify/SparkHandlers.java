package edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.io.DatabaseConn;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.kdtree.KDTree;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents.User;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents.Workout;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Class containing all spark handlers
 */
public class SparkHandlers {

  private static DatabaseConn mainDatabase;
  private static Map<String, User> users;
  private static User curUser;
  private static Map<String, Workout> allWorkouts;

  /**
   * Handles requests for Login Page
   */
  protected static class LoginHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
      JSONObject data = new JSONObject(request.body());
      String error = "";
      boolean userpwdMatch;
      // gets username, password from frontend
      String user = data.getString("username");
      String pwd = data.getString("password");

      User userWithUsername = users.get(user);
      if (userWithUsername == null) {

        //System.out.println("ERROR: user with given username was not found");

        error = "ERROR: user with given username was not found";
        userpwdMatch = false;
      } else {
        //System.out.println("user w given username found");
        userpwdMatch = userWithUsername.checkPassword(pwd);
        if (userpwdMatch) {

          //System.out.println("password match");

          curUser = userWithUsername;
          //System.out.println("getting last workout");
          if (curUser.getLastWorkout() != null) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime dayAfter = curUser.getLastWorkout().plusDays(1);
            if (dayAfter.getDayOfYear() != now.getDayOfYear()) {
              curUser.breakStreak();
            }
          }
        } else {

          error = "ERROR: incorrect password for this user";
        }
      }

      System.out.println("curUser name: " + curUser.getUsername());
      System.out.println("# of past workouts " + curUser.getPastWorkouts().size());
      for (int i = 0; i < curUser.getPastWorkouts().size(); i++) {
        System.out.println("workout name: " + curUser.getPastWorkouts().get(i).getName());
      }

      Map<String, Object> variables = ImmutableMap.of(
          "success", userpwdMatch,
          "results", curUser,
          "error", error,
          "user", curUser);
      return new Gson().toJson(variables);
    }
  }


  /**
   * Handles requests for Explore page
   */
  protected static class ExploreHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
      boolean success = true;
      JSONObject data = new JSONObject(request.body());
      String error = "";
      List<Workout> workouts = new ArrayList<>();
      String[] keys = allWorkouts.keySet().toArray(new String[0]);
      int[] indexesInList = new int[5];
      for (int i = 0; i < 5; i++) {
        boolean usable = false;
        int index = -1;
        while (!usable) {
          Random rand = new Random();
          index = rand.nextInt(keys.length);
          boolean usableHelp = true;
          for (int j = 0; j < i; j++) {
            if (index == indexesInList[j]) {
              usableHelp = false;
            }
          }
          usable = usableHelp;
        }
        try {
          workouts.add(allWorkouts.get(keys[index]));
        } catch (Exception E) {
          success = false;
          error = "ERROR: Not enough workouts are in this database";
        }
      }
      Map<String, Object> variables = ImmutableMap.of(
          "success", success,
          "results", workouts,
          "error", error,
          "user", curUser);
      return new Gson().toJson(variables);
    }
  }

  /**
   * Handles logging out
   */
  protected static class LogOutHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
      boolean success = true;
      JSONObject data = new JSONObject(request.body());
      String error = "";
      String result = "";
      mainDatabase.deleteUser(curUser.getUsername());
      mainDatabase.addUser(curUser);
      curUser = null;
      Map<String, Object> variables = ImmutableMap.of(
          "success", success,
          "results", result,
          "error", error,
          "user", curUser);
      return new Gson().toJson(variables);
    }
  }

  /**
   * Handles requests upon finishing workout
   */
  protected static class FinishWorkoutHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
      System.out.println("hi");
      JSONObject data = new JSONObject(request.body());
      String error = "";
      boolean success = true;
      String workoutID = data.getString("workoutID");
      System.out.println(workoutID);
      curUser.startNewWorkout(allWorkouts.get(workoutID));
      System.out.println(curUser.getUsername());
      Map<String, Object> variables = ImmutableMap.of(
          "success", success,
          "results", curUser,
          "error", error,
          "user", curUser);
      return new Gson().toJson(variables);
    }
  }

  /**
   * Handles requests for creating new user account
   */
  protected static class NewAccountHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
      JSONObject data = new JSONObject(request.body());
      String error = "";
      boolean usernameOK = false;
      boolean pwdOK = false;
      // gets username, password from frontend
      String username = data.getString("username");
      String pwd = data.getString("password");
      double fitnessLevel = data.getDouble("level");
      if (users.get(username) == null) {
        if (username.length() > 3 && username.length() < 16) {
          if (username.split(" ").length == 1) {
            usernameOK = true;
          } else {
            error = "ERROR: Your username cannot be more than one word.";
          }
        } else {
          error = "ERROR: Your username must be at least 4 and no more than 15 characters long.";
        }
      } else {
        error = "ERROR: This username is already taken!";
      }
      if (!pwd.equals(username)) {
        if (pwd.length() > 7 && username.length() < 21) {
          pwdOK = true;
        } else {
          error = "ERROR: Your password needs to be between 8 and 20 characters long";
        }
      } else {
        error = "ERROR: Your password cannot be the same as your username!";
      }
      if (usernameOK && pwdOK) {
        User newUser = new User(username, pwd, fitnessLevel, allWorkouts);
        users.put(newUser.getUsername(), newUser);
        curUser = newUser;
        mainDatabase.addUser(newUser);
        System.out.println("done creating user");
      }
      // In the React files, use the success boolean to check whether to display the results
      // or the error that prevented results from being obtained
      Map<String, Object> variables = ImmutableMap.of(
          "success", (usernameOK && pwdOK),
          "results", curUser,
          "error", error,
          "user", curUser);
      return new Gson().toJson(variables);
    }
  }


  /**
   * Handler for recommending workouts
   */
  protected static class RecommendWorkoutsHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {

      JSONObject data = new JSONObject(request.body());
      boolean success = true;
      String error = "";
      List<Workout> bestRecommendations = new ArrayList<>();

      // gets energy, time, target areas, (flexibility, difficulty) from request body
      double energy = data.getDouble("energy");
      int time = data.getInt("time");
      boolean flexibility = data.getBoolean("flexibility");

      //collecting desired targets from response
      JSONArray unusableTargetAreas = data.getJSONArray("targets");
      List<String> targetAreas = new ArrayList<String>();
      System.out.println(unusableTargetAreas.length() + " Target Areas Desired: ");
      for (int i = 0; i < unusableTargetAreas.length(); i++) {
        targetAreas.add(unusableTargetAreas.getString(i));
        System.out.println(unusableTargetAreas.getString(i) + " ");
      }


      //setting up desired difficulty based on user's overall fitness level
      double difficulty = curUser.getOFL();
      final double defaultChangeRange = 10;
      double changeRange;
      final double maxOFL = 100;
      double distToMax = maxOFL - difficulty;
      if (defaultChangeRange > distToMax) {
        changeRange = distToMax;
      } else if (defaultChangeRange < difficulty){
        changeRange = difficulty;
      } else {
        changeRange = defaultChangeRange;
      }
      difficulty += ((energy - 50) / 50) * changeRange;

      KDTree toSearch;
      List<Workout> workouts = new ArrayList<Workout>();
      Set<String> keys = allWorkouts.keySet();
      Iterator<String> iterate = keys.iterator();
      if (flexibility) {
        while (iterate.hasNext()) {
          Workout newWorkout = allWorkouts.get(iterate.next());
          if (newWorkout.getMetric("time") > time / 2) {
            workouts.add(newWorkout);
          }
        }
      } else {
        while (iterate.hasNext()) {
          Workout newWorkout = allWorkouts.get(iterate.next());
          if (newWorkout.getMetric("time") > time / 2
              && newWorkout.getMetric("time") < time) {
            workouts.add(newWorkout);
          }
        }
      }
      if (workouts.size() > 0) {
        System.out.println("Tree to search");
        toSearch = new KDTree(workouts, workouts.get(0).getAllMetrics().size());
      } else {
        System.out.println("No workouts to build tree to search from");
        toSearch = new KDTree(new ArrayList<>(), 0);
      }

      //setting up Ideal Workout to search KDtree with
      String[] metricNames = new String[]{"time", "difficulty", "cardio", "abs", "legs", "arms", "glutes", "back", "chest"};
      double[] metrics = new double[metricNames.length];
      metrics[0] = time;
      metrics[1] = difficulty;
      for (int i = 2; i < metricNames.length; i++) {
        //if metric is a desired target area
        if (targetAreas.contains(metricNames[i])) {
          metrics[i] = 1.0 / targetAreas.size();
        } else {
          metrics[i] = 0;
        }
      }

      System.out.println("Constructing ideal workout");
      for (int i = 0; i < metricNames.length; i++) {
        System.out.println("Metric: " + metricNames[i] + ", Value: " + metrics[i]);
      }


      Workout idealWorkout = new Workout(metrics);
      bestRecommendations = toSearch.kNearestNeighbors(idealWorkout, 5);

      System.out.println("Recommendations");
      System.out.println(bestRecommendations);
      for (Workout w : bestRecommendations) {
        if (w != null) {
          System.out.println(w.getName());
        } else {
          System.out.println("workout null");
        }
      }

      Map<String, Object> variables = ImmutableMap.of(
          "success", success,
          "results", bestRecommendations,
          "error", error,
          "user", curUser);
      return new Gson().toJson(variables);
    }
  }

  public static void setMainDatabase(
      DatabaseConn mainDatabase) {
    SparkHandlers.mainDatabase = mainDatabase;
  }

  public static void setUsers(
      Map<String, User> users) {
    SparkHandlers.users = users;
  }

  public static void setCurUser(
      User curUser) {
    SparkHandlers.curUser = curUser;
  }

  public static void setAllWorkouts(
      Map<String, Workout> allWorkouts) {
    SparkHandlers.allWorkouts = allWorkouts;
  }

  public static DatabaseConn getMainDatabase() {
    return mainDatabase;
  }
}
