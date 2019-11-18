SPELLCHECKER IMPLEMENTATION
By Briant Shen
Completed 11/17/2019, 6PM
---------------------------------------------------
Files
Trees.java
	Parent class of BSTSpellchecker and Trie

Trie.java
   contains Node based Trie implementation of search and insert
	
BSTSpellchecker.java
	contains Node based BST implementation of search and insert

a1properties.txt
	controls which search tree CS245A1 uses.

CS245A1.java
	Takes Trie and BSTSpellchecker as well as a1properties.txt. Reads english.0 from raw URL to parse it into Trie and BSTSpellchecker (named treeTrie and tree BST respectivly)
	Creates a hash map of levenshtein per each word using a 2d array, finding the 3 best suggestions from dictionary made from english.0 and a data tree.

Runtimes
- BST: 216.0 ms
- Trie: 1914 ms

Oddly enough, BSt has better run times than Trie. This could be because the way the data is stored in each node allows for the algorithm to return words slightly faster than trie functions, who needs to run through branches of the tree to return data.