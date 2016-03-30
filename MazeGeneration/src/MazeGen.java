import java.io.*;
import java.util.*;

public class MazeGen {

    final static int EAST = 1; // 0001
    final static int NORTH = 2; // 0010
    final static int WEST = 4; // 0100
    final static int SOUTH = 8; // 1000

    public static int[] mazeDFS(Graph g, int src) { //Depth first search
        Random rand = new Random(); //first random seed
        int v = g.numVerts(); //verts
        boolean visited[] = new boolean[v]; //arraylist if visted
        int parent[] = new int[v]; //parents

        visited[src] = true;
        parent[src] = -1;
        int visitCount = 1;

        Stack<Integer> stk = new Stack<Integer>(); //stack
        int s = src;

        while(visitCount < v) { //while we haven't visted every vert
            ArrayList<Integer> adjacents = g.adjacents(s); //
            ArrayList<Integer> unvisited = new ArrayList<Integer>();

            for (Integer n : adjacents) { //add unvisted items in adjacents
                if(!visited[n]) {
                    unvisited.add(n);
                }
            }

            if(unvisited.size() > 0) { //unvisted arraylist is greater than zero
                int random_adj = unvisited.get(rand.nextInt(unvisited.size()));
                visited[random_adj] = true;
                visitCount++;
                parent[random_adj] = s;
                stk.push(random_adj);
                s = random_adj;
            } else { //pop the stack
                s = stk.pop();
            }

        }
        return parent;
    }

    public static void main(String args[]) throws FileNotFoundException {
        Random rand2 = new Random(); //second random seed
        int width, height;
        String fname;
        if (args.length != 3) { //if args not given
            width = 16;
            height = 16;
            fname = "maze16x16.txt";
        } else {
            width = Integer.parseInt(args[0]);
            height = Integer.parseInt(args[1]);
            if (width < 5 || height < 5) {
                System.err.println("bogus size!");
                return;
            }
            fname = args[2];
        }

        Graph g = new MazeGraph(width,height);

        int start = rand2.nextInt(g.numVerts()); //random start point
        int parent[] = mazeDFS(g, start); //parent of start point

        int cells[][] = new int[height][width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                cells[r][c] = 0xF; // bit code = 1111
            }
        }

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                int v = r * width + c;
                int w = parent[v];

                if (w >= 0) {
                    int r0 = w / width;
                    int c0 = w % width;
                    if (c0 == c + 1) {
                        cells[r][c] &= ~EAST; // NOT EAST = 1110
                        cells[r0][c0] &= ~WEST; // NOT WEST = 1101
                    }
                    if (c0 == c - 1) {
                        cells[r][c] &= ~WEST; //NOT WEST = 1011
                        cells[r0][c0] &= ~EAST; //NOT EAST = 1110
                    }
                    if (r0 == r + 1) {
                        cells[r][c] &= ~SOUTH; //NOT SOUTH = 0111
                        cells[r0][c0] &= ~NORTH; //NOT NORTH = 1101
                    }

                    if (r0 == r - 1) {
                        cells[r][c] &= ~NORTH; //NOT NORTH = 1101
                        cells[r0][c0] &= ~SOUTH; //NOT SOUTH = 0111
                    }

                }
            }
        }

        int startRow = rand2.nextInt(height);
        int exitRow = rand2.nextInt(height);

        cells[startRow][0] &= ~WEST; //remove wall at the start
        cells[exitRow][width-1] &= ~EAST; //remove wall at the end

        try {
            PrintStream ps = new PrintStream(fname);
            ps.println(width + " " + height);
            for (int r = 0; r < height; r++) {
                for (int c = 0; c < width; c++) {
                    ps.print(cells[r][c] + " ");
                }
                ps.println();
            }
            ps.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
