package edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph;

import java.util.*;

/**
 * This class handles the dijkstra algorithm to find the shortest path between
 * two nodes.
 * @param <E> represents Edges of graph
 * @param <V> represent Vertices of graph
 */
public class Graph<E extends Edge<E, V>, V extends Vertex<E, V>> {
  private final Map<String, V> allNodes;
  /**
   * Creates Graph object with empty HashMap containing all Nodes to be contained by the Graph.
   */
  public Graph() {
    this.allNodes = new HashMap<>();
  }

  /**
   * Creates Graph object with HashMap containing all Nodes to be contained by the Graph.
   * @param allNodes the nodes that the graph object is intended to contain
   */
  public Graph(Map<String, V> allNodes) {
    this.allNodes = new HashMap<String, V>(allNodes);
  }

  /**
   * Returns the allNodes field of the Graph.
   * @return the HashMap containing the Nodes in the graph and their unique names
   */
  public HashMap<String, V> getAllNodes() {
    return new HashMap<>(allNodes);
  }

  /**
   * Calculates the cumulative weight of a List of connected Edges.
   * @param connectedMapWays the List of Edges that cumulative edge weight is to be determined for.
   * @return a double that equals the sum of all the Edge weights of the Edges in the List.
   */
  public double getTotalLength(ArrayList<E> connectedMapWays) {
    double totalLength = 0;
    for (E connectedMapWay : connectedMapWays) {
      totalLength += connectedMapWay.getWeight();
    }
    return totalLength;
  }

