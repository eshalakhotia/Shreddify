package edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents;

import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.Graph;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.OutEdgeCache;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.Vertex;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.kdtree.KDNode;

import java.util.*;

/**
 * Workout class representing a record in the workouts table of the database
 */
public class Workout implements KDNode<Workout>, Vertex<WorkoutConnection, Workout> {
  //variables accessed from frontend
  private String workoutID;
  private String name;
  private int workoutTime;
  private double workoutDifficulty;
  private Set<String> targetAreas;
  private Set<String> equipment;
  private int numCycles;

  // all metrics of a workout that we want to look at
  // (String array necessary to support comparing dimensions on KDTree)
  private String[] metricNames = new String[]{"time", "difficulty", "cardio", "abs", "legs", "arms", "glutes", "back", "chest"};
  private Map<String, Double> metrics;
  // we somehow need to figure out a way to turn the target areas and their percentages
  // into a single number to be compared in the KDTree traversal
  // Maybe the "target matching" string in metric names could be changed to
  // the individual names of targetAreas
  private Map<String, Double> targetAreasComponents;
  private List<Exercise> exercises;


  // delete outgoingEdges soon
  private double preference = 50;
  private OutEdgeCache cache;

  /**
   * Workout constructor
   * @param name - name of workout to display
   * @param id - unique ID of workout
   * @param numCycles - number of times this workout repeats over the set of exercises
   * @param exercises - specific exercises in the workout
   * @param cache - cache for edge
   */
  public Workout(String name, String id, int numCycles, List<Exercise> exercises, OutEdgeCache cache) {

    this.workoutID = id;
    this.name = name;
    this.targetAreas = new HashSet<>();
    this.numCycles = numCycles;
    this.metrics = new HashMap<String, Double>();
    this.exercises = new LinkedList<>();
    this.equipment = new HashSet<String>();
    this.cache = cache;
    double oneCycleTime = 0;
    double totalDifficulty = 0;
    //initialize metrics hashmap with 0's
    for (int i = 0; i < metricNames.length; i++) {
      metrics.put(metricNames[i], Double.valueOf(0));
    }
    //calculate time of one cycle
    for (int i = 0; i < exercises.size(); i++) {
      oneCycleTime += exercises.get(i).getExerciseTime();
    }
    //add total time to metrics hashmap and totalTime
    metrics.put("time", oneCycleTime * numCycles);
    workoutTime = (int) (oneCycleTime * numCycles);

    //process muscle groups of each exercise in the workout
    for (int i = 0; i < exercises.size(); i++) {
      Exercise curExercise = exercises.get(i);
      //add exercise to linked list
      this.exercises.add(curExercise);

      Set<String> curExerciseMuscles = curExercise.getExerciseMuscle();
      Iterator<String> iterate = curExerciseMuscles.iterator();
      //add muscles for exercise to metrics, increase "percentage" of that muscle
      while (iterate.hasNext()) {
        String curMuscle = iterate.next();
        metrics.put(curMuscle, (metrics.get(curMuscle)
                + ((curExercise.getExerciseTime())
                / (oneCycleTime * curExerciseMuscles.size()))));
      }
      targetAreas.addAll(curExerciseMuscles);
      //update total difficulty based on this exercise difficulty
      totalDifficulty +=
              ((curExercise.getExerciseTime()) / oneCycleTime)
                      * (curExercise.getExerciseDifficulty());
    }
    final double difficultyScale = 1.10;
    for (int i = numCycles; i > 0; i--) {
      double finalDifficulty = totalDifficulty * Math.pow(difficultyScale, i);
      if (finalDifficulty < 100) {
        totalDifficulty = finalDifficulty;
        break;
      }
    }

    //update metrics with total difficulty
    metrics.put("difficulty", totalDifficulty);
    workoutDifficulty = totalDifficulty;

    //add equipment of each exercise to hashset
    for (int i = 0; i < exercises.size(); i++) {
      Exercise curExercise = exercises.get(i);
      equipment.addAll(curExercise.getExerciseEquipment());
    }

    for (int i = 2; i < getDim(); i++) {
      metrics.put(metricNames[i], getMetric(i) * 100);
    }
  }

  /**
   * alternative Workout constructor to populate map of metric names to metric values
   * @param metrics
   */
  public Workout(double[] metrics) {
    this.metrics = new HashMap<>();
    for (int i = 0; i < metricNames.length; i++) {
      this.metrics.put(metricNames[i], metrics[i]);
    }
  }

  //gets number of dimensions
  @Override
  public int getDim() {
    return metricNames.length;
  }

  //gets metric value of provided dim/attribute
  @Override
  public double getMetric(int dimLevel) {
    int scaledDimLevel = dimLevel % this.getDim();
    String metricName = metricNames[scaledDimLevel];
    return metrics.get(metricName);
  }

  public double getMetric(String metricName) {
    return metrics.get(metricName);
  }

  @Override
  public void setParameterToUpdate(double newPreference) {
    this.preference = newPreference;
  }

  @Override
  public double getParameterToUpdate() {
    return this.preference;
  }


  @Override
  //gets overall metric of node's attributes (...hashmap? ab->20%, arms->30%, difficulty->8...)
  public Map<String, Double> getAllMetrics() {
    return metrics;
  }

  /**
   * calculates aggregate "closeness"/similarity with provided workout node using the Metrics
   * @param other - workout to measure distance to
   * @return - numerical distance
   */
  @Override
  public double calcDistance(Workout other) {
    double differenceSum = 0;
    for (int i = 0; i < metricNames.length; i++) {
      double difference;
      if (i == 0) {
        difference = Math.abs((metrics.get(metricNames[i]) / 60)
                - (other.getAllMetrics().get(metricNames[i])) / 60);
      } else {
        difference = Math.abs(metrics.get(metricNames[i]) - other.getAllMetrics().get(metricNames[i]));
      }
      differenceSum += difference;
    }
    return (differenceSum / (metricNames.length));
  }

  /**
   * Gets all edges from a specified node
   * @param graph the graph from which we want to see the traversable edges from.
   * @return - list of edges (i.e. WorkoutConnection)
   */
  @Override
  public List<WorkoutConnection> getEdgesFromNode(
          Graph<WorkoutConnection, Workout> graph) {
    List<WorkoutConnection> cacheEdges = cache.getOutgoingEs(this.getID());
    if (cacheEdges != null) {
      return cacheEdges;
    } else {
      List<WorkoutConnection> edges = new ArrayList<WorkoutConnection>();
      Map<String, Workout> workouts = graph.getAllNodes();
      Set<String> keys = workouts.keySet();
      Iterator iterate = keys.iterator();
      while (iterate.hasNext()) {
        Workout curWorkout = workouts.get(iterate.next());
        if (!curWorkout.getID().equals(this.getID())) {
          for (int i = 0; i < curWorkout.getAllMetrics().size(); i++) {
            if ((Math.abs(curWorkout.getMetric(i) / this.getMetric(i)) > 0.95)
                    && (Math.abs(curWorkout.getMetric(i) / this.getMetric(i)) < 1.05)) {
              WorkoutConnection newConnection = new WorkoutConnection(this, curWorkout);
              edges.add(newConnection);
              break;
            }
          }
        }
      }
      cache.addToCache(this, edges);
      return edges;
    }
  }

  @Override
  /**
   * Returns the ID or name of this Node.
   * @return String that represents the ID or name of this
   */
  public String getID() {
    return workoutID;
  }

  @Override
  public String getName() {
    return name;
  }

  public int getTime() {
    return workoutTime;
  }

  public Workout cloneWorkout() {
    return new Workout(this.getName(), this.getID(), numCycles, exercises, cache);
  }
  public int getCycles() {
    return numCycles;
  }
  public List<Exercise> getExercises() {
    return exercises;
  }
}
