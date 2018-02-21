import java.io.*;
import java.util.Optional;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Provides a generic way to filter Frames.
 *
 * Frames can be filtered out completely by returning Optional.empty() in the transform callback.
 *
 TransformFrameFilter filterOutFramesWithTempHigherThan100 = new TransformFrameFilter((frame) -> {
 	if (frame.temperature != null && frame.temperature > 100) {
 		return Optional.empty();
 	}
 	return Optional.ofNullable(frame);
 });
 */

public class TransformFrameFilter extends FrameFilterFramework {

	final private TransformFrameCallback transformFrameCallback;

	TransformFrameFilter(TransformFrameCallback transformFrameCallback) {
		this.transformFrameCallback = transformFrameCallback;
	}

	public void run() {
		byte databyte;
		System.out.print( "\n" + this.getName() + "::TransformFrameFilter ");
		while (true) {
			try {
				transformFrameCallback.transform(readFrame(this.InputReadPort)).ifPresent(transfomedFrame -> {
				    try {
						ObjectOutputStream output = new ObjectOutputStream(this.OutputWritePort);
						output.writeObject(transfomedFrame);
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
