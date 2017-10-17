/*
 * Object stars extend AOS
 * Add some element planets needs
 */

import java.util.HashSet;

public class Stars extends AOS {

    private Double starID;
    private String starType, starConstellation;
    public static final HashSet<AOS> starsSet = new HashSet<>();

    public Stars(Double id, Double rap, Double decp, Double magp, Double disp,
                 String type, String con) {
        super(rap, decp, magp, disp);
        starID = id;
        starType = type;
        starConstellation = con;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stars)) return false;
        if (!super.equals(o)) return false;

        Stars stars = (Stars) o;

        if (starID != null ? !starID.equals(stars.starID) : stars.starID != null) return false;
        if (starType != null ? !starType.equals(stars.starType) : stars.starType != null) return false;
        return starConstellation != null ? starConstellation.equals(stars.starConstellation) : stars.starConstellation == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (starID != null ? starID.hashCode() : 0);
        result = 31 * result + (starType != null ? starType.hashCode() : 0);
        result = 31 * result + (starConstellation != null ? starConstellation.hashCode() : 0);
        return result;
    }

    public Double getStarID() {
        return starID;
    }

    public void setStarID(Double starID) {
        this.starID = starID;
    }

    public String getStarType() {
        return starType;
    }

    public void setStarType(String starType) {
        this.starType = starType;
    }

    public String getStarConstellation() {
        return starConstellation;
    }

    public void setStarConstellation(String starConstellation) {
        this.starConstellation = starConstellation;
    }

    @Override
    public String toString() {
        return "Stars ID : " + starID;
    }

}
