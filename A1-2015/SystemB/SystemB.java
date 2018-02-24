import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.Optional;

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
                new TransformFrameFilter((frame) -> {
            if (frame.temperature != null) {
                frame.temperature = (frame.temperature - 32) * 5 / 9; // F -> C
            }
            if (frame.altitude != null) {
                frame.altitude = frame.altitude * 0.3048; // Feet to meters.
            }
            return Optional.ofNullable(frame);
        });
        
        // correct the wild points
        BufferedSmoothingFilter bufferedSmoothingFilter = new BufferedSmoothingFilter();
        
        // split the stream to log "wild points" and corrected points separately
        SplitFilter splitFilter = new SplitFilter();

        // wild points logging branch ---------
        
        // filter out non-wild points
        TransformFrameFilter filterNormalPoints = new TransformFrameFilter(frame -> {
            return (frame.smoothedPressure != null) ? Optional.of(frame) : Optional.empty();
        });
        
        // print wild points
        FramePrinterSink wildPointsSink = new FramePrinterSink(
            "Wildpoints.dat",
            "Time:\t\t\t" + "Pressure (psi):\t" + "\n",
            (frame) -> {
            return Optional.of(timeStampFormatter.format(frame.timestamp) + "\t" +
                    altitudeFormatter.format(frame.originalPressure) + "\n");
            });
        
        // main branch
        
        // print the corrected stream of frames
        FramePrinterSink sink = new FramePrinterSink(
            "OutputB.dat",
            "Time:\t\t\t" + "Temperature (C):\t" + "Altitude (m):\t" + "Pressure (psi):\t" + "\n",
            (frame) -> {
                boolean smoothedPressure = frame.smoothedPressure != null;
                String asterix = smoothedPressure ? " *": "";
                String pressure = smoothedPressure ?
                        altitudeFormatter.format(frame.smoothedPressure) + asterix :
                        altitudeFormatter.format(frame.originalPressure);

            return Optional.of((frame.timestamp != null ? timeStampFormatter.format(frame.timestamp) : "<null>") + "\t" +
                    (frame.temperature != null ? temperatureFormatter.format(frame.temperature) : "<null>") + "\t" +
                    (frame.altitude != null ? altitudeFormatter.format(frame.altitude) : "<null>") + "\t" +
                    pressure + "\n");
        });

        /****************************************************************************
         * Create the pipe and filter network
         ****************************************************************************/

        wildPointsSink.Connect(filterNormalPoints);
        splitFilter.ConnectOutput(sink);
        splitFilter.ConnectOutput(filterNormalPoints);
        splitFilter.Connect(bufferedSmoothingFilter);
        bufferedSmoothingFilter.Connect(transformTemperatureAndConvertAltitude);
        transformTemperatureAndConvertAltitude.Connect(bytesToFrame);
        bytesToFrame.Connect(fileReaderSource);

        /****************************************************************************
         * Here we start the filters up.
         ****************************************************************************/
        fileReaderSource.start();
        bytesToFrame.start();
        transformTemperatureAndConvertAltitude.start();
        bufferedSmoothingFilter.start();
        splitFilter.start();
        filterNormalPoints.start();
        sink.start();
        wildPointsSink.start();
    }
}
