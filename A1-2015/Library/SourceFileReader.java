import java.io.*;

public class SourceFileReader extends FilterFramework {

	final String filePath;

	public SourceFileReader(String filePath) {
		this.filePath = filePath;
	}

	public void run() {

		int bytesRead = 0;
		int bytesWritten = 0;
		DataInputStream inputStream = null;

		try {
			inputStream = new DataInputStream(new FileInputStream(this.filePath));
			System.out.println("\n" + this.getName() + "::Source reading file...");

			while(true) {
				byte readByte = inputStream.readByte();
				++bytesRead;
				this.WriteFilterOutputPort(readByte);
				++bytesWritten;
			}
		} catch (EOFException e) {
			System.out.println("\n" + this.getName() + "::End of file reached...");

			try {
				inputStream.close();
				this.ClosePorts();
				System.out.println("\n" + this.getName() + "::Read file complete, bytes read::" + bytesRead + " bytes written: " + bytesWritten);
			} catch (Exception e2) {
				System.out.println("\n" + this.getName() + "::Problem closing input data file::" + e2);
			}
		} catch (IOException e3) {
			System.out.println("\n" + this.getName() + "::Problem reading input data file::" + e3);
		}
	}
}