  /**
   * Finds the shortest path between nodes.
   * Uses a greedy breadth-first algorithm that explores closest nodes first.
   * @param startNodeID the ID of the Node to start from when finding the shortest path.
   * @param endNodeID the ID of the Node to end with when finding the shortest path.
   * @param curShortestPaths HashMap of NodeIDs and shortest known paths to reach their Nodes.
   * @param finalShortestPaths HashMap of NodeIDs and optimal paths to reach their Nodes.
   * @param nextClosestNodeIDs PriorityQueue containing NodeIDs from curShortestPaths,
   *                           sorted in ascending order of total distance of paths.
   * @param cache the cache containing nodes and the outgoing edges from those nodes.
   * @return an ArrayList of Edges that make up the shortest path from start Node to end Node.
   */
  public ArrayList<E> dijkstra(
          String startNodeID,
          String endNodeID,
          HashMap<String, ArrayList<E>> curShortestPaths,
          HashMap<String, ArrayList<E>> finalShortestPaths,
          PriorityQueue<String> nextClosestNodeIDs,
          OutEdgeCache<E, V> cache) {
    try {
      // checks if finalized path to goal node has been found yet
      ArrayList<E> pathToEndNode = finalShortestPaths.get(endNodeID);
      if (pathToEndNode == null) {
        // gets path to current start node
        ArrayList<E> pathToStartNode = finalShortestPaths.get(startNodeID);
        if (pathToStartNode == null) {
          throw new RuntimeException("ERROR: no path to start node");
        } else {
          // gets traversable nodes from current start node
          List<E> waysFromStartNode = allNodes.get(startNodeID).getEdgesFromNode(this);
          for (int i = 0; i < waysFromStartNode.size(); i++) {
            ArrayList<E> pathToStartNodeCopy = new ArrayList<E>(pathToStartNode);
            E curMapWay = waysFromStartNode.get(i);
            String curWayEndID = curMapWay.getEnd().getID();
            if (finalShortestPaths.get(curWayEndID) == null) {
              // calculates length of path to each of neighbors of current start node
              double curLength = this.getTotalLength(pathToStartNode) + curMapWay.getWeight();
              ArrayList<E> prevPath = curShortestPaths.get(curWayEndID);
              // replaces previous shortest path to that neighbor if no previous path existed
              // or if this path is shorter
              if ((prevPath == null) || curLength < this.getTotalLength(prevPath)) {
                pathToStartNodeCopy.add(curMapWay);
                curShortestPaths.put(curWayEndID, pathToStartNodeCopy);
                nextClosestNodeIDs.remove(curWayEndID);
                nextClosestNodeIDs.add(curWayEndID);
              }
            }
          }
          // next shortest path from curShortestPaths is moved to finalCurrentShortestPaths
          // the node that this path points to becomes the next start node (dijkstra recurs)
          if (nextClosestNodeIDs.size() > 0) {
            String nextClosestNodeID = nextClosestNodeIDs.poll();
            ArrayList<E> pathToNextClosestNode = curShortestPaths.remove(nextClosestNodeID);
            finalShortestPaths.put(nextClosestNodeID, pathToNextClosestNode);
            return dijkstra(
                    nextClosestNodeID,
                    endNodeID,
                    curShortestPaths,
                    finalShortestPaths,
                    nextClosestNodeIDs,
                    cache);
          } else {
            return new ArrayList<>();
          }
        }
      } else {
        return pathToEndNode;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  /**
   * Finds the shortest path between nodes.
   * Uses combination of depth-first and breadth-first by accounting for distance from goal node
   * when choosing a node to explore.
   * @param startNode the Node to start from when finding the shortest path.
   * @param endNode the Node to end with when finding the shortest path.
   * @param curShortestPaths HashMap of NodeIDs and shortest known paths to reach their Nodes.
   * @param finalShortestPaths HashMap of NodeIDs and optimal paths to reach their Nodes.
   * @param nextClosestNodeIDs PriorityQueue containing NodeIDs from curShortestPaths,
   *                           sorted in ascending order of total distance of paths
   * @param cache the cache containing nodes and the outgoing edges from those nodes.
   * @return an ArrayList of Edges that make up the shortest path from start Node to end Node.
   */
  public ArrayList<E> aStar(
          V startNode,
          V endNode,
          HashMap<String, ArrayList<E>> curShortestPaths,
          HashMap<String, ArrayList<E>> finalShortestPaths,
          PriorityQueue<String> nextClosestNodeIDs,
          OutEdgeCache<E, V> cache) {
    try {
      // checks if finalized path to goal node has been found yet
      String startNodeID = startNode.getID();
      String endNodeID = endNode.getID();
      ArrayList<E> pathToEndNode = finalShortestPaths.get(endNodeID);
      if (pathToEndNode == null) {
        // gets path to current start node
        ArrayList<E> pathToStartNode = finalShortestPaths.get(startNodeID);
        if (pathToStartNode == null) {
          throw new RuntimeException("ERROR: no path to start node");
        } else {
          // gets traversable nodes from current start node
          List<E> waysFromStartNode = allNodes.get(startNodeID).getEdgesFromNode(this);
          for (E e : waysFromStartNode) {
            addNode(e.getEnd().getID(), e.getEnd());
          }
          for (E curMapWay : waysFromStartNode) {
            ArrayList<E> pathToStartNodeCopy = new ArrayList<>(pathToStartNode);
            V curWayEnd = curMapWay.getEnd();
            String curWayEndID = curWayEnd.getID();
            if (finalShortestPaths.get(curWayEndID) == null) {
              // calculates distance from neighbor node to goal node using heuristic
              double heuristic =
                  curMapWay.heuristic(curWayEnd, endNode);
              // adds this to length of path to neighbor node
              // (compromise between breadth or depth focused search
              double curLength =
                  this.getTotalLength(pathToStartNode) + heuristic + curMapWay.getWeight();
              ArrayList<E> prevPath = curShortestPaths.get(curWayEndID);
              // replaces previous shortest path to that neighbor if no previous path existed
              // or if this path is shorter
              if ((prevPath == null) || curLength < this.getTotalLength(prevPath)) {
                pathToStartNodeCopy.add(curMapWay);
                curShortestPaths.put(curWayEndID, pathToStartNodeCopy);
                nextClosestNodeIDs.remove(curWayEndID);
                nextClosestNodeIDs.add(curWayEndID);
              }
            }
          }
          // next shortest path from curShortestPaths is moved to finalCurrentShortestPaths
          // the node that this path points to becomes the next start node (A* recurs)
          if (nextClosestNodeIDs.size() > 0) {
            String nextClosestNodeID = nextClosestNodeIDs.poll();
            ArrayList<E> pathToNextClosestNode = curShortestPaths.remove(nextClosestNodeID);
            finalShortestPaths.put(nextClosestNodeID, pathToNextClosestNode);
            return aStar(
                    allNodes.get(nextClosestNodeID),
                    endNode,
                    curShortestPaths,
                    finalShortestPaths,
                    nextClosestNodeIDs,
                    cache);
          } else {
            return new ArrayList<>();
          }
        }
      } else {
        return pathToEndNode;
      }
    } catch (Exception e) {
      throw new RuntimeException("ERROR: database");
    }
  }

  /**
   * Updates parameter based on a given update to its neighbor.
   * @param id the id of the Vertex we know we want to update.
   * @param scaledRating value that we want to update the parameterToUpdate of the given Vertex to
   */
  public void updateParameter(String id, double scaledRating) {
    V curVertex = this.getAllNodes().get(id);
    curVertex.setParameterToUpdate(scaledRating);
    List<E> outEdges = curVertex.getEdgesFromNode(this);
    for (int i = 0; i < outEdges.size(); i++) {
      final double cappedMaxDist = 27;
      double adjustedWeight;
      E edgeToConnectedVertex = outEdges.get(i);
      double rawWeight = edgeToConnectedVertex.getWeight();
      if (rawWeight >= cappedMaxDist) {
        adjustedWeight = cappedMaxDist;
      } else {
        adjustedWeight = rawWeight;
      }
      V vertexToUpdate = edgeToConnectedVertex.getEnd();
      double parameterToUpdate = vertexToUpdate.getParameterToUpdate();
      if (parameterToUpdate < scaledRating) {
        vertexToUpdate.setParameterToUpdate(
                parameterToUpdate
                        + ((scaledRating - parameterToUpdate)
                        * ((cappedMaxDist - adjustedWeight) / cappedMaxDist)));
      } else {
        vertexToUpdate.setParameterToUpdate(
                parameterToUpdate
                        - ((parameterToUpdate - scaledRating)
                        * ((cappedMaxDist - adjustedWeight) / cappedMaxDist)));
      }
    }
  }

  /**
   * @param id of node to add to allNodes list
   * @param node to add to allNodes list
   */
  public void addNode(String id, V node) {
    allNodes.put(id, node);
  }

}
