package edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents;

import java.util.Set;

/**
 * Exercise class that contains a record of the exercises table in the database
 */
public class Exercise {
  private String iD;
  private String name;
  private int time;
  private int reps;
  private String mType;
  private double difficulty;
  private Set<String> muscle;
  private Set<String> equipment;
  private String description;

  /**
   * Exercise constructor
   * @param id - unique exercise ID
   * @param eName - exercise name to display
   * @param diff - difficulty of exercuse
   * @param eTime - length of time of exercises in seconds
   * @param eReps - number of reps of the exercises
   * @param measurementType - measurement type i.e., either 'reps' or 'time'
   * @param eMuscle - set of muscles targeted in exercise
   * @param equip - set of required equipment for the exercise
   * @param desc - description of exercise
   */
  public Exercise(String id, String eName, double diff, int eTime, int eReps, String measurementType, Set<String> eMuscle, Set<String> equip, String desc) {
    iD = id;
    name = eName;
    time = eTime;
    reps = eReps;
    mType = measurementType;
    muscle = eMuscle;
    difficulty = diff;
    equipment = equip;
    description = desc;
  }
  public String getExerciseId() {
    return iD;
  }
  public String getExerciseName() {
    return name;
  }
  public int getExerciseTime() {
    return time;
  }
  public int getExerciseReps() {
    return reps;
  }
  public String getmType() {
    return mType;
  }
  public double getExerciseDifficulty() {
    return difficulty;
  }
  public Set<String> getExerciseMuscle() {
    return muscle;
  }
  public Set<String> getExerciseEquipment() {
    return equipment;
  }

  public String getDescription() {
    return description;
  }
}
