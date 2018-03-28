/******************************************************************************************************************
* File:MessageWindow.java
* Course: 17655
* Project: Assignment 3
* Copyright: Copyright (c) 2009 Carnegie Mellon University
* Versions:
*	1.0 March 2009 - Initial rewrite of original assignment 3 (ajl).
*
* Description:
*
* This class is used to create a message window in for the A3 assignment. Messages posted are displayed with a
* timestamp and followed by a newline character. The message window will also auto scroll as messages fill
* the window.
*
* Parameters: SEE THE CONSTRUCTOR BELOW
*
* Internal Methods:
*
*	public int GetX
*	public int GetY
*	public int Height()
*	public int Width()
*	public int TermHeight()
*	public int TermWidth()
*	public void WriteMessage( String message )
*
******************************************************************************************************************/
package InstrumentationPackage;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;

public class MessageWindow
{
	private int WindowWidth; 		// Width of the window in pixels
	private int WindowHeight;		// Height of the window in pixels
	private int ScreenWidth;		// Width of the screen in pixels
	private int ScreenHeight;		// Height of the screen in pixels 
	private int UpperLeftX;			// The default window's upper left hand corner's X position
	private int UpperLeftY;			// The default window's upper left hand corner's Y position
	private JFrame MessageWindow;   // Message window frame
	private JTextArea MessageArea; 	// This is the message area widget

	/***************************************************************************
	* Constructor:: MessageWindow
	* Purpose: This method sets up the JFrame window with the title specified
	*		   at the position indicated by the x, y coordinates
	*
	* Arguments: String Title - the window title
	*			 Float Xpos - the vertical position of the window on the screen
	*			 			specified in percentage of the total screen width.
	*			 Float Ypos - the horizontal position of the window on the screen
	*			 			specified in percentage of the total screen width.
	*
	* Returns: MessageWindow
	*
	* Exceptions: none
	*
	****************************************************************************/
	
	public MessageWindow(String Title, float Xpos, float Ypos)
	{
		MessageWindow = new JFrame(Title);
		JPanel MessagePanel = new JPanel();

		MessageWindow.getContentPane().setBackground( Color.blue );
		Toolkit aKit = MessageWindow.getToolkit();
		Dimension WindowSize = aKit.getScreenSize();

		/* Make window height 25% of the screen height and 
		** the window width 50% of the screen width
		*/
		
		ScreenHeight = WindowSize.height;
		ScreenWidth  = WindowSize.width;
				
		WindowHeight = (int)(ScreenHeight * 0.20);
		WindowWidth  = (int)(ScreenWidth * 0.5);

		/* Calculate the X and Y position of the window's upper left
		** hand corner as a proportion of the screen
		*/
		
		UpperLeftX = (int)(ScreenWidth * Xpos);
		UpperLeftY = (int)(ScreenHeight * Ypos);
		MessageWindow.setBounds(UpperLeftX, UpperLeftY, WindowWidth, WindowHeight);
		MessageWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		/* This sets up the size of the message area within the window
		*/
		
		MessageArea= new JTextArea((int)(WindowHeight/20),(int)(WindowWidth/12));
		MessageArea.setLineWrap(true);

		JScrollPane MessageAreaScrollPane = new JScrollPane(MessageArea);

		MessageWindow.add(MessagePanel);
		MessagePanel.add(MessageAreaScrollPane);

		MessageWindow.setVisible(true);

	} // constructor
	
	/***************************************************************************
	* Constructor:: MessageWindow
	* Purpose: This method sets up the JFrame window with the title specified
	*		   at the position indicated by the x, y coordinates
	*
	* Arguments: String Title - the window title
	*			 int Xpos - the vertical position of the window on the screen
	*			 			specified in pixels.
	*			 int Ypos - the horizontal position of the window on the screen
	*			 			specified in pixels.
	*
	* Returns: MessageWindow
	*
	* Exceptions: none
	*
	****************************************************************************/

