package edu.brown.cs.abahl1elakhotimlu39skothar7;

import edu.brown.cs.abahl1elakhotimlu39skothar7.graph.Graph;
import edu.brown.cs.abahl1elakhotimlu39skothar7.graph.Vertex;
import edu.brown.cs.abahl1elakhotimlu39skothar7.graph.Workout;

import java.util.HashMap;

public class User {
  private String username;
  private int password;
  private int overallFitnessLevel;
  private int totalNumWorkouts;
  private int streak;
  private Graph connectedPreferences;

  public User(
          String username,
          String password,
          int overallFitnessLevel,
          HashMap<String, Workout> allWorkouts) {
    this.username = username;
    this.password = password.hashCode();
    this.overallFitnessLevel = overallFitnessLevel;
    this.totalNumWorkouts = 0;
    this.streak = 0;
    this.connectedPreferences = new Graph(allWorkouts);
  }

  public boolean checkPassword(String enteredPassword) {
    return (this.password == enteredPassword.hashCode());
  }

  public String getUsername() {
    return this.username;
  }


  public int getOFL() {
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

  //Void updatePreferences(String workoutID, double newPreference)




}
