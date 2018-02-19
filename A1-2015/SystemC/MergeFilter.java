import java.io.PipedInputStream;

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
          if(this.InputFilter.isAlive() && this.mergeInputFilter.isAlive()){
            if(inputStreamActive){
                newestInputFrame = ReadFrameInput(this.InputReadPort);
                System.out.print("Read frame from input stream: " + newestInputFrame.toString() + "\n");
                if(newestInputFrame.timestamp.getTime() <= newestMergeInputFrame.timestamp.getTime()){
                    WriteFrameOutput(this.OutputWritePort, newestInputFrame);
                } else {
                    inputStreamActive = false;
                    WriteFrameOutput(this.OutputWritePort, newestMergeInputFrame);
                }
            }else{
                newestMergeInputFrame = ReadFrameInput(this.mergeInputReadPort);
                System.out.print("Read frame from merge stream: " + newestMergeInputFrame.toString() + "\n");
                if(newestMergeInputFrame.timestamp.getTime() <= newestInputFrame.timestamp.getTime()){
                    WriteFrameOutput(this.OutputWritePort, newestMergeInputFrame);
                } else {
                    inputStreamActive = true;
                    WriteFrameOutput(this.OutputWritePort, newestInputFrame);
                }
                
            }
          }else {
              if(this.InputFilter.isAlive()){
                  while(this.InputFilter.isAlive()){
                      newestInputFrame = ReadFrameInput(this.InputReadPort);
                      WriteFrameOutput(this.OutputWritePort, newestInputFrame);
                  }
              }else {
                  while(this.mergeInputFilter.isAlive()){
                      newestMergeInputFrame = ReadFrameInput(this.mergeInputReadPort);
                      WriteFrameOutput(this.OutputWritePort, newestMergeInputFrame);
                  }
              }
              return;
          }
      }
        
    }

    

}
