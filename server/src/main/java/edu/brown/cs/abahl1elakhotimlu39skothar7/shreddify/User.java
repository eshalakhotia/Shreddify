package edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify;

import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.Graph;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.Workout;
import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;

public class User {
  private String username;
  private int password;
  private double overallFitnessLevel;
  private int totalNumWorkouts;
  private LocalDateTime lastWorkout;
  private int streak;
  private Graph connectedPreferences;

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
    this.lastWorkout = null;
    this.connectedPreferences = new Graph(allWorkouts);
  }

  public User(
          String username,
          int password,
          double overallFitnessLevel,
          int totalNumWorkouts,
          int streak,
          LocalDateTime lastWorkout,
          Map<String, Workout> allWorkouts) {
    this.username = username;
    this.password = password;
    this.overallFitnessLevel = overallFitnessLevel;
    this.totalNumWorkouts = totalNumWorkouts;
    this.streak = streak;
    this.lastWorkout = lastWorkout;
    this.connectedPreferences = new Graph(allWorkouts);
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

  public void updateStreak() {
    this.streak++;
  }

  public void breakStreak() {
    this.streak = 0;
  }

  public void startNewWorkout() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime nextDay = lastWorkout.plusDays(1);
    if (now.getDayOfYear() == nextDay.getDayOfYear()) {
      updateStreak();
    }
    totalNumWorkouts++;
    lastWorkout = now;
  }

  public LocalDateTime getLastWorkout() {
    return lastWorkout;
  }

  public void updatePreferences(String workoutID, double newPreference) {
    connectedPreferences.updateParameter(workoutID, newPreference);
  }




}
