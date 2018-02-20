import java.io.*;
import java.util.*;

class TestFilter extends FilterFramework {

    final LinkedList<Frame> testInputs;
    final LinkedList<Frame> expectedOutputs;
    final String testName;

    TestFilter(String testName, List<Frame> testInputs, List<Frame> expectedOutputs) {
        this.testName = testName;
        this.testInputs = new LinkedList(testInputs);
        this.expectedOutputs = new LinkedList(expectedOutputs);
    }

    public void run() {
        System.out.print( "\n" + this.getName() + this.testName+ "\n");
        while (true) {
            try
            {
                while(!testInputs.isEmpty()) {
                    Frame frame = testInputs.removeFirst();
                    ObjectOutputStream output = new ObjectOutputStream(this.OutputWritePort);
                    output.writeObject(frame);
                    System.out.println("Test Filter Wrote frame: " + frame);
                }
                while(!expectedOutputs.isEmpty()) {
                    Frame actual = ReadFrame();
                    System.out.println("Test Filter received frame " + actual.toString());
                    Frame expected = expectedOutputs.removeFirst();
                    if(!expected.equals(actual)) {
                        throw new Exception("Unexpected value,\n expected: " +
                                expected.toString() + "\n actual: " + actual.toString());
                    }
                }
                throw new Exception("Success! \n");
            }
            catch (Exception e2) {
                e2.printStackTrace();
                ClosePorts();
                break;
            }
        }
    }
}