import java.io.*;
import java.util.Optional;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Provides a generic way to filter Frames.
 *
 * Frames can be filtered out completely by returning Optional.empty() in the transform callback.
 *
 TransformFrameFilter filterOutFramesWithTempHigherThan100 = new TransformFrameFilter((frame, lastNSamples) -> {
 	if (frame.temperature != null && frame.temperature > 100) {
 		return Optional.empty();
 	}
 	return Optional.ofNullable(frame);
 }, 3);

 lastNSamples is an immutable list with the last N accepted frames,
 */

public class TransformFrameFilter extends FilterFramework {

	final private TransformFrameCallback transformFrameCallback;
	final private int maxNumberOfSamplesToSendToTheCallback;
	final private LinkedList<Frame> lastNFrames = new LinkedList<>();

	TransformFrameFilter(TransformFrameCallback transformFrameCallback, int maxNumberOfSamplesToSendToTheCallback) {
		this.maxNumberOfSamplesToSendToTheCallback = maxNumberOfSamplesToSendToTheCallback;
		this.transformFrameCallback = transformFrameCallback;
	}

	public void run() {
		byte databyte;
		System.out.print( "\n" + this.getName() + "::TransformFrameFilter ");
		while (true) {
			try {
				transformFrameCallback.transform(ReadFrame(),
						Collections.unmodifiableList(lastNFrames)).ifPresent(transfomedFrame -> {
				    try {
						lastNFrames.addFirst(transfomedFrame);
						if (lastNFrames.size() > maxNumberOfSamplesToSendToTheCallback) {
							lastNFrames.removeLast();
						}
						if (lastNFrames.size() == maxNumberOfSamplesToSendToTheCallback) {
							ObjectOutputStream output = new ObjectOutputStream(this.OutputWritePort);
							output.writeObject(lastNFrames.getLast());
						}
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
				});
			}
			catch (EndOfStreamException e) {
				try {
					lastNFrames.removeLast();
					while(!lastNFrames.isEmpty()) {
						Frame frame = lastNFrames.removeLast();
						ObjectOutputStream output = new ObjectOutputStream(this.OutputWritePort);
						output.writeObject(frame);
					}
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}

				ClosePorts();
				break;
			}
		}
   }
}
