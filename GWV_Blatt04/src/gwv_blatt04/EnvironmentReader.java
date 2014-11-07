package gwv_blatt04;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * An EnvironmentReader can read an environment for a search from a text file.
 * It converts the environment to a char[][] so a search algorithm can be
 * applied to it. It also detects the start point for a search that must be
 * spefied in the input file
 *
 * @author 3dibbern
 */
public class EnvironmentReader
{

    private final String _location;
    private final int _lineCount;
    private final int _lineLength;
    private final char START_CHAR = 's';
    private final char GOAL_CHAR = 'g';

    private char[][] _environment;
    private int[] _startPos;
    private int[] _goalPos;

    /**
     * Creates an Environment reader that reads the specified file. The text
     * file must contain exactly one 's'. This denotes the start point for a
     * search. 
     *
     * @param location The path of the the text file to be read
     * @param lineCount The number of lines that are in the text file or that
     * should be read
     * @param lineLength The length of a line (all lines need to have the same
     * length)
     * @throws IOException If the path is not valid or not a text file, an
     * IOException is thrown
     */
    public EnvironmentReader(String location, int lineCount, int lineLength) throws IOException
    {
        _location = location;
        _lineCount = lineCount;
        _lineLength = lineLength;

        readEnvironment();
    }

    private void readEnvironment() throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(_location));

        char[][] environment = new char[_lineCount][_lineLength];

        for (int currentLine = 0; currentLine < _lineCount; ++currentLine)
        {
            String line = reader.readLine();
            for (int linePos = 0; linePos < line.length(); ++linePos)
            {
                char currentChar = line.charAt(linePos);
                if (currentChar == START_CHAR)
                {
                    _startPos = new int[2];
                    _startPos[0] = currentLine;
                    _startPos[1] = linePos;
                }
                if (currentChar == GOAL_CHAR) {
                    _goalPos = new int[2];
                	_goalPos [0] = currentLine;
                	_goalPos [1] = linePos;
            	}
                
                environment[currentLine][linePos] = currentChar;
            }
        }

        reader.close();
        _environment = environment;
    }

    /**
     * Returns the environment that was read during instanciation
     * @return the environment as a char[][]
     */
    public char[][] getEnvironment()
    {
        return _environment;
    }

    /**
     * Returns the start point of the environment that was read during instanciation
     * @return the start postions as an array. [startPosX, startPosY]
     */
    public int[] getStartPos()
    {
        return _startPos;
    }

    /**
     * Returns the start point of the environment that was read during instanciation
     * @return the X-Coordinate of the start point
     */
    public int getStartPosX()
    {
        return _startPos[1];
    }

    /**
     * Returns the start point of the environment that was read during instanciation
     * @return the Y-Coordinate of the start point
     */
    public int getStartPosY()
    {
        return _startPos[0];
    }
    
    /**
     * Returns the goal point of the environment that was read during instanciation
     * @return the X-Coordinate of the goal point
     */
    public int getGoalPosX()
    {
        return _goalPos[1];
    }

    /**
     * Returns the goal point of the environment that was read during instanciation
     * @return the Y-Coordinate of the goal point
     */
    public int getGoalPosY()
    {
        return _goalPos[0];
    }
    
    public char getGoalChar()
    {
        return GOAL_CHAR;
    }
}
