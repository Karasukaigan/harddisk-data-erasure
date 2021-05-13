# Harddisk Data Erasure
Erase the original data of the hard disk by writing data.
Written in Java.

Class:
MainWindow -> The main window of the program.The GUI is made using the WindowBuilder plugin.
RunDataErasure -> The class used to execute the erase command.
JTextFieldLimit -> JTextFieldLimit inherits the PlainDocument class. Compared with JTextField, it limits the number of characters that can be entered.

How to achieve data erasure?
STEP1.
Generate a random text column of 1KB size and add the text column to the String array. 
This command is executed 1024 times, which means that there is 1MB-sized data in the String array.
STEP2.
Generate an empty file in the root directory of the hard disk, write the data in the String array to this file, and loop the operation.
STEP3.
The maximum number of files generated is 1000, and the number is from 1 to 1000. 
This means that when the cycle reaches 1001 times, it will start writing from the file numbered 1 again.
STEP4.
Each time, 1MB data is written to the file, looping until it fills up all the remaining space on the entire hard disk.
The number of cycles is determined by calculating the remaining space on the hard disk in advance.
STEP5.
Delete all generated files.

What is the difference between choosing HDD and SSD?
If you choose SSD, it will only fill the remaining space on the hard drive once.
If you select HDD, it will be filled, deleted, loop 3 times.

How long does it take to erase the data?
I tested it with a piece of SSD the size of 480GB. It took 1 to 1.5 hours to erase the data.
