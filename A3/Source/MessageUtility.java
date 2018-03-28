/******************************************************************************************************************
* File:MessageUtility.java
* Course: 17655
* Project: Assignment 3
* Copyright: Copyright (c) 2009 Carnegie Mellon University
* Versions:
*	1.0 February 2009 - Initial rewrite of original assignment 3 (ajl).
*
* Description: This class illustrates how to utilize the message manager and provides a few
* basic utilities that can help developers test and debug their systems.
*
* Parameters: None
*
* Internal Methods: None
*
******************************************************************************************************************/
import TermioPackage.*;
import MessagePackage.*;

public class MessageUtility
{
	public static void main(String args[])
	{
    	Termio UserInput = new Termio();// Termio IO Object
		String MsgMgrIP;				// Message Manager IP address
		String MsgMgrPort;				// Message Manager port
		boolean Done = false;			// Main loop flag
		String Option;					// Menu choice from user
		Message Msg = null;				// Message object
		boolean Error;					// Error flag
		MessageQueue eq = null;			// Message Queue
		int MsgId = 0;					// User specified message ID
		MessageManagerInterface ef = null;// Interface object to the message manager

		/////////////////////////////////////////////////////////////////////////////////
		// Get the IP address of the message manager
		/////////////////////////////////////////////////////////////////////////////////

 		System.out.println( "\n\n\n\n" );

  		System.out.println( "Enter IP address of message manager or..." );
  		System.out.print( "press enter if on local machine: " );
		MsgMgrIP = UserInput.KeyboardReadString();

		System.out.println( "\n\n\n\n" );

		System.out.print("\n\nAttempting to register..." );

		if (MsgMgrIP.length() == 0)
		{
				try
				{
					// Here we create a message manager interface object. This assumes
					// that the message manager is on the local machine

					ef = new MessageManagerInterface();
				}

				catch (Exception e)
				{
					System.out.println("Error instantiating message manager interface: " + e);

				} // catch

		} else {

			if (MsgMgrIP.length() != 0)
			{
				try
				{
					// Here we create a message manager interface object. This assumes
					// that the message manager is NOT on the local machine

					ef = new MessageManagerInterface( MsgMgrIP );
				}

				catch (Exception e)
				{
					System.out.println("Error instantiating message manager interface: " + e);

				} // catch

			}// if

		}// if

		// Here we check to see if registration worked. If ef is null then the
		// message manager interface was not properly created.

		if (ef != null )
		{
			System.out.println("Registered with the message manager.\n\n" );

			while (!Done)
			{
				// Here is the main menu

				System.out.println( "Select an Option: \n" );
				System.out.println( "1: What is my ID?" );
				System.out.println( "2: What was my registration time? " );
				System.out.println( "3: Send Message Message." );
				System.out.println( "4: Get Message List." );
				System.out.println( "X: Exit \n" );
				System.out.print( "\n>>>> " );
				Option = UserInput.KeyboardReadString();

				//////////// option 1 ////////////

				// Here we print out the participants ID number. This is established when
				// the message interface is instantiated. If the participant is not
				// registered an exception is thrown.

				if ( Option.equals( "1" ) )
				{
			    	try
			    	{
						long MyParticipantID = ef.GetMyId();
						System.out.println( "\nMy participant id = " + MyParticipantID );

			    	} // try

			    	catch (Exception e)
			    	{
						System.out.println("Error:: " + e);

			    	} // catch

				}

				//////////// option 2 ////////////

				// Here we print out the participants registrations time. This is actual time
				// that the message interface was instantiated. If the participant is not
				// registered an exception is thrown.

				if ( Option.equals( "2" ) )
				{
			    	try
			    	{
						String MyRegistrationTime = ef.GetRegistrationTime();
						System.out.println( "\nMy participant id = " + MyRegistrationTime );

			    	} // try

			    	catch (Exception e)
			    	{
						System.out.println("Error:: " + e);

			    	} // catch

				}

				//////////// option 3 ////////////

				if ( Option.equals( "3" ) )
				{
					// Here we get an message ID from the user,... it has to be an integer,
					// so the input is verified. If the input is not an integer, we chastise
					// the user and ask them to try again...

					Error = true;

					while (Error)
					{
						System.out.println( "\nEnter an integer message ID: " );
						Option = UserInput.KeyboardReadString();

						if (UserInput.IsNumber(Option))
						{
							Error = false;
							MsgId = Integer.valueOf(Option).intValue();

						} else {

						System.out.println( "Please enter an integer value... try again..." );

						} // if

					} // while

					// Here ask the user to provide a string message to post with the
					// if message.

					System.out.println( "\nEnter a string to post with the message, or enter to continue: " );
					Option = UserInput.KeyboardReadString();

					// Here we create the message.

					Msg = new Message( MsgId, Option );

					// Here we send the message to the message manager.

			    	try
			    	{
						ef.SendMessage( Msg );
						System.out.println( "Message posted." );

			    	} // try

			    	catch (Exception e)
			    	{
						System.out.println("Error:: " + e);

			    	} // catch

				} // if

				//////////// option 4 ////////////

				if ( Option.equals( "4" ) )
				{
					// Here we get the message queue for this message interface from the message manager.

					Error = true;

			    	try
			    	{
						eq = ef.GetMessageQueue();

						System.out.println( eq.GetSize() + " messages received..." );
						System.out.println( "=========================" );

						int qlen = eq.GetSize();

						for ( int i = 0; i < qlen; i++ )
						{
							Msg = eq.GetMessage();
							System.out.print( (i+1) + "::Sender ID: " + Msg.GetSenderId());
							System.out.print( ":: Message ID:: " + Msg.GetMessageId());
							System.out.println("::" + Msg.GetMessage());

						} // for

						System.out.println("\n");

			    	} // try

			    	catch (Exception e)
			    	{
						System.out.println("Error getting message list: " + e);

			    	} // catch

				} // if

				//////////// option X ////////////

				if ( Option.equalsIgnoreCase( "X" ) )
				{
					// Here the user is done, so we set the Done flag and unregister
					// the message interface from the message manager. If you fail to
					// unregister, the message manager doesn't know to remove queues.
					// These become dead queues and they collect messages and will messageually
					// cause problems for the message manager.

					Done = true;

					try
					{
						ef.UnRegister();

			    	} // try

			    	catch (Exception e)
			    	{
						System.out.println("Error unregistering: " + e);

			    	} // catch

				} // if

			} // while

		} else {

			System.out.println("Unable to register with the message manager.\n\n" );

		} // if

  	} // main

} // MessageTest
