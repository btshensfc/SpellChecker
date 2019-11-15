/* This spell checker will use the trie data structure 
** to check spelling of words in an external data file.
** It will print out the correct words in another file.
*/

import java.util.*;

class TNode{
	TNode[] arr; //arr refers to all letters of alphabet + apostrophe (at 27)
	boolean isEnd; //checks if the node is at the end

	TNode(){
		this.arr = new TNode[27];
	}
}

public class Trie{
	private TNode root;

	public Trie(){
		root = new TNode();
	}

	private int valASCI(char w){ //converts char into correct index on array
		int num;
		if(w == 39){
			num = 26;
		} else {
			num = w - 'a';
		}
		return num;
	}

    public void insert(String word) {
        TNode p = root;
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
            int index = valASCI(c);
            if(p.arr[index] == null){ //nullpointer error here
                TNode temp = new TNode();
                p.arr[index] = temp;
                p = temp;
            }else{
                p=p.arr[index];
            }
        }
        p.isEnd=true;
    }
	public boolean search(String word){
		String searching = word.toLowerCase();
		TNode looking = root;
		for (int i = 0; i < word.length(); i++){
			int index = valASCI(searching.charAt(i));
			if(looking.arr[index] == null){
				return false;
			} else{
				looking = looking.arr[index];
			}
		}
		return true;
	}
} 
