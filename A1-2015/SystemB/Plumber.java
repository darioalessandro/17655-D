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
		* Here we instantiate six filters.
		****************************************************************************/

		SourceFilter source = new SourceFilter();
		FahrenheitToCelsiusFilter fahrenheitToCelsius = new FahrenheitToCelsiusFilter();
	  FeetToMetersFilter feetToMeters = new FeetToMetersFilter();
		PressureFilter pressure = new PressureFilter();
		SinkFilter sink = new SinkFilter();
		SinkFilterWildPoint sinkwildpoint = new SinkFilterWildPoint();


		/****************************************************************************
		* Here we connect the filters starting with the source filter (source) which
		* we connect to the Fahrenheit To Celsius Filter (fahrenheitToCelsius) to
    * modify the tempature. Then we connect Fahrenheit To Celsius Filter
    * (fahrenheitToCelsius) to the Feet To Meters Filter (feetToMeters)
    * to modify the altitude. Then we connect the Feet To Meters Filter (feetToMeters)
    * to the Pressure Filter (pressure) to determine if the pressure data is valid or
    * if it is a wildpoint (varies more than 10PSI between samples and/or is negative).
    * Then pressure is connect to the Sink Wildpoint Filter (sinkwildpoint).
    * Then pressure is also connected to the Sink Filter (sink).
		****************************************************************************/


		sink.Connect(pressure, 1); // This esstially says, "connect the sink filter input port to the pressure filter output port"
		sinkwildpoint.Connect(pressure, 2);  //This essentially says, "connect the wildpoint sink filter input port to the pressure filter output port"
		pressure.Connect(feetToMeters); // This esstially says, "connect pressure filter to Feet input port to Meters Filter output port"
		feetToMeters.Connect(fahrenheitToCelsius); // This esstially says, "connect Feet to Meters Filter input port to the Fahrenheit to Celsius Filter output port"
		fahrenheitToCelsius.Connect(source); //This essentially says, "Connect Fahrenheit To Celsius Filter input port to the Source Filter output port"

		/****************************************************************************
		* Here we start the filters up. All-in-all,... its really kind of boring.
		****************************************************************************/

		source.start();
		fahrenheitToCelsius.start();
		feetToMeters.start();
		pressure.start();
		sink.start();
		sinkwildpoint.start();

   } // main

} // Plumber
