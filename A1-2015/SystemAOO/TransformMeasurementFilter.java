import java.io.*;

public class TransformMeasurementFilter extends FilterFramework {

	final TransformMeasurementCallback transformMeasurementCallback;

	TransformMeasurementFilter(TransformMeasurementCallback transformMeasurementCallback) {
		this.transformMeasurementCallback = transformMeasurementCallback;
	}

	public void run() {
		byte databyte;
		System.out.print( "\n" + this.getName() + "::TransformMeasurementFilter ");
		while (true) {
			try {
				Measurement m = ReadMeasurement();
				Measurement transformed = transformMeasurementCallback.transform(m);
				ObjectOutputStream OutputWritePorto = new ObjectOutputStream(this.OutputWritePort);
				OutputWritePorto.writeObject(transformed);
			}
			catch (EndOfStreamException e) {
				ClosePorts();
				break;
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
   }
}
