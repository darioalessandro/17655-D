import java.io.*;
import java.util.*;						// This class is used to interpret time words

public class FramePrinterSink extends FrameFilterFramework {
	final String filePath;
	final String header;
	final FrameToStringCallback frameToStringCallback;
	FramePrinterSink(String filePath,
						   String header,
						   FrameToStringCallback frameToStringCallback) {
		this.filePath = filePath;
		this.header = header;
		this.frameToStringCallback = frameToStringCallback;
	}

	public void run() {
		OutputStream outStream;
		try {
			System.out.print("\n" + this.getName() + "::SinkMeasurementPrinter ");
			outStream = new BufferedOutputStream(new FileOutputStream(this.filePath));
			outStream.write(this.header.getBytes("UTF-8"));
			while (true) {
				try {
					Frame m = readFrame(this.InputReadPort);
					this.frameToStringCallback.transform(m).ifPresent((textToWrite) -> {
						try {
							System.out.println(textToWrite);
							outStream.write(textToWrite.getBytes("UTF-8"));
						}
						catch (IOException e) {
							e.printStackTrace();
						}
					});
				} catch (EndOfStreamException e) {
					outStream.close();
					ClosePorts();
					return;
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("\n" + this.getName() + "::Problem closing output data file::" + e);
		}
   }
}
