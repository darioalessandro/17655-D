import java.util.*;
import java.io.*;

class InputTuple {
    final PipedInputStream inputStream;
    final FilterFramework filter;

    InputTuple(PipedInputStream inputStream, FilterFramework filter) {
        this.inputStream = inputStream;
        this.filter = filter;
    }
}

public class MergeFilter extends FilterFramework
{
    private ArrayList<InputTuple> inputs = new ArrayList<>();

    public void run() {
        byte databyte;
        System.out.println( "\n" + this.getName() + "::MergeFilter ");

        while (true) {
            try {
                Measurement m = ReadFilterInputPorts();
                System.out.println("MergeFilter: " + m.toString());
                ObjectOutputStream OutputWritePorto = new ObjectOutputStream(this.OutputWritePort);
                OutputWritePorto.writeObject(m);
            } catch (EndOfStreamException e) {
                ClosePorts();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void Connect( FilterFramework Filter ) {
        try {
            // Connect this filter's input to the upstream pipe's output stream
            PipedInputStream inputReadPort = new PipedInputStream();
            inputReadPort.connect( Filter.OutputWritePort );
            inputs.add(new InputTuple(inputReadPort, Filter));
        }
        catch( Exception Error ) {
            System.out.println( "\n" + this.getName() + " FilterFramework error connecting::"+ Error );
        }
    }

    boolean inputsAvailable() throws Exception {
        for (InputTuple input : inputs) {
            if (input.inputStream.available() != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean EndOfInputStream() {
        for (InputTuple input : inputs) {
            if (input.filter.isAlive()) {
                return false;
            }
        }
        return true;
    }

    Measurement ReadFilterInputPorts() throws EndOfStreamException {

        /***********************************************************************
         * Since delays are possible on upstream filters, we first wait until
         * there is data available on the input port. We check,... if no data is
         * available on the input port we wait for a quarter of a second and check
         * again. Note there is no timeout enforced here at all and if upstream
         * filters are deadlocked, then this can result in infinite waits in this
         * loop. It is necessary to check to see if we are at the end of stream
         * in the wait loop because it is possible that the upstream filter completes
         * while we are waiting. If this happens and we do not check for the end of
         * stream, then we could wait forever on an upstream pipe that is long gone.
         * Unfortunately Java pipes do not throw exceptions when the input pipe is
         * broken. So what we do here is to see if the upstream filter is alive.
         * if it is, we assume the pipe is still open and sending data. If the
         * filter is not alive, then we assume the end of stream has been reached.
         ***********************************************************************/

        try {
            while (!inputsAvailable()) {
                if (EndOfInputStream()) {
                    throw new EndOfStreamException("End of input stream reached");
                }
                sleep(250);
            }
        }
        catch( EndOfStreamException Error ) {
            throw Error;
        }
        catch( Exception Error ) {
            System.out.println( "\n" + this.getName() + " Error in read port wait loop::" + Error );
        }

        /***********************************************************************
         * If at least one byte of data is available on the input
         * pipe we can read it. We read and write one byte to and from ports.
         ***********************************************************************/

        try {
            for (InputTuple input : inputs) {
                if (input.filter.isAlive() && input.inputStream.available() != 0) {
                    ObjectInputStream objectInputPort = new ObjectInputStream(input.inputStream);
                    return (Measurement)objectInputPort.readObject();
                }
            }
        }
        catch( Exception Error ) {
            System.out.println( "\n" + this.getName() + " Pipe read error::" + Error );
            return null;
        }
        return null;
    }
}