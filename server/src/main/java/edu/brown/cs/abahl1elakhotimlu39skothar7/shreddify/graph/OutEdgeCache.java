package edu.brown.cs.abahl1elakhotimlu39skothar7.shreddify.graph;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * This class handles caching each id with its node object and outgoing edges.
 * @param <E> Class representing edges
 * @param <V> Class representing nodes
 */
public class OutEdgeCache<E extends Edge<E, V>, V extends Vertex<E, V>> {
  //this maps holds each id with its corresponding node object and list of outgoing edges
  private Cache<String, Map<V, List<E>>> outgoingEs;
  private final int cacheSize = 200000;
  private final int expirationMins = 15;

  /**
   * OutEdgeCache Constructor.
   */
  public OutEdgeCache() {
    outgoingEs = CacheBuilder.newBuilder().maximumSize(cacheSize)
            .expireAfterWrite(expirationMins, TimeUnit.MINUTES).build();
  }

  /**
   * @param curNode node for which we are saving outgoing edges
   * @param edges edges to add to node
   */
  public void addToCache(V curNode, List<E> edges) {
    Map<V, List<E>> map = new HashMap<>();
    map.put(curNode, edges);
    outgoingEs.put(curNode.getID(), map);
  }

  /**
   * @param id corresponding to node for which we want to retrieve outgoing edges
   * @return list of outgoing edges
   */
  public List<E> getOutgoingEs(String id) {
    Map<V, List<E>> map = outgoingEs.getIfPresent(id);
    if (map == null) {
      return null;
    }
    return map.values().iterator().next();
  }

  /**
   * @param id corresponding to node for which we want to retrieve its saved node object
   * @return node object corresponding to inputted id
   */
  public V getNode(String id) {
    Map<V, List<E>> map = outgoingEs.getIfPresent(id);
    if (map == null) {
      return null;
    }
    return map.keySet().iterator().next();
  }

  /**
   * Method to clear all elements of cache.
   */
  public void clearCache() {
    outgoingEs = CacheBuilder.newBuilder().maximumSize(cacheSize)
            .expireAfterWrite(expirationMins, TimeUnit.MINUTES).build();
  }
}

