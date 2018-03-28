/******************************************************************************************************************
* File:ECSConsole.java
* Course: 17655
* Project: Assignment 3
* Copyright: Copyright (c) 2009 Carnegie Mellon University
* Versions:
*	1.0 February 2009 - Initial rewrite of original assignment 3 (ajl).
*
* Description: This class is the console for the museum environmental control system. This process consists of two
* threads. The ECSMonitor object is a thread that is started that is responsible for the monitoring and control of
* the museum environmental systems. The main thread provides a text interface for the user to change the temperature
* and humidity ranges, as well as shut down the system.
*
* Parameters: None
*
* Internal Methods: None
*
******************************************************************************************************************/
import TermioPackage.*;
import MessagePackage.*;

public class ECSConsole
{
	public static void main(String args[])
	{
    	Termio UserInput = new Termio();	// Termio IO Object
		boolean Done = false;				// Main loop flag
		String Option = null;				// Menu choice from user
		Message Msg = null;					// Message object
		boolean Error = false;				// Error flag
		ECSMonitor Monitor = null;			// The environmental control system monitor
		float TempRangeHigh = (float)100.0;	// These parameters signify the temperature and humidity ranges in terms
		float TempRangeLow = (float)0.0;	// of high value and low values. The ECSmonitor will attempt to maintain
		float HumiRangeHigh = (float)100.0;	// this temperature and humidity. Temperatures are in degrees Fahrenheit
		float HumiRangeLow = (float)0.0;	// and humidity is in relative humidity percentage.

		/////////////////////////////////////////////////////////////////////////////////
		// Get the IP address of the message manager
		/////////////////////////////////////////////////////////////////////////////////

 		if ( args.length != 0 )
 		{
			// message manager is not on the local system

			Monitor = new ECSMonitor( args[0] );

		} else {

			Monitor = new ECSMonitor();

		} // if


		// Here we check to see if registration worked. If ef is null then the
		// message manager interface was not properly created.

		if (Monitor.IsRegistered() )
		{
			Monitor.start(); // Here we start the monitoring and control thread

			while (!Done)
			{
				// Here, the main thread continues and provides the main menu

				System.out.println( "\n\n\n\n" );
				System.out.println( "Environmental Control System (ECS) Command Console: \n" );

				if (args.length != 0)
					System.out.println( "Using message manger at: " + args[0] + "\n" );
				else
					System.out.println( "Using local message manger \n" );

				System.out.println( "Set Temperature Range: " + TempRangeLow + "F - " + TempRangeHigh + "F" );
				System.out.println( "Set Humidity Range: " + HumiRangeLow + "% - " + HumiRangeHigh + "%\n" );
				System.out.println( "Select an Option: \n" );
				System.out.println( "1: Set temperature ranges" );
				System.out.println( "2: Set humidity ranges" );
				System.out.println( "X: Stop System\n" );
				System.out.print( "\n>>>> " );
				Option = UserInput.KeyboardReadString();

				//////////// option 1 ////////////

				if ( Option.equals( "1" ) )
				{
					// Here we get the temperature ranges

					Error = true;

					while (Error)
					{
						// Here we get the low temperature range

						while (Error)
						{
							System.out.print( "\nEnter the low temperature>>> " );
							Option = UserInput.KeyboardReadString();

							if (UserInput.IsNumber(Option))
							{
								Error = false;
								TempRangeLow = Float.valueOf(Option).floatValue();

							} else {

								System.out.println( "Not a number, please try again..." );

							} // if

						} // while

						Error = true;

						// Here we get the high temperature range

						while (Error)
						{
							System.out.print( "\nEnter the high temperature>>> " );
							Option = UserInput.KeyboardReadString();

							if (UserInput.IsNumber(Option))
							{
								Error = false;
								TempRangeHigh = Float.valueOf(Option).floatValue();

							} else {

								System.out.println( "Not a number, please try again..." );

							} // if

						} // while

						if ( TempRangeLow >= TempRangeHigh )
						{
							System.out.println( "\nThe low temperature range must be less than the high temperature range..." );
							System.out.println( "Please try again...\n" );
							Error = true;

						} else {

							Monitor.SetTemperatureRange(TempRangeLow, TempRangeHigh );

						} // if

					} // while

				} // if

				//////////// option 2 ////////////

				if ( Option.equals( "2" ) )
				{
					// Here we get the humidity ranges

					Error = true;

					while (Error)
					{
						// Here we get the low humidity range

						while (Error)
						{
							System.out.print( "\nEnter the low humidity>>> " );
							Option = UserInput.KeyboardReadString();

							if (UserInput.IsNumber(Option))
							{
								Error = false;
								HumiRangeLow = Float.valueOf(Option).floatValue();

							} else {

								System.out.println( "Not a number, please try again..." );

							} // if

						} // while

						Error = true;

						// Here we get the high humidity range

						while (Error)
						{
							System.out.print( "\nEnter the high humidity>>>  " );
							Option = UserInput.KeyboardReadString();

							if (UserInput.IsNumber(Option))
							{
								Error = false;
								HumiRangeHigh = Float.valueOf(Option).floatValue();

							} else {

								System.out.println( "Not a number, please try again..." );

							} // if

						} // while

						if ( HumiRangeLow >= HumiRangeHigh )
						{
							System.out.println( "\nThe low humidity range must be less than the high humidity range..." );
							System.out.println( "Please try again...\n" );
							Error = true;

						} else {

							Monitor.SetHumidityRange(HumiRangeLow, HumiRangeHigh );

						} // if

					} // while

				} // if

				//////////// option X ////////////

				if ( Option.equalsIgnoreCase( "X" ) )
				{
					// Here the user is done, so we set the Done flag and halt
					// the environmental control system. The monitor provides a method
					// to do this. Its important to have processes release their queues
					// with the message manager. If these queues are not released these
					// become dead queues and they collect messages and will eventually
					// cause problems for the message manager.

					Monitor.Halt();
					Done = true;
					System.out.println( "\nConsole Stopped... Exit monitor mindow to return to command prompt." );
					Monitor.Halt();

				} // if

			} // while

		} else {

			System.out.println("\n\nUnable start the monitor.\n\n" );

		} // if

  	} // main

} // ECSConsole
