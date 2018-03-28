/******************************************************************************************************************
* File:MessageQueue.java
* Course: 17655
* Project: Assignment 3
* Copyright: Copyright (c) 2009 Carnegie Mellon University
* Versions:
*	1.0 February 2009 - Initial rewrite of original assignment 3 (ajl).
*
* Description: This class defines message queues which are stored by the MessageManger. Each registered participant
*			   has an message queue assigned to them. As events are sent by registered participants to the MessageManger
*			   they are posted in each queue. Queues are removed when participants unregister.
*
* Parameters:
*			   MessageList - This is the list of message objects
*			   id - this is the participant's registration id
*			   ListSize - this variable indicates how many events are in the message queue.
*
* Internal Methods: None
*
******************************************************************************************************************/
package MessagePackage;

import java.io.Serializable;
import java.util.*;

public class MessageQueue implements Serializable
{
	private Vector<Message> MessageList;// This is the list of events associated with a participant
	private long QueueId;				// This is the participants id
	private	int ListSize;				// This is the size of the list

	public MessageQueue()
	{
		MessageList = new Vector<Message> (15, 1);
		Calendar TimeStamp = Calendar.getInstance();
		QueueId = TimeStamp.getTimeInMillis();
		ListSize = 0;

	} // constructor

	/***************************************************************************
	* CONCRETE METHOD:: GetId
	* Purpose: This method returns the message queue id (which is the participants id).
	*
	* Arguments: None
	*
	* Returns: long integet
	*
	* Exceptions: None
	*
	****************************************************************************/

	public long GetId()
	{
		return QueueId;

	} // AddMessage

	/***************************************************************************
	* CONCRETE METHOD:: GetSize
	* Purpose: This method returns the size of the queue.
	*
	* Arguments: None
	*
	* Returns: int
	*
	* Exceptions: None
	*
	****************************************************************************/

	public int GetSize()
	{
		return MessageList.size();

	} // AddMessage

	/***************************************************************************
	* CONCRETE METHOD:: AddMessage
	* Purpose: This method adds an message to the list arriving messages are
	*		   appended to the end of the list.
	*
	* Arguments: Message from a participant
	*
	* Returns: None
	*
	* Exceptions: None
	*
	****************************************************************************/

	public void AddMessage( Message m )
	{
		MessageList.add( m );

	} // AddMessage

	/***************************************************************************
	* CONCRETE METHOD:: GetMessage
	* Purpose: This method gets the message off of the front of the list. This is
	*		   the oldest message in the list (arriving messages are appended to the
	*		   list, hence the newest message is at the end of the list). This
	*		   method removes messages from the list.
	*
	* Arguments: None
	*
	* Returns: Message
	*
	* Exceptions: None
	*
	****************************************************************************/

	public Message GetMessage()
	{
		Message m = null;

		if (MessageList.size() > 0)
		{
			m = MessageList.get(0);
			MessageList.removeElementAt(0);

		} // if

		return m;

	} // GetMessage

	/***************************************************************************
	* CONCRETE METHOD:: ClearMessageQueue
	* Purpose: This method will clears all the messages the message queue.
	*
	* Arguments: None
	*
	* Returns: None
	*
	* Exceptions: None
	*
	****************************************************************************/

	public void ClearMessageQueue()
	{
		MessageList.removeAllElements();

	} // ClearMessageQueue

	/***************************************************************************
	* CONCRETE METHOD:: GetCopy
	* Purpose: This method is used to obtain a copy of the message queue. This
	*		   method returns a second copy (separate memory) of the queue, not
	*		   a pointer to the queue.
	*
	* Arguments: None
	*
	* Returns: MessageQueue. This method returns a second copy (separate memory)
	*		   of the queue, not a pointer to the queue.
	*
	* Exceptions: None
	*
	****************************************************************************/
   	@SuppressWarnings("unchecked")

	public MessageQueue GetCopy()
	{
		MessageQueue mq = new MessageQueue();
		mq.QueueId = QueueId;
		mq.MessageList = (Vector<Message>) MessageList.clone();

		return mq ;

	} // GetCopy

} // MessageQueue class