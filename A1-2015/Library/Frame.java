import java.io.Serializable;
import java.util.*;

/**
 * Provides an object oriented representation of the data frames.
 *
 * Any property can be null, filters are responsible for validating that the properties != null before attempting to
 * perform any computations.
 *
 */

class Frame implements Serializable {

    static final long serialVersionUID = -5399605122490343339L;

    public Frame() { }

    public Frame(Date timestamp, Double velocity, Double altitude, Double originalPressure, Double temperature, Double attitude) {
        this.timestamp = timestamp;
        this.velocity = velocity;
        this.altitude = altitude;
        this.originalPressure = originalPressure;
        this.temperature = temperature;
        this.attitude = attitude;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Measurement{" +
                "timestamp=" + (timestamp != null ? timestamp.getTime() : "<null>") +
                ", velocity=" + (velocity != null ? velocity : "<null>") +
                ", altitude=" + (altitude != null ? altitude : "<null>") +
                ", originalPressure=" + (originalPressure != null ? originalPressure : "<null>") +
                ", modifiedPressure=" + (modifiedPressure != null ? modifiedPressure : "<null>") +
                ", temperature=" + (temperature != null ? temperature : "<null>") +
                ", attitude=" + (attitude != null ? attitude : "<null>") +
                '}';
    }
    public Date timestamp = null;
    public Double velocity = null;
    public Double altitude = null;
    public Double originalPressure = null;
    public Double temperature = null;
    public Double attitude = null;
    public Double modifiedPressure = null;

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Frame frame = (Frame) object;

        if (timestamp != null ? !timestamp.equals(frame.timestamp) : frame.timestamp != null) return false;
        if (velocity != null ? Math.abs(velocity - frame.velocity) > 0.01 : frame.velocity != null) return false;
        if (altitude != null ? Math.abs(altitude - frame.altitude) > 0.01 : frame.altitude != null) return false;
        if (originalPressure != null ? Math.abs(originalPressure - frame.originalPressure) > 0.01 : frame.originalPressure != null) return false;
        if (modifiedPressure != null ? Math.abs(modifiedPressure - frame.modifiedPressure) > 0.01 : frame.modifiedPressure != null) return false;
        if (temperature != null ? Math.abs(temperature - frame.temperature) > 0.01 : frame.temperature != null) return false;
        if (attitude != null ? Math.abs(attitude - frame.attitude) > 0.01 : frame.attitude != null) return false;
        return true;
    }
}
