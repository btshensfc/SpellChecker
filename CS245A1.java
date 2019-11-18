import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.Collectors;
public class CS245A1{
	static Properties property = new Properties();
	static String inputFile, outputFile;
	static BSTSpellchecker treeBST;
	static Trie treeTrie;
	static boolean type = true; //true sets Trie, false sets BST
	static ArrayList<String> dictionary, input;
	static BufferedWriter output;
	private static long start, end = 0;

	public CS245A1(){
		treeTrie = new Trie();
		treeBST = new BSTSpellchecker();
		dictionary = new ArrayList<String>();
		input = new ArrayList<String>();
	}

	public static void main(String[] args){
		InputStream file;
		try{
			String filePath = new File("").getAbsolutePath();
			filePath = filePath.concat("/a1properties");
			file = new FileInputStream(filePath);
			property.load(file);
		} catch (IOException e){
			property.setProperty("storage", "trie");
		}

		if(property.getProperty("storage").equals("trie")){
			type = true;
		} else {
			type = false;
		}

		if(args.length < 2){
			System.out.println("Missing at least 1 Argument");
			return;
		} 
		CS245A1 assignment1 = new CS245A1();
		assignment1.inputFile = args[0];
		assignment1.outputFile = args[1];
		//start = System.currentTimeMillis();
		assignment1.createDictionary(type);
		assignment1.readInput(type);
		//end = System.currentTimeMillis();
		//System.out.println("Total time needed "+ (end-start));

	}

	public static void createDictionary(boolean type){
		try{
			URL url = new URL("https://raw.githubusercontent.com/magsilva/jazzy/master/resource/dict/english.0"); //find URL 
			Scanner scan = new Scanner(url.openStream()); //Create scanner 
			long start = System.currentTimeMillis();
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				if (!line.isEmpty()) {
					dictionary.add(line); //store words in dictionary array 
				}
			}
			if (type == true){ //find correct tree to insert into given Type
				while (scan.hasNext()){ 
					String word = scan.next(); //Read file data into trie or bst depending on type. 
					treeTrie.insert(word);
				}
				long end = System.currentTimeMillis();
				float sec = (end-start);
				System.out.println(sec + "ms");
			} else if (type == false){
				while (scan.hasNext()){
					String word1 = scan.next();
					treeBST.insert(word1);
				}
				long end = System.currentTimeMillis();
				float sec = (end-start);
				System.out.println("Time needed:" +sec + "ms");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public static void readInput(boolean type) {
        try{
        	Scanner scan = new Scanner(new File(inputFile));
        	while(scan.hasNext()){
        		String word = scan.next();
        		if (word != null){
        			input.add(word); //add all words in input to input arraylist for use in correcting
        		}
        	}
        }catch(FileNotFoundException e){
            System.out.println("Input file not found.");
			return;
        }
        if (type == true){
        	trieStore();
        } else {
        	bstStore();
        }
       
    }

    public static void trieStore(){
    	try{
    		File makeFile = new File(outputFile); //write an output file
    		output = new BufferedWriter(new FileWriter(makeFile));
    		for (int i = 0; i < input.size(); i++){
    			HashMap<String, Integer> mapDistance = new HashMap<String, Integer>(); //a map of strings in Dictionary + Leven. distance w/ input
    			String inWord = input.get(i);
    			if(treeTrie.search(inWord)){
    				output.write(inWord + "\n"); //if word spelled correctly write it out in the file.
    			} else {
    				for (int j = 0; j < dictionary.size(); j++){
    					String word = dictionary.get(j);
    					mapDistance.put(word, levenshtein(inWord, word, inWord.length(), word.length()));
    				}
    				//sort map distance by val
    				mapDistance = mapDistance.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    				Iterator<String> iterator = mapDistance.keySet().iterator();
    				for(int k = 0; k < 3; k++){ //write 3 elements in L.distance.
    					output.write(iterator.next() + " ");
    				}
    				output.write("\n");
    			}
    		}
    		output.close();
    	} catch (Exception e){
    		System.out.println(e.toString());
    	}
    }

    public static void bstStore(){
    	try{
    		File makeFile = new File(outputFile);
    		output = new BufferedWriter(new FileWriter(makeFile));
    		for (int i = 0; i < input.size(); i++){
    			HashMap<String, Integer> mapDistance = new HashMap<String, Integer>();
    			String inWord = input.get(i);
    			if(treeBST.search(inWord)){
    				output.write(inWord + "\n");
    			} else {
    				for (int j = 0; j < dictionary.size(); j++){
    					String word = dictionary.get(j);
    					mapDistance.put(word, levenshtein(inWord, word, inWord.length(), word.length()));
    				}
    				mapDistance = mapDistance.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    				Iterator<String> iterator = mapDistance.keySet().iterator();
    				for(int k = 0; k < 3; k++){
    					output.write(iterator.next() + " ");
    				}
    				output.write("\n");
    			}
    		}
    		output.close();
    	} catch (Exception e){
    		System.out.println(e.toString());
    	}
    }

	public static int levenshtein(String st1, String st2, int lt1, int lt2) { //enter two words + length of each
		int[][] length = new int[lt1+1][lt2+1]; //create graph that stores distances
		for(int i = 0; i <= lt1; i++){ 
			for(int j = 0; j <= lt2; j++){     //nested for loops testing each element of st1 and st2
				if(i==0){                      
					length[i][j] = j; 
				} else if (j == 0){
					length[i][j] = i;
				} else {
					length[i][j] = min(length[i - 1][j - 1] 
                 + costOfSubstitution(st1.charAt(i - 1), st2.charAt(j - 1)),
                  length[i - 1][j] + 1, 
                  length[i][j - 1] + 1); //set length[i][j] tothe smallest element of the lengths of words -1 and the cost of substitition between said words
				}
			}
		}
		return length[lt1][lt2];
	}
	public static int costOfSubstitution(char a, char b){ //returns how many subsitutions required to create correct words.
		return a==b ? 0:1;
	}

	public static int min(int ... numbers) //finds the minimym number of all vals.
	{
		return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
	}


}