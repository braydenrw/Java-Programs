/**
 * Created by braydenrw on 10/7/14.
 */
import java.io.*;
import java.util.Vector;

public class SplaySymbolTableTest {

    public static void printTree(SplaySymbolTable<?,?> table, String fname) {
        Vector<String> serializedTable = table.serialize();
        TreePrinter treePrinter = new TreePrinter(serializedTable);
        try {
            FileOutputStream out = new FileOutputStream(fname);
            PrintStream ps = new PrintStream(out);
            treePrinter.printSVG(ps);
        } catch (Exception e){
            System.err.println ("Error writing SVG file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SplaySymbolTable<Integer,Integer> table = new SplaySymbolTable<Integer, Integer>();
        int[] keys = {4, 1, 3, 5, 9, 6, 8, 7};//9, 8, 7, 6};
        for (int i = 0; i < keys.length; i++)
            table.insert(keys[i], keys[i]);
        printTree(table, "splay-tree-1.svg");
        table.remove(8);
        printTree(table, "splay-tree-2.svg");
        /*
        table.search(3);
        table.search(9);
        table.search(7);
        printTree(table, "splay-tree-2.svg");
        table.remove(7);
        printTree(table, "splay-tree-3.svg");
        table.remove(1);
        printTree(table, "splay-tree-4.svg");
        table.search(4);
        printTree(table, "splay-tree-5.svg");
        */
    }

}
