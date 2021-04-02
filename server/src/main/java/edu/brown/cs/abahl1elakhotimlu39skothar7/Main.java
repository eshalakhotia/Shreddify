package edu.brown.cs.abahl1elakhotimlu39skothar7;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.*;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.abahl1elakhotimlu39skothar7.graph.Workout;
import edu.brown.cs.abahl1elakhotimlu39skothar7.kdtree.KDTree;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import org.json.JSONArray;
import org.json.JSONObject;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import freemarker.template.Configuration;

/**
 * The Main class of our project. This is where execution begins.
 */
public class Main {
  private static final int DEFAULT_PORT = 4567;
  private static final Gson GSON = new Gson();

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;
  private static HashMap<String, User> users;
  private static User curUser;
  private static HashMap<String, Workout> allWorkouts;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {

    // Parse command line arguments succeeding ./
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
      .defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);

    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    //run repl?
  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
              templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();

    Spark.options("/*", (request, response) -> {
      String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
      if (accessControlRequestHeaders != null) {
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
      }

      String accessControlRequestMethod = request.headers("Access-Control-Request-Method");

      if (accessControlRequestMethod != null) {
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
      }

      return "OK";
    });

    Spark.before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
    Spark.exception(Exception.class, new ExceptionPrinter());
  }


  /**
   * Display an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

  private static class LoginHandler implements Route {
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
        error = "ERROR: user with given username was not found";
        userpwdMatch = false;
      } else {
        userpwdMatch = userWithUsername.checkPassword(pwd);
        if (userpwdMatch) {
          curUser = userWithUsername;
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
      // In the React files, use the success boolean to check whether to display the results
      // or the error that prevented results from being obtained
      Map<String, Object> variables = ImmutableMap.of(
              "success", userpwdMatch,
              "results", curUser,
              "error", error);
      return new Gson().toJson(variables);
    }
  }

  private static class NewAccountHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
      JSONObject data = new JSONObject(request.body());
      String error = "";
      boolean usernameOK = false;
      boolean pwdOK = true;
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
        // add database command to add new user to user database here
      }
      // In the React files, use the success boolean to check whether to display the results
      // or the error that prevented results from being obtained
      Map<String, Object> variables = ImmutableMap.of(
              "success", (usernameOK && pwdOK),
              "results", curUser,
              "error", error);
      return new Gson().toJson(variables);
    }
  }

  private static class RecommendWorkoutsHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
      JSONObject data = new JSONObject(request.body());
      boolean success = true;
      String error = "";
      List<Workout> bestRecommendations = new ArrayList<>();
      // gets username, password from frontend
      int time = data.getInt("time");
      boolean flexibility = data.getBoolean("flexibility");
      double energy = data.getDouble("energy");
      double difficulty = data.getDouble("difficulty");
      JSONArray unusableTargetAreas = data.getJSONArray("targetAreas");
      List<String> targetAreas = new ArrayList<String>();
      for (int i = 0; i < unusableTargetAreas.length(); i++) {
        targetAreas.add(unusableTargetAreas.getString(i));
      }
      KDTree toSearch;
      if (flexibility) {
        List<Workout> workouts = new ArrayList<Workout>();
        Set<String> keys = allWorkouts.keySet();
        Iterator<String> iterate = keys.iterator();
        while (iterate.hasNext()) {
          workouts.add(allWorkouts.get(iterate.next()));
        }
        if (workouts.size() > 0) {
          toSearch = new KDTree(workouts, workouts.get(0).getAllMetrics().size());
        } else {
          toSearch = new KDTree(new ArrayList<>(), 0);
        }
        // finish when Workout constructor done
        // Workout idealWorkout = new Workout()
        Workout idealWorkout = null;
        bestRecommendations = toSearch.kNearestNeighbors(idealWorkout, 5);
      }
      // In the React files, use the success boolean to check whether to display the results
      // or the error that prevented results from being obtained
      Map<String, Object> variables = ImmutableMap.of(
              "success", success,
              "results", bestRecommendations,
              "error", error);
      return new Gson().toJson(variables);
    }
  }



}
