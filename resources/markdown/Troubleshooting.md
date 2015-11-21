# Troubleshooting

The SkyProc Application isn't running properly: Wacky error messages, or empty patches. This issue can be from a few sources. This is a running list of things that could be causing issues for you:

## Skyproc patcher does not appear in SUM
Very old SkyProc patchers (released before SUM) may not be compatible with SUM. If a patcher does not show up on SUM's list, this is most likely the case. In order to make them compatible, the mod author needs to rebuild their patcher with the most recent SkyProc.

You can always use the old fashioned multiple SkyProc patching protocol to deal with the issue.

## Your Java is out of date. Make sure you have Java 7.
FIX: Try [updating your Java](http://www.oracle.com/technetwork/java/javase/downloads/jre7-downloads-1637588.html). Make sure you install the correct 32/64 bit version.

## You have the wrong version (32/64 bit) of Java installed.
Users who install the wrong version typically don't export right, and/or the application never exits during the export.

FIX: Confirm you have the correct 32-bit or 64-bit version of Java installed.

## You have .jar files associated with something besides Java. 
Some people have .jar files associated with WinRar or other programs besides Java.

It has been reported that you have to change the default association back to Java; Simply doing "Open With..." without setting Java as the default is not good enough.

Optionally you can use the [JarFix tool](http://www.softpedia.com/get/Others/Miscellaneous/Jarfix.shtml) to restore your Java setup the way it should be. 

## <a name="permissions"></a>Windows isn't giving the patcher permission. This is a common issue that people run into.
Here are some options to try. Try any or all:

* Select the Skyrim folder, right-click, and select properties. You go to security, and click on the edit permissions button. Make sure to allow ANYONE to do everything with the folder. (Obviously, you'll need an admin-account to do so). The system will set the properties of the entire folder accordingly.

* Move your Skyrim installation out of Program Files. Many modders move Skyrim out of Program Files because Windows puts especially strict protection protocols on that folder, and moving it out just simplifies your life.

    This is actually quite easy with [Steam Mover](http://download.cnet.com/Steam-Mover/3000-2248_4-75764205.html).

* [Take ownership](http://www.howtogeek.com/howto/windows-vista/add-take-ownership-to-explorer-right-click-menu-in-vista/). Thanks R4444KKON

## You're a non-English user and need to tweak some more stuff.
First, try setting the Language in the Other Settings panel to match your Skyrim language.

If this doesn't work, or if it doesn't open in the first place, try this tip from niennasill:

"Non-english windows users must be change their (windows) format, location, region and display language to English before executing [the patcher]...

To do this they must be download english language pack ( like 50 / 100 mb ) from windows update."

## There is an exclaimation mark (!) in your folder path to Skyrim.
People have reported this causing issues.

## The Windows Registry doesn't have Java as the default "runtime environment", but rather a strange web version of Java.
You can easily tell this is the case if when you open the patcher application, a window that says "Java" pops up, and it looks like it's loading up the application. When the patcher is working properly, you should NOT see a Java loading window, but rather, the application should open immediately to the user interface.

FIX:

1. You need Administrator rights to manually edit the Windows Registry.
2. Run regedit program via the Windows "Start" menu, type `regedit` in the run window there.
3. In the left-hand pane, go down to this subdirectory of the regedit file `HKEY_CLASSES_ROOT/Applications/javaw.exe/shell/open/command`
4. In the right-hand pane, right-click on "Name" and choose "change" or "edit". A little window pops open.
5. On the lower line for the Value, delete anything AFTER the `...bin\` and put this instead `javaw.exe" -jar "%1" %*`
    You can copy-paste this line. Make sure it is written exactly as above, with the quotes and everything. Afterwards the new value should read something like this `"C:\Program Files(x86)\Java\jre7\bin\javaw.exe" -jar "%1" %*` 

    This assumes that you have the Java JRE installed in `C:\Program Files(x86)\...`

Thanks to Tommy_H for the fix!

Original Link (in german): http://www.windows-7-forum.net/windows-7-software/18921-jar-datei-java-laesst-starten-2.html 

Otherwise, please submit a report to me. 

## Patching is running exceptionally slow.
Depending on your mod setup and your patchers, there might just be a lot to do. With a slow computer this can take a while sometimes.

However, A possible cause is that the program doesn't have enough memory. Try bumping it up in SUM and seeing if it runs better. If you make it too high, though, Windows may reject the program's request for the memory. In that case you'll have to bump it back down.

If you're convinced it's exceptionally long, or that it takes way too much memory, feel free to contact the mod author and give him your results. 

## <a name="ctd"></a>You get a Crash when opening Skyrim
This could be due to one of a few common issues:

1.  Your load order is wrong.

    Install BOSS and use it. SUM will use BOSS by default if it's installed.

2.  You have "circular masters".
 
    This occurs when two mods both have each other marked as masters, and so they both require the other to be loaded first, which is impossible because it's circular. Skyrim doesn't know what to do at that point and crashes.

    You probably didn't follow protocol when dealing with a [Bashed Patch](FAQ#How do I handle a Bashed Patch with SUM?).

3.  A patch did not generate correctly and it is unreadable by Skyrim.

    Try disabling patchers and locating which is causing the issue, then contact that author.

## "Could not find the main class: ..." Error
This is most likely due to either not having Java 7 installed (you might think you do, but you have Java 6 instead), or you might have both Java 6 and 7 installed and your computer is not opening the program with Java 7.

FIX:

1. Uninstall Java 6
2. Install Java 7
3. If the above doesn't work and you have Java 7, you can try [this helper program](http://www.softpedia.com/get/Others/Miscellaneous/Jarfix.shtml) or [set up a .bat file](#bat).

## A patcher is running slowly while importing or creating a patch for a large mod setup.
This might be due to it running out of memory. This causes massive slowdowns when the program hits its memory limit

FIX:
Allocate more memory in the Other Settings panel

## <a name="bat"></a>Setting up a bat file to force-start the program, or give it special instructions.
Some versions of the Java installer don't modify the correct registry entries for the default bat file to work properly. A more direct/customized approach is needed.

FIX:

1. Create a bat file.

   Make a text file and rename the file extension to `.bat`.

2. Add the following to it: 
```bat
start "C:\Program Files\Java\jre7\bin\javaw.exe" -jar "[THE JAR NAME]"
```
    Where the path is customized/directed to wherever your javaw.exe is located. This is usually in the path setup shown above.
    Any directories with spaces need to be enclosed in quotation marks, as above. Putting the full path in speech marks / quotation marks does not work. 

3. Put it alongside the jar application and then double click the bat file.

## Your .esp was created, but the CK/Tes5Edit are having trouble with the .esp, or are showing the .esp has errors!
This is either due to the SkyProc mod author making code that generated a patch with that error, or SkyProc itself may need updating. (It's always expanding and being updated)

First, contact the mod author and see what they say. Have them contact me if they believe SkyProc internals need updating.

## Why doesn't SkyProc mod _____ show up in SUM?
Most likely this means it's an older SkyProc mod that was created before SUM came out. Poke the author and make them update their patcher to the latest standards.

You can check the debug logs to find out for sure that SUM saw the patcher, but skipped it `Data/SkyProc Patchers/SUM/SkyProcDebug/Jar Hooking.txt`

Authors: as always, contact Leviathan1753 if you need any tips or guides on how to update.
