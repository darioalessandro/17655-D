import java.io.Serializable;
import java.util.*;

class Measurement implements Serializable {

    static final long serialVersionUID = -5399605122490343339L;

    @java.lang.Override
    public java.lang.String toString() {
        return "Measurement{" +
                "timestamp=" + timestamp.getTime() +
                ", velocity=" + velocity +
                ", altitude=" + altitude +
                ", pressure=" + pressure +
                ", temperature=" + temperature +
                ", attitude=" + attitude +
                '}';
    }
    public Date timestamp;
    public double velocity;
    public double altitude;
    public double pressure;
    public double temperature;
    public double attitude;

}