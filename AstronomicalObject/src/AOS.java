/*
 * Abstract Data Type of all planets
 * include planets, stars and messiers
 */

abstract class AOS {
    private Double ra, declination, magnitude, distance;

    AOS(Double rap, Double decp, Double magp, Double disp) {
        ra = rap;
        declination = decp;
        magnitude = magp;
        distance = disp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AOS)) return false;

        AOS aos = (AOS) o;

        if (getra() != null ? !getra().equals(aos.getra()) : aos.getra() != null) return false;
        if (declination != null ? !declination.equals(aos.declination) : aos.declination != null) return false;
        if (magnitude != null ? !magnitude.equals(aos.magnitude) : aos.magnitude != null) return false;
        return distance != null ? distance.equals(aos.distance) : aos.distance == null;
    }

    @Override
    public int hashCode() {
        int result = getra() != null ? getra().hashCode() : 0;
        result = 31 * result + (declination != null ? declination.hashCode() : 0);
        result = 31 * result + (magnitude != null ? magnitude.hashCode() : 0);
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        return result;
    }

    public Double getra() {
        return ra;
    }

    public Double getdecl() {
        return declination;
    }

    public Double getmagnitude() {
        return magnitude;
    }

    public Double getdistance() {
        return distance;
    }
}
