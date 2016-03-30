import java.util.Vector;

class SplaySymbolTableUnitTest {

    private static class Node {
        public String key;
        public Node left, right;
        public Node(String k) {key = k; left = right = null;}
    }

    private static class Scanner {
        private int next;
        private Vector<String> vec;
        public Scanner(Vector<String> serializedTree) {
            next = 0;
            vec = serializedTree;
        }
        public boolean hasNext() {return next < vec.size();}
        public String next() {return vec.elementAt(next++);}
    }

    private static Node buildTree(Scanner scanner) {
        if (scanner.hasNext()) {
            String str = scanner.next();
            if (str == null)
                return null;
            String[] a = str.split(":"); // a[1] is color if it exists
            Node n = new Node(a[0]);
            n.left = buildTree(scanner);
            n.right = buildTree(scanner);
            return n;
        }
        return null;
    }

    private static Node reconstructTree(SplaySymbolTable<?,?> symtab) {
        Vector<String> serializedTree = symtab.serialize();
        Scanner scanner = new Scanner(serializedTree);
        Node tree = buildTree(scanner);
        return tree;
    }

    private static boolean isBST(Node tree, String min, String max) {
        if (tree == null) return true;
        if (min != null && min.compareTo(tree.key) > 0)
            return false;
        if (max != null && max.compareTo(tree.key) < 0)
            return false;
        return isBST(tree.left, min, tree.key) &&
                isBST(tree.right, tree.key, max);
    }

    private static boolean isBST(Node tree) {
        return isBST(tree, null, null);
    }

    private static int treeSize(Node tree) {
        return tree == null ? 0 : 1 + treeSize(tree.left) + treeSize(tree.right);
    }

    private static int treeHeight(Node tree) {
        return tree == null ?
                -1 : 1 + Math.max(treeHeight(tree.left), treeHeight(tree.right));
    }


    public int runCount = 0;
    public int failCount = 0;

    private void assertEquals(String test, String string, int n, int i) {
        runCount++;
        if (n != i) {
            failCount++;
            System.err.println(test + " : " + string + "(" + n + " != " + i + ")");
        }
    }

    private void assertTrue(String test, String string, boolean bst) {
        runCount++;
        if (!bst) {
            failCount++;
            System.err.println(test + " : " + string);
        }
    }

    private void fail(String message) {
        System.err.println("FAIL!!!!!");
    }

    public void testSevenStrings() {
        final String strings[] = {
                "now", "is", "the", "winter", "of", "our", "discontent"
        };
        SplaySymbolTable<String,String> symtab = new SplaySymbolTable<String,String>();
        for (int i = 0; i < strings.length; i++) {
            symtab.insert(strings[i], strings[i]);
            Node t = reconstructTree(symtab);
            assertTrue("testSevenStrings",
                    "Last inserted key " + strings[i] + " must be at root!",
                    t != null && t.key.equals(strings[i]));
        }
        Node tree = reconstructTree(symtab);
        assertTrue("testSevenStrings", "tree must be BST", isBST(tree));
        int N = treeSize(tree);
        assertEquals("testSevenStrings", "tree must have 7 nodes", N, 7);
        for (int i = 0; i < strings.length; i++) {
            String s = symtab.search(strings[i]);
            assertTrue("testSevenStrings", "Should find key " + strings[i] + "in tree!",
                    s != null && s.equals(strings[i]));
            Node t = reconstructTree(symtab);
            assertTrue("testSevenStrings",
                    "Last searched for key " + strings[i] + " must be at root!",
                    t != null && t.key.equals(strings[i]));
        }
    }

    public void test32StringsWithDuplicateKeys() {
        final String strings[] = {
                "now", "is", "the", "winter", "of", "our", "discontent",
                "made", "glorious", "summer", "by", "this", "sun", "of", "york",
                "and", "all", "the", "clouds", "that", "lourd", "upon", "our",
                "house", "in", "the", "deep", "bosom", "of", "the", "ocean", "buried"
        };
        SplaySymbolTable<String,String> symtab = new SplaySymbolTable<String,String>();
        for (int i = 0; i < strings.length; i++) {
            symtab.insert(strings[i], strings[i]);
            Node t = reconstructTree(symtab);
            assertTrue("test32StringsWithDuplicateKeys",
                    "Last inserted key " + strings[i] + " must be at root!",
                    t != null && t.key.equals(strings[i]));
        }
        Node tree = reconstructTree(symtab);
        assertTrue("test32StringsWithDuplicateKeys",
                "tree must be BST", isBST(tree));
        int N = treeSize(tree);
        assertEquals("test32StringsWithDuplicateKeys",
                "tree must have 26 unique nodes", N, 26);
        for (int i = 0; i < strings.length; i++) {
            String s = symtab.search(strings[i]);
            assertTrue("test32StringsWithDuplicateKeys",
                    "Should find key " + strings[i] + "in tree!",
                    s != null && s.equals(strings[i]));
            Node t = reconstructTree(symtab);
            assertTrue("test32StringsWithDuplicateKeys",
                    "Last searched for key " + strings[i] + " must be at root!",
                    t != null && t.key.equals(strings[i]));
        }
    }

    public void testBalance() {
        SplaySymbolTable<Integer,Integer> symtab = new SplaySymbolTable<Integer,Integer>();
        final int numKeys = 1001;
        for (int i = 0; i < numKeys; i++)
            symtab.insert(i,i);
        Node tree = reconstructTree(symtab);
        int H = treeHeight(tree);
        assertTrue("test32StringsWithDuplicateKeys",
                "height of tree with 1001 keys inserted in order should be 1000", H == 1000);
        int n = symtab.search(500);
        assertTrue("test32StringsWithDuplicateKeys",
                "Search for 500 should succeed", n == 500);
        tree = reconstructTree(symtab);
        H = treeHeight(tree);
        assertTrue("test32StringsWithDuplicateKeys",
                "height of tree with after searching for 500 should be 500", H == 500);
    }

    public static void main(String[] args) {
        SplaySymbolTableUnitTest test = new SplaySymbolTableUnitTest();

        test.testSevenStrings();
        test.test32StringsWithDuplicateKeys();
        test.testBalance();

        int runs = test.runCount;;
        int fails = test.failCount;
        int successes = runs - fails;
        System.out.println(successes + "/" + runs + " succeeded.");
    }


}