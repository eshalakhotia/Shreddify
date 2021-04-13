package edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify;

import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.Graph;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.Workout;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.WorkoutConnection;
import org.checkerframework.checker.units.qual.A;

import java.time.LocalDateTime;

import java.util.*;

public class User {
  private String username;
  private int password;
  private double overallFitnessLevel;
  private int totalNumWorkouts;
  private LocalDateTime lastWorkout;
  private List<String> pastWorkoutIDs;
  private int streak;
  private Graph<WorkoutConnection, Workout> connectedPreferences;

  public User(
          String username,
          String password,
          double overallFitnessLevel,
          Map<String, Workout> allWorkouts) {
    this.username = username;
    this.password = password.hashCode();
    this.overallFitnessLevel = overallFitnessLevel;
    this.totalNumWorkouts = 0;
    this.streak = 0;
    this.pastWorkoutIDs = new ArrayList<>();
    this.lastWorkout = null;
    Map<String, Workout> allCloneWorkouts = new HashMap<String, Workout>();
    Set<String> keys = allWorkouts.keySet();
    Iterator<String> iterate = keys.iterator();
    while (iterate.hasNext()) {
      Workout cloneWorkout = allWorkouts.get(iterate.next()).cloneWorkout();
      allCloneWorkouts.put(cloneWorkout.getID(), cloneWorkout);
    }
    this.connectedPreferences = new Graph<WorkoutConnection, Workout>(allCloneWorkouts);
  }

  public User(
          String username,
          int password,
          double overallFitnessLevel,
          int totalNumWorkouts,
          int streak,
          List<String> pastWorkoutIDs,
          LocalDateTime lastWorkout,
          Map<String, Workout> allWorkouts) {
    this.username = username;
    this.password = password;
    this.overallFitnessLevel = overallFitnessLevel;
    this.totalNumWorkouts = totalNumWorkouts;
    this.streak = streak;
    this.pastWorkoutIDs = pastWorkoutIDs;
    this.lastWorkout = lastWorkout;
    Map<String, Workout> allCloneWorkouts = new HashMap<String, Workout>();
    Set<String> keys = allWorkouts.keySet();
    Iterator<String> iterate = keys.iterator();
    while (iterate.hasNext()) {
      Workout cloneWorkout = allWorkouts.get(iterate.next()).cloneWorkout();
      allCloneWorkouts.put(cloneWorkout.getID(), cloneWorkout);
    }
    this.connectedPreferences = new Graph<WorkoutConnection, Workout>(allCloneWorkouts);;
  }

  public boolean checkPassword(String enteredPassword) {
    return (this.password == enteredPassword.hashCode());
  }

  public String getUsername() {
    return this.username;
  }

  public double getOFL() {
    return this.overallFitnessLevel;
  }

  public int getTotalNumWorkouts() {
    return this.totalNumWorkouts;
  }

  public int getStreak() {
    return this.streak;
  }

  public Graph getConnectedPreferences() {
    return this.connectedPreferences;
  }

  public void updateStreak() {
    this.streak++;
  }

  public void breakStreak() {
    this.streak = 0;
  }

  public void startNewWorkout(Workout workout) {
    LocalDateTime now = LocalDateTime.now();
    if (lastWorkout == null) {
      updateStreak();
    } else {
      LocalDateTime nextDay = lastWorkout.plusDays(1);
      if (now.getDayOfYear() == nextDay.getDayOfYear()) {
        updateStreak();
      }
    }
    totalNumWorkouts++;
    pastWorkoutIDs.add(workout.getID());
    lastWorkout = now;
  }

  public LocalDateTime getLastWorkout() {
    return lastWorkout;
  }

  public void updatePreferences(String workoutID, double newPreference) {
    connectedPreferences.updateParameter(workoutID, newPreference);
  }




}
