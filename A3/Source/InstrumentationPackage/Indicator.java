/******************************************************************************************************************
* File:Indicator.java
* Course: 17655
* Project: Assignment A3
* Copyright: Copyright (c) 2009 Carnegie Mellon University
* Versions:
*	1.0 March 2009 - Initial rewrite of original assignment 3 (ajl).
*
* Description:
*
* This class is used to create an indicator lamp on the terminal. The indicator lamp is essentiall a square box
* with a round corner rectangle inside that is the indicator lamp. The lamp's color can be turn black, green,
* yellow, or red. A short message can be displayed below the indicator lamp as well. Both the lamp color and the
* message can be changed at run time.
*
*
* Parameters: SEE THE CONSTRUCTOR BELOW
*
* Internal Methods:
*
*	public int GetX
*	public int GetY
*	public int Height()
*	public int Width()
*	public void SetLampColorAndMessage(String message, int color)
*	public void SetMessage( String message )
*
******************************************************************************************************************/
package InstrumentationPackage;

import javax.swing.*;
import java.awt.*;

public class Indicator extends JFrame
{
	private int Height;
	private int UpperLeftX;
	private int UpperLeftY;
	private String MessageLabel;
	private Color IluminationColor = Color.black;
	private Color TextColor = Color.black;
	private JFrame IndicatorWindow;

	/***************************************************************************
	* Constructor:: Indicator
	* Purpose: This method sets up a JFrame window and drawing pane with the
	*		   title specified at the position indicated by the x, y coordinates.
	*
	* Arguments: String Label - the indicator title
	*			 Float Xpos - the vertical position of the indicator on the screen
	*			 			  specified in terms of a percentage of the screen width.
	*			 Float Ypos - the horizontal position of the indicator on the screen
	*			 		 	  specified in terms of a percentage of the screen height.
	*
	* Returns: Indicator
	*
	* Exceptions: none
	*
	****************************************************************************/

	public Indicator(String Label, float Xpos, float Ypos)
	{
		MessageLabel = Label;

    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground( Color.lightGray );
		Toolkit aKit = this.getToolkit();
		Dimension ScreenSize = aKit.getScreenSize();

		// Size up the indicators. They width and height is 1% of the 
		// screen height or width (which ever is larger).
		
		if (ScreenSize.width >= ScreenSize.height)
		{
			Height = (int)(ScreenSize.height * 0.1);

		} else {

			Height = (int)(ScreenSize.width * 0.1);

		} // if

		/* Calculate the X and Y position of the window's upper left
		** hand corner as a proportion of the screen
		*/
		
		UpperLeftX = (int)(ScreenSize.width * Xpos);
		UpperLeftY = (int)(ScreenSize.height * Ypos);
		
		setBounds(UpperLeftX, UpperLeftY, Height, Height);
		setVisible(true);
		Graphics g = getGraphics();

		repaint();

	} // constructor

	/***************************************************************************
	* Constructor:: Indicator
	* Purpose: This method sets up a JFrame window and drawing pane with the
	*		   title specified at the position indicated by the x, y coordinates.
	*
	* Arguments: String Label - the indicator title
	*			 int Xpos - the vertical position of the indicator on the screen
	*			 			specified in pixels.
	*			 int Ypos - the horizontal position of the indicator on the screen
	*			 			specified in pixels.
	*
	* Returns: Indicator
	*
	* Exceptions: none
	*
	****************************************************************************/

	public Indicator(String Label, int Xpos, int Ypos)
	{
		MessageLabel = Label;

    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground( Color.lightGray );
		Toolkit aKit = this.getToolkit();
		Dimension ScreenSize = aKit.getScreenSize();
		
		// Size up the indicators. They width and height is 1% of the 
		// screen height or width (which ever is larger).		

		if (ScreenSize.width >= ScreenSize.height)
		{
			Height = (int)(ScreenSize.height * 0.1);

		} else {

			Height = (int)(ScreenSize.width * 0.1);

		} // if

		UpperLeftX = Xpos;
		UpperLeftY = Ypos;

		setBounds(Xpos, Ypos, Height, Height);
		setVisible(true);
		Graphics g = getGraphics();

		repaint();

	} // constructor
	/***************************************************************************
	* Constructor:: Indicator
	* Purpose: This method sets up a JFrame window and drawing pane with the
	*		   title specified at the position indicated by the x, y coordinates.
	*
	* Arguments: String Label - the indicator title
	*			 Float Xpos - the vertical position of the indicator on the screen
	*			 			  specified in terms of a percentage of the screen width.
	*			 Float Ypos - the horizontal position of the indicator on the screen
	*			 		 	  specified in terms of a percentage of the screen height.
	*
	*			 int InitialColor - specifies the color that the indicator should
	*								be on startup.
	*
	* Returns: Indicator
	*
	* Exceptions: none
	*
	****************************************************************************/

