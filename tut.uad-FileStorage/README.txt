Unlocking Android - FileStorage
------------------------------------

Android code example that walks through
creating and writing text to a file, then 
reading from a file, then reading from a raw
resource, then reading from an XML resource, and 
finally reading and writing to the SD card. 

NOTE that the final step, reading and writing to the 
sdcard will not work in the emulator unless you
first create an SD image with the mksdcard tool.
See the text in the book for details (or use the instructions here:
http://code.google.com/android/reference/othertools.html#mksdcard).

--------------------------------------

Checkout:
svn co http://unlocking-android.googlecode.com/svn/chapter5/trunk/FileStorage/


Eclipse:
Setup a SVN repository for the UAD code project (http://unlocking-android.googlecode.com/svn). 
Then checkout chapter5/trunk/FileStorage as an Eclipse project. 