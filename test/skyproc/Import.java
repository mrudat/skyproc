package skyproc;

import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;
import lev.debug.LDebug;

import org.junit.Test;

import skyproc.FormID;
import skyproc.Mod;
import skyproc.ModListing;
import skyproc.SPGlobal;
import skyproc.SPImporter;
import skyproc.SkyProcTester;
import skyproc.exceptions.MissingMaster;
import skyproc.gui.SPDefaultGUI;
/*
public class Import extends TestCase {

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
	gui = new SPDefaultGUI("Import",
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
    public void testImport() throws IOException, MissingMaster {
	SPImporter.importActiveMods();
	Mod patch = new Mod(new ModListing("Test.esp"));
	patch.setFlag(Mod.Mod_Flags.STRING_TABLED, false);
	patch.addAsOverrides(SPGlobal.getDB());
	patch.allFormIDs();
    }

}
*/