	public Indicator(String Label, float Xpos, float Ypos, int InitialColor )
	{
		MessageLabel = Label;

		switch( InitialColor )
		{
			case 0:
				IluminationColor = Color.black;
				break;

			case 1:
				IluminationColor = Color.green;
				break;

			case 2:
				IluminationColor = Color.yellow;
				break;

			case 3:
				IluminationColor = Color.red;
				break;

		} // switch

    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground( Color.lightGray );
		Toolkit aKit = this.getToolkit();
		Dimension ScreenSize = aKit.getScreenSize();

		if (ScreenSize.width >= ScreenSize.height)
		{
			Height = (int)(ScreenSize.height * 0.1);

		} else {

			Height = (int)(ScreenSize.width * 0.1);

		} // if

		/* Calculate the X and Y position of the window's upper left
		** hand corner as a proportion of the screen
		*/
		
		UpperLeftX = (int)(ScreenSize.width * Xpos);
		UpperLeftY = (int)(ScreenSize.height * Ypos);
		
		setBounds(UpperLeftX, UpperLeftY, Height, Height);
		
		setVisible(true);
		Graphics g = getGraphics();

		repaint();

	} // constructor

	
	/***************************************************************************
	* Constructor:: Indicator
	* Purpose: This method sets up a JFrame window and drawing pane with the
	*		   title specified at the position indicated by the x, y coordinates.
	*
	* Arguments: String Label - the indicator title
	*			 int Xpos - the vertical position of the indicator on the screen
	*			 			specified in pixels.
	*			 int Ypos - the horizontal position of the indicator on the screen
	*			 			specified in pixels.
	*			 int InitialColor - specifies the color that the indicator should
	*								be on startup.
	*
	* Returns: Indicator
	*
	* Exceptions: none
	*
	****************************************************************************/

	public Indicator(String Label, int Xpos, int Ypos, int InitialColor )
	{
		MessageLabel = Label;

		switch( InitialColor )
		{
			case 0:
				IluminationColor = Color.black;
				break;

			case 1:
				IluminationColor = Color.green;
				break;

			case 2:
				IluminationColor = Color.yellow;
				break;

			case 3:
				IluminationColor = Color.red;
				break;

		} // switch

    	//setUndecorated(true);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground( Color.lightGray );
		Toolkit aKit = this.getToolkit();
		Dimension ScreenSize = aKit.getScreenSize();

		if (ScreenSize.width >= ScreenSize.height)
		{
			Height = (int)(ScreenSize.height * 0.1);

		} else {

			Height = (int)(ScreenSize.width * 0.1);

		} // if

		UpperLeftX = Xpos;
		UpperLeftY = Ypos;

		setBounds(Xpos, Ypos, Height, Height);
		setVisible(true);
		Graphics g = getGraphics();

		repaint();

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
		return ( this.Height );

	} // Height

	/***************************************************************************
	* CONCRETE Class:: Width
	* Purpose: This method returns the horizontal length of the window.
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
		return ( this.Height );

	} // Width

	/***************************************************************************
	* CONCRETE Class::SetLampColorAndMessage
	* Purpose: This method will change both the indicator lamp color and the
	* indicator label to the specified color and label. The display text is
	* always black.
	*
	* Arguments: String s - the new indicator label
	*			 int c - the new indicator color where 0=black, 1=green,
	*			 2=yellow, and 3=red
	*
	* Returns: none
	*
	* Exceptions: none
	*
	****************************************************************************/

	public void SetLampColorAndMessage(String s, int c)
	{
		switch( c )
		{
			case 0:
				IluminationColor = Color.black;
				break;

			case 1:
				IluminationColor = Color.green;
				break;

			case 2:
				IluminationColor = Color.yellow;
				break;

			case 3:
				IluminationColor = Color.red;
				break;

		} // switch

		MessageLabel = s;

		repaint();

	} // SetLampColor

	/***************************************************************************
	* CONCRETE Class::SetLampColor
	* Purpose: This method will change the indicator lamp color to the specified
	* color.
	*
	* Arguments: int c - the new indicator color where 0=black, 1=green,
	*			 2=yellow, and 3=red
	*
	* Returns: none
	*
	* Exceptions: none
	*
	****************************************************************************/

	public void SetLampColor(int c)
	{
		switch( c )
		{
			case 0:
				IluminationColor = Color.black;
				break;

			case 1:
				IluminationColor = Color.green;
				break;

			case 2:
				IluminationColor = Color.yellow;
				break;

			case 3:
				IluminationColor = Color.red;
				break;

		} // switch

		repaint();

	} // SetLampColor

	/***************************************************************************
	* CONCRETE Class::SetMessage
	* Purpose: This method will change both the indicator label to the specified
	* label string. The display text is always black.
	*
	* Arguments: String s - the new indicator label
	*
	* Returns: none
	*
	* Exceptions: none
	*
	****************************************************************************/

	public void SetMessage(String m)
	{
		MessageLabel = m;
		repaint();

	} // SetMessage

	/***************************************************************************
	* CONCRETE Class::paint
	* Purpose: The paint() method is an overridden method inherited from JFrame
	* that draws the indicator on the screen. This method must be overridden so
	* that various JRE services can update the display. If you do not override
	* paint, the indicator will not be consistiently drawn to the screen and may
	* have various graphics disappear at run time. This method is invoked
	* indirectly by the repaint() method.
	*
	* Arguments: Graphics g this is the indicator's graphic instance.
	*
	* Returns: none
	*
	* Exceptions: none
	*
	****************************************************************************/

	public void paint(Graphics g)
	{
		super.paint(g);

		FontMetrics fm = g.getFontMetrics();

		int xLabelPosition = (int)(Height*0.5)- (int)(fm.stringWidth(MessageLabel)*0.5);
		int yLabelPosition = (int)(Height*0.90);
		g.setColor(IluminationColor);
		g.fillRoundRect( (int)(Height*0.15), (int)(Height*0.35), (int)(Height*0.70), (int)(Height*0.40), (int)(Height*0.20), (int)(Height*0.20) );
		g.setColor(TextColor);
		g.drawString( MessageLabel, xLabelPosition, yLabelPosition );

	} // paint

} // Indicator