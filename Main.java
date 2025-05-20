import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.*;

class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        BufferedReader input = new BufferedReader(new FileReader("TimeMachine.txt"));
        List<String> list = input.lines().collect(Collectors.toList());
        input.close();

        Map<Integer, List<String>> map = topWords(list, 100);
        
        for (Integer key : map.keySet()) {
            System.out.println(key + " - " + map.get(key));
        }
    }

    public static Map<Integer, List<String>> topWords(List<String> lines, int num) {
        // create a new map (word, frequency)
        Map<String, Integer> wordMap = new HashMap<>();

		// iterate through lines
		for (String line : lines) {
            // check if line is empty
            if (line.isEmpty()) { continue; }

			// remove number & punctuations
			for (int i = 0; i < line.length(); i++) {
				// variable stores each letter in the line
				String lineChar = line.substring(i, i+1);
				// if char is number or punctuation (except hyphen and apostrophe), remove
				if (!(lineChar.equals("-") || lineChar.equals("'"))) {
					if (lineChar.matches(".*\\d.*") || lineChar.matches("(.*)[\\p{P}](.*)")) {
						line = line.substring(0, i) + " " + line.substring(i+1);
						i--;
					}
				}
        	}

			// split array to have multiple items
			ArrayList<String> arr = new ArrayList<String>();
			arr.addAll(Arrays.asList(line.trim().split("\\s* \\s*")));
            
			// convert all items to lowercase
			arr.replaceAll(String::toLowerCase);

			// put words & their frequencies in the arr to the map
            for (String word : arr) {
                if (wordMap.containsKey(word)) {
                    wordMap.put(word, wordMap.get(word) + 1);
                } else {
                    wordMap.put(word, 1);
                }
            }
		}

        // create a map (frequency, word) 
        Map<Integer, List<String>> frequencyMap = new TreeMap<>();


        // put frequency & the word (if more than one words share same freq. add it to the list)
        for (String word : wordMap.keySet()) {
            // frequency 
            int frequency = wordMap.get(word);
            // check if the frequency is greter than num
            if (frequency > num) {
                // if freq is in the map, add the word into the list
                if (frequencyMap.containsKey(frequency)) {
                    frequencyMap.get(frequency).add(word);
                // if freq is not in the map, make a new list value
                } else {
                    List<String> list = new ArrayList<>();
                    list.add(word);
                    frequencyMap.put(frequency, list);
                }                
            }
        }

		return frequencyMap;
    }
}
