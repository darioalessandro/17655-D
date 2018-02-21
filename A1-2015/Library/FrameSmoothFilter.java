import java.io.*;
import java.util.Optional;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Provides a generic way to smooth out streams of frames.
 *
 * Example:
 *
 * FrameSmoothFilter smoothFilter = new FrameSmoothFilter((next, current, previous) -> {
 	if (next == null) {
 		return current;
 	}
 	if (previous == null) {
 		return current;
 	}
 	if (Math.abs(next.originalPressure - current.originalPressure) > 10
 		&& Math.abs(previous.originalPressure - current.originalPressure) > 10) {
 		current.modifiedPressure = (next.originalPressure + previous.originalPressure) / 2;
 	}
 	return current;
 });
 *
 * The user is expected to always return a valid frame back usign the callback.
 *
 * The current implementation guarantees that current will never be null.
 */

public class FrameSmoothFilter extends FrameFilterFramework {

	final private FrameSmoothFilterCallback frameSmoothFilterCallback;

	FrameSmoothFilter(FrameSmoothFilterCallback frameSmoothFilterCallback) {
		this.frameSmoothFilterCallback = frameSmoothFilterCallback;
	}

	public void run() {
		byte databyte;
		System.out.print( "\n" + this.getName() + "::TransformFrameFilter ");
		Frame next = null;
		Frame current= null;
		Frame previous = null;
		while (true) {
			try {
				next = readFrame(this.InputReadPort);
				if(current == null && previous == null) {
					current = next;
					continue;
				}
				//Frame transformedCurrent = frameSmoothFilterCallback.smoothCurrentFrame(next, current, previous);
				frameSmoothFilterCallback.smoothCurrentFrame(next, current, previous).ifPresent(frame -> {
				    previous = frame;
            current = next;
		        try {
		            ObjectOutputStream output = new ObjectOutputStream(this.OutputWritePort);
		            output.writeObject(frame);
		            
		          }
		          catch (IOException e) {
		            e.printStackTrace();
		          }
				}); 

			}
			catch (EndOfStreamException e) {
				ClosePorts();
				break;
			}
		}
   }
}
