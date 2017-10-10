import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class DFA {
	
	/** We use a adjacency list to map the transition table.  The HashMap maps a integer to an ArrayList of connections
	 * so that the first index of the ArrayList is where the integer would go on the first letter of the alphabet.
	 */
	HashMap<Integer, ArrayList<Integer>> transition;
	
	// Final states are represented by an array list of integers.
	ArrayList<Integer> finalStates;
	int numLetters = 0;
	
	// Build our DFA given a txt file of transitions and final states
	public DFA (String file) {
		transition = new HashMap<Integer, ArrayList<Integer>>();
		finalStates = new ArrayList<Integer>();
		try {
			BufferedReader dfaText = new BufferedReader(new FileReader(file));
			int currState = 0;
			boolean foundF = false;
			String line;
			while ((line = dfaText.readLine()) != null) {
				currState++;
				String[] numbers = line.split(" ");
				ArrayList<Integer> inumbers = new ArrayList<Integer>();
				for (String s : numbers) {
				  if (s.equals("F")) {
					  foundF = true;
					  continue;
				  } else {
					  inumbers.add(Integer.parseInt(s));
				  }
				}
				if (!foundF) {
					transition.put(currState, inumbers);
				} else {
					finalStates = inumbers;
				}
			}
			dfaText.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(transition);
		System.out.println("Our final states are " + finalStates);
	}
	
	// Method to check is two states p and q are bad (one is in set of Final states, one is not)
	private boolean bad(int p, int q) {
		return !(finalStates.contains(p) == finalStates.contains(q));
	}
	
	// Method to check is two states p and q are state equivalent.  We output a String[] for easy printing.
	public String[] testStateEquivalence(int p, int q) {
		boolean flag = false;
		boolean flip = false;
		boolean bad = false;
		
		// We use a stack to keep track of the pairs we still need to process.
		Stack<Pair> stack = new Stack<Pair>();
		Pair base = new Pair(0, 0);
		UnionFind uf = new UnionFind(transition.size());
		int numLetters = transition.get(1).size();
		stack.push(new Pair(p, q));
		
		String partition = "";
		
		while (stack.size() > 0 && !flag) {
		      Pair curr = stack.pop();
		      int currP = curr.getX();
		      int currQ = curr.getY();

		      if (!flip) {
		        stack.push(new Pair(p, q));
		      }

		      if (bad(currP, currQ)) {
		        bad = true;
		        base = curr;
		        System.out.println("The bad pair is: " + curr);
		      } else {
		        int parentP = uf.find(currP);
		        int parentQ = uf.find(currQ);

		        if (parentP != parentQ) {
		          uf.union(parentP, parentQ);
		          partition = uf.partitions();
		          System.out.println(partition);
		          for (int i = 0; i < numLetters; i++) {
		            stack.push(new Pair(transition.get(currP).get(numLetters - i - 1),
	            		transition.get(currQ).get(numLetters - i - 1)));
		          }
		        }
		      }
		      flip = true;
		    }

		    if (bad) {
		    	String[] s = {("" + base.getX() + " " + base.getY()), partition};
			    return s;
		    } else {
		    	System.out.println("The states " + p + " and " + q + " are equivalent.");
			    String[] s = {"G", partition};
			    return s;
		    }
	}
}
