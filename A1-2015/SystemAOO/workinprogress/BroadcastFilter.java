import java.util.*;
import java.io.*;

public class BroadcastFilter extends FilterFramework {
    private ArrayList<FilterFramework> outputs = new ArrayList<>();

    class Connector extends FilterFramework {

        Connector(FilterFramework filter) {
            this.InputFilter = filter;
        }
        public void run() {
            System.out.print( "\n" + this.getName() + "::Connector ");
            while(true) {

            }
        }
    }

    Connector createOutput() {
        Connector w = new Connector(this.InputFilter);
        w.InputFilter = this.InputFilter;
        outputs.add(w);
        w.start();
        return w;
    }

    public void run() {
        byte databyte;
        System.out.print( "\n" + this.getName() + "::BroadcastFilter ");
        while (true) {
            try {
                databyte = ReadFilterInputPort();
                for (FilterFramework output : outputs) {
                    WriteFilterOutputPort(databyte, output.OutputWritePort);
                }
            }
            catch (EndOfStreamException e) {
                ClosePorts();
                break;
            }
        }
    }

    void WriteFilterOutputPort(byte datum, PipedOutputStream output) {
        try {
            output.write((int) datum );
            output.flush();
        }
        catch( Exception Error ) {
            System.out.println("\n" + this.getName() + " Pipe write error::" + Error );
        }
        return;
    }
}