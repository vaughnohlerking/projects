Name: Vaughn Ohlerking
Project: PA-2
File: README.txt
Instructor: Feng Chen
Class: cs4103-sp21
LogonID: cs410360
LSUID: 893416490
Email: Vohler1@lsu.edu


This program simulates the CLOCK (second chance bit) page replacement algorithm. It takes input from the two sources, command line arguments, and a a specified file that simulates a processor requesting to read and write to pages. It outputs the result of each request to a file of specified name. It also outputs some stats on the program's run, regarding faults and time taken.


How to run:

Execute the following lines in an environment with JDK. UNIX classes server works. If trying to run on local device, input and output file names MAY need to include filePath.

javac App.java
java App pageFrames inputFileName.txt accessTime swapInTime swapOutTime outputFileName.txt


Example input given here:

	javac App.java
	java App 20 pageref.txt 1 10 20 trace.txt

The requests consist of the letter R for read or W for write, followed by a number. The number represents the page to be swapped in. Each line in the file of "requests" should look like:
R 11
W 39
W 419

The output will come in lines like:
W 28 F 13 1 10 20
R 5 H -1 1 0 0

The first two fields are the request. The third is a Fault or a Hit. Fourth is the victim page chosen to be evicted. Fifth is time spent accessing page. Sixth is time spent swapping in page. Finally the seventh is time spent swapping out the page.
After each of those lines will be the report at the end. It will look similar to this one:

	total page references: 550
	total page faults on read references: 331
	total page faults on write references: 86
	total access time: 550
	total time swapping in pages: 4170
	total time swapping out pages: 2140

At the end it will also print "complete" to console.
