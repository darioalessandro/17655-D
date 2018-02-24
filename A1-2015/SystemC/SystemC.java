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
        
     SourceFileReader subsetASource = new SourceFileReader("./SubSetA.dat");
     BytesToFrameFilter bytesToFrameFilterA = new BytesToFrameFilter();
     
     SourceFileReader subsetBSource = new SourceFileReader("./SubSetB.dat");
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
     
     // split the stream to log "wild points"
     SplitFilter splitFilter2 = new SplitFilter();

     // wild points logging branch ---------
     
     // filter out non-wild points
     FrameSmoothFilter filterNormalPoints = new FrameSmoothFilter((next, current, previous) -> {
         if (next == null) { return Optional.empty(); }
         if (previous == null) { return Optional.of(current); }
         if (Math.abs(next.originalPressure - current.originalPressure) > 10
                 && Math.abs(previous.originalPressure - current.originalPressure) > 10) {
             return Optional.of(current);
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

     // correct the wild points
     FrameSmoothFilter smoothFilter = new FrameSmoothFilter((next, current, previous) -> {
         if (next == null) { return Optional.empty(); }
         if (previous == null) { return Optional.of(current); }
         if (Math.abs(next.originalPressure - current.originalPressure) > 10
                 && Math.abs(previous.originalPressure - current.originalPressure) > 10) {
             current.smoothedPressure = (next.originalPressure + previous.originalPressure) / 2;
         }
         return Optional.ofNullable(current);
     });
     
     // final output sink
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
     * Here we connect the filters starting with the sink filter (Filter 1) which
     * we connect to Filter2 the middle filter. Then we connect Filter2 to the
     * source filter (Filter3).
     ****************************************************************************/
     
     sink.Connect(smoothFilter);
     wildPointsSink.Connect(filterNormalPoints);
     
     splitFilter2.ConnectOutput(smoothFilter);
     splitFilter2.ConnectOutput(filterNormalPoints);
     splitFilter2.Connect(filterLowAltitude);
     
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
     splitFilter2.start();
     smoothFilter.start();
     filterNormalPoints.start();
     wildPointsSink.start();
     sink.start();
     
    } // main
}