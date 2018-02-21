import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.Optional;

/**
 * Pipe-and-filter network that does what System A does but includes pressure data in the
 * output. In addition, System B should filter “wild points�? out of the data stream for pressure
 * measurements. A wild point is any pressure data that varies more than 10PSI between samples
 * and/or is negative. For wild points encountered in the stream, extrapolate a replacement value by
 * using the last known valid measurement and the next valid measurement in the stream.
 * Extrapolate the replacement value by computing the average of the last valid measurement and
 * the next valid measurement in the stream. If a wild point occurs at the beginning of the stream,
 * replace it with the first valid value; if a wild point occurs at the end of the stream, replace it with
 * the last valid value. Write the output to a text file called OutputB.dat and format the output as
 * shown below – denote any extrapolated values with an asterisk by the value as shown below for
 * the second pressure measurement:
 *
 * Time: Temperature (C): Altitude (m): Pressure (psi):
 * YYYY:DD:HH:MM:SS TTT.ttttt AAAAAA.aaaaa PP:ppppp
 * YYYY:DD:HH:MM:SS TTT.ttttt AAAAAA.aaaaa PP:ppppp*
 * YYYY:DD:HH:MM:SS TTT.ttttt AAAAAA.aaaaa PP:ppppp
 * : : : :
 * Write any rejected wild point values and the time that they occurred to a second text file called
 * WildPoints.dat using a similar format as follows:
 * Time: Pressure (psi):
 * YYYY:DD:HH:MM:SS PP:ppppp
 *
 */

public class SystemB {
    public static void main(String argv[]) {

        SimpleDateFormat timeStampFormatter = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");
        DecimalFormat temperatureFormatter = new DecimalFormat("000.00000");
        DecimalFormat altitudeFormatter = new DecimalFormat("00000.00000");

        // input file
        SourceFileReader fileReaderSource = new SourceFileReader("../DataSets/FlightData.dat");
        BytesToFrameFilter bytesToFrame = new BytesToFrameFilter();
        
        // transform the temperatures and filter frames without temperature and altitude attributes
        TransformFrameFilter transformTemperatureAndConvertAltitude =
                new TransformFrameFilter((frame, lastNSamples) -> {
            if (frame.temperature != null) {
                frame.temperature = (frame.temperature - 32) * 5 / 9; // F -> C
            }
            if (frame.altitude != null) {
                frame.altitude = frame.altitude * 0.3048; // Feet to meters.
            }
            return Optional.ofNullable(frame);
        }, 3);
        
        // split the stream to log "wild points"
        SplitFilter splitFilter = new SplitFilter();
        

        // wild points logging branch ---------
        
        // filter out non-wild points
        FrameSmoothFilter filterNormalPoints = new FrameSmoothFilter((next, current, previous) -> {
            if (next == null || previous == null) {
                return Optional.empty();
            }
            if (Math.abs(next.originalPressure - current.originalPressure) > 10
                    && Math.abs(previous.originalPressure - current.originalPressure) > 10) {
                return Optional.ofNullable(current);
            }
            return Optional.empty();
        });
        
        
        FramePrinterSink wildPointsSink = new FramePrinterSink(
            "Wildpoints.dat",
            "Time:\t\t\t" + "Pressure (psi):\t" + "\n",
            (frame) -> {
            return Optional.of(timeStampFormatter.format(frame.timestamp) + "\t" +
                    altitudeFormatter.format(frame.originalPressure) + "\n");
            });

        // wild points normalizing branch -------------

        // TODO: handle scenario where the first frame and last frames have a wild point.
        FrameSmoothFilter smoothFilter = new FrameSmoothFilter((next, current, previous) -> {
            if (next == null) {
                return Optional.of(null);//current;
            }
            if (previous == null) {
                return current;
            }
            if (Math.abs(next.originalPressure - current.originalPressure) > 10
                    && Math.abs(previous.originalPressure - current.originalPressure) > 10) {
                current.modifiedPressure = (next.originalPressure + previous.originalPressure) / 2;
            }
            return current;
        });
        
        FramePrinterSink sink = new FramePrinterSink(
            "OutputB.dat",
            "Time:\t\t\t" + "Temperature (C):\t" + "Altitude (m):\t" + "Pressure (psi):\t" + "\n",
            (frame) -> {
                boolean modifiedPressure = frame.modifiedPressure != null;
                String asterix = modifiedPressure ? " *": "";
                String pressure = modifiedPressure ?
                        altitudeFormatter.format(frame.modifiedPressure) + asterix :
                        altitudeFormatter.format(frame.originalPressure);

            return Optional.of((frame.timestamp != null ? timeStampFormatter.format(frame.timestamp) : "<null>") + "\t" +
                    (frame.temperature != null ? temperatureFormatter.format(frame.temperature) : "<null>") + "\t" +
                    (frame.altitude != null ? altitudeFormatter.format(frame.altitude) : "<null>") + "\t" +
                    pressure + "\n");

        });

        /****************************************************************************
         * Create the pipe and filter network
         ****************************************************************************/
      
        sink.Connect(smoothFilter);
        wildPointsSink.Connect(filterNormalPoints);
        
        splitFilter.ConnectOutput(smoothFilter);
        splitFilter.ConnectOutput(filterNormalPoints);
        splitFilter.Connect(transformTemperatureAndConvertAltitude);

        transformTemperatureAndConvertAltitude.Connect(bytesToFrame);
        bytesToFrame.Connect(fileReaderSource);

        /****************************************************************************
         * Here we start the filters up.
         ****************************************************************************/
        fileReaderSource.start();
        bytesToFrame.start();
        transformTemperatureAndConvertAltitude.start();
        splitFilter.start();
        filterNormalPoints.start();
        smoothFilter.start();
        sink.start();
        wildPointsSink.start();
    }
}
