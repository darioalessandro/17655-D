
public class Main {
    public static void main( String argv[])
    {
     /****************************************************************************
     * Here we instantiate three filters.
     ****************************************************************************/
     GenericSourceFilter subsetAReader = new GenericSourceFilter("../DataSets/SubSetA.dat");
     GenericSourceFilter subsetBReader = new GenericSourceFilter("../DataSets/SubSetB.dat");
     
     MergeFilter mergeFilter = new MergeFilter();


     GenericSinkFilter sinkFilter = new GenericSinkFilter("Output.txt");

     /****************************************************************************
     * Here we connect the filters starting with the sink filter (Filter 1) which
     * we connect to Filter2 the middle filter. Then we connect Filter2 to the
     * source filter (Filter3).
     ****************************************************************************/
     
     mergeFilter.Connect(subsetAReader);
     mergeFilter.ConnectMergeInput(subsetBReader);
     sinkFilter.Connect(mergeFilter);

     /****************************************************************************
     * Here we start the filters up. All-in-all,... its really kind of boring.
     ****************************************************************************/
     subsetAReader.start();
     subsetBReader.start();
     mergeFilter.start();
     sinkFilter.start();
     
    } // main
}
