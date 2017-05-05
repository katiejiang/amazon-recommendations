import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Parse {

	private static class Label implements Comparable<Label> {
		String id;
		double label;

		public Label(String id, double label) {
			this.id = id;
			this.label = label;
		}

		@Override
		public int compareTo(Label other) {
			return Double.compare(this.label, other.label);
		}

		@Override
		public String toString() {
			return id;
		}
	}

	public static void main (String args[]) throws IOException {
		Adsorption g = new Adsorption();
		File file = new File("output.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		    String line;
		    while ((line = br.readLine()) != null) {
		    	String[] elts = line.split("\\s+");
		    	g.addReview(elts[0], elts[1], Double.parseDouble(elts[2]));
		    }
		g.runAdsorption(10);
		Iterator<Entry<String, HashMap<String, Double>>>iter = g.iter();
		try{
			Writer writer = new BufferedWriter(new OutputStreamWriter(
		              new FileOutputStream("src/adsorptionresults.json"), "utf-8"));
		    writer.write("{\"recommendations\": [");
		    Entry<String, HashMap<String, Double>> first = iter.next();
		    writer.write(entryString(first));
		    while(iter.hasNext()) {
		    	writer.write(", \n");
		    	Entry<String, HashMap<String, Double>> e = iter.next();
		    	writer.write(entryString(e));
		    }
		    writer.write("]}");
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
	}

	static String entryString(Entry<String, HashMap<String, Double>> entry) {
		StringBuilder build = new StringBuilder();
		build.append("{\"id\":\"" + entry.getKey() + "\", \"recs\":[");
		ArrayList<Label> labels = new ArrayList<Label>();
		for(Entry<String, Double> l : entry.getValue().entrySet()) {
			Label la = new Label(l.getKey(), l.getValue());
			labels.add(la);
		}
		Collections.sort(labels);
		Iterator<Label> iter = labels.iterator();
		String s = iter.next().toString();
		build.append("\"" + s + "\"");
		int i = 0;
		while(iter.hasNext()) {
			if(i > 8) {
				break;
			}
			build.append(", \"" + iter.next().toString() + "\"");
			i++;
		}
		build.append("]}");
		return build.toString();
	}

}
