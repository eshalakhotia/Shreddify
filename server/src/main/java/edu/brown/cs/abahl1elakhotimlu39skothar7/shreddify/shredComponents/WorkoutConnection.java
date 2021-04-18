package edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.shredComponents;

import edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph.Edge;

/**
 * Edge that connects different Workout objects.
 */
public class WorkoutConnection implements Edge<WorkoutConnection, Workout> {
  private Workout start;
  private Workout end;
  private double weight;

  public WorkoutConnection(Workout start, Workout end) {
    this.start = start;
    this.end = end;
    this.weight = start.calcDistance(end);
  }

  @Override
  /**
   * @return Vertex edge is pointing from
   */
  public Workout getStart() {
    return start;
  }

  @Override
  /**
   * @return Vertex edge is pointing to
   */
  public Workout getEnd() {
    return end;
  }

  @Override
  /**
   * @return Edge weight
   */
  public double getWeight() {
    return weight;
  }

  @Override
  /**
   * Returns the heuristic (not necassarily along an edge) distance between two nodes.
   * @param s a V that extends Vertex, represents the start from which heuristic is calculated.
   * @param e a V that extends Vertex, represents the end where the heuristic is calculated to.
   * @return a double that equals the heuristic distance.
   */
  public double heuristic(Workout s, Workout e) {
    return s.calcDistance(e);
  }

}
