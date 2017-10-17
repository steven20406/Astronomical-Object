/*
 * Object messiers extend AOS
 * Add some element planets needs
 */

import java.util.HashSet;

public class Messiers extends AOS {
    private Double M42;
    private String constellations;
    private String description;
    public static final HashSet<AOS> messierSet = new HashSet<>();

    public Messiers(Double M42p, Double rap, Double decp, Double magp,
                    Double disp, String con, String des) {
        super(rap, decp, magp, disp);
        M42 = M42p;
        description = des;
        constellations = con;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Messiers)) return false;
        if (!super.equals(o)) return false;

        Messiers messiers = (Messiers) o;

        if (getM42() != null ? !getM42().equals(messiers.getM42()) : messiers.getM42() != null) return false;
        return description != null ? description.equals(messiers.description) : messiers.description == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getM42() != null ? getM42().hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public Double getM42() {
        return M42;
    }

    public void setM42(Double m42) {
        M42 = m42;
    }

    public String getConstellations() {
        return constellations;
    }

    public void setConstellations(String constellations) {
        this.constellations = constellations;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Messier : M" + M42;
    }

}