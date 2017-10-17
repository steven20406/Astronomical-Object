/*
 * Object planets extend AOS
 * Add some element planets needs
 */

import java.util.HashSet;

public class Planets extends AOS {
    private String planetName;
    private Double planetAlbedo;
    public static final HashSet<AOS> planetsSet = new HashSet<>();

    Planets(String namep, Double rap, Double decp, Double magp, Double disp,
            Double alep) {
        super(rap, decp, magp, disp);
        planetName = namep;
        planetAlbedo = alep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Planets)) return false;
        if (!super.equals(o)) return false;

        Planets planets = (Planets) o;

        if (planetName != null ? !planetName.equals(planets.planetName) : planets.planetName != null) return false;
        return planetAlbedo != null ? planetAlbedo.equals(planets.planetAlbedo) : planets.planetAlbedo == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (planetName != null ? planetName.hashCode() : 0);
        result = 31 * result + (planetAlbedo != null ? planetAlbedo.hashCode() : 0);
        return result;
    }

    public String getName() {
        return planetName;
    }

    @Override
    public String toString() {
        return "Planets name : " + planetName;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public Double getPlanetAlbedo() {
        return planetAlbedo;

    }

    public void setPlanetAlbedo(Double planetAlbedo) {
        this.planetAlbedo = planetAlbedo;
    }
}
