package edu.brown.cs.abahl1elakhotimlu39skothar7.graph;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Exercise {
  private String iD;
  private String name;
  private int time;
  private int reps;
  private double difficulty;
  private Map<String, Double> muscle;
  private Set<String> equipment;
  private List<String[]> usersList = new ArrayList<>();
  
  public Exercise(String id, String eName, double diff, int eTime, int eReps, Map<String, Double> eMuscle, Set<String> equip) {
    iD = id;
    name = eName;
    time = eTime;
    reps = eReps;
    muscle = eMuscle;
    difficulty = diff;
    equipment = equip;
  }
  public String getExerciseName() throws SQLException {
    return name;
  }
  public int getExerciseTime() throws SQLException {
    return time;
  }
  public int getExerciseReps() throws SQLException {
    return reps;
  }
  public double getExerciseDifficulty() throws SQLException {
    return difficulty;
  }
  public Map<String, Double> getExerciseMuscle() {
    return muscle;
  }
  public Set<String> getExerciseEquipment() {
    return equipment;
  }
}
