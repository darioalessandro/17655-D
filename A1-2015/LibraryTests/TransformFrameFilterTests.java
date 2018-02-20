import java.io.*;
import java.util.*;

public class TransformFrameFilterTests {
    public static void main(String argv[]) {

        Date d = new Date();

        /** TEST 1 filterFrameWithTemperatureLessThan100 */

        TransformFrameFilter filterFrameWithTemperatureLessThan100 = new TransformFrameFilter((frame, lastNSamples) -> {
            if (frame.temperature != null && frame.temperature < 100) {
                return Optional.empty();
            }
            return Optional.ofNullable(frame);
        }, 1);

        TestFilter filterFrameWithTemperatureLessThan100Test = new TestFilter(
                "filterFrameWithTemperatureLessThan100Test",
                new ArrayList<>(Arrays.asList(new Frame(d, null, 0.0, 0.0, 120.0, 0.0),
                        new Frame(d, null, 0.0, 0.0, 50.0, 0.0),
                        new Frame(d, null, 0.0, 0.0, 130.0, 0.0),
                        new Frame(d, null, 0.0, 0.0, 50.0, 0.0),
                        new Frame(d, null, 0.0, 0.0, 50.0, 0.0))),
                new ArrayList<>(Arrays.asList(new Frame(d, null, 0.0, 0.0, 120.0, 0.0),
                        new Frame(d, null, 0.0, 0.0, 130.0, 0.0)))
        );

        filterFrameWithTemperatureLessThan100.Connect(filterFrameWithTemperatureLessThan100Test);
        filterFrameWithTemperatureLessThan100Test.Connect(filterFrameWithTemperatureLessThan100);

        filterFrameWithTemperatureLessThan100.start();
        filterFrameWithTemperatureLessThan100Test.start();

        /** TEST 2 filterOutTempSpikesOfMoreThan10DegreesAndReplaceWithAverage */

        TransformFrameFilter filterOutTempSpikesOfMoreThan10DegreesAndReplaceWithAverage
                = new TransformFrameFilter((frame, lastNSamples) -> {
            System.out.println("filterOutTempSpikesOfMoreThan10DegreesAndReplaceWithAverage called");
            System.out.println("lastNSamples.size() -> " + lastNSamples.size());
            Frame previousFrame = (!lastNSamples.isEmpty()) ? lastNSamples.get(0) : null;
            if (previousFrame!= null &&
                    frame.temperature != null &&
                    Math.abs(frame.temperature - previousFrame.temperature) > 10) {
                double sum = 0;
                for(Frame f : lastNSamples) {
                    sum += f.temperature;
                }
                frame.temperature = sum / lastNSamples.size();
            }
            return Optional.ofNullable(frame);
        }, 3);

        TestFilter filterOutTempSpikesOfMoreThan10DegreesAndReplaceWithAverageTest = new TestFilter(
                "filterOutTempSpikesOfMoreThan10DegreesAndReplaceWithAverageTest",
                new ArrayList<>(Arrays.asList(new Frame(d, null, 0.0, 0.0, 101.0, 0.0),
                        new Frame(d, null, 0.0, 0.0, 102.0, 0.0),
                        new Frame(d, null, 0.0, 0.0, 103.0, 0.0),
                        new Frame(d, null, 0.0, 0.0, 150.0, 0.0),
                        new Frame(d, null, 0.0, 0.0, 104.0, 0.0),
                        new Frame(d, null, 0.0, 0.0, 105.0, 0.0),
                        new Frame(d, null, 0.0, 0.0, 106.0, 0.0),
                        new Frame(d, null, 0.0, 0.0, 107.0, 0.0),
                        new Frame(d, null, 0.0, 0.0, 108.0, 0.0))),
                new ArrayList<>(Arrays.asList(new Frame(d, null, 0.0, 0.0, 101.0, 0.0),
                        new Frame(d, null, 0.0, 0.0, 102.0, 0.0),
                        new Frame(d, null, 0.0, 0.0, 103.0, 0.0),
                        new Frame(d, null, 0.0, 0.0, 102.0, 0.0),
                        new Frame(d, null, 0.0, 0.0, 104.0, 0.0)))
        );

        filterOutTempSpikesOfMoreThan10DegreesAndReplaceWithAverageTest
                .Connect(filterOutTempSpikesOfMoreThan10DegreesAndReplaceWithAverage);

        filterOutTempSpikesOfMoreThan10DegreesAndReplaceWithAverage
                .Connect(filterOutTempSpikesOfMoreThan10DegreesAndReplaceWithAverageTest);

        filterOutTempSpikesOfMoreThan10DegreesAndReplaceWithAverage.start();
        filterOutTempSpikesOfMoreThan10DegreesAndReplaceWithAverageTest.start();

    }
}