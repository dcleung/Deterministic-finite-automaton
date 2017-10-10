import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The main method that takes in a DFA.txt and Query.txt and prints out test.txt and partition.txt files.
 * We can run this in command line in the src folder to generate our desired txt files.
 *
 * @author Derek Leung
 */

public class Main {
	public static void main(String[] args) {
	    DFA myDFA = new DFA("DFA.txt");
	    
	    // We use a list data structure to keep track of the pairs we want to test.
	    List<Pair> pairs = new ArrayList<Pair>();
	    try {
	    	
	    	BufferedReader br = new BufferedReader(new FileReader("Query.txt"));
	    	String line;
	    	while ((line = br.readLine()) != null) {
	    		String[] numbers = line.split(" ");
	    		ArrayList<Integer> inumbers = new ArrayList<Integer>();
	    		for (String s : numbers) {
	    			inumbers.add(Integer.parseInt(s));
		    	}
		        pairs.add(new Pair(inumbers.get(0), inumbers.get(1)));
	    	}
	    	br.close();
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}

	    try {
	    	PrintStream writeToTest = new PrintStream(new File("test.txt"));
	    	PrintStream writeToPartition = new PrintStream(new File("partition.txt"));
	    	
	    	// We test state equivalence for each pair in the list of pairs.
	    	for (Pair p : pairs) {
	    		String[] ans = myDFA.testStateEquivalence(p.getX(), p.getY());
	    		writeToTest.println(ans[0]);
	    		writeToPartition.println(ans[1]);
	    	}
	    	writeToTest.close();
	    	writeToPartition.close();
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
	}
}
