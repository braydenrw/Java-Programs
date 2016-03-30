import java.util.ArrayList;

public class MazeGraph implements Graph {
    private int width, height;

    int x[]={1, -1, 0, 0};
    int y[]={0, 0, -1, 1};

    public MazeGraph(int W, int H) {
        width = W; height = H;
    }

    @Override
    public int numVerts() { return width * height; }

    @Override
    public ArrayList<Integer> adjacents(int v) { //returns adjacents arraylist

        int row = v/width;
        int col = v%width;
        ArrayList<Integer> adj = new ArrayList<Integer>();
        for(int i=0;i<4;i++) { //loop through
            int numRow = row+x[i];
            int numCol = col+y[i];

            if(numRow>=0 && numRow<height && numCol>=0 && numCol<width) {
                adj.add(numRow*width+numCol); //add adjacents
            }
        }
        return adj;
    }
}
