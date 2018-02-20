import java.io.*;
import java.util.*;                        // This class is used to interpret time words
import java.text.SimpleDateFormat;        // This class is used to format and write time in a string format.

public class BytesToFrameFilter extends FilterFramework {
    int MeasurementLength = 8;        // This is the length of all measurements (including time) in bytes
    int IdLength = 4;                // This is the length of IDs in the byte stream

    public void run() {
        /************************************************************************************
         *	TimeStamp is used to compute time using java.util's Calendar class.
         * 	TimeStampFormat is used to format the time value so that it can be easily printed
         *	to the terminal.
         *************************************************************************************/

        Calendar TimeStamp = Calendar.getInstance();
        SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");

        byte databyte = 0;                // This is the data byte read from the stream
        int bytesread = 0;                // This is the number of bytes read from the stream
        long measurement;                // This is the word used to store all measurements - conversions are illustrated.
        int id;                            // This is the measurement id
        int i;                            // This is a loop counter

        /*************************************************************
         *	First we announce to the world that we are alive...
         **************************************************************/

        System.out.print("\n" + this.getName() + "::BytesToFrameFilter ");
        Frame frame = new Frame();
        while (true) {
            try {
                /***************************************************************************
                 // We know that the first data coming to this filter is going to be an ID and
                 // that it is IdLength long. So we first decommutate the ID bytes.
                 ****************************************************************************/

                id = 0;

                for (i = 0; i < this.IdLength; i++) {
                    databyte = ReadFilterInputPort();    // This is where we read the byte from the stream...
                    id = id | (databyte & 0xFF);        // We append the byte on to ID...
                    // If this is not the last byte, then slide the previously appended byte to the left by one byte
                    if (i != this.IdLength - 1) {
                        id = id << 8;                    // to make room for the next byte we append to the ID
                    }
                    bytesread++;                        // Increment the byte count
                }

                /****************************************************************************
                 // Here we read measurements. All measurement data is read as a stream of bytes
                 // and stored as a long value. This permits us to do bitwise manipulation that
                 // is neccesary to convert the byte stream into data words. Note that bitwise
                 // manipulation is not permitted on any kind of floating point types in Java.
                 // If the id = 0 then this is a time value and is therefore a long value - no
                 // problem. However, if the id is something other than 0, then the bits in the
                 // long value is really of type double and we need to convert the value using
                 // Double.longBitsToDouble(long val) to do the conversion which is illustrated.
                 // below.
                 *****************************************************************************/

                measurement = 0;

                for (i = 0; i < this.MeasurementLength; i++) {
                    databyte = ReadFilterInputPort();
                    measurement = measurement | (databyte & 0xFF);    // We append the byte on to measurement...
                    // If this is not the last byte, then slide the previously appended byte to the left by one byte
                    if (i != this.MeasurementLength - 1) {
                        measurement = measurement << 8;                // to make room for the next byte we append to the
                    }
                    bytesread++;                                    // Increment the byte count
                }

                /****************************************************************************
                 // Here we look for an ID of 0 which indicates this is a time measurement.
                 // Every frame begins with an ID of 0, followed by a time stamp which correlates
                 // to the time that each proceeding measurement was recorded. Time is stored
                 // in milliseconds since Epoch. This allows us to use Java's calendar class to
                 // retrieve time and also use text format classes to format the output into
                 // a form humans can read. So this provides great flexibility in terms of
                 // dealing with time arithmetically or for string display purposes. This is
                 // illustrated below.
                 ****************************************************************************/

                if (id == 0) {
                    if (frame.timestamp != null) {
                        try {
                            ObjectOutputStream OutputWritePorto = new ObjectOutputStream(this.OutputWritePort);
                            OutputWritePorto.writeObject(frame);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    TimeStamp.setTimeInMillis(measurement);
                    frame.timestamp = TimeStamp.getTime();
                } else {
                    double value = Double.longBitsToDouble(measurement);
                    switch (id) {
                        case 1:
                            frame.velocity = value;
                            break;
                        case 2:
                            frame.altitude = value;
                            break;
                        case 3:
                            frame.pressure = value;
                            break;
                        case 4:
                            frame.temperature = value;
                            break;
                        case 5:
                            frame.attitude = value;
                            break;
                        default:
                            System.out.println("unknown id: " + id);
                    }
                }
            }
            /*******************************************************************************
             *	The EndOfStreamExeception below is thrown when you reach end of the input
             *	stream (duh). At this point, the filter ports are closed and a message is
             *	written letting the user know what is going on.
             ********************************************************************************/
            catch (EndOfStreamException e) {
                if (frame.timestamp != null) {
                    try {
                        ObjectOutputStream OutputWritePorto = new ObjectOutputStream(this.OutputWritePort);
                        OutputWritePorto.writeObject(frame);
                    }
                    catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                ClosePorts();
                System.out.print("\n" + this.getName() + "::Sink Exiting; bytes read: " + bytesread);
                break;
            }
        }
    }
}
