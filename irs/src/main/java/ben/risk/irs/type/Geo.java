package ben.risk.irs.type;

import net.jcip.annotations.Immutable;

import java.io.Serializable;

/**
 * Geo.
 */
@Immutable
public final class Geo implements Serializable {

    /**
     * The Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The latitude coordinate of the position.
     */
    private final double latitude;

    /**
     * The longitude coordinate of the position.
     */
    private final double longitude;

    /**
     * Constructor.
     * @param latitude the latitude coordinate of the position
     * @param longitude the longitude coordinate of the position
     */
    public Geo(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Get the latitude coordinate of the position.
     * @return the latitude coordinate of the position
     */
    public final double getLatitude() {
        return latitude;
    }

    /**
     * Get the longitude coordinate of the position.
     * @return the longitude coordinate of the position
     */
    public final double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Geo geo = (Geo) o;

        if (Double.compare(geo.latitude, latitude) != 0) return false;
        return Double.compare(geo.longitude, longitude) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
