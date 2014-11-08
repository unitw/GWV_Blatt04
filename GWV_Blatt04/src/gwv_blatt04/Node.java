package gwv_blatt04;

public class Node implements Comparable {

    private final int x;

    private final int y;
    private final char Direction;

    public int kosten;
    public int heuristik;

    Node vorgaenger;

    public Node(int x, int y, char Direction) {

        this.x = x;
        this.y = y;
        this.Direction = Direction;

    }

    public Node(int x, int y, int Schrittkosten, int heuristik, Node vorgaenger) {

        this.x = x;
        this.y = y;
        this.kosten = Schrittkosten;
        this.heuristik = heuristik;
        this.vorgaenger = vorgaenger;
        this.Direction='1';
    }

    public int getKostenSum() {
        return this.getKostenBisher() + this.kosten + this.heuristik;
    }

    public int getKostenBisher() {
        if (this.vorgaenger == null) {
            return 0;
        }
        return this.vorgaenger.getKostenBisher() + this.kosten;
    }

    public void setVorgaenger(Node neuer) {
        this.vorgaenger = neuer;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getDirection() {
        return Direction;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.x;
        hash = 59 * hash + this.y;
        hash = 59 * hash + this.Direction;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.Direction != other.Direction) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {

        return 123;
    }

    public void AusgabeXY() {
        if (this.vorgaenger != null) {
            this.vorgaenger.AusgabeXY();
        }
        System.out.println("X-Koordinate:" + x + " Y-Koordinate:" + y);
    }

    @Override
    public String toString() {

        return "X:" + x + "Y:" + y;
    }

}
