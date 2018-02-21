import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class FrameFilterFramework extends FilterFramework {

    // add functions to read & write frames
    Frame readFrame(PipedInputStream input) throws EndOfStreamException {

        try {
          while (input.available()==0 ) {
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
          ObjectInputStream objectInputPort = new ObjectInputStream(input);
          Frame datum = (Frame)objectInputPort.readObject();
          return datum;
        }
        catch( Exception Error ) {
          System.out.println( "\n" + this.getName() + " Pipe read error::" + Error );
          return null;
        }
     }
    
    void writeFrame(Frame frame, PipedOutputStream output) {
        try {
            ObjectOutputStream output2 = new ObjectOutputStream(output);
            output2.writeObject(frame);
        }
        catch( Exception Error ) {
            System.out.println("\n" + this.getName() + " Pipe write error::" + Error );
        }
        return;
    }
}