	public MessageWindow(String Title, int Xpos, int Ypos)
	{
		MessageWindow = new JFrame(Title);
		JPanel MessagePanel = new JPanel();

		MessageWindow.getContentPane().setBackground( Color.blue );
		Toolkit aKit = MessageWindow.getToolkit();
		Dimension WindowSize = aKit.getScreenSize();

		/* Make window height 25% of the screen height and 
		** the window width 50% of the screen width
		*/
		
		ScreenHeight = WindowSize.height;
		ScreenWidth  = WindowSize.width;
		
		WindowHeight = (int)(ScreenHeight * 0.25);
		WindowWidth  = (int)(ScreenWidth * 0.5);

		UpperLeftX = Xpos;
		UpperLeftY = Ypos;
		MessageWindow.setBounds(Xpos, Ypos, WindowWidth, WindowHeight);
		MessageWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		/* This sets up the size of the message area within the window
		*/
		
		MessageArea= new JTextArea((int)(WindowHeight/20),(int)(WindowWidth/12));
		MessageArea.setLineWrap(true);

		JScrollPane MessageAreaScrollPane = new JScrollPane(MessageArea);

		MessageWindow.add(MessagePanel);
		MessagePanel.add(MessageAreaScrollPane);

		MessageWindow.setVisible(true);

	} // constructor

	/***************************************************************************
	* CONCRETE Class:: GetX
	* Purpose: This method returns the X (vertical) position of the window 
	* in pixels
	*
	* Arguments: none
	*
	* Returns: integer length in pixels
	*
	* Exceptions: none
	*
	****************************************************************************/

	public int GetX()
	{
		return ( UpperLeftX );

	} // GetX

	/***************************************************************************
	* CONCRETE Class:: GetY
	* Purpose: This method returns the Y (horizontal) position of the window 
	* in pixels
	*
	* Arguments: none
	*
	* Returns: integer length in pixels
	*
	* Exceptions: none
	*
	****************************************************************************/

	public int GetY()
	{
		return ( UpperLeftY );

	} // GetY		
	
	/***************************************************************************
	* CONCRETE Class:: Height
	* Purpose: This method returns the vertical height of the window
	*
	* Arguments: none
	*
	* Returns: integer length in pixels
	*
	* Exceptions: none
	*
	****************************************************************************/

	public int Height()
	{
		return ( WindowHeight );

	} // WriteMessage

	/***************************************************************************
	* CONCRETE Class:: Width
	* Purpose: This method returns the horizontal length of the window
	*
	* Arguments: none
	*
	* Returns: integer length in pixels
	*
	* Exceptions: none
	*
	****************************************************************************/

	public int Width()
	{
		return ( WindowWidth );

	} // WriteMessage

	/***************************************************************************
	* CONCRETE Class:: TermHeight
	* Purpose: This method returns the vertical height of the display device
	*
	* Arguments: none
	*
	* Returns: integer length in pixels
	*
	* Exceptions: none
	*
	****************************************************************************/

	public int TermHeight()
	{
		return ( ScreenHeight );

	} // WriteMessage

	/***************************************************************************
	* CONCRETE Class:: TermWidth
	* Purpose: This method returns the horizontal length of the display device
	*
	* Arguments: none
	*
	* Returns: integer length in pixels
	*
	* Exceptions: none
	*
	****************************************************************************/

	public int TermWidth()
	{
		return ( ScreenWidth );

	} // WriteMessage
	
	
	/***************************************************************************
	* CONCRETE Class:: WriteMessage
	* Purpose: This method writes a message to the text area. All messages are
	* written with a date stamp and all strings are followed by a newline.
	*
	* Arguments: String message
	*
	* Returns: none
	*
	* Exceptions: none
	*
	****************************************************************************/

	public void WriteMessage( String message )
	{
		Calendar TimeStamp = Calendar.getInstance();
		SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");

		String TimeString = TimeStampFormat.format(TimeStamp.getTime());

		MessageArea.append( TimeString + ":: " + message + "\n");

		MessageArea.setCaretPosition( MessageArea.getDocument().getLength() );

	} // WriteMessage

} // MessageWindow