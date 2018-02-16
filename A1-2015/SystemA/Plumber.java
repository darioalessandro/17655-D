/******************************************************************************************************************
* File:Plumber.java
* Course: 17655
* Project: Assignment 1
* Copyright: Copyright (c) 2003 Carnegie Mellon University
* Versions:
*	1.0 November 2008 - Sample Pipe and Filter code (ajl).
*   1.1 February 2017 - System A implementation.
*
* Description:
* System A implementation.
*
* Parameters: 		None
*
* Internal Methods:	None
*
******************************************************************************************************************/
public class Plumber
{
   public static void main( String argv[])
   {
		/****************************************************************************
		* Here we instantiate three filters.
		****************************************************************************/

		SourceFilter fileReaderSource = new SourceFilter();
	    FahrenheitToCelsiusFilter fahrenheitToCelsius = new FahrenheitToCelsiusFilter();
	    FeetToMetersFilter feetToMeters = new FeetToMetersFilter();
		SinkFilter sink = new SinkFilter();

		/****************************************************************************
		* Here we connect the filters starting with the sink filter (sink) which
		* we connect to feetToMeters the fahrenheitToCelsius filter. Then we connect fahrenheitToCelsius to the
		* source filter (fileReaderSource).
		****************************************************************************/

	    sink.Connect(feetToMeters);
	    feetToMeters.Connect(fahrenheitToCelsius); // This esstially says, "connect Filter3 input port to Filter2 output port
	    fahrenheitToCelsius.Connect(fileReaderSource); // This esstially says, "connect Filter2 intput port to Filter1 output port

		/****************************************************************************
		* Here we start the filters up. All-in-all,... its really kind of boring.
		****************************************************************************/

	    fileReaderSource.start();
	    fahrenheitToCelsius.start();
		feetToMeters.start();
	    sink.start();

   } // main

} // Plumber