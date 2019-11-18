import java.io.*;
import java.net.*;
import java.util.*;
public class Test{
	static Trie treeTrie = new Trie();
	static BSTSpellchecker treeBST = new BSTSpellchecker();
	public static void main(String[] args){
		System.out.println("test");
		try{
			Scanner scan = new Scanner(new File("temp.txt"));
			 while(scan.hasNext()){
			 	String word = scan.next();
			 	treeBST.insert(word);
			 }
			 System.out.println(treeBST.search("A"));

		} catch (FileNotFoundException e){
			System.out.println("error");
			return;
		}
	}
}
