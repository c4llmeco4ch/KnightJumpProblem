import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class ConnorBottum{

    final static int MAX_CONSTRAINED = 2;
    final static char[] CONSTRAINED_CHARS = {'a', 'e', 'i', 'o', 'u', 'y'}; // sorted

    public static void main(String[] args){

    }

    /**
     * An implementation to solve the Jumping Knight problem. 
     * Determines the total number of unique 8-cell paths
     * for the Knight from all starting locations.
     * 
     * @return
     */
    public static long SolveMatrix(){
        char[][] grid = parseFile("test.txt");
        // A knight requires at least a 2x2 grid to move
        if(grid.length < 2 || grid[0].length < 2){
            return 0;
        }
        HashSet<String> finalPaths = new HashSet<String>();
        ArrayList<String> pathQueue = initQueue(grid);
        
        /*
        * 
        */
        while(!pathQueue.isEmpty()){
            String current = pathQueue.remove(0);
            int l = current.length();
            char[] moves = calcJumps(current.charAt(l - 1), grid);
            for(char c : moves){
                String possibleMove = current + c;
                if(isValid(possibleMove)){
                    if(l == 7){
                        finalPaths.add(possibleMove);
                    }
                    else{
                        pathQueue.add(possibleMove);
                    }
                }
            }
        }
        return finalPaths.size();
    }

    /**
     * 
     * @param board
     * @return
     */
    private static ArrayList<String> initQueue(char[][] board){
        ArrayList<String> startQueue = new ArrayList<String>();
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[0].length; col++){
                if(board[row][col] == '-' || board[row][col] == ' '){
                    continue;
                }
                startQueue.add(String.valueOf(board[row][col]));
            }
        }
        return startQueue;
    }

    /**
     * 
     * @param curr
     * @param board
     * @return
     */
    private static char[] calcJumps(char curr, char[][] board){
        
    }


    /**
     * 
     * @param s
     * @return
     */
    private static boolean isValid(String s){
        int totalConstrained = 0;
        for(int pos = 0; pos < s.length(); pos++){
            char c = s.charAt(pos);
            if(Arrays.binarySearch(CONSTRAINED_CHARS, c) >= 0){
                ++totalConstrained;
                if(totalConstrained > MAX_CONSTRAINED){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns a 2-dimensional grid representing
     * the board state a Knight will jumping on inside
     * the SolveMatrix() method
     * <p>
     * The file will contain a row for the grid dimensions
     * (in the format '{numCols}x{numRows}'),
     * then rows of chars representing the
     * characters on the board. Empty spaces will either be
     * represented with a ' ' (space) or a '-' (hyphen). An 
     * example file might look like:
     * <blockquote>
     * 5x5
     * abc-e
     * -ghij
     * klmno
     * pqrst
     * uv--y
     * </blockquote>
     * 
     * @param path the file path for the input file
     * @return     the 2D "chess board"
     */
    private static char[][] parseFile(String path){
        Scanner inputFile = null;
        try{
            inputFile = new Scanner(new File(path));
        }
        catch(FileNotFoundException e){
            System.out.println("No file found at " + path);
            System.exit(1);
        }
        String[] dimensions = inputFile.nextLine().split("x");
        int rows = Integer.parseInt(dimensions[1]);
        int cols = Integer.parseInt(dimensions[0]);
        char[][] board = new char[rows][cols];
        for(int r = 0; r < rows; r++){
            String line = inputFile.nextLine();
            if(line.length() != cols){
                System.out.printf("Line %d has an incorrect length of %d characters.", rows, cols);
            }
            for(int c = 0; c < cols; c++){
                board[r][c] = line.charAt(c);
            }
        }
        return board;
    }
}