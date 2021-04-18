package edu.brown.cs.abahl1elakhotimlu39skothar7;

import java.util.*;

import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents.Exercise;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.OutEdgeCache;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents.Workout;
import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents.WorkoutConnection;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;


public class CacheTest {

  private OutEdgeCache _cache;

  @Before
  public void setUp() {
    Set<String> targetAreas1 = new HashSet<>();
    targetAreas1.add("abs");
    Set<String> targetAreas2 = new HashSet<>();
    targetAreas2.add("cardio");
    targetAreas2.add("legs");
    targetAreas2.add("arms");
    Set<String> targetAreas3 = new HashSet<>();
    targetAreas3.add("glutes");
    Set<String> equipment1 = new HashSet<>();
    equipment1.add("flat wall");
    Set<String> targetAreas4 = new HashSet<>();
    targetAreas4.add("cardio");
    targetAreas4.add("legs");
    Set<String> targetAreas5 = new HashSet<>();
    targetAreas5.add("arms");
    Set<String> equipment2 = new HashSet<>();
    equipment2.add("5 lb dumbbell");
    Exercise bicycleCrunches = new Exercise("bicycleCrunches40", "40 bicycle crunches", 60, 60, 40, "reps", targetAreas1, new HashSet<>(), "");
    Exercise jumpingJacks = new Exercise("jumpingJacks100", "100 jumping jacks", 88, 300, 100,"reps", targetAreas2, new HashSet<>(), "");
    Exercise wallSit = new Exercise("wallSit60", "60 sec wall sit", 85, 60, 1,"time", targetAreas3, equipment1, "");
    Exercise toeTouches = new Exercise("toeTouches60", "60 sec toe touches", 32, 60, 1,"time", targetAreas4, new HashSet<>(), "");
    Exercise pushUps = new Exercise("pushUps20", "20 push ups", 46, 70, 20,"reps", targetAreas5, new HashSet<>(), "");
    Exercise russianTwists = new Exercise("russianTwists40", "40 push ups", 56, 70, 40,"reps", targetAreas1, equipment2, "");
    List<Exercise> exerciseList1 = new ArrayList<Exercise>();
    exerciseList1.add(jumpingJacks);
    exerciseList1.add(pushUps);
    List<Exercise> exerciseList2 = new ArrayList<Exercise>();
    exerciseList2.add(wallSit);
    exerciseList2.add( bicycleCrunches);
    exerciseList2.add(toeTouches);
    exerciseList2.add(pushUps);
    exerciseList2.add(russianTwists);
    exerciseList2.add(jumpingJacks);
    List<Exercise> exerciseList3 = new ArrayList<Exercise>();
    exerciseList3.add(bicycleCrunches);
    exerciseList3.add(toeTouches);
    exerciseList3.add(russianTwists);
    exerciseList3.add(jumpingJacks);
    List<Exercise> exerciseList4 = new ArrayList<Exercise>();
    exerciseList4.add(toeTouches);
    exerciseList4.add(jumpingJacks);
    List<Exercise> exerciseList5 = new ArrayList<Exercise>();
    exerciseList5.add(wallSit);
    exerciseList5.add(bicycleCrunches);
    exerciseList5.add(pushUps);
    List<Exercise> exerciseList6 = new ArrayList<Exercise>();
    exerciseList6.add(jumpingJacks);
    exerciseList6.add(bicycleCrunches);
    exerciseList6.add(jumpingJacks);
    exerciseList6.add(pushUps);
    exerciseList6.add(jumpingJacks);
    exerciseList6.add(wallSit);
    exerciseList6.add(jumpingJacks);
    exerciseList6.add(russianTwists);
    exerciseList6.add(jumpingJacks);
    _cache = new OutEdgeCache<WorkoutConnection, Workout>();
    Workout workout1 = new Workout("new workout 1", "skajdhfalsd", 10, exerciseList1, _cache);
    Workout workout2 = new Workout("new workout 2", "asdfkjhasdlf", 1, exerciseList2, _cache);
    Workout workout3 = new Workout("new workout 3", "iequowrhjfk", 4, exerciseList3, _cache);
    Workout workout4 = new Workout("new workout 4", "asdjfhqwe", 15, exerciseList4, _cache);
    Workout workout5 = new Workout("new workout 5", "34253p9o", 6, exerciseList5, _cache);
    Workout workout6 = new Workout("new workout 6", "jkbmnvsa", 1, exerciseList6, _cache);
    WorkoutConnection w1w4 = new WorkoutConnection(workout1, workout4);
    WorkoutConnection w1w6 = new WorkoutConnection(workout1, workout6);
    WorkoutConnection w1w5 = new WorkoutConnection(workout1, workout5);
    WorkoutConnection w2w6 = new WorkoutConnection(workout2, workout6);
    WorkoutConnection w3w6 = new WorkoutConnection(workout3, workout6);
    WorkoutConnection w4w6 = new WorkoutConnection(workout4, workout6);
    WorkoutConnection w4w5 = new WorkoutConnection(workout4, workout5);
    WorkoutConnection w4w1 = new WorkoutConnection(workout4, workout1);
    WorkoutConnection w5w1 = new WorkoutConnection(workout5, workout1);
    WorkoutConnection w5w4 = new WorkoutConnection(workout5, workout4);
    WorkoutConnection w6w1 = new WorkoutConnection(workout6, workout1);
    WorkoutConnection w6w2 = new WorkoutConnection(workout6, workout2);
    WorkoutConnection w6w3 = new WorkoutConnection(workout6, workout3);
    WorkoutConnection w6w4 = new WorkoutConnection(workout6, workout4);
    List<WorkoutConnection> w1Edges = new ArrayList<>();
    w1Edges.add(w1w4);
    w1Edges.add(w1w5);
    w1Edges.add(w1w6);
    List<WorkoutConnection> w2Edges = new ArrayList<>();
    w1Edges.add(w2w6);
    List<WorkoutConnection> w3Edges = new ArrayList<>();
    w3Edges.add(w3w6);
    List<WorkoutConnection> w4Edges = new ArrayList<>();
    w4Edges.add(w4w1);
    w4Edges.add(w4w5);
    w4Edges.add(w4w6);
    List<WorkoutConnection> w5Edges = new ArrayList<>();
    w5Edges.add(w5w1);
    w5Edges.add(w5w4);
    _cache.addToCache(workout1, w1Edges);
    _cache.addToCache(workout2, w2Edges);
    _cache.addToCache(workout3, w3Edges);
    _cache.addToCache(workout4, w4Edges);
    _cache.addToCache(workout5, w5Edges);
  }

  @After
  public void tearDown() {
    _cache = null;
  }

  /**
   ** Tests that the cache can be cleared
   */
  @Test
  public void testClearCache() {
    assertEquals(_cache.getOutgoingEs("skajdhfalsd").size(), 4);
    assertEquals(_cache.getOutgoingEs("asdjfhqwe").size(), 3);
    _cache.clearCache();
    assertEquals(_cache.getOutgoingEs("skajdhfalsd"), null);
    assertEquals(_cache.getOutgoingEs("asdjfhqwe"), null);
  }

  /**
   ** Tests that the cache can correctly get vertexes it contains
   */
  @Test
  public void cacheGetNodeTest(){
    assertEquals("new workout 1", _cache.getNode("skajdhfalsd").getName());
    assertEquals("new workout 4", _cache.getNode("asdjfhqwe").getName());
    assertEquals(null, _cache.getNode("asduaflisetuawjlskd"));
  }

}