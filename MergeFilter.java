import java.io.IOException;
import java.io.PipedInputStream;

public class MergeFilter extends FrameFilterFramework {
    
    private PipedInputStream mergeInputReadPort = new PipedInputStream();
    private FilterFramework mergeInputFilter;
    
    // extra connect function defined to accomodate a second input stream to be merged
    void ConnectMergeInput( FilterFramework Filter )
    {
      mergeInputFilter = Filter;  
      try
      {
        // Connect this filter's input to the upstream pipe's output stream
        mergeInputReadPort.connect( Filter.OutputWritePort );
      } // try
      catch( Exception Error )
      {
        System.out.println( "\n" + this.getName() + " FilterFramework error connecting::"+ Error );
      } // catch

    } // ConnectMergeInput
    
    public void run() {
        
      boolean inputStreamActive = true;
      Frame newestInputFrame = new Frame();
      Frame newestMergeInputFrame = new Frame();
      //get the first values of each stream
      try {
        newestInputFrame = readFrame(this.InputReadPort);
        newestMergeInputFrame = readFrame(this.mergeInputReadPort);
    } catch (EndOfStreamException e1) {
        e1.printStackTrace();
    }

      
      while(true) {
          
          if(inputStreamActive){
              // read data from the main input port
              try {
                  newestInputFrame = readFrame(this.InputReadPort);
                  if(newestInputFrame.timestamp.getTime() <= newestMergeInputFrame.timestamp.getTime()){
                      writeFrame(newestInputFrame, this.OutputWritePort);
                  } else {
                      inputStreamActive = false;
                      writeFrame(newestMergeInputFrame, this.OutputWritePort);
                  }
              } catch (EndOfStreamException e){
                  // main input is done, run out the rest of merge stream
                  try {
                      while(true){
                          newestMergeInputFrame = readFrame(this.mergeInputReadPort);
                          writeFrame(newestMergeInputFrame, this.OutputWritePort);
                      }
                  } catch (EndOfStreamException e2) {
                      //both streams done
                      return;
                  }
              }
              
          }else{
              // read data from the merging input port
              try {
                  newestMergeInputFrame = readFrame(this.mergeInputReadPort);
                  if(newestMergeInputFrame.timestamp.getTime() <= newestInputFrame.timestamp.getTime()){
                      writeFrame(newestMergeInputFrame, this.OutputWritePort);
                  } else {
                      inputStreamActive = true;
                      writeFrame(newestInputFrame, this.OutputWritePort);
                  }
              } catch (EndOfStreamException e){
                  // merge stream is done, run out the rest of input stream
                  try {
                      while(true){
                          newestInputFrame = readFrame(this.InputReadPort);
                          writeFrame(newestInputFrame, this.OutputWritePort);
                      }
                  } catch (EndOfStreamException e2) {
                      //both streams done
                      return;
                  }
              }
              
          }
      }
        
    }
    
    @Override
    void ClosePorts() {
        try {
            super.ClosePorts();
            mergeInputReadPort.close();
        } catch (IOException e) {
            System.out.println( "\n" + this.getName() + " ClosePorts error::" + e );
            e.printStackTrace();
        }
        
    }

    

}
