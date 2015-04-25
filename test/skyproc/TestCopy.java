package skyproc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;
import lev.debug.LDebug;

import org.junit.Test;

import skyproc.FormID;
import skyproc.GRUP;
import skyproc.MajorRecord;
import skyproc.Mod;
import skyproc.ModListing;
import skyproc.NiftyFunc;
import skyproc.SPGlobal;
import skyproc.exceptions.BadRecord;
import skyproc.gui.SPDefaultGUI;
import skyproc.gui.SPProgressBarPlug;
/*
public class TestCopy extends TestCase {

    // FIXME determine this at runtime!
    private static final String SKYRIM_DATA = "C:\\Program Files (x86)\\Steam\\steamapps\\common\\Skyrim\\Data\\";

    private ArrayList<FormID> badIDs;

    private SPDefaultGUI gui;

    private boolean streaming = false;

    public void setUp() throws Exception {
	super.setUp();

	SPGlobal.globalLock.lock();

	SPGlobal.createGlobalLog();
	LDebug.timeElapsed = true;
	SPGlobal.setStreamMode(streaming);
	SPGlobal.logging(true);
	SPGlobal.setGlobalPatch(new Mod(new ModListing("Test", false)));
	badIDs = new ArrayList<>();
	ModListing skyrim = new ModListing("Skyrim.esm");
	badIDs.add(new FormID("018A45", skyrim)); // RiverwoodZone
	badIDs.add(new FormID("00001E", skyrim)); // NoZoneZone
	SPGlobal.setTesting(true);
	gui = new SPDefaultGUI("Copy",
		"A tester program meant to flex SkyProc.");

	SPGlobal.pathToData = SKYRIM_DATA;
    }

    public void tearDown() throws Exception {
	SPGlobal.globalLock.unlock();
	gui.finished();
	LDebug.wrapUp();
	super.tearDown();
    }

    @Test
    public void testCopy() throws IOException, BadRecord {
	SPProgressBarPlug.pause(true);

	boolean passed = true;
	Mod merger = new Mod(new ModListing("tmpMerge.esp"));
	merger.addAsOverrides(SPGlobal.getDB());
	for (FormID f : badIDs) {
	    merger.remove(f);
	}

	Mod patch = new Mod(new ModListing("Test.esp"));
	patch.setFlag(Mod.Mod_Flags.STRING_TABLED, false);
	patch.setAuthor("Leviathan1753");

	for (GRUP g : merger) {
	    for (Object o : g) {
		MajorRecord m = (MajorRecord) o;
		m.copyOf(patch);
	    }
	}

	patch.export(new File(SPGlobal.pathToData + patch.getName()));
	passed = passed && NiftyFunc.validateRecordLengths(SPGlobal.pathToData + "Test.esp", 10);

	SPProgressBarPlug.pause(false);
	SPProgressBarPlug.incrementBar();
	
	org.junit.Assert.assertEquals(true, passed);
	
    }

}
*/