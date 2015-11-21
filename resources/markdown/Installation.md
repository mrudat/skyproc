# Installation
## Requirements
* [Java 7](http://www.oracle.com/technetwork/java/javase/downloads/jre7-downloads-1637588.html)
* Some fantastic SkyProc patchers

## Installation Process
1. Extract or Install via Mod Manager

    Either manually merge the `Data/` folder from the zip into your `Skyim/Data/` folder, or use a mod manager program.

    If you are using a lesser known mod manager program, just confirm that all the files were successfully extracted from the .zip.

2. Update your Java

    Just to make sure everything runs okay, update Java to the newest version. 

    SkyProc programs require [Java 7](http://www.java.com/en/download/).

3. Double check that Java associations are correct.

    Sometimes `.jar` files get associated with other programs like the "Java(TM) Web Start Launcher", or Unzipper programs (especially if you've played Minecraft).

    These startup programs are not correct.

    You want `.jar` files to open with "Java(TM) Platform SE binary".

    You can fix this by using the [JarFix tool](http://www.softpedia.com/get/Others/Miscellaneous/Jarfix.shtml), or

    You can fix this manually by right clicking the `Data/SkyProc Patchers/SUM/SUM.jar` file and clicking "Opens With:", and then selecting "Java(TM) Platform SE binary".

4. Download and Install Some SkyProc patchers

    SUM does nothing by itself, so go get yourself some patchers.

5. Download BOSS so SUM can use it. Adjust load order as desired.

    SUM uses BOSS to know which order to run the patchers. It is highly recommended you download it.

    NOTE: Be aware that shuffling the order of SkyProc patchers has the possibility of screwing up savegames that were played on the old ordering. It is highly recommended that once you pick a patcher order, you stick with it. 

    When downloading BOSS and running SUM, double check that BOSS order matches the order you've been patching manually up until now. If it doesn't, you may want to adjust BOSS so that it matches. If you need to adjust patching order away from BOSS's default settings, use [BUM](http://skyrim.nexusmods.com/mods/311). 

6. Use the SUM.jar file to run the Application

    The patcher is located in `Data/SkyProc Patchers/SUM/SUM.jar`

    SUM will then look in your SkyProc Patchers for compatible patchers that you have installed. You should see a list of your installed SkyProc patchers once SUM starts up.

    Tweak any settings, and then press patch! 

    You do not need to run the individual patchers themselves. SUM's job is to do that for you.

    If you're having issues getting SUM to start up, The most common reason is Windows Permission issues. There is a [great article](Troubleshooting#permissions) covering how to deal with windows permission issues if you encounter them. 

    Otherwise check out the whole [Troubleshooting](Troubleshooting) area for other solutions. 

7. Repatch your SkyProc patches when you add/update/remove/shuffle mods

    If you add or remove any mods, you must rerun SUM to update your custom patches.

    You can do this simply by opening up the SUM program, and then patching again. The patcher will check your setup and determine if a new patch is needed; It may decide you do not need a patch and just close. 

    If you want to force fresh patches for any reason, you can force the patch by clicking the checkbox at the top right. 
