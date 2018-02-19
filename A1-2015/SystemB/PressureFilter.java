/******************************************************************************************************************
* File:Pressure.java
* Course: 17655
* Project: Assignment 1
* Copyright: Copyright (c) 2003 Carnegie Mellon University
* Versions:
*	1.0 November 2008 - Sample Pipe and Filter code (ajl).
*
* Description:
*
* This class serves as an example for how to use the FilterRemplate to create a standard filter. This particular
* example is a simple "pass-through" filter that reads data from the filter's input port and writes data out the
* filter's output port.
*
* Parameters: 		None
*
* Internal Methods: None
*
******************************************************************************************************************/
import java.util.*;						// This class is used to interpret time words
import java.text.SimpleDateFormat;		// This class is used to format and write time in a string format.

public class PressureFilter extends FilterFrameworkWildPoint
{

	public void run()
    {

    	Calendar TimeStamp = Calendar.getInstance();
		SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");

		int MeasurementLength = 8;		// This is the length of all measurements (including time) in bytes
		int IdLength = 4;				// This is the length of IDs in the byte stream

		Map< String, Double > map = new HashMap< String, Double >();       // To save previous, current, and next pressure measurements in order to
			 									                            // filter “wild points” out of the data stream

		int bytesread = 0;				// Number of bytes read from the input file.
		int byteswritten = 0;			// Number of bytes written to the stream.
		byte databyte = 0;				// The byte of data read from the file
		double midvalue;					// This is the word used to store all converted measurements.

		byte[] datastreams = new byte[100];
	    int streamindex = 0;

		int id;							// This is the measurement id
		int i;							// This is a loop counter
		long measurement;				// This is the word used to store all measurements - conversions are illustrated.

		Double prevvalue;               // previous measurement
		Double currentvalue;			// current measurement
		Double nextvalue;				// next measurement
		Double pressurecomp;			// prevvalue - currentvalue

//* A wild point is any pressure data that varies more than 10PSI between samples and/or is negative.
		boolean wildpoint = false; //This value is set to True if a measurement needs to be sent to WildPoints.dat
		double midvaluewildpoint = 0.0; // This is the word used to store all converted measurements that are wildPoints
		long wildpointmeasurement; //This is the word used to store all wildpoint measurmenets.
		byte[] wildpointstreams = new byte[100];
		int wildpointstreams_length = 0;

		// Next we write a message to the terminal to let the world know we are alive...

		System.out.print( "\n" + this.getName() + "::Pressure Reading ");

		while (true)
		{
			wildpoint = false;
			streamindex = 0;
			wildpointstreams_length = 0;
			/*************************************************************
			*	Here we read a byte and write a byte
			*************************************************************/

			try
			{
				//readInput();
				id = 0;

				for (i=0; i<IdLength; i++ )
				{
					databyte = ReadFilterInputPort();	// This is where we read the byte from the stream...

					id = id | (databyte & 0xFF);		// We append the byte on to ID...

					if (i != IdLength-1)				// If this is not the last byte, then slide the
					{									// previously appended byte to the left by one byte
						id = id << 8;					// to make room for the next byte we append to the ID

					} // if

					bytesread++;						// Increment the byte count

					//WriteFilterOutputPort(databyte, 1);
					//byteswritten++;
					datastreams[streamindex++] = databyte;

				} // for

				if ( id == 3 )
				{
					measurement = 0;

					for (i=0; i<MeasurementLength; i++ )
					{
						databyte = ReadFilterInputPort();
						measurement = measurement | (databyte & 0xFF);	// We append the byte on to measurement...

						if (i != MeasurementLength-1)					// If this is not the last byte, then slide the
						{												// previously appended byte to the left by one byte
							measurement = measurement << 8;				// to make room for the next byte we append to the
																		// measurement
						} // if

						bytesread++;									// Increment the byte count

					} // if

					midvalue = Double.longBitsToDouble(measurement);

					if ( map.containsKey( "current" ) )
					{
						/* get the previous value (if there is one) */
						prevvalue = map.get( "current" );

						/* check if the current value is a éwild point */
						pressurecomp = midvalue - prevvalue;
						if(pressurecomp > 10.0 || midvalue < 0.0 || pressurecomp < 0.0)
						{
							wildpoint = true;
							midvaluewildpoint = midvalue;


                            System.arraycopy( datastreams, 0, wildpointstreams, 0, datastreams.length );    // copy id data
                            wildpointstreams_length = streamindex;

							midvalue = prevvalue;
            }
            map.put( "prev", prevvalue );           // may be we can remove the map variable ??
					}

					map.put( "current", midvalue );
					midvalue = Math.round (midvalue * 100000.0) / 100000.0;   //formatting double to show only 5 decimal places

					measurement = Double.doubleToLongBits(midvalue);
					for(i = 0; i < MeasurementLength; i++) {
						databyte = (byte) ((measurement >> ((7 - i) * 8)) & 0xFF);
						//WriteFilterOutputPort(databyte, 1);
						//byteswritten++;
						datastreams[streamindex++] = databyte;
					}

					if(wildpoint)
					{
						midvaluewildpoint = Math.round (midvaluewildpoint * 100000.0) / 100000.0;   //formatting double to show only 5 decimal places
						wildpointmeasurement = Double.doubleToLongBits(midvaluewildpoint);
						//wildpointstreams_length = streamindex;
					    for(i = 0; i < MeasurementLength; i++) {
							databyte = (byte) ((wildpointmeasurement >> ((7 - i) * 8)) & 0xFF);
							wildpointstreams[wildpointstreams_length++] = databyte;
						}
					}
				}
				else
				{
					databyte = ReadFilterInputPort();
					bytesread++;
					//WriteFilterOutputPort(databyte, 1);
					//byteswritten++;
					datastreams[streamindex++] = databyte;

					if(wildpoint)
					{
						wildpointstreams[wildpointstreams_length++] = databyte;
					}
				}

				for (int j = 0; j < streamindex; j++)
				{
					WriteFilterOutputPort(datastreams[j], 1);
					byteswritten++;
				}

				if(wildpoint)
				{
					for (int j = 0; j < wildpointstreams.length; j++)
					{
						WriteFilterOutputPort(wildpointstreams[j], 2);
					    byteswritten++;
					}
				}

			} // try

			catch (EndOfStreamException e)
			{
				ClosePorts();
				System.out.print( "\n" + this.getName() + "::Pressure Exiting; bytes read: " + bytesread + " bytes written: " + byteswritten );
				break;

			} // catch

		} // while

   } // run

} // MiddleFilter
