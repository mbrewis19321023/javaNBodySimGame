You need to compile it all in an IDE to make sure it actually works and to produce the class files
All files need to be in the same directory 
Next unzip any library .jar files (stdlib.jar)
This will make it all .java and .class files which will then be freshly packaged into new .jar
Next make a manifest.mf file that says what the main class is:

Manifest-Version: 1.0
Main-Class: StellarCrush

Note that the mainfest file must end on an empty line 

Next open a git bash in the folder with all this shit in and type this:

$ jar cfm StellarCrush.jar manifest.mf *.class *.java Dovahkiin_Kostya_Remix_.wav
