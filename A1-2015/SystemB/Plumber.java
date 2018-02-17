/******************************************************************************************************************
* File:Plumber.java
* Course: 17655
* Project: Assignment 1
* Copyright: Copyright (c) 2003 Carnegie Mellon University
* Versions:
*	1.0 November 2008 - Sample Pipe and Filter code (ajl).
*
* Description:
*
* This class serves as an example to illstrate how to use the PlumberTemplate to create a main thread that
* instantiates and connects a set of filters. This example consists of three filters: a source, a middle filter
* that acts as a pass-through filter (it does nothing to the data), and a sink filter which illustrates all kinds
* of useful things that you can do with the input stream of data.
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

		SourceFilter source = new SourceFilter();
		FahrenheitToCelsiusFilter fahrenheitToCelsius = new FahrenheitToCelsiusFilter();
	    FeetToMetersFilter feetToMeters = new FeetToMetersFilter();
		PressureFilter pressure = new PressureFilter();
		SinkFilter sink = new SinkFilter();

		/****************************************************************************
		* Here we connect the filters starting with the sink filter (Filter 1) which
		* we connect to pressure the middle filter. Then we connect pressure to the
		* source filter (sink).
		****************************************************************************/

		sink.Connect(pressure); // This esstially says, "connect sink input port to pressure output port
		pressure.Connect(feetToMeters); // This esstially says, "connect pressure intput port to "source" output port
		feetToMeters.Connect(fahrenheitToCelsius); // This esstially says, "connect pressure intput port to "source" output port
		fahrenheitToCelsius.Connect(source);

		/****************************************************************************
		* Here we start the filters up. All-in-all,... its really kind of boring.
		****************************************************************************/

		source.start();
		fahrenheitToCelsius.start();
		feetToMeters.start();
		pressure.start();
		sink.start();

   } // main

} // Plumber