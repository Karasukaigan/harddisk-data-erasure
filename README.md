# Harddisk Data Erasure
Erase the original data of the hard disk by writing data.<br>
Written in Java.<br>
<br>
<img src="https://github.com/Karasukaigan/harddisk-data-erasure/blob/main/Run%20Screen.png"  alt="Run Screen" /><br>
<br>
<strong>Class:</strong><br>
MainWindow -> The main window of the program.The GUI is made using the WindowBuilder plugin.<br>
RunDataErasure -> The class used to execute the erase command.<br>
JTextFieldLimit -> JTextFieldLimit inherits the PlainDocument class. Compared with JTextField, it limits the number of characters that can be entered.<br>
<br>
<strong>How to achieve data erasure?</strong><br>
STEP1.<br>
Generate a random text column of 1KB size and add the text column to the String array. <br>
This command is executed 1024 times, which means that there is 1MB-sized data in the String array.<br>
STEP2.<br>
Generate an empty file in the root directory of the hard disk, write the data in the String array to this file, and loop the operation.<br>
STEP3.<br>
The maximum number of files generated is 1000, and the number is from 1 to 1000. <br>
This means that when the cycle reaches 1001 times, it will start writing from the file numbered 1 again.<br>
STEP4.<br>
Each time, 1MB data is written to the file, looping until it fills up all the remaining space on the entire hard disk.<br>
The number of cycles is determined by calculating the remaining space on the hard disk in advance.<br>
STEP5.<br>
Delete all generated files.<br>
<br>
<strong>What is the difference between choosing HDD and SSD?</strong><br>
If you choose SSD, it will only fill the remaining space on the hard drive once.<br>
If you select HDD, it will be filled, deleted, loop 3 times.<br>
<br>
<strong>How long does it take to erase the data?</strong><br>
I tested it with a piece of SSD the size of 480GB. <br>
It took 1 to 1.5 hours to erase the data.<br>
