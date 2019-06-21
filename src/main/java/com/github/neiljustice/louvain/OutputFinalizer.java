package com.github.neiljustice.louvain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OutputFinalizer {
	
	String filepath;
	
	OutputFinalizer(String filepath) {
		this.filepath = System.getProperty("user.dir") + "\\data\\" + filepath;
	}

	void finalizeOutput(String inputFile, int etape) throws FileNotFoundException, IOException {
		inputFile = System.getProperty("user.dir") + "\\data\\" + inputFile;
		int count = getFinalNodesCount(inputFile) +1;
	    
		ArrayList<String> clusters[] = new ArrayList[count];
		for(int i = 0; i < count; i++) {
			clusters[i] = new ArrayList<String>();
		}
		
		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
			String line;
			int y = 0;
			
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(":");
		        List<String> records = new ArrayList<String>(Arrays.asList(values));
		        String originalCluster = records.get(0);
		        String finalCluster = records.get(etape);
		        String originalClusterString = getClasses().get(Integer.parseInt(originalCluster));
		        clusters[Integer.parseInt(finalCluster)].add(originalClusterString);
		        y++;
		    }
		    
		    System.out.println("[");
			for (int i=0;i<count;i++) {
				int a = 0;
				if (!clusters[i].isEmpty()) {
					for(String name : clusters[i]) {
						if (a == 0) System.out.print("{");
						
						if (clusters[i].indexOf(name) == (clusters[i].size() -1))  {
							if (i == clusters.length - 1) System.out.println(name + "}");
							else System.out.println(name + "},");
						}
						else System.out.print(name + ", ");
						a++;
					}
				}
			}
		    System.out.print("]");
		}
	}
	
	int getFinalNodesCount(String filepath) throws FileNotFoundException, IOException {
		List<String> records;
		List<Integer> finalRecords = new ArrayList<Integer>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(":");
		        records = new ArrayList<String>(Arrays.asList(values));
		        String value = records.get(1);
		        if(!finalRecords.contains(value)) finalRecords.add(Integer.parseInt(value));
		    }
		    return findMax(finalRecords);
		}
	}
	
    public static Integer findMax(List<Integer> list) 
    { 
        if (list == null || list.size() == 0) return Integer.MIN_VALUE;  
  
        List<Integer> sortedlist = new ArrayList<>(list); 
  
        Collections.sort(sortedlist); 
  
        return sortedlist.get(sortedlist.size() - 1); 
    } 
	
	List<String> getClasses() throws IOException {
		List<String> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(",");
		        String first = values[0];
		        String second = values[1];
		        
		        if(!records.contains(first)) records.add(first);
		        if(!records.contains(second)) records.add(second);
		    }		    
		    return records;
		}
	}
	
	int getNumberOfClasses() throws IOException {
		return getClasses().size();
	}
}
