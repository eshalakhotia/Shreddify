package edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import com.google.gson.Gson;

import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.io.DatabaseConn;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
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


  private Main(String[] args) throws SQLException, ClassNotFoundException {
    this.args = args;

    try {
      SparkHandlers.setMainDatabase(new DatabaseConn());
      SparkHandlers.setUsers(SparkHandlers.getMainDatabase().getUsers());
      SparkHandlers.setAllWorkouts(SparkHandlers.getMainDatabase().getWorkouts());
    } catch (Exception e) {
      SparkHandlers.setMainDatabase(null);
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

    //FreeMarkerEngine freeMarker = createEngine();


    //maps routes
    Spark.post("/login", new SparkHandlers.LoginHandler());
    Spark.post("/signup", new SparkHandlers.NewAccountHandler());
    Spark.post("/recs", new SparkHandlers.RecommendWorkoutsHandler());
    Spark.post("/explore", new SparkHandlers.ExploreHandler());
    Spark.post("/logout", new SparkHandlers.LogOutHandler());
    Spark.post("/finishworkout", new SparkHandlers.FinishWorkoutHandler());


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


}
