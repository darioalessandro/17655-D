import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class SystemC {
    public static void main( String argv[])
    {
       // formatters for sink nodes
      SimpleDateFormat timeStampFormatter = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");
      DecimalFormat temperatureFormatter = new DecimalFormat("000.00000");
      DecimalFormat altitudeFormatter = new DecimalFormat("00000.00000");   
        
     SourceFileReader subsetASource = new SourceFileReader("../DataSets/SubSetA.dat");
     BytesToFrameFilter bytesToFrameFilterA = new BytesToFrameFilter();
     
     SourceFileReader subsetBSource = new SourceFileReader("../DataSets/SubSetB.dat");
     BytesToFrameFilter bytesToFrameFilterB = new BytesToFrameFilter();
     
     MergeFilter mergeFilter = new MergeFilter();
     
     SplitFilter splitFilter = new SplitFilter();
     
     // low altitude branch
     TransformFrameFilter filterHighAltitude =
         new TransformFrameFilter(frame -> frame.altitude >= 10000 ? Optional.empty() : Optional.of(frame));

     FramePrinterSink lowAltitudeSink = new FramePrinterSink(
         "LessThan10K.dat",
         "Time:\t\t\t" + "Temperature (C):\t" + "Altitude (m):\t" + "Pressure (psi):\t" + "\n",
         (frame) -> {
             String pressure = frame.smoothedPressure != null ?
                 altitudeFormatter.format(frame.smoothedPressure) + " *" :
                 altitudeFormatter.format(frame.originalPressure);
             
          return Optional.of((frame.timestamp != null ? timeStampFormatter.format(frame.timestamp) : "<null>") + "\t" +
                 (frame.temperature != null ? temperatureFormatter.format(frame.temperature) : "<null>") + "\t" +
                 (frame.altitude != null ? altitudeFormatter.format(frame.altitude) : "<null>") + "\t" +
                 pressure + "\n"); //TODO how to make this a
         });
     
     // main branch
     
     TransformFrameFilter filterLowAltitude =
         new TransformFrameFilter(frame -> frame.altitude < 10000 ? Optional.empty() : Optional.of(frame));
     
     
     // smooth any wild points in the stream
     BufferedSmoothingFilter bufferedSmoothingFilter = new BufferedSmoothingFilter();
     
     // split the stream to log "wild points"
     SplitFilter splitFilter2 = new SplitFilter();

     // wild points logging branch ---------
     
     // filter out non-wild points
     TransformFrameFilter filterNormalPoints = new TransformFrameFilter(frame -> {
         return (frame.smoothedPressure != null) ? Optional.of(frame) : Optional.empty();
     });
     
     
     FramePrinterSink wildPointsSink = new FramePrinterSink(
         "Wildpoints.dat",
         "Time:\t\t\t" + "Pressure (psi):\t" + "\n",
         (frame) -> {
         return Optional.of(timeStampFormatter.format(frame.timestamp) + "\t" +
                 altitudeFormatter.format(frame.originalPressure) + "\n");
     });

     // main branch

     // final output sink
     FramePrinterSink sink = new FramePrinterSink(
         "OutputC.dat",
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
     * Here we connect the filters starting with the sink filter (Filter 1) which
     * we connect to Filter2 the middle filter. Then we connect Filter2 to the
     * source filter (Filter3).
     ****************************************************************************/
     

     wildPointsSink.Connect(filterNormalPoints);
     
     splitFilter2.ConnectOutput(sink);
     splitFilter2.ConnectOutput(filterNormalPoints);
     splitFilter2.Connect(bufferedSmoothingFilter);
     
     bufferedSmoothingFilter.Connect(filterLowAltitude);
     
     lowAltitudeSink.Connect(filterHighAltitude);
     
     splitFilter.ConnectOutput(filterLowAltitude);
     splitFilter.ConnectOutput(filterHighAltitude);
     splitFilter.Connect(mergeFilter);
     
     mergeFilter.Connect(bytesToFrameFilterA);
     mergeFilter.ConnectMergeInput(bytesToFrameFilterB);
     
     bytesToFrameFilterA.Connect(subsetASource);
     bytesToFrameFilterB.Connect(subsetBSource);


     /****************************************************************************
     * Here we start the filters up. All-in-all,... its really kind of boring.
     ****************************************************************************/
     subsetASource.start();
     subsetBSource.start();
     bytesToFrameFilterA.start();
     bytesToFrameFilterB.start();
     mergeFilter.start();
     splitFilter.start();
     filterHighAltitude.start();
     lowAltitudeSink.start();
     filterLowAltitude.start();
     bufferedSmoothingFilter.start();
     splitFilter2.start();
     filterNormalPoints.start();
     wildPointsSink.start();
     sink.start();
     
    } // main
}
