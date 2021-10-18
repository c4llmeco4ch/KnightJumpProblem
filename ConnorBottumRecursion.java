import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class ConnorBottumRecursion{

    final static int MAX_CONSTRAINED = 2;
    final static int MAX_PATH_LENGTH = 8;
    final static char[] CONSTRAINED_CHARS = {'a', 'e', 'i', 'o', 'u', 'y'};
    final static char[] EMPTY_CHARS = {'-', ' '};
    static HashMap<Character, Integer[]> positions;
    static HashSet<String> finalPaths;

    public static void main(String[] args){
        long pathCount = SolveMatrix();
        System.out.printf("There are %d total paths\n", pathCount);
    }

    /**
     * An implementation to solve the Jumping Knight problem. 
     * Determines the total number of unique 8-cell paths
     * for the Knight from all starting locations.
     * 
     * @return the total number of viable paths a Knight can take
     */
    public static long SolveMatrix(){
        char[][] grid = parseFile("test.txt");
        // A knight requires at least a 2x2 grid to move
        if(grid.length < 2 || grid[0].length < 2){
            return 0;
        }
        finalPaths = new HashSet<String>();
        initDive(grid);
        return finalPaths.size();
    }

    /**
     * Initializes the depth-first search by beginning the recursive
     * calls, starting with the length-1 strings for each individual square
     * 
     * @param board The 2D grid representing the board state
     */
    private static void initDive(char[][] board){
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[0].length; col++){
                char val = board[row][col];
                if(isEmpty(val)){
                    continue;
                }
                calcPaths(board, ""+val, isConstrained(val) ? 1 : 0);
            }
        }
    }

    /**
     * For a given path, calculate all the possible branches and recursively
     * Dive down to the next layer
     * 
     * @param grid the board state
     * @param currPath the current path we are considering
     * @param currCon the number of constrained characters in the path
     */
    private static void calcPaths(char[][] grid, String currPath, int currCon){
        int l = currPath.length();
        ArrayList<Character> moves = calcJumps(currPath.charAt(l - 1), grid);
        for(char c : moves){
            if(isConstrained(c) && currCon >= MAX_CONSTRAINED){
                continue;
            }
            String possibleMove = currPath + c;
            if(l == MAX_PATH_LENGTH - 1){
                finalPaths.add(possibleMove);
            }
            else{
                calcPaths(grid, possibleMove, currCon + (isConstrained(c) ? 1 : 0));
            }
        }
    }

    /**
     * For the Knight on a given position, evaluate the 8 surrounding
     * positions and determine which are valid jumps, elimiating any
     * out-of-bounds jumps or jumps to empty squares
     * 
     * @param curr the character on which the Knight sits
     * @param board the 2D grid representing the board state
     * @return the list of valid jumps the Knight can make on its next move
     */
    private static ArrayList<Character> calcJumps(char curr, char[][] board){
        Integer pos[] = positions.get(curr);
        int y = pos[0];
        int x = pos[1];
        ArrayList<Character> moves = new ArrayList<Character>();
        int left = (x < 1 ? 0 : x < 2 ? 1 : 2);
        int right = (x >= board[0].length - 1 ? 0 : x >= board[0].length - 2 ? 1 : 2);
        int top = (y < 1 ? 0 : y < 2 ? 1 : 2);
        int bottom = (y >= board.length - 1 ? 0 : y >= board.length - 2 ? 1 : 2);
        if(left >= 1){
            if(top >= 2 && !isEmpty(board[y - 2][x - 1])){
                moves.add(board[y - 2][x - 1]);
            }
            if(bottom >= 2 && !isEmpty(board[y + 2][x - 1])){
                moves.add(board[y + 2][x - 1]);
            }
        }
        if(left >= 2){
            if(top >= 1 && !isEmpty(board[y - 1][x - 2])){
                moves.add(board[y - 1][x - 2]);
            }
            if(bottom >= 1 && !isEmpty(board[y + 1][x - 2])){
                moves.add(board[y + 1][x - 2]);
            }
        }
        if(right >= 1){
            if(top >= 2 && !isEmpty(board[y - 2][x + 1])){
                moves.add(board[y - 2][x + 1]);
            }
            if(bottom >= 2 && !isEmpty(board[y + 2][x + 1])){
                moves.add(board[y + 2][x + 1]);
            }
        }
        if(right >= 2){
            if(top >= 1 && !isEmpty(board[y - 1][x + 2])){
                moves.add(board[y - 1][x + 2]);
            }
            if(bottom >= 1 && !isEmpty(board[y + 1][x + 2])){
                moves.add(board[y + 1][x + 2]);
            }
        }
        return moves;
    }

    /**
     * Determines if a given character qualifies as a representative
     * of an empty square
     * 
     * @param c the character to be evaluated
     * @return whether the square <i>c</i> rests on is empty or not
     */
    private static boolean isEmpty(char c){
        for(char em : EMPTY_CHARS){
            if(c == em){return true;}
        }
        return false;
    }

    /**
     * Determines if a given character qualifies as a 
     * constrained character
     * 
     * @param c the character on the board being evaluated
     * @return whether the letter is a limited-use character or not
     */
    private static boolean isConstrained(char c){
        for(char con: CONSTRAINED_CHARS){
            if(c == con){return true;}
        }
        return false;
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
        positions = new HashMap<Character, Integer[]>();
        char[][] board = new char[rows][cols];
        for(int r = 0; r < rows; r++){
            String line = inputFile.nextLine();
            if(line.length() != cols){
                System.out.printf("Line %d has an incorrect length of %d characters.", rows, cols);
            }
            for(int c = 0; c < cols; c++){
                board[r][c] = line.charAt(c);
                Integer[] pos = {r, c};
                positions.put(Character.valueOf(board[r][c]), pos);
            }
        }
        return board;
    }
}