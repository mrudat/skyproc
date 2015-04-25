package skyproc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.TestCase;
import lev.Ln;
import lev.debug.LDebug;

import org.junit.Test;

import skyproc.exceptions.BadMod;
import skyproc.exceptions.BadRecord;
import skyproc.gui.SPDefaultGUI;
import skyproc.gui.SPProgressBarPlug;
/*
public class ValidateAll extends TestCase {

    // FIXME determine this at runtime!
    private static final String SKYRIM_DATA = "C:\\Program Files (x86)\\Steam\\steamapps\\common\\Skyrim\\Data\\";

    private ArrayList<FormID> badIDs;

    private SPDefaultGUI gui;

    private boolean streaming = false;
    
    static GRUP_TYPE[] types;

    static ArrayList<GRUP_TYPE> skip = new ArrayList<>(Arrays.asList(new GRUP_TYPE[]{GRUP_TYPE.BOOK}));


    public void setUp() throws Exception {
	super.setUp();

	SPGlobal.globalLock.lock();

	types = GRUP_TYPE.values();
	
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
	gui = new SPDefaultGUI("Validate All",
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
    public void testValidateAll() throws Exception {
	ModTestPackage[] mods = {
		new ModTestPackage("Skyrim.esm", "Skyrim.esm", "Update.esm"),
		new ModTestPackage("Dawnguard.esm", "Skyrim.esm", "Update.esm",
			"Dawnguard.esm"),
		new ModTestPackage("Dragonborn.esm", "Skyrim.esm",
			"Update.esm", "Dragonborn.esm") };
	SPGlobal.setCheckMissingMasters(false);
	for (ModTestPackage p : mods) {
	    org.junit.Assert.assertTrue(validate(p));
	}
    }

    private static class ModTestPackage {
	ModListing main;
	ModListing[] importList;

	public ModTestPackage(String main, String... list) {
	    this.main = new ModListing(main);
	    importList = new ModListing[list.length];
	    for (int i = 0; i < list.length; i++) {
		importList[i] = new ModListing(list[i]);
	    }
	}
    }

    private boolean validate(ModTestPackage p) throws Exception {

	skyproc.SubStringPointer.shortNull = false;

	FormID.allIDs.clear();

	SPProgressBarPlug.reset();
	SPProgressBarPlug.setMax(types.length);

	boolean exportPass = true;
	boolean idPass = true;
	for (GRUP_TYPE g : types) {
	    if (!GRUP_TYPE.unfinished(g) && !GRUP_TYPE.internal(g)
		    && !skip.contains(g)) {
		for (ModListing m : p.importList) {
		    SPImporter.importMod(m, SPGlobal.pathToData, g);
		}
		if (!test(g, p)) {
		    SPProgressBarPlug.setStatus("FAILED: " + g);
		    exportPass = false;
		    break;
		}
		SPProgressBarPlug.setStatus("Validating DONE");
		for (FormID id : FormID.allIDs) {
		    if (!id.isNull() && id.getMaster() == null
			    && !badIDs.contains(id)) {
			System.out.println("A bad id: " + id);
			System.out
				.println("Some FormIDs were unstandardized!!");
			return false;
		    }
		}
		SPGlobal.reset();
	    }
	}

	SPGlobal.reset();
	return exportPass && idPass;
    }

    private boolean test(GRUP_TYPE type, ModTestPackage p) throws IOException, BadRecord, BadMod {
	System.out.println("Testing " + type + " in " + p.main);
	SPProgressBarPlug.setStatus("Validating " + type);
	SPProgressBarPlug.pause(true);

	boolean passed = true;
	Mod patch = new Mod(new ModListing("Test.esp"));
	patch.setFlag(Mod.Mod_Flags.STRING_TABLED, false);
	patch.addAsOverrides(SPDatabase.getMod(p.main), type);
	// Test to see if stream has been prematurely imported
	if (SPGlobal.streamMode && type != GRUP_TYPE.NPC_) {
	    GRUP g = patch.GRUPs.get(type);
	    if (!g.listRecords.isEmpty()) {
		MajorRecord m = (MajorRecord) g.listRecords.get(0);
		if (m.subRecords.map.size() > 2) {
		    System.out.println("Premature streaming occured: " + m);
		    return false;
		}
	    }
	}
	// Remove known bad ids
	for (FormID f : badIDs) {
	    patch.remove(f);
	}
	patch.setAuthor("Leviathan1753");
        for (ModListing depend : p.importList)
        {
            patch.addMaster(depend);
        }
	try {
	    patch.export(new File(SPGlobal.pathToData + patch.getName()));
	} catch (BadRecord ex) {
	    SPGlobal.logException(ex);
	    System.out.println("Record Lengths were off.");
	}
	passed = passed && NiftyFunc.validateRecordLengths(SPGlobal.pathToData + "Test.esp", 10);
	File validF = new File("Validation Files/" + type.toString() + "_" + p.main.printNoSuffix() + ".esp");
	if (validF.isFile()) {
	    passed = Ln.validateCompare(SPGlobal.pathToData + "Test.esp", validF.getPath(), 10) && passed;
	} else {
	    System.out.println("Didn't have a source file to validate bytes to.");
	}

	SPProgressBarPlug.pause(false);
	SPProgressBarPlug.incrementBar();
	return passed;
    }
}
*/