package edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents;

import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.Graph;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents.Workout;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents.WorkoutConnection;

import java.time.LocalDateTime;

import java.util.*;

/**
 * Class that represents a user from the users table in the database
 */
public class User {
  private String username;
  private int password;
  private double overallFitnessLevel;
  private int totalNumWorkouts;
  private LocalDateTime lastWorkout;
  private List<Workout> pastWorkouts;
  private int streak;
  private Graph<WorkoutConnection, Workout> connectedPreferences;

  /**
   * User constructor to create new user
   * @param username - username of user
   * @param password - password (unhashed)
   * @param overallFitnessLevel - fitness level from which to tailor recommendations
   * @param allWorkouts - all workouts used to retrieve workouts that user has done
   */
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
    this.pastWorkouts = new ArrayList<>();
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

  /**
   * Alternative user constructor for existing user
   * @param username - corresponding username
   * @param password - hashed password
   * @param overallFitnessLevel - fitness level from which to tailor recommendations
   * @param totalNumWorkouts - number of workouts completed in the past
   * @param streak - number of consecutive days worked out
   * @param pastWorkoutIDs - list of previously completed workout IDs
   * @param lastWorkout - date of last completed workout
   * @param allWorkouts - all workouts used to retrieve workouts that user has done
   */
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
    this.pastWorkouts = new ArrayList<>();

    for (int i = 0; i < pastWorkoutIDs.size(); i++) {
      this.pastWorkouts.add(allWorkouts.get(pastWorkoutIDs.get(i)));
    }
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

  /**
   * method to verify password
   * @param enteredPassword - password to check
   * @return - true if verified, false if incorrect
   */
  public boolean checkPassword(String enteredPassword) {
    return (this.password == enteredPassword.hashCode());
  }

  public String getUsername() {
    return this.username;
  }

  public int getPassword() {
    return this.password;
  }

  public double getOFL() {
    return this.overallFitnessLevel;
  }

  public int getTotalNumWorkouts() {
    return this.totalNumWorkouts;
  }

  public List<Workout> getPastWorkouts() { return new ArrayList<>(this.pastWorkouts); }

  public int getStreak() {
    return this.streak;
  }

  public Graph getConnectedPreferences() {
    return this.connectedPreferences;
  }


  public void updateStreak() {
    this.streak++;
  }

  /**
   * resets streak to 0 if last workout date is not previous day
   */
  public void breakStreak() {
    this.streak = 0;
  }

  /**
   * method to record new workout date and tally after new workout is started
   * @param workout - the workout that is started
   */
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
    pastWorkouts.add(0, workout);
    lastWorkout = now;
  }

  public LocalDateTime getLastWorkout() {
    return lastWorkout;
  }

  /**
   * updates user's preference of workout to update preference graph
   * @param workoutID - ID of workout whose preference is being updated
   * @param newPreference - new corresponding preference
   */
  public void updatePreferences(String workoutID, double newPreference) {
    connectedPreferences.updateParameter(workoutID, newPreference);
  }




}
