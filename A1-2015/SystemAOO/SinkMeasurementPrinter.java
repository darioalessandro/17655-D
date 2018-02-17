import java.io.*;
import java.util.*;						// This class is used to interpret time words

public class SinkMeasurementPrinter extends FilterFramework {
	final String filePath;
	final String header;
	final SinkMeasurementMeasurementToStringCallback sinkMeasurementMeasurementToStringCallback;
	SinkMeasurementPrinter(String filePath,
						   String header,
						   SinkMeasurementMeasurementToStringCallback transform) {
		this.filePath = filePath;
		this.header = header;
		this.sinkMeasurementMeasurementToStringCallback = transform;
	}

	public void run() {
		DataOutputStream outStream;
		try {
			System.out.print("\n" + this.getName() + "::SinkMeasurementPrinter ");
			outStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(this.filePath)));
			outStream.writeUTF(this.header);
			while (true) {
				try {
					Measurement m = ReadMeasurement();
					String textToWrite = this.sinkMeasurementMeasurementToStringCallback.transform(m);
					System.out.println(textToWrite);
					outStream.writeUTF(textToWrite);
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