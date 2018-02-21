import java.io.PipedInputStream;

import sun.font.TrueTypeFont;

//import FilterFramework.EndOfStreamException;


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
        //System.out.println( "\n" + this.getName() + " FilterFramework error connecting::"+ Error );

      } // catch

    } // ConnectMergeInput
    
    public void run() {
        
      boolean inputStreamActive = true;
      Frame newestInputFrame = new Frame();
      Frame newestMergeInputFrame = new Frame();
      
      while(true) {
          if(inputStreamActive){
              try {
                  newestInputFrame = readFrame(this.InputReadPort);
                  if(newestInputFrame.timestamp.getTime() <= newestMergeInputFrame.timestamp.getTime()){
                      writeFrame(newestInputFrame, this.OutputWritePort);
                  } else {
                      inputStreamActive = false;
                      writeFrame(newestMergeInputFrame, this.OutputWritePort);
                  }
              } catch (Exception e){
                  // run out the rest of merge stream
                  try {
                      while(true){
                          newestMergeInputFrame = readFrame(this.mergeInputReadPort);
                          writeFrame(newestMergeInputFrame, this.OutputWritePort);
                      }
                  } catch (Exception e2) {
                      return;
                  }
              }
              
          }else{
              try {
                  newestMergeInputFrame = readFrame(this.mergeInputReadPort);
                  if(newestMergeInputFrame.timestamp.getTime() <= newestInputFrame.timestamp.getTime()){
                      writeFrame(newestMergeInputFrame, this.OutputWritePort);
                  } else {
                      inputStreamActive = true;
                      writeFrame(newestInputFrame, this.OutputWritePort);
                  }
              } catch (Exception e){
                  // run out the rest of merge stream
                  try {
                      while(true){
                          newestInputFrame = readFrame(this.InputReadPort);
                          writeFrame(newestInputFrame, this.OutputWritePort);
                      }
                  } catch (Exception e2) {
                      return;
                  }
              }
              
          }
      }
        
    }

    

}
