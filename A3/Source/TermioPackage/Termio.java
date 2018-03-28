
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// This class contains methods that allow the callers do the
// following:
//
//   public String KeyboardReadString():
//      Allows the caller to read a string from the keyboard.
//
//   public char KeyboardReadChar()
//      Allows the caller to read a single unicode char from
//      the keyboard.
//
//   public boolean IsNumber( String StringItem )
//      Allows the caller to test StringItem to see if it is
//      a numeric value.
//
//   public float ToFloat( String StringItem )
//      Allows the caller to convert StringItem into a
//      float.
//
//   public double ToDouble(String StringItem)
//      Allows the caller to convert StringItem into a
//      double.
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

package TermioPackage;
import java.io.*;

public class Termio
{
   //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   // This method reads a string from the keyboard and
   // returns it to the caller
   //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

   public String KeyboardReadString()
   {
      BufferedReader MyReader =
      new BufferedReader(new InputStreamReader(System.in));

      String StringItem = "";

      try {

          StringItem = MyReader.readLine();

      } // try

      catch (IOException IOError)
      {

          System.out.println( "Read Error in Termio.KeyboardReadString method" );

      } // catch

      return StringItem;

   } // KeyboardReadString

   //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   // This method reads a single character from the keyboard and
   // returns it to the caller.
   //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

   public char KeyboardReadChar()
   {
      BufferedReader MyReader =
      new BufferedReader(new InputStreamReader(System.in));

      char CharItem = ' ';

      try {

          CharItem = (char)MyReader.read();

      } // try

      catch (IOException IOError)
      {

          System.out.println( "Read Error in Termio.KeyboardReadChar method" );

      } // catch

      return CharItem;

   } // KeyboardReadChar

   //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   // This method accepts a String Object and determines if the
   // string is representing a number.  If an integer is
   // passed (for example 4), then the program will assume that
   // it is a floating point number (for example 4.0).  If the
   // string represents a numeric value, then a true is returned
   // to the caller, otherwise a false is returned.
   //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

   public boolean IsNumber( String StringItem )
   {
      Float FloatItem = new Float(0.0);

      try {

          FloatItem = FloatItem.valueOf( StringItem );

      } // try

      catch (NumberFormatException IOError)
      {

          return false;

      } // catch

      return true;

   } //IsNumber

   //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   // This method accepts a string and if the string represents
   // a number, then it is converted to a float and returned to
   // the caller, otherwise a NumericFormatException is raised
   // and a message is printed for the caller.
   //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

   public float ToFloat( String StringItem )
   {
      Float FloatItem = new Float(0.0);

      try {

          FloatItem = FloatItem.valueOf( StringItem );

      } // try

      catch (NumberFormatException IOError)
      {

          System.out.print(   "Error converting " + StringItem );
          System.out.print( " to a floating point number::");
          System.out.println( " Termio.ToFloat method.");

      } // catch

      return FloatItem.floatValue();

   } //ToFloat

   //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   // This method accepts a string and if the string represents
   // a number, then it is converted to a double and returned to
   // the caller, otherwise a NumericFormatException is raised
   // and a message is printed for the caller.
   //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

   public double ToDouble(String StringItem)
   {
      Float FloatItem = new Float(0.0);

      try {

          FloatItem = FloatItem.valueOf( StringItem );

      } // try

      catch (NumberFormatException IOError)
      {
          System.out.print(   "Error converting " + StringItem );
          System.out.print( " to a floating point number::");
          System.out.println( " Termio.ToDouble method.");
      } // catch

      return FloatItem.doubleValue();

   } //ToDouble

   //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   // This method accepts a string and if the string represents
   // a number, then it is converted to a integer and returned to
   // the caller, otherwise a NumericFormatException is raised
   // and a message is printed for the caller.
   //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

   public int ToInteger(String StringItem)
   {
      Integer IntegerItem = new Integer(0);

      try {

          IntegerItem = IntegerItem.valueOf( StringItem );

      } // try

      catch (NumberFormatException IOError)
      {
          System.out.print(   "Error converting " + StringItem );
          System.out.print( " to an integer number::");
          System.out.println( " Termio.ToInteger method.");

      } // catch

      return IntegerItem.intValue();

   } //ToDouble


} //class
