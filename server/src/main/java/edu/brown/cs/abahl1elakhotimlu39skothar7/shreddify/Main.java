package edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.DatabaseConn;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.Exercise;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.OutEdgeCache;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.Workout;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.kdtree.KDTree;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import org.json.JSONArray;
import org.json.JSONObject;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import freemarker.template.Configuration;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {
  private static final int DEFAULT_PORT = 4567;
  private static final Gson GSON = new Gson();
//  private static final DatabaseConn database = new DatabaseConn();

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) throws SQLException, ClassNotFoundException {
    new Main(args).run();
  }

  private String[] args;
  private static DatabaseConn mainDatabase;
  private static Map<String, User> users;
  private static User curUser;
  private static Map<String, Workout> allWorkouts;

  private Main(String[] args) throws SQLException, ClassNotFoundException {
    this.args = args;
    try {
      mainDatabase = new DatabaseConn();
      users = mainDatabase.getUsers();
      allWorkouts = mainDatabase.getWorkouts();
    } catch (Exception e) {
      mainDatabase = null;
    }
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


    //maps routes
    Spark.post("/login", new LoginHandler());
    Spark.post("/recs", new RecommendWorkoutsHandler());
    Spark.post("/explore", new ExploreHandler());


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

      //System.out.println("username: " + user);
      //System.out.println("password: " + pwd);

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
          //System.out.println("password wrong");
          //System.out.println("curUser: " + curUser);
          error = "ERROR: incorrect password for this user";
        }
      }

      System.out.println("curUser name: " + curUser.getUsername());
      System.out.println("# of past workouts " + curUser.getPastWorkouts().size());
      for (int i = 0; i < curUser.getPastWorkouts().size(); i++) {
        System.out.println("workout name: " + curUser.getPastWorkouts().get(i));
      }

      Map<String, Object> variables = ImmutableMap.of(
              "success", userpwdMatch,
              "results", curUser,
              "error", error);
      return new Gson().toJson(variables);
    }
  }



  private static class ExploreHandler implements Route {
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
        "error", error);
      return new Gson().toJson(variables);
    }
  }

  private static class LogOutHandler implements Route {
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
        mainDatabase.addUser(newUser);
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
//  private static class SignUpHandler implements Route {
//    @Override
//    public Object handle(Request request, Response response) throws Exception {
//      JSONObject sign = new JSONObject(request.body());
//      String error = "";
//      String username = sign.getString("username");
//      String pwd = sign.getString("password");
//      // In the React files, use the success boolean to check whether to display the results
//      // or the error that prevented results from being obtained
//      Map<String, Object> variables = ImmutableMap.of(
//        "success", null,
//        "results", null,
//        "error", error);
//      return new Gson().toJson(variables);
//    }
//  }

  private static class RecommendWorkoutsHandler implements Route {
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

      JSONArray unusableTargetAreas = data.getJSONArray("targets");
      List<String> targetAreas = new ArrayList<String>();
      for (int i = 0; i < unusableTargetAreas.length(); i++) {
        targetAreas.add(unusableTargetAreas.getString(i));
      }
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
          workouts.add(allWorkouts.get(iterate.next()));
        }
      } else {
        while (iterate.hasNext()) {
          Workout newWorkout = allWorkouts.get(iterate.next());
          if (newWorkout.getMetric("time") < time) {
            workouts.add(newWorkout);
          }
        }
      }
      if (workouts.size() > 0) {
        toSearch = new KDTree(workouts, workouts.get(0).getAllMetrics().size());
      } else {
        toSearch = new KDTree(new ArrayList<>(), 0);
      }
      // finish when Workout constructor done
      String[] metricNames = new String[]{"time", "difficulty", "cardio", "abs", "legs", "arms", "glutes", "back", "chest"};
      double[] metrics = new double[9];
      metrics[0] = time;
      metrics[1] = difficulty;
      for (int i = 2; i < metricNames.length; i++) {
        if (targetAreas.contains(metricNames[i])) {
          metrics[i] = 1 / targetAreas.size();
        } else {
          metrics[i] = 0;
        }
      }
      Workout idealWorkout = new Workout(metrics);
      bestRecommendations = toSearch.kNearestNeighbors(idealWorkout, 5);


      Map<String, Object> variables = ImmutableMap.of(
              "success", success,
              "results", bestRecommendations,
              "error", error);
      return new Gson().toJson(variables);
    }
  }



}
