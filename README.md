# 17655-D

## A1

Due Feb 22nd.

#### INSTRUCTIONS TO RUN a System

```bash
cd A1-2015/<System Name>
javac -cp ../Library/:. Plumber.java
java -cp ../Library/:. Plumber
```  

#### Instructions to RUN tests

```bash
cd A1-2015/LibraryTests
javac -cp ../Library/:. *.java
java -cp ../Library/:. TransformFrameFilterTests
```  

### System A

Feature:

  - 2 MidFilters for conversion of Temperature and Altitude, respectively
  - Output data to OutputA.dat in text format


### System B

Feature:

  - Adding a third pressure filter (linear path)
  - Output data to OutputB.dat in text format

TODO:
In case of wild point, compute the average of the last valid measurement and the next valid measurement in the stream.


### System C

## A2
