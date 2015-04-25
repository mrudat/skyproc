package skyproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.DataFormatException;

import lev.LImport;
import skyproc.exceptions.BadParameter;
import skyproc.exceptions.BadRecord;

// TODO: Auto-generated Javadoc
/**
 * A more accurate representation of a modname: A combination of mod name
 * without the suffix and its master flag. Skyrim.esm, for example, would be
 * "Skyrim" with a true master flag.
 *
 * @author Justin Swanson
 */
@SuppressWarnings("serial")
public class ModListing extends SubRecord<ModListing> implements
		Comparable<ModListing> {

	/** The Constant type. */
	private final static ArrayList<String> type = new ArrayList<>(
			Arrays.asList(new String[] { "MAST", "DATA" }));

	/** Base Skyrim ModListing. */
	static ModListing skyrim = new ModListing("Skyrim.esm");

	/** Skyrim update ModListing. */
	static ModListing update = new ModListing("Update.esm");

	/** TODO what is this? */
	SubString mast = SubString.getNew("MAST", true);

	/**
	 * True if this is a master file. eg. "Skyrim.esm", false indicates a patch,
	 * eg. "Unofficial Skyrim Patch.esp"
	 */
	boolean master = false;

	/** The str hash. */
	int strHash = 0;

	/**
	 * ModListing objects are used to uniquely identify mods via name and master
	 * tag.
	 *
	 * @param name
	 *            The name to give to a mod. Eg. "Skyrim" (with no suffix)
	 * @param master
	 *            The master tag. (.esp or .esm)
	 */
	public ModListing(String name, Boolean master) {
		this(name);
		this.master = master;
	}

	/**
	 * Instantiates a new mod listing.
	 *
	 * @param nameWithSuffix
	 *            String containing the modname AND suffix. Eg "Skyrim.esm"
	 */
	public ModListing(String nameWithSuffix) {
		this();
		setString(nameWithSuffix);
	}

	/**
	 * Instantiates a new mod listing.
	 */
	ModListing() {
		super();
	}

	/**
	 * Sets the string.
	 *
	 * @param in
	 *            the new string
	 */
	final void setString(String in) {
		String upper = in.toUpperCase();
		if (upper.contains(".ESM")) {
			setMasterTag(true);
			in = in.substring(0, upper.indexOf(".ES"));
		} else if (upper.contains(".ESP")) {
			setMasterTag(false);
			in = in.substring(0, upper.indexOf(".ES"));
		}
		mast.setString(in);
		strHash = 259 + this.mast.hashUpperCaseCode();
	}

	/**
	 * Prints the mod name and appropriate suffix (.esp or .esm)
	 *
	 * @return the string
	 */
	@Override
	public String print() {
		if (master) {
			return printNoSuffix() + ".esm";
		} else {
			return printNoSuffix() + ".esp";
		}
	}

	/**
	 * Prints the mod name with no suffix.
	 *
	 * @return the string
	 */
	public String printNoSuffix() {
		return mast.print();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see skyproc.SubRecord#toString()
	 */
	@Override
	public String toString() {
		return print();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see skyproc.SubRecord#export(skyproc.ModExporter)
	 */
	@Override
	void export(ModExporter out) throws IOException {
		mast.string = print(); // Put the suffix in the record
		mast.export(out);
		SubData data = new SubData("DATA");
		data.initialize(8);
		data.export(out);
		setString(print()); // Take suffix back out of record
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see skyproc.Record#parseData(lev.LImport, skyproc.Mod)
	 */
	@Override
	final void parseData(LImport in, Mod srcMod) throws BadRecord,
			DataFormatException, BadParameter {
		switch (getNextType(in)) {
		case "MAST":
			mast.parseData(in, srcMod);
			setString(mast.string);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see skyproc.SubRecord#getNew(java.lang.String)
	 */
	@Override
	ModListing getNew(String type_) {
		return new ModListing();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see skyproc.Record#getContentLength(skyproc.ModExporter)
	 */
	@Override
	int getContentLength(ModExporter out) {
		return mast.getContentLength(out) + 14 + 4; // 14 for DATA, 4 for .esp
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see skyproc.Record#isValid()
	 */
	@Override
	boolean isValid() {
		return mast.isValid();
	}

	/**
	 * Sets the master tag.
	 *
	 * @param in
	 *            the new master tag
	 */
	void setMasterTag(Boolean in) {
		master = in;
	}

	/**
	 * Gets the master tag.
	 *
	 * @return the master tag
	 */
	boolean getMasterTag() {
		return master;
	}

	/**
	 * Checks if the modname's are equal (case ignored), and the master tags are
	 * the same.
	 *
	 * @param obj
	 *            Another ModListing
	 * @return True if modname's are equal (case ignored), and the master tags
	 *         are the same.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Mod) {
			obj = ((Mod) obj).getInfo();
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ModListing other = (ModListing) obj;
		if (this.mast != other.mast
				&& (this.mast == null || !this.mast
						.equalsIgnoreCase(other.mast))) {
			return false;
		}
		if (this.master != other.master) {
			return false;
		}
		return true;
	}

	/**
	 * Hash code.
	 *
	 * @return A hashcode with modname and master flag incorperated.
	 */
	@Override
	public int hashCode() {
		int hash = strHash;
		hash = 37 * hash + (this.master ? 1 : 0);
		return hash;
	}

	/**
	 * Compare funtion is as follows:<br>
	 * 1) A master always is less than a non-master plugin<br>
	 * 2) The global patch always is greater<br>
	 * 3) A plugin that is on the active plugins list via importActivePlugins()
	 * comes before a plugin that was created manually. <br>
	 * 4) Remaining plugins are ordered in the same order they were created in
	 * the code.
	 *
	 * @param rhs
	 *            the rhs
	 * @return Whether this modlisting is >/==/< the parameter.
	 */
	@Override
	public int compareTo(ModListing rhs) {
		// ModListing rhs = (ModListing) o;
		if (equals(rhs)) {
			return 0;
		}
		if (master && !rhs.master) {
			return -1;
		}
		if (!master && rhs.master) {
			return 1;
		}
		if (SPGlobal.getGlobalPatch() != null) {
			if (equals(SPGlobal.getGlobalPatch().getInfo())) {
				return 1;
			}
			if (rhs.equals(SPGlobal.getGlobalPatch().getInfo())) {
				return -1;
			}
		}
		if (equals(skyrim)) {
			return -1;
		}
		if (rhs.equals(skyrim)) {
			return 1;
		}
		if (equals(update)) {
			return -1;
		}
		if (rhs.equals(update)) {
			return 1;
		}
		boolean thisActive = SPDatabase.activePlugins.contains(this);
		boolean rhsActive = SPDatabase.activePlugins.contains(rhs);
		if (thisActive) {
			if (!rhsActive) {
				return -1;
			} else {
				int comp = SPDatabase.activePlugins.indexOf(this)
						- SPDatabase.activePlugins.indexOf(rhs);
				if (comp != 0) {
					return comp;
				} else {
					return 1;
				}
			}
		} else {
			if (rhsActive) {
				return 1;
			} else {
				int comp = SPDatabase.addedPlugins.indexOf(this)
						- SPDatabase.addedPlugins.indexOf(rhs);
				if (comp != 0) {
					return comp;
				} else {
					return 1;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see skyproc.Record#getTypes()
	 */
	@Override
	ArrayList<String> getTypes() {
		return type;
	}
}