package gwv_blatt04;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 3dibbern
 */
public class Start {

    public static final int LINE_COUNT = 10;
    public static final int LINE_LENGTH = 20;

    private Search _search;

    public static char[][] copy2DCharArray(char[][] original) {
        int sizeY = original.length;
        char[][] copy = new char[sizeY][];

        for (int y = 0; y < sizeY; ++y) {
            copy[y] = original[y].clone();
        }
        return copy;
    }

    public static void print2DCharArray(char[][] array) {

    }

    private void initSearch() {
        EnvironmentReader reader = null;
        URL urlreader = getClass().getResource("/resources/blatt3_environment.txt");
        String pfad = urlreader.toString().substring(6);

        try {
            reader = new EnvironmentReader("C:/blatt3_environment.txt", LINE_COUNT, LINE_LENGTH);
        } catch (IOException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        char[][] environment = reader.getEnvironment();

        for (int y = 0; y < 10; ++y) {
            String line = "";
            for (int x = 0; x < 20; ++x) {
                line = line + environment[y][x];
            }

            System.out.println(line);
        }

        int startPosX = reader.getStartPosX();
        int startPosY = reader.getStartPosY();
        int goalPosX = reader.getGoalPosX();
        int goalPosY = reader.getGoalPosY();
        char goalChar = reader.getGoalChar();

        System.out.println("X: " + startPosX + ", Y: " + startPosY);

        Node goalNode = new Node(goalPosX, goalPosY, goalChar);

        _search = new Search(environment, startPosX, startPosY, goalNode);
    }

    /**
     * Sets up the search environment and initiates the search process.
     */
    public final void BreadthFirstSearch(int fastmode) {
        List<Character> goalPath = _search.startBFS();

        System.out.println(goalPath.toString());
        //   ui.Schritte.setText("Auszufuehrende Schritte" + goalPath.toString());
    }

    public final void DepthFirstSearch() {
        List<Character> goalPath = _search.startDFS();

        System.out.println(goalPath.toString());
        // ui.Schritte.setText("Auszufuehrende Schritte" + goalPath.toString());

    }

}
