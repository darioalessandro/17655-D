import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.sun.corba.se.spi.ior.Writeable;

//import FilterFramework.EndOfStreamException;

public class FrameFilterFramework extends FilterFramework{
    
    private int IdLength = 4; // This is the length of IDs in the byte stream
    private int MeasurementLength = 8; // This is the length of all measurements (including time) in bytes
    
    protected Frame ReadFrameInput(PipedInputStream pipedInputStream) {   
        Calendar TimeStamp = Calendar.getInstance();
        SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");
        Frame frame = new Frame();
    
        int bytesread = 0;          // Number of bytes read from the input file.
        int byteswritten = 0;       // Number of bytes written to the stream.
        byte databyte = 0;          // The byte of data read from the file
    
        long measurement = 0;       // This is the word used to store all measurements - conversions are illustrated.
        int id;             // This is the measurement id
        int i;              // This is a loop counter
    
        // Next we write a message to the terminal to let the world know we are alive...
    
        while (true)
        {
          /*************************************************************
          * Here we read a byte and write a byte
          *************************************************************/
    
          try
          {
            id = 0;
    
            // Get the ID
            id=0;
            for (i=0; i<IdLength; i++ )
            {
              databyte = ReadFilterInputPort(pipedInputStream); // This is where we read the byte from the stream...
              id = id | (databyte & 0xFF);    // We append the byte on to ID...
              if (i != IdLength-1){        // If this is not the last byte, then slide the, previously appended byte to the left by one byte
                id = id << 8;         // to make room for the next byte we append to the ID
              } // if
              bytesread++;            // Increment the byte count
              //WriteFilterOutputPort(this.OutputWritePort, databyte);
              //byteswritten++;
            } // for
            
            // Get the measurement
            measurement = 0;
            for (i = 0; i < MeasurementLength; i++) {
                databyte = ReadFilterInputPort(pipedInputStream);
                measurement = measurement | (databyte & 0xFF);    // We append the byte on to measurement...
                // If this is not the last byte, then slide the previously appended byte to the left by one byte
                if (i != MeasurementLength - 1) {
                    measurement = measurement << 8;                // to make room for the next byte we append to the
                }
                bytesread++;                                    // Increment the byte count
            }
            
            // Convert the measurement based on ID
            if (id == 0) {
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
            if (id == 5) {
                return frame;
                
            }
    
          } // try
    
          catch (EndOfStreamException e)
          {
            ClosePorts();
            System.out.print( "\n" + this.getName() + "::Middle Exiting; bytes read: " + bytesread + " bytes written: " + byteswritten );
            break;
            
          } // catch
    
        } // while
        return frame;
     } // ReadFrameInput
    
    protected void WriteFrameOutput(PipedOutputStream pipedOutputStream, Frame frame) {
        //write out for id = 0
        writeIDBytewise(pipedOutputStream, 0);
        writeMeasurementBytewise(pipedOutputStream, frame.timestamp.getTime());
        
        writeIDBytewise(pipedOutputStream, 1);;
        writeMeasurementBytewise(pipedOutputStream, Double.doubleToLongBits(frame.velocity));
        
        writeIDBytewise(pipedOutputStream, 2);;
        writeMeasurementBytewise(pipedOutputStream, Double.doubleToLongBits(frame.altitude));
        
        writeIDBytewise(pipedOutputStream, 3);;
        writeMeasurementBytewise(pipedOutputStream, Double.doubleToLongBits(frame.pressure));
        
        writeIDBytewise(pipedOutputStream, 4);;
        writeMeasurementBytewise(pipedOutputStream, Double.doubleToLongBits(frame.temperature));
        
        writeIDBytewise(pipedOutputStream, 5);;
        writeMeasurementBytewise(pipedOutputStream, Double.doubleToLongBits(frame.attitude));

    }
    
    protected void writeIDBytewise(PipedOutputStream pipedOutputStream, int id) {
        byte[] bytes = intToBytes(id);
        for (int i=0; i<IdLength; i++ ) {
          WriteFilterOutputPort(pipedOutputStream, bytes[i]);
        }
    }
    protected void writeMeasurementBytewise(PipedOutputStream pipedOutputStream, long measurement) {
        byte[] bytes = longToBytes(measurement);
        for (int i=0; i<MeasurementLength; i++ ) {
          WriteFilterOutputPort(pipedOutputStream, bytes[i]);
        }
    }
    private byte[] longToBytes(long l) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte)(l & 0xFF);
            l >>= 8;
        }
        return result;
    }
    private byte[] intToBytes(int l) {
        byte[] result = new byte[4];
        for (int i = 3; i >= 0; i--) {
            result[i] = (byte)(l & 0xFF);
            l >>= 8;
        }
        return result;
    }

    
    
}
