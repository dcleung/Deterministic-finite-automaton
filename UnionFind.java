import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * A data structure for tracking a set of elements partitioned into disjoint subsets. We can
 * <em>union</em> these subsets together and then <em>find</em> which set an element belongs to. In
 * particular, we can determine when two elements belong to the same set.
 *
 * @author Derek Leung
 */
public class UnionFind {
	
	private int[] parent;
	private int[] rank;
	
    /**
     * Initializes a new union-find structure for the specified number of elements.
     * <p/>
     * Do NOT modify this constructor header.
     *
     * @param n the number of singleton sets with which to start
     * @throws IllegalArgumentException if {@code n} is negative
     */
	public UnionFind(int n) {
		if (n < 0) {
        	throw new IllegalArgumentException();
        }
		parent = new int[n+1];
		rank = new int[n+1];
		for (int i = 1; i < parent.length; i++) {
			parent[i] = i;
			rank[i] = 0;
		}
	}
	
    /**
     * Joins two sets.
     * <p/>
     *
     * @param u a vertex in the first set
     * @param v a vertex in the second set
     * @throws NoSuchElementException if {@code u < 0} or {@code u >= n} or {@code v < 0} o {@code v
     * >= n}
     */
	public void union(int u, int v) {
		link(find(u), find(v));
	}
	
    /**
     * Helper method for union that takes in two parents and makes one the parent of the other
     * by comparing the rank of each.
     * <p/>
     *
     * @param u a vertex in the first set
     * @param v a vertex in the second set
     * @throws NoSuchElementException if {@code u < 0} or {@code u >= n} or {@code v < 0} o {@code v
     * >= n}
     */
	public void link(int u, int v) {
		if (u < 0 || u >= parent.length || v < 0 || v >= parent.length) {
        	throw new NoSuchElementException();
        }
		if (rank[u] > rank[v]) {
			parent[v] = u;
		} else {
			parent[u] = v;
			if (rank[u] == rank[v]) {
				rank[v] = rank[v] + 1;
			}
		}
	}
	
    /**
     * Finds which set a vertex belongs to. We represent a vertex's set by the index of its tree's
     * root node. If for some {@code v}, {@code find(u) == find(v)}, then {@code u} and {@code v}
     * are in the same set.
     * <p/>
     *
     * @param u the vertex
     * @return the root of the set to which the input vertex belongs
     * @throws NoSuchElementException if {@code u < 0} or {@code u >= n}
     */
	public int find(int u) {
        if (u < 0 || u >= parent.length) {
        	throw new NoSuchElementException();
        }
		if (u != parent[u]) {
			parent[u] = find(parent[u]);
		}
		return parent[u];
	}
	
    /**
     * Generates the partitions in the form of a printable string.
     * <p/>
     *
     */
	public String partitions() {
		Map<Integer, ArrayList<Integer>> partitionMap = new HashMap<Integer, ArrayList<Integer>>();
		for (int i = 1; i < parent.length; i++) {
			int tgt = find(i);
		    if (partitionMap.containsKey(tgt)) {
		    	ArrayList<Integer> curr = partitionMap.get(tgt);
		        curr.add(i);
		        partitionMap.put(tgt, curr);
		    } else {
		        ArrayList<Integer> curr = new ArrayList<Integer>();
		        curr.add(i);
		        partitionMap.put(tgt, curr);
		    }
		}

		ArrayList<ArrayList<Integer>> partitions = new ArrayList<ArrayList<Integer>>();
		String tgt = "";
		for (Integer i : partitionMap.keySet()) {
			partitions.add(partitionMap.get(i));
		    String elt = partitionMap.get(i).toString();
		    elt = elt.substring(1, elt.length() - 1);
		    elt = elt.replace(",", "");
		    tgt = tgt + elt + "; ";
		 }
		 tgt = tgt.substring(0, tgt.length() - 2);
		 return tgt;
	}
}
