# Frequently Asked Questions

## What is SUM?
SUM is a manager program to help streamline the use of multiple SkyProc patchers. Instead of manually patching each mod you have, just run SUM once.

1. SUM handles correctly running multiple SkyProc patchers.
2. Primary method for setting alternate language preferences for SkyProc patchers.
3. Allows you to designate the amount of memory to allocate for all your SkyProc patchers.
4. (Experimental) SUM will merge all your SkyProc patches into one esp file.

The end result is a quick and easy repatch whenever you've added or removed other mods or patchers.

## What is SkyProc?
As a user, you do not need to download or use SkyProc directly. Just go download some SkyProc -patchers- by mod authors, and then use SUM to run them.

SkyProc is a Java library used by modders to create patcher programs. They use the programs to do specific tasks and make custom patches for you.

## How do I handle a Bashed Patch with SUM?
To use SUM with a Bashed Patch:

1. Make sure the Bashed Patch is before any SkyProc mod in your load order.
2. Rebuild the Bashed Patch, making sure it didn't process any SkyProc mods. It shouldn't if it comes before them in your load order.
3. Run SUM to create new patches based on the updated Bashed Patch.

Not doing this procedure correctly could lead to circular masters, and you'll get a [CTD when starting up Skyrim](Troubleshooting#ctd).

## Do I need to erase my existing patches when installing SUM?
Nope! Just install SUM and run it. Each patcher will overwrite their old patches just like normal.

## How do I block a mod from being imported into SUM?
Sometimes you don't want a specific mod imported into your SkyProc patchers, for whatever reason.
You can achieve this easily by simply adding it to the blocklist found in `My Documents/My Games/Skyrim/SkyProc/SUM Mod Blocklist.txt`

NOTE: This will block the mod for ALL patchers. If you want to block the mod for a single patcher, add it to that patchers blocklist instead `Data/SkyProc Patchers/[The Patcher]/Files/Blocklist.txt` 

If you're blocking a mod because you found that it broke a patcher, please notify the mod author involved so it can be fixed, rather than just avoided. Thanks!

## How do I handle multiple SkyProc mods if I don't want to use SUM, or a patcher is incompatible?
To start off, you can treat all SkyProc patchers that are run/managed by SUM as a single patcher for the purposes of this guide.

The central important idea is that no SkyProc mods that come AFTER the one you are running should be active. 

<a name="protocol"></a>There is a "protocol" you need to follow to get a proper repatch.

1. Disable all your SkyProc mod esp files.
2. Run the first SkyProc patcher.
3. Enable that SkyProc patcher's .esp file.
4. Run the second patcher, then activate it's esp, and repeat for all patchers you have.

This will make sure that no SkyProc mod that comes AFTER the one you are running is active and gets imported.

If you don't follow this procedure, you'll most likely end up with circular masters, and you'll get a CTD when starting up Skyrim. 

