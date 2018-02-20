import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.Optional;

/**
 * Pipe-and-filter network that will read the data stream in FlightData.dat file, convert the
 * temperature measurements from Fahrenheit to Celsius, and convert altitude from feet to meters.
 * Filter out all other measurements (no need to save these). Write the output to a text file called
 * OutputA.dat. Format the output as follows:
 *
 * Time: Temperature (C): Altitude (m):
 * YYYY:DD:HH:MM:SS TTT.ttttt AAAAAA.aaaaa
 */

public class Plumber {
    public static void main(String argv[]) {

        /**
         * Create date and number formatters
         */
        SimpleDateFormat timeStampFormatter = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");
        DecimalFormat temperatureFormatter = new DecimalFormat("000.00000");
        DecimalFormat altitudeFormatter = new DecimalFormat("00000.00000");

        /****************************************************************************
         * Here we instantiate 4 filters.
         ****************************************************************************/

        SourceFileReader fileReaderSource = new SourceFileReader("../DataSets/FlightData.dat");
        BytesToFrameFilter bytesToFrame = new BytesToFrameFilter();
        FramePrinterSink sink = new FramePrinterSink(
            "OutputA.dat",
            "Time:\t\t\t" + "Temperature (C):\t" + "Altitude (m):\t" + "\n",
            (frame) -> {
            return Optional.of((frame.timestamp != null ? timeStampFormatter.format(frame.timestamp) : "<null>") + "\t" +
                    (frame.temperature != null ? temperatureFormatter.format(frame.temperature) : "<null>") + "\t" +
                    (frame.altitude != null ? altitudeFormatter.format(frame.altitude) : "<null>")  + "\n");
        });

        TransformFrameFilter transformTemperatureAndConvertAltitude = new TransformFrameFilter((frame, lastNSamples) -> {
            if (frame.temperature != null) {
                frame.temperature = (frame.temperature - 32) * 5 / 9; // F -> C
            }
            if (frame.altitude != null) {
                frame.altitude = frame.altitude * 0.3048; // Feet to meters.
            }
            return Optional.ofNullable(frame);
        }, 3);

        /****************************************************************************
         * Here we connect the filters starting with the sink filter (sink) which
         * we connect to feetToMeters the fahrenheitToCelsius filter. Then we connect fahrenheitToCelsius to the
         * source filter (fileReaderSource).
         ****************************************************************************/

        sink.Connect(transformTemperatureAndConvertAltitude);
        transformTemperatureAndConvertAltitude.Connect(bytesToFrame);
        bytesToFrame.Connect(fileReaderSource);

        /****************************************************************************
         * Here we start the filters up.
         ****************************************************************************/

        fileReaderSource.start();
        bytesToFrame.start();
        sink.start();
        transformTemperatureAndConvertAltitude.start();
    }
}
