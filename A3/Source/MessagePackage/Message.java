/******************************************************************************************************************
* File:Message.java
* Course: 17655
* Project: Assignment 3
* Copyright: Copyright (c) 2009 Carnegie Mellon University
* Versions:
*	1.0 February 2009 - Initial rewrite of original assignment 3 (ajl).
*
* Description:	This class defines messages. Messages include the sender's ID, the message ID, and
*				a message field for sending small messages between entities participating in
*				the mesage system.
*
* Parameters:
*				MessageId - Integer that indicates an id correlating to an message type. The message framework does not
*						  enforce an ID semantic on the system.
*
*				SenderId - Long integer id that indicates who posted the message to the message manager. The sender
*						   id is issued when an entity registers with the message manager. No messages can be posted
*						   by an entity until it is registered with the message manager. The participant's id is
*						   determined by the message manager at registration time and the id for every message is set
*						   to the participant's id before the message is posted.
*
*				MessageText - This is a string of text that is passed along with the message. Again, there is no
*							  particular semantic associated with the text.
*
* Internal Methods: None
*
******************************************************************************************************************/
package MessagePackage;

import java.io.Serializable;

public class Message implements Serializable
{

	private String MessageText;	// Any string message.
	private int MessageId;		// Message Id is defined by the participant.
	private long SenderId;		// Id assigned at registration time by the message manager. The ID for every message is
								// set by the MessageManagerInterface before the message is sent to the message manager.

	public Message(int MsgId, String Text )
	{
		MessageText = Text;
		MessageId = MsgId;

	} // constructor

	public Message(int MsgId )
	{
		MessageText = null;
		MessageId = MsgId;

	} // constructor

	/***************************************************************************
	* CONCRETE METHOD:: GetSenderID
	* Purpose: This method returns the ID of the participant that posted this
	*		   Message.
	*
	* Arguments: None
	*
	* Returns: long integer
	*
	* Exceptions: None
	*
	****************************************************************************/

	public long GetSenderId()
	{
		return SenderId;

	} // GetSenderId

	/***************************************************************************
	* CONCRETE METHOD:: SetSenderID
	* Purpose: This method sets the ID of message to the long value.
	*
	* Arguments: long integer
	*
	* Returns: None
	*
	* Exceptions: None
	*
	****************************************************************************/

	public void SetSenderId( long id )
	{
		SenderId = id;

	} // GetSenderId

	/***************************************************************************
	* CONCRETE METHOD:: SetMessageID
	* Purpose: This method returns the message ID of the posted message. There is not
	*		   semantic imposed on IDs.
	*
	* Arguments: None
	*
	* Returns: int
	*
	* Exceptions: None
	*
	****************************************************************************/

	public int GetMessageId()
	{
		return MessageId;

	} // GetMessageId

	/***************************************************************************
	* CONCRETE METHOD:: GetMessage
	* Purpose: This method returns the message (if there is one) of the posted message. There is not
	*		   semantic imposed on IDs.
	*
	* Arguments: None
	*
	* Returns: int
	*
	* Exceptions: None
	*
	****************************************************************************/

	public String GetMessage()
	{
		return MessageText;

	} // GetMessage

} // Message class