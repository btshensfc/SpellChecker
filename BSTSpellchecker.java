public class BSTSpellchecker implements Trees{
	private class Node{ //store data, pointers left right
		protected String data;
		protected Node left;
		protected Node right;

		public Node (String word){
			data = word;
			left = null;
			right = null;
		}

		public String returnData(){ //allows access to Node data
			return data;
		}

	}

	private Node root = null;


	public void insert(String word){
		root = insert(root, word); 
	}
	private Node insert(Node root, String word){
		if (root == null){
			root = new Node(word); //insert word into root.data if root empty
		}
		if (word.compareTo(root.data) < 0){ //checks node to the left, inserts roots recursivly.
			root.left = insert(root.left, word);
		} else if (word.compareTo(root.data) > 0){
			root.right = insert(root.right, word);
		}
		return root;
	}

	public boolean search(String word){
		return find(root, word);
	}
    private boolean find(Node root, String word){
    	if (root == null){ //if root.data empty, return false
    		return false;
    	}
    	if (word.compareTo(root.returnData())==0){
    		return true; //if root.data equalsto word, return true
    	}
    	if (word.compareTo(root.returnData())<0){
    		return find(root.left, word);
    	} else {
   			return find(root.right, word);
	   	}
    }


}