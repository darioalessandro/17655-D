/******************************************************************************************************************
* File:FilterFrameworkWildPoint.java
* Course: 17655
* Project: Assignment 1
* Copyright: Copyright (c) 2003 Carnegie Mellon University
* Versions:
*	1.0 November 2008 - Initial rewrite of original assignment 1 (ajl).
*
* Description:
*
* This superclass that extends FilterFramework.java and defines a skeletal wildpoint filter framework
* that defines a filter in terms of the input and output ports. All filters must be defined in terms
* of this framework - that is, filters must extend this class in order to be considered valid system
* filters. Filters as standalone threads until the inputport no longer has any data - at which point
* the filter finishes up any work it has to do and then terminates.
*
* Parameters:
*
* InputReadPort:	This is the filter's input port. Essentially this port is connected to another filter's piped
*					output steam. All filters connect to other filters by connecting their input ports to other
*					filter's output ports. This is handled by the Connect() method.
*
* OutputWritePort:	This the filter's output port. Essentially the filter's job is to read data from the input port,
*					perform some operation on the data, then write the transformed data on the output port.
*
* FilterFramework:  This is a reference to the filter that is connected to the instance filter's input port. This
*					reference is to determine when the upstream filter has stopped sending data along the pipe.
*
* Internal Methods:
*
*	public void Connect( FilterFramework Filter )
*	public void WriteFilterOutputPort(byte datum)
*
******************************************************************************************************************/

import java.io.*;

public class FilterFrameworkWildPoint extends FilterFramework
{
	private PipedOutputStream OutputWritePort2 = new PipedOutputStream();

	/***************************************************************************
	* CONCRETE METHOD:: Connect
	* Purpose: This method connects filters to each other. All connections are
	* through the inputport of each filter. That is each filter's inputport is
	* connected to another filter's output port through this method.
	*
	* Arguments:
	* 	FilterFramework - this is the filter that this filter will connect to.
	*
	* Returns: void
	*
	* Exceptions: IOException
	*
	****************************************************************************/

	void Connect( FilterFrameworkWildPoint Filter, int port)
	{
		try
		{
			// Connect this filter's input to the upstream pipe's output stream

			if (port == 1)
			{
			    InputReadPort.connect( Filter.OutputWritePort );
			}
			else
			{
				InputReadPort.connect( Filter.OutputWritePort2 );
			}

			InputFilter = Filter;

		} // try

		catch( Exception Error )
		{
			System.out.println( "\n" + this.getName() + " FilterFramework error connecting::"+ Error );

		} // catch

	} // Connect


	/***************************************************************************
	* CONCRETE METHOD:: WriteFilterOutputPort
	* Purpose: This method writes data to the output port one byte at a time.
	*
	* Arguments:
	* 	byte datum - This is the byte that will be written on the output port.of
	*	the filter.
	*
	* Returns: void
	*
	* Exceptions: IOException
	*
	****************************************************************************/

	void WriteFilterOutputPort(byte datum, int port)
	{
		try
		{
			if (port == 1)
			{
                OutputWritePort.write((int) datum );
		   	    OutputWritePort.flush();
		   	}
		   	else
		   	{
		   		OutputWritePort2.write((int) datum );
		   	    OutputWritePort2.flush();
		   	}

		} // try

		catch( Exception Error )
		{
			System.out.println("\n" + this.getName() + " Pipe write error::" + Error );

		} // catch

		return;

	} // WriteFilterPort



	/***************************************************************************
	* CONCRETE METHOD:: ClosePorts
	* Purpose: This method is used to close the input and output ports of the
	* filter. It is important that filters close their ports before the filter
	* thread exits.
	*
	* Arguments: void
	*
	* Returns: void
	*
	* Exceptions: IOExecption
	*
	****************************************************************************/
	void ClosePorts()
	{
		try
		{
			InputReadPort.close();
			OutputWritePort.close();
			OutputWritePort2.close();

		}
		catch( Exception Error )
		{
			System.out.println( "\n" + this.getName() + " ClosePorts error::" + Error );

		} // catch

	} // ClosePorts
}
