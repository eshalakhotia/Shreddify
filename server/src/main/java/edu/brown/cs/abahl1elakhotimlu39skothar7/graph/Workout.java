package edu.brown.cs.abahl1elakhotimlu39skothar7.graph;

import edu.brown.cs.abahl1elakhotimlu39skothar7.kdtree.KDNode;

import java.util.*;

/**
 * Edge that connects different Workout objects.
 */
public class Workout implements KDNode, Vertex<WorkoutConnection, Workout> {
  private String workoutID;
  private String name;
  // all metrics of a workout that we want to look at
  // (String array necassary to support comparing dimensions on KDTree)
  private String[] metricNames = new String[]{"time", "difficulty", "cardio", "abs", "legs", "arms", "glutes", "HIIT"};
  // Getting a specific metric calls from HashMap for constant time access
  private HashMap<String, Double> metrics;
  // we somehow need to figure out a way to turn the target areas and their percentages
  // into a single number to be compared in the KDTree traversal
  // Maybe the "target matching" string in metric names could be changed to
  // the individual names of targetAreas
  private HashMap<String, Double> targetAreasComponents;
  // keep line commented out until Exercise class created
  // private LinkedList<Exercise> exercises;
  private int numCycles;
  private int time;
  private Double difficulty;
  private HashSet<String> equipment;
  // delete outgoingEdges soon
  private ArrayList<WorkoutConnection> outgoingEdges;
  private double preference = 0.5;

  public Workout(String name, String id, int numCycles, List<Exercise> exercises) {
    this.workoutID = id;
    this.name = name;
    this.numCycles = numCycles;
    this.metrics = new HashMap<String, Double>();
    this.equipment = new HashSet<String>();
    int oneCycleTime = 0;
    double totalDifficulty = 0;
    for (int i = 0; i < metricNames.length; i++) {
      metrics.put(metricNames[i], Double.valueOf(0));
    }
    for (int i = 0; i < exercises.size(); i++) {
      oneCycleTime += exercises.get(i).getExerciseTime();
    }
    metrics.put("time", Double.valueOf(oneCycleTime * numCycles));
    for (int i = 0; i < exercises.size(); i++) {
      Exercise curExercise = exercises.get(i);
      Set<String> curExerciseMuscles = curExercise.getExerciseMuscle();
      Iterator<String> iterate = curExerciseMuscles.iterator();
      while (iterate.hasNext()) {
        String curMuscle = iterate.next();
        metrics.put(curMuscle, metrics.get(curMuscle)
                + ((curExercise.getExerciseTime())
                / (oneCycleTime * curExerciseMuscles.size())));
      }
      totalDifficulty +=
              ((curExercise.getExerciseTime()) / oneCycleTime)
                      * (curExercise.getExerciseDifficulty());
    }
    metrics.put("difficulty", totalDifficulty);
    for (int i = 0; i < exercises.size(); i++) {
      Exercise curExercise = exercises.get(i);
      equipment.addAll(curExercise.getExerciseEquipment());
    }
  }

  @Override
  public int getDim() {
    return metricNames.length;
  }

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
  public HashMap<String, Double> getAllMetrics() {
    return metrics;
  }

  @Override
  //calculates aggregate "closeness"/similarity with provided workout node using the Metrics
  public double calcDistance(KDNode other) {
    double differenceSum = 0;
    for (int i = 0; i < metricNames.length; i++) {
      double difference = Math.abs(metrics.get(metricNames[i]) - other.getAllMetrics().get(metricNames[i]));
      differenceSum += difference;
    }
    return (differenceSum / (metricNames.length));
  }

  @Override
  public ArrayList<WorkoutConnection> getTraversableEdgesFromNode(
          Graph<WorkoutConnection, Workout> graph) {
    // check if data in cache
    // if not need to use function that queries from database here
    // database query should find other workouts
    // that have one or more of the same metrics as current Workout
    return outgoingEdges;
  }


  @Override
  /**
   * Returns the ID or name of this Node.
   * @return String that represents the ID or name of this
   */
  public String getID() {
    return workoutID;
  }

}
