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
  private int difficulty;
  private Map<String, Double> muscle;
  private Set<String> equipment;
  private DatabaseConn base;
  private List<String[]> usersList = new ArrayList<>();
  
  public Exercise(String id, String eName, int eTime, int eReps, Map<String, Double> eMuscle, Set<String> equip, int diff)
    throws SQLException, ClassNotFoundException {
    iD = id;
    name = eName;
    time = eTime;
    reps = eReps;
    muscle = eMuscle;
    difficulty = diff;
    equipment = equip;
    base = new DatabaseConn();
  }
  public String getExerciseName() throws SQLException {
    name = base.getAllExerciseAttributes().get(1);
    return name;
  }
  public int getExerciseTime() throws SQLException {
    time = Integer.valueOf(base.getAllExerciseAttributes().get(5));
    return time;
  }
  public int getExerciseReps() throws SQLException {
    reps = Integer.valueOf(base.getAllExerciseAttributes().get(4));
    return reps;
  }
  public int getExerciseDifficulty() throws SQLException {
    difficulty = Integer.valueOf(base.getAllExerciseAttributes().get(3));
    return difficulty;
  }
  public Map<String, Double> getExerciseMuscle() {
    return muscle;
  }
  public Set<String> getExerciseEquipment() {
    return equipment;
  }
}
