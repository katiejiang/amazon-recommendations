import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;


public class Adsorption {
	HashMap<String, HashMap<String, Double>> vertices;
	HashMap<String, HashMap<String, Double>> labels;
	
	public Adsorption() {
		vertices = new HashMap<String, HashMap<String, Double>>();
		labels = new HashMap<String, HashMap<String, Double>>();
	}
	
	void addReview(String uid, String pid, double weight) {
		weight = weight - 3.0;
		HashMap<String, Double> un;
		HashMap<String, Double> pn;
		if(vertices.containsKey(uid)) {
			un = vertices.get(uid);
		} else {
			un = new HashMap<String, Double>();
			vertices.put(uid, un);
			labels.put(uid, new HashMap<String, Double>());
		}
		if(vertices.containsKey(pid)) {
			pn = vertices.get(pid);
		} else {
			pn = new HashMap<String, Double>();
			vertices.put(pid, pn);
			HashMap<String, Double> pl = new HashMap<String, Double>();
			pl.put(pid, 1.0);
			labels.put(pid, pl);
		}
		pn.put(uid, weight);
		un.put(pid, weight);
	}
	
	void runAdsorption(int iterations) {
		for(int i = 0; i < iterations; i++) {
			HashMap<String, HashMap<String, Double>> newLabels = 
					new HashMap<String, HashMap<String, Double>>();
			for(Entry<String, HashMap<String, Double>> node : vertices.entrySet()) {
				String nodeName = node.getKey();
				if(!newLabels.containsKey(nodeName)) {
					newLabels.put(nodeName, new HashMap<String, Double>());
				}
				for(Entry<String, Double> edge : node.getValue().entrySet()) {
					double w = edge.getValue();
					String neighbor = edge.getKey();
					for(Entry<String, Double> label: labels.get(nodeName).entrySet()) {
						double nw = label.getValue() * w;
						String s = label.getKey();
						if(newLabels.containsKey(neighbor)) {
							HashMap<String, Double> map = newLabels.get(neighbor);
							if(map.containsKey(s)) {
								map.put(s, map.get(s) + nw);
							} else {
								map.put(s, nw);
							}
						}
						else {
							HashMap<String, Double> map = new HashMap<String, Double>();
							map.put(s, nw);
							newLabels.put(neighbor, map);
						}
					}
				}
			}
			labels = newLabels;
		}
	}
	
	void printLabels(int limit) {
		int i = 0;
		for(Entry<String, HashMap<String, Double>> entry : labels.entrySet()) {
			System.out.println(entry.getKey() + entry.getValue());
			i++;
			if(i > limit) {
				return;
			}
		}
	}
	
	Iterator<Entry<String, HashMap<String, Double>>> iter() {
		Set<Entry<String, HashMap<String, Double>>> c = new 
				HashSet<Entry<String, HashMap<String, Double>>>();
		for(Entry<String, HashMap<String, Double>> e : labels.entrySet()) {
			if(!e.getValue().isEmpty()) {
				c.add(e);
			}
		}
		return c.iterator();
	}
	
}