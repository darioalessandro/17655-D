import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.LinkedList;
import java.util.Queue;

public class BufferedSmoothingFilter extends FrameFilterFramework {

    BufferedSmoothingFilter() {
    }

    public void run() {

      Frame current = new Frame();
      double mostRecentPressure;
    
      try {
        current = readFrame(this.InputReadPort);
        mostRecentPressure = current.originalPressure;
      } catch (EndOfStreamException e1) {

          e1.printStackTrace();
          return;
      }
      

      while (true) {
        try {
          current = readFrame(this.InputReadPort);
          
          if(isWildPoint(current, mostRecentPressure)){
              mostRecentPressure = handleWildPoint(mostRecentPressure, current, this.InputReadPort, this.OutputWritePort);
          } else {
              mostRecentPressure = current.originalPressure;
              writeFrame(current, this.OutputWritePort);
          }
          
        }
        catch (EndOfStreamException e) {
          ClosePorts();
          break;
        }
      }

     }
    boolean isWildPoint(Frame current, double mostRecentPressure) {
        return (Math.abs(current.originalPressure - mostRecentPressure) > 10 )
            || (current.originalPressure < 0);
    }
    
    double handleWildPoint(double mostRecentPressure, Frame thisWildPoint, PipedInputStream pipe, PipedOutputStream outPipe) throws EndOfStreamException{

        Queue<Frame> wildPointQueue = new LinkedList<Frame>();
        wildPointQueue.add(thisWildPoint);
        Frame next = new Frame();
        
        // read inputs until a normal point is found, then process the backlogue of wild points
        while(true) {
            try{
                next = readFrame(pipe);
                
                if(isWildPoint(next, mostRecentPressure)){
                    // store the wild point
                    wildPointQueue.add(next);
                } else {
                    //we found a normal point, correct the wild points
                    double corrected = (mostRecentPressure + next.originalPressure)/2;
                    writeCorrectedWildPointQueue(corrected, wildPointQueue, outPipe);
                    //finally, print out the normal point unchanged and return
                    writeFrame(next, outPipe);
                    return next.originalPressure;
                }
            } catch (EndOfStreamException e) {
                // the stream ended, set the wild points to last good value and rethrow the EOS exception
                double corrected = mostRecentPressure;
                writeCorrectedWildPointQueue(corrected, wildPointQueue, outPipe);
                throw e;
            }
        }

    }
    
    void writeCorrectedWildPointQueue(double correctedValue, Queue<Frame> queue, PipedOutputStream pipe){
        while(queue.size() > 0){
            Frame corrected = new Frame();
            corrected = queue.poll();
            corrected.smoothedPressure = correctedValue;
            writeFrame(corrected, pipe);
        }
    }

}
