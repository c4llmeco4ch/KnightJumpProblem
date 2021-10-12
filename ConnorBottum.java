import java.util.HashSet;
import java.util.Scanner;
import java.io.File;

public class ConnorBottum{

    final int MAX_CONSTRAINED = 2;
    final char[] CONSTRAINED_CHARS = {'y', 'e', 'i', 'o', 'u', 'y'};

    public static void main(String[] args){

    }

    /**
     * 
     * @return
     */
    public static long SolveMatrix(){
        char[][] grid = parseFile("test.txt");
        HashSet<String> paths = new HashSet<String>();
        
    }

    /**
     * Returns a 2-dimensional grid representing
     * the board state a Knight will jumping on inside
     * the SolveMatrix() method
     * <p>
     * The file will contain rows of chars representing the
     * characters on the board. Empty spaces will either be
     * represented with a ' ' (space) or a '-' (hyphen). An 
     * example file might look like
     * 
     * @param path the file path for the input file
     * @return     the 2D "chess board"
     */
    private static char[][] parseFile(String path){
        Scanner inputFile = new Scanner(new File(path));

    }
}