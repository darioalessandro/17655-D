/******************************************************************************************************************
* File:HumiditySensor.java
* Course: 17655
* Project: Assignment A3
* Copyright: Copyright (c) 2009 Carnegie Mellon University
* Versions:
*	1.0 March 2009 - Initial rewrite of original assignment 3 (ajl).
*
* Description:
*
* This class simulates a humidity sensor. It polls the message manager for messages corresponding to changes in state
* of the humidifier or dehumidifier and reacts to them by trending the relative humidity up or down. The current
* relative humidity is posted to the message manager.
*
* Parameters: IP address of the message manager (on command line). If blank, it is assumed that the message manager is
* on the local machine.
*
* Internal Methods:
*	float GetRandomNumber()
*	boolean CoinToss()
*   void PostHumidity(MessageManagerInterface ei, float humidity )
*
******************************************************************************************************************/
import InstrumentationPackage.*;
import MessagePackage.*;
import java.util.*;

class HumiditySensor
{

	public static void main(String args[])
	{
		String MsgMgrIP;					// Message Manager IP address
		Message Msg = null;					// Message object
		MessageQueue eq = null;				// Message Queue
		int MsgId = 0;						// User specified message ID
		MessageManagerInterface em = null;	// Interface object to the message manager
		boolean HumidifierState = false;	// Humidifier state: false == off, true == on
		boolean DehumidifierState = false;	// Dehumidifier state: false == off, true == on
		float RelativeHumidity;				// Current simulated ambient room humidity
		float DriftValue;					// The amount of humidity gained or lost
		int	Delay = 2500;					// The loop delay (2.5 seconds)
		boolean Done = false;				// Loop termination flag



		/////////////////////////////////////////////////////////////////////////////////
		// Get the IP address of the message manager
		/////////////////////////////////////////////////////////////////////////////////

 		if ( args.length == 0 )
 		{
			// message manager is on the local system

			System.out.println("\n\nAttempting to register on the local machine..." );

			try
			{
				// Here we create an message manager interface object. This assumes
				// that the message manager is on the local machine

				em = new MessageManagerInterface();
			}

			catch (Exception e)
			{
				System.out.println("Error instantiating message manager interface: " + e);

			} // catch

		} else {

			// message manager is not on the local system

			MsgMgrIP = args[0];

			System.out.println("\n\nAttempting to register on the machine:: " + MsgMgrIP );

			try
			{
				// Here we create an message manager interface object. This assumes
				// that the message manager is NOT on the local machine

				em = new MessageManagerInterface( MsgMgrIP );
			}

			catch (Exception e)
			{
				System.out.println("Error instantiating message manager interface: " + e);

			} // catch

		} // if

		// Here we check to see if registration worked. If ef is null then the
		// message manager interface was not properly created.

		if (em != null)
		{

			// We create a message window. Note that we place this panel about 1/2 across
			// and 2/3s down the screen

			float WinPosX = 0.5f; 	//This is the X position of the message window in terms
									//of a percentage of the screen height
			float WinPosY = 0.60f;	//This is the Y position of the message window in terms
								 	//of a percentage of the screen height

			MessageWindow mw = new MessageWindow("Humidity Sensor", WinPosX, WinPosY);

			mw.WriteMessage("Registered with the message manager." );

	    	try
	    	{
				mw.WriteMessage("   Participant id: " + em.GetMyId() );
				mw.WriteMessage("   Registration Time: " + em.GetRegistrationTime() );

			} // try

	    	catch (Exception e)
			{
				mw.WriteMessage("Error:: " + e);

			} // catch

			mw.WriteMessage("\nInitializing Humidity Simulation::" );

			RelativeHumidity = GetRandomNumber() * (float) 100.00;

			if ( CoinToss() )
			{
				DriftValue = GetRandomNumber() * (float) -1.0;

			} else {

				DriftValue = GetRandomNumber();

			} // if

			mw.WriteMessage("   Initial Humidity Set:: " + RelativeHumidity );
			mw.WriteMessage("   Drift Value Set:: " + DriftValue );

			/********************************************************************
			** Here we start the main simulation loop
			*********************************************************************/

			mw.WriteMessage("Beginning Simulation... ");


			while ( !Done )
			{
				// Post the current relative humidity

				PostHumidity( em, RelativeHumidity );

				mw.WriteMessage("Current Relative Humidity:: " + RelativeHumidity + "%");

				// Get the message queue

				try
				{
					eq = em.GetMessageQueue();

				} // try

				catch( Exception e )
				{
					mw.WriteMessage("Error getting message queue::" + e );

				} // catch

				// If there are messages in the queue, we read through them.
				// We are looking for MessageIDs = -4, this means the the humidify or
				// dehumidifier has been turned on/off. Note that we get all the messages
				// from the queue at once... there is a 2.5 second delay between samples,..
				// so the assumption is that there should only be a message at most.
				// If there are more, it is the last message that will effect the
				// output of the humidity as it would in reality.

				int qlen = eq.GetSize();

				for ( int i = 0; i < qlen; i++ )
				{
					Msg = eq.GetMessage();

					if ( Msg.GetMessageId() == -4 )
					{
						if (Msg.GetMessage().equalsIgnoreCase("H1")) // humidifier on
						{
							HumidifierState = true;

						} // if

						if (Msg.GetMessage().equalsIgnoreCase("H0")) // humidifier off
						{
							HumidifierState = false;

						} // if

						if (Msg.GetMessage().equalsIgnoreCase("D1")) // dehumidifier on
						{
							DehumidifierState = true;

						} // if

						if (Msg.GetMessage().equalsIgnoreCase("D0")) // dehumidifier off
						{
							DehumidifierState = false;

						} // if

					} // if

					// If the message ID == 99 then this is a signal that the simulation
					// is to end. At this point, the loop termination flag is set to
					// true and this process unregisters from the message manager.

					if ( Msg.GetMessageId() == 99 )
					{
						Done = true;

						try
						{
							em.UnRegister();

				    	} // try

				    	catch (Exception e)
				    	{
							mw.WriteMessage("Error unregistering: " + e);

				    	} // catch

				    	mw.WriteMessage("\n\nSimulation Stopped. \n");

					} // if

				} // for

				// Now we trend the relative humidity according to the status of the
				// humidifier/dehumidifier controller.

				if (HumidifierState)
				{
					RelativeHumidity += GetRandomNumber();

				} // if humidifier is on

				if (!HumidifierState && !DehumidifierState)
				{
					RelativeHumidity += DriftValue;

				} // if both the humidifier and dehumidifier are off

				if (DehumidifierState)
				{
					RelativeHumidity -= GetRandomNumber();

				} // if dehumidifier is on

				// Here we wait for a 2.5 seconds before we start the next sample

				try
				{
					Thread.sleep( Delay );

				} // try

				catch( Exception e )
				{
					mw.WriteMessage("Sleep error:: " + e );

				} // catch

			} // while

		} else {

			System.out.println("Unable to register with the message manager.\n\n" );

		} // if

	} // main

