import java.io.Serializable;
import java.util.*;

class Frame implements Serializable {

    static final long serialVersionUID = -5399605122490343339L;
    
    public Frame() {
        this.timestamp = new Date(0);
        this.velocity = 0;
        this.altitude = 0;
        this.pressure = 0;
        this.temperature = 0;
        this.attitude = 0;
    }

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