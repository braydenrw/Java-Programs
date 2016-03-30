import java.util.Vector;
import java.io.*;

/**
 * Created by braydenrw on 10/3/14.
 */

public class SplaySymbolTable<K extends Comparable<K>, V> implements SplayTree<K,V> {

    private Node root;
    private Node savNode; //save a node for the parent pointer
    private Node X; //current node
    public int rotations;
    public int comparisons;

    public SplaySymbolTable() { //constructor
        root = null;
    }

    @Override
    public void insert(K key, V val) { //inserts and splays
        root = insertAux(root, key, val); //comment out if using the iterative

        //insertAux2(key, val); //comment out if you want recursion

        //splay(X);
    }

    private Node insertAux(Node tree, K key, V val) {
        if(root == null) { //empty tree
            X = new Node(key, val);
            return X;
        }
        if(tree == null) { //base case, end of the tree, new leaf
            tree = new Node(key, val);
            X = tree;
            tree.parent = savNode;
            return tree;
        }
        int cmp = key.compareTo(tree.key);
        if(cmp == 0) { //replace existing node, no duplicates
            comparisons += 1;
            tree.key = key;
            tree.val = val;
            X = tree;
        } else if(cmp < 0) { //traverse left
            savNode = tree;
            comparisons += 1;
            tree.left = insertAux(tree.left, key, val);
        } else { //traverse right
            savNode = tree;
            comparisons += 1;
            tree.right = insertAux(tree.right, key, val);
        }
        return tree;
    }

    private void insertAux2(K key, V val) { //Iterative insert if recursion isn't your thing
        if(root == null) { //empty tree
            X = new Node(key, val);
            root = X;
            return;
        }
        Node temp = root;
        while(true) {
            int cmp = key.compareTo(temp.key);
            if (cmp < 0 && temp.left != null) { //go left
                comparisons += 1;
                temp = temp.left;
            } else if (cmp < 0 && temp.left == null) { //it goes in the next left
                comparisons += 1;
                X = new Node(key, val);
                temp.left = X;
                X.parent = temp;
                break;
            }
            if (cmp > 0 && temp.right != null) { //go right
                comparisons += 1;
                temp = temp.right;
            } else if (cmp > 0 && temp.right == null) { //it goes in the next right
                comparisons += 1;
                X = new Node(key, val);
                temp.right = X;
                X.parent = temp;
                break;
            }
            if(cmp == 0) { //no doubles, overlap pre-existing node
                comparisons += 1;
                temp.key = key;
                temp.val = val;
                X = temp;
                break;
            }
        }
    }

    private void splay(Node tree) { //rotates a node up to the root
        assert(tree != null);
        while(tree.parent != null && tree.parent.parent != null) { //while has a parent and a grandparent
            if(tree.parent.left == tree && tree.parent.parent.left == tree.parent) { //zig-zig case
                rotateRight(tree.parent.parent);
                rotateRight(tree.parent);
            } else if(tree.parent.right == tree && tree.parent.parent.left == tree.parent) { //zig-zag case
                rotateLeft(tree.parent);
                rotateRight(tree.parent);
            } else if(tree.parent.right == tree && tree.parent.parent.right == tree.parent) { //zag-zag case
                rotateLeft(tree.parent.parent);
                rotateLeft(tree.parent);
            } else if(tree.parent.left == tree && tree.parent.parent.right == tree.parent) { //zag-zig case
                rotateRight(tree.parent);
                rotateLeft(tree.parent);
            }
        }
        if(tree != root) { //tree is only of depth 1
            if (root.left == tree) { //zig
                rotateRight(root);
            } else { //zag
                rotateLeft(root);
            }
        }
    }

    @Override
    public V search(K key) {
        Node srch = searchAux(root, key); //use for recursive method

        //Node srch = searchAux2(key); //use for iterative method

        if(srch != null) { splay(srch); }
        if(srch == null || srch.key.compareTo(key) != 0) { return null; }
        else { return srch.val; }
    }

    private Node searchAux(Node tree, K key) { //the real search function that finds the Node of the val
        if(root == null) { return null; } //empty tree
        int cmp = key.compareTo(tree.key);
        if(cmp == 0 || cmp < 0 && tree.left == null || cmp > 0 && tree.right == null) { //found the node or last checked
            comparisons += 1;
            return tree;
        }
        if(cmp < 0) {
            comparisons += 1;
            return searchAux(tree.left, key);
        } else {
            comparisons += 1;
            return searchAux(tree.right, key);
        }
    }

    private Node searchAux2(K key) { //iterative search
        if(root == null) { return null; } //empty tree
        Node temp = root;
        while(true) {
            int cmp = key.compareTo(temp.key);;
            if (cmp < 0 && temp.left != null) {
                comparisons += 1;
                temp = temp.left;
            } else if (cmp < 0 && temp.left == null) {
                comparisons += 1;
                return temp;
            }
            if (cmp > 0 && temp.right != null) {
                comparisons += 1;
                temp = temp.right;
            } else if (cmp > 0 && temp.right == null) {
                comparisons += 1;
                return temp;
            }
            if (cmp == 0) {
                comparisons += 1;
                return temp;
            }
        }
    }

    private void rotateRight(Node tree) { //rotates to the right
        assert tree != null;
        assert tree.left != null;

        Node temp = tree.left;
        tree.left = temp.right;
        if(temp.right != null) { //no null pointers
            temp.right.parent = tree;
        }
        temp.parent = tree.parent;
        if(temp.parent != null) { //am i at the root?
            if(temp.parent.left == tree) {
                temp.parent.left = temp;
            } else if (temp.parent.right == tree) {
                temp.parent.right = temp;
            }
        }
        tree.parent = temp;
        temp.right = tree;
        if(tree == root) { root = temp; } //rewrite the root
        rotations += 1;
    }

    private void rotateLeft(Node tree) { //rotate left
        assert tree != null;
        assert tree.right != null;

        Node temp = tree.right;
        tree.right = temp.left;
        if(temp.left != null) { //no null pointers
            temp.left.parent = tree;
        }
        temp.parent = tree.parent;
        if(temp.parent != null) { //am i at the root?
            if(temp.parent.left == tree) {
                temp.parent.left = temp;
            } else if (temp.parent.right == tree) {
                temp.parent.right = temp;
            }
        }
        tree.parent = temp;
        temp.left = tree;
        if(tree == root) { root = temp; } //re assign the root
        rotations += 1;
    }

    @Override
    public V remove(K key) {
        Node rem = removeAux(key);
        if(rem == null) { return null; }
        else { return rem.val; }
    }

    private Node removeAux(K key) {
        if(root == null) { return null; }
        Node rem = searchAux(root, key); //use for recursive search function

        //Node rem = searchAux2(key); //use for iterative search function

        splay(rem);
        Node rootRight = root.right;
        Node rootLeft = root.left;
        if(rootRight == null && rootLeft == null) {
            root = null;
            return rem;
        } else if(rootRight == null) {
            root = rootLeft;
            root.parent = null;
            return rem;
        } else {
            Node temp = rootRight;
            while(temp.left != null) {
                temp = temp.left;
            }
            root = temp;
            temp.parent.left = temp.right;
            if(temp.right != null) {
                temp.right.parent = temp.parent;
            }
            root.left = rootLeft;
            root.right = rootRight;
            if(root.left != null) {
                root.left.parent = root;
            }
            if(root.right != null) {
                root.right.parent = root;
            }
            root.parent = null;
            return rem;
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

    void printTree(String fname) {
        Vector<String> st = serialize();
        TreePrinter treePrinter = new TreePrinter(st);
        treePrinter.fontSize = 14;
        treePrinter.nodeRadius = 14;
        try {
            FileOutputStream out = new FileOutputStream(fname);
            PrintStream ps = new PrintStream(out);
            treePrinter.printSVG(ps);
        } catch (FileNotFoundException e) {}
    }

    public static void main(String[] args) {
        SplaySymbolTable<Integer,Integer> symtab = new SplaySymbolTable<Integer, Integer>();
        for (int i = 0; i < 10; i++)
            symtab.insert(i,i);
        symtab.printTree("splay-insert-10.svg");

        Integer I = symtab.search(0);
        System.out.println("searched/found " + I);
        symtab.printTree("splay-search-0.svg");

        I = symtab.remove(7);
        System.out.println("removed/found " + I);
        symtab.printTree("splay-remove-7.svg");

        I = symtab.remove(1);
        System.out.println("removed/found " + I);
        symtab.printTree("splay-remove-1.svg");
    }

    public class Node {
        public K key;
        public V val;
        public Node left, right;
        public Node parent;
        public Node(K key, V val) {
            this.key = key;
            this.val = val;
            left = null;
            right = null;
            parent = null;
        }
    }
}