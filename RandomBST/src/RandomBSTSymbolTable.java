import java.io.*;
import java.util.Random;
import java.util.Vector;

public class RandomBSTSymbolTable<K extends Comparable<K>,V> 
	implements SymbolTable<K,V>{  
    Node root;

    static Random rng = new Random(1234);

    int size(Node tree) { // the full size of the subtree
        return (tree == null) ? 0 : tree.n;
    }

    public RandomBSTSymbolTable() {
        root = null;

    }

    @Override
    public void insert(K key, V val) { //calls InsertRandom
        root = insertRandom(root, key, val);

    }

    @Override
    public V search(K key) { //calls the real search
        if(searchAux(root, key) == null) return null;
        else {return searchAux(root, key).val;}
    }

    @Override
    public V remove(K key) { //removes the desired node and keeps the tree in order
        V remVal = search(key);
        if(root == null || remVal == null) return null; //empty tree or value isn't there
        if(key == root.key) { //removing the root
            root = join(root.left, root.right);
            return remVal;
        }
        Node parent = parent(root, key); //finds the parent of the node to be removed
        Node del;
        if(parent.left.key.compareTo(key) == 0) { //removing to the left of the parent
            del = parent.left;
            parent.left = join(del.left, del.right);
            return remVal;
        }
        if(parent.right.key.compareTo(key) == 0) { //removing to the right of the parent
            del = parent.right;
            parent.right = join(del.left, del.right);
            return remVal;
        }
        return null;
    }

	private Node insertRoot(Node tree, K key, V val) { //Inserts node at the root
		 if (tree == null) //checks for an empty tree
			 return new Node(key, val);
		 int cmp = key.compareTo(tree.key);
		 if (cmp == 0) { //replaces old Node with a new Node so there are no duplicates
			 tree.key = key; 
			 tree.val = val;
		 } else if (cmp < 0) { //traverses left and increases n where needed
			 tree.left = insertRoot(tree.left, key, val);
			 tree = rotateRight(tree);
			 tree.n = 1 + size(tree.left) + size(tree.right);
		 } else { //traverses right and increases n where needed
			 tree.right = insertRoot(tree.right, key, val);
			 tree = rotateLeft(tree);
			 tree.n = 1 + size(tree.left) + size(tree.right) ;
		 }
		 return tree;
	}

	private Node insertRandom(Node tree, K key, V val) { //builds the tree with random values
		if (tree == null) return new Node(key, val);
		if (rng.nextDouble()*(tree.n+1) < 1.0)  return insertRoot(tree, key, val);
		int cmp = key.compareTo(tree.key);
			if (cmp == 0) { //replaces old node with the same node so there's no duplicates
				tree.key = key; 
				tree.val = val; 
			} else if (cmp < 0) { //traverses left and keeps n in line
				tree.left = insertRandom(tree.left, key, val);
				tree.n = 1 + tree.left.n + size(tree.right);
			} else { //traverses right and keeps n in line
				tree.right = insertRandom(tree.right, key, val);
				tree.n = 1 + size(tree.left) + tree.right.n;
			}
		return tree;
	
	}	
	
	private Node searchAux(Node tree, K key) { //the real search function that finds the val
		if (tree == null) return null; //empty tree
		int cmp = key.compareTo(tree.key);
		if (cmp == 0) return tree; //found the Node return the val
		return (cmp < 0) ? searchAux(tree.left, key) : searchAux(tree.right, key); //recursion either left or right
	}

    private Node parent(Node tree, K key) { //finds the parent should the node be in the tree
        if (searchAux(tree, key) == null || tree == null) return null; //empty tree or node not found
        int cmp = key.compareTo(tree.key);
        if (cmp < 0 && tree.left.key.compareTo(key) == 0)  return tree; //found the parent
        if (cmp > 0 && tree.right.key.compareTo(key) == 0)  return tree; //found the parent
        if (cmp < 0) { //traverse left and decrement n since the parent does exist
            tree.n -= 1;
            return parent(tree.left, key);
        } else { //traverse right and decrement n
            tree.n -= 1;
            return parent(tree.right, key);
        }
    }

	private Node rotateRight(Node tree) { //rotates nodes to the right
		Node root = tree.left;
		tree.left = root.right;
		root.right = tree;
		return root;
	}

	private Node rotateLeft(Node tree) { //rotates nodes to the left
		Node root = tree.right;
		tree.right = root.left;
		root.left = tree;
		return root;
	}

	private Node join(Node X, Node Y) { //joins two subtrees into one tree
		if (X == null) return Y; //no subtree in the x direction
		if (Y == null) return X; //no subtree in the y direction
		if (rng.nextDouble()*(X.n + Y.n) < X.n) { // flip a weighted coin
			X.right = join(X.right, Y);
			X.n = 1 + size(X.left) + size(X.right);
			return X;
		} else {
			Y.left = join(X, Y.left);
			Y.n = 1 + size(Y.left) + size(Y.right);
			return Y;
		}
	}

	private void serializeAux(Node tree, Vector<String> vec) {
		if (tree == null)
			vec.addElement(null);
		else {
			vec.addElement(tree.key.toString() + ":black");
			serializeAux(tree.left, vec);
			serializeAux(tree.right, vec);
		}
	}
	
	public Vector<String> serialize() {
		Vector<String> vec = new Vector<String>();
	    serializeAux(root, vec);
	    return vec;
	}

	public static void main(String args[]) {

		RandomBSTSymbolTable<Integer,Integer> symtab = new RandomBSTSymbolTable<Integer,Integer>();
		
		//Random RNG = new Random(1234);
		for (int i = 0; i < 100; i++) {
			//int r = (int) (rng.nextDouble()*100);
			symtab.insert(i, i);
		}

        symtab.remove(38);
		symtab.remove(45);
		symtab.remove(22);
		symtab.remove(5);
		symtab.remove(70);
        symtab.remove(12);

//		int keys[] = {6, 1, 9, 3, 4, 5, 1, 10, 12, 0, 8, 2};
//		for (int i = 0; i < keys.length; i++)
//			symtab.insert(keys[i], keys[i]);
		
//		Integer val = symtab.search(5);
//		if (val == null)
//			System.out.println("not found!");
//		else
//			System.out.println("found!");
		
		Vector<String> st = symtab.serialize();
		TreePrinter treePrinter = new TreePrinter(st);
		try {
			FileOutputStream out = new FileOutputStream("tree.svg");
			PrintStream ps = new PrintStream(out);
			treePrinter.printSVG(ps);
		} catch (FileNotFoundException e) {}
		
		
	}

    class Node { //node class
        K key; V val;
        Node left, right;
        int n;

        public Node(K key, V val) { //node constructor
            this.key = key;
            this.val = val;
            this.left = null;
            this.right = null;
            n = 1;
        }
    }

}
