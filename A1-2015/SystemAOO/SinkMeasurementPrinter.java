/******************************************************************************************************************
* File:SinkMeasurementPrinter.java
* Course: 17655
* Project: Assignment 1
* Copyright: Copyright (c) 2003 Carnegie Mellon University
* Versions:
*	1.0 November 2008 - Initial rewrite of original assignment 1 (ajl).
*
* Description:
*
* This class serves as a template for creating sink filters. The details of threading, connections writing output
* are contained in the FilterFramework super class. In order to use this template the program should rename the class.
* The template includes the run() method which is executed when the filter is started.
* The run() method is the guts of the filter and is where the programmer should put their filter specific code.
* In the template there is a main read-write loop for reading from the input port of the filter. The programmer is
* responsible for writing the data to a file, or device of some kind. This template assumes that the filter is a sink
* filter that reads data from the input file and writes the output from this filter to a file or device of some kind.
* In this case, only the input port is used by the filter. In cases where the filter is a standard filter or a source
* filter, you should use the BytesToMeasurementsTransformer.java or the SourceFileReader.java as a starting point for creating
* standard or source filters.
*
* Parameters: 		None
*
* Internal Methods:
*
*	public void run() - this method must be overridden by this class.
*
******************************************************************************************************************/
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