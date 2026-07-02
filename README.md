## Overview
This repository consists of executable .jar files in "Benchmarking.jar" and "SimulationApp.jar", raw source codes in the folder "source code", and buildable raw source codes with Maven in the zip file "DSFinalProjectApp.zip"

### How to Run the .jar Files
- Have OpenJDK version 23 or above installed and active
- Double click either .jar files
- "Benchmarking.jar" is a benchmarking app that facilitates direct input of variables and outputs runtime performance for operations into a .txt file
- "SimulationApp.jar" is a simulation app that functions as a visual benchmarker, sacrificing precision for presentation
  
- Alternatively, you can run either .jar files using the command line, navigating to the folder containing either files and executing "java -jar filename.jar" with openJDK version 23+ installed

### How to use SimulationApp
- To use the benchmarking .jar, simply double tap it, which will prompt you for inputs, and after 
the inputs are submitted, it will create a text file containing benchmarking measurement for that 
input.

### How to use Benchmarker
- To use the visual application, double tap it, then you can move around using WASD, and 
manipulate attributes such as map size, data structures used, and perform operations such as 
insertion, all while having the ability to render objects in the map itself.
- For the visual application, you can choose to create a map of modifiable sizes using any of the 
three data structures by clicking “create world”. You can modify the operation count for random 
insert, delete, and move. Random insertion attempts to insert x entities at random coordinates. 
Random deletion attempts to delete entities at x random coordinates if found (not x random 
entities, but x random coordinates). Move is similar to delete, where it attempts to find entities 
at x random coordinates, and update the position of entities found. You can zoom with the 
button on the panel as well.

### How to Access Raw Readable Source Codes
- Raw readable source codes are contained in the folder "source code" which contains .java files that can be read

### How to Access and Possibly Independently Compile Code
- The zip file "DSFinalProjectApp" contains the full build file made in Netbeans using Maven
