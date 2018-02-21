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
 		current.smoothedPressure = (next.originalPressure + previous.originalPressure) / 2;
 	}
 	return current;
 });
 *
 * The user is expected to always return a valid frame back usign the callback.
 *
 * The current implementation guarantees that current will never be null.
 */

public class FrameSmoothFilter extends FrameFilterFramework {
	Frame next = null;
	Frame current= null;
	Frame previous = null;

	final private FrameSmoothFilterCallback frameSmoothFilterCallback;

	FrameSmoothFilter(FrameSmoothFilterCallback frameSmoothFilterCallback) {
		this.frameSmoothFilterCallback = frameSmoothFilterCallback;
	}

	void updatePreviousAndCurrentValues(Frame previous, Frame current) {
		this.previous = previous;
		this.current = current;
	}

	public void run() {
		byte databyte;
		System.out.print( "\n" + this.getName() + "::TransformFrameFilter ");
		while (true) {
			try {
				next = readFrame(this.InputReadPort);
				if(current == null && previous == null) {
					current = next;
					continue;
				}
				frameSmoothFilterCallback.smoothCurrentFrame(next, current, previous).ifPresent(frame -> {
					updatePreviousAndCurrentValues(frame, next);
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
