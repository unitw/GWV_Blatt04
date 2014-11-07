package gwv_blatt04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Path {

    ArrayList<Node> nodelist = new ArrayList<Node>();

    public Path(Node s) {
        nodelist.add(s);
    }

    public Path() {

    }

    public void addNode(Node node) {
        nodelist.add(node);
    }

    public Node getLastNode() {
        int lastIndex = nodelist.size() - 1;
        if (lastIndex >= 0) {
            return nodelist.get(lastIndex);
        } else {
            return null;
        }
    }

    public List<Character> getCharPath() {
        List<Character> charList = new ArrayList<Character>();
        for (Node node : nodelist) {
            charList.add(node.getDirection());
        }

        return charList;
    }

    /**
     * Returns a new path that gets extended by the specified node
     * @param newNode the node to extend the path
     * @return extended path
     */
    public Path expandPath(Node newNode) {
            Path newPath = new Path();
            newPath.nodelist.addAll(this.nodelist);
            
            newPath.addNode(newNode);
            return newPath;    
    }
}