	/***************************************************************************
	* CONCRETE METHOD:: GetRandomNumber
	* Purpose: This method provides the simulation with random floating point
	*		   humidity values between 0.1 and 0.9.
	*
	* Arguments: None.
	*
	* Returns: float
	*
	* Exceptions: None
	*
	***************************************************************************/

	static private float GetRandomNumber()
	{
		Random r = new Random();
		Float Val;

		Val = Float.valueOf((float)-1.0);

		while( Val < 0.1 )
		{
			Val = r.nextFloat();
	 	}

		return( Val.floatValue() );

	} // GetRandomNumber

	/***************************************************************************
	* CONCRETE METHOD:: CoinToss
	* Purpose: This method provides a random true or false value used for
	* determining the positiveness or negativeness of the drift value.
	*
	* Arguments: None.
	*
	* Returns: boolean
	*
	* Exceptions: None
	*
	***************************************************************************/

	static private boolean CoinToss()
	{
		Random r = new Random();

		return(r.nextBoolean());

	} // CoinToss

	/***************************************************************************
	* CONCRETE METHOD:: PostHumidity
	* Purpose: This method posts the specified relative humidity value to the
	* specified message manager. This method assumes an message ID of 2.
	*
	* Arguments: MessageManagerInterface ei - this is the messagemanger interface
	*			 where the message will be posted.
	*
	*			 float humidity - this is the humidity value.
	*
	* Returns: none
	*
	* Exceptions: None
	*
	***************************************************************************/

	static private void PostHumidity(MessageManagerInterface ei, float humidity )
	{
		// Here we create the message.

		Message msg = new Message( (int) 2, String.valueOf(humidity) );

		// Here we send the message to the message manager.

		try
		{
			ei.SendMessage( msg );
			//mw.WriteMessage( "Sent Humidity Message" );

		} // try

		catch (Exception e)
		{
			System.out.println( "Error Posting Relative Humidity:: " + e );

		} // catch

	} // PostHumidity

} // Humidity Sensor