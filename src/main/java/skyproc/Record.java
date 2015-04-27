package skyproc;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.zip.DataFormatException;

import lev.*;
import skyproc.exceptions.BadParameter;
import skyproc.exceptions.BadRecord;

// TODO: Auto-generated Javadoc
/**
 * Abstract class outlining functions for a generic record.
 *
 * @author Justin Swanson
 */
@SuppressWarnings("serial")
public abstract class Record implements Serializable {

	/** The Constant typeLists. */
	final static HashMap<String, ArrayList<String>> typeLists = new HashMap<>();

	/**
	 * Instantiates a new record.
	 */
	Record() {
	}

	/**
	 * Parses the data.
	 *
	 * @param in
	 *            the data to parse
	 * @param srcMod
	 *            the source mod
	 * @throws BadRecord
	 * @throws BadParameter
	 * @throws DataFormatException
	 */
	void parseData(LImport in, Mod srcMod) throws BadRecord, BadParameter,
			DataFormatException {
		in.skip(getIdentifierLength() + getSizeLength());
	}

	/**
	 * Parses the data.
	 *
	 * @param in
	 *            the data to parse
	 * @param srcMod
	 *            the source mod
	 * @throws BadRecord
	 * @throws BadParameter
	 * @throws DataFormatException
	 */
	final void parseData(ByteBuffer in, Mod srcMod) throws BadRecord,
			BadParameter, DataFormatException {
		parseData(new LShrinkArray(in), srcMod);
	}

	/**
	 * Checks if is valid.
	 *
	 * @return true, if is valid
	 */
	boolean isValid() {
		return true;
	}

	/**
	 * Returns a short summary/title of the record.
	 *
	 * @return A short summary/title of the record.
	 */
	@Override
	public abstract String toString();

	/**
	 * Returns the contents of the record, or exports them to a log, depending
	 * on the record type.
	 *
	 * @return The contents of the record, OR the empty string, depending on the
	 *         record type.
	 */
	public abstract String print();

	/**
	 * Gets the types.
	 *
	 * @return the types
	 */
	abstract ArrayList<String> getTypes();

	/**
	 * Gets the type list.
	 *
	 * @param t
	 *            the t
	 * @return the type list
	 */
	static ArrayList<String> getTypeList(String t) {
		ArrayList<String> out = typeLists.get(t);
		if (out == null) {
			out = new ArrayList<>(Arrays.asList(new String[] { t }));
			typeLists.put(t, out);
		}
		return out;
	}

	/**
	 * Gets the type.
	 *
	 * @return The type string associated with record.
	 */
	public String getType() {
		return getTypes().get(0);
	}

	/**
	 * Gets a new Record
	 *
	 * @return the new Record
	 */
	Record getNew() {
		return getNewRecord();
	}

	/**
	 * Gets a new R.
	 *
	 * @param <R>
	 *            the generic type
	 * @return the new record
	 */
	<R extends Record> R getNewRecord() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Gets the next type.
	 *
	 * @param in
	 *            the in
	 * @return the next type
	 * @throws BadRecord
	 *             the bad record
	 */
	static String getNextType(LImport in) throws BadRecord {
		return (Ln.arrayToString(in.getInts(0, 4)));
	}

	/**
	 * Export.
	 *
	 * @param out
	 *            the out
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	void export(ModExporter out) throws IOException {
		if (isValid()) {
			out.write(getType().toString());
			out.write(getContentLength(out));
		}
	}

	/**
	 * Gets the record length.
	 *
	 * @param in
	 *            the in
	 * @return the record length
	 */
	public int getRecordLength(LImport in) {
		return Ln
				.arrayToInt(in.getInts(getIdentifierLength(), getSizeLength()))
				+ getSizeLength() + getIdentifierLength() + getFluffLength();
	}

	/**
	 * Extract record data.
	 *
	 * @param in
	 *            the in
	 * @return the l import
	 */
	LImport extractRecordData(LImport in) {
		return extractData(in, getRecordLength(in));
	}

	/**
	 * Extract data.
	 *
	 * @param in
	 *            the in
	 * @param size
	 *            the size
	 * @return the l import
	 */
	LImport extractData(LImport in, int size) {
		LImport extracted;
		if (SPGlobal.streamMode
				&& (in instanceof RecordShrinkArray || in instanceof LInChannel)) {
			extracted = new RecordShrinkArray(in, size);
		} else {
			extracted = new LShrinkArray(in, size);
		}
		in.skip(size);
		return extracted;
	}

	/**
	 * Gets the header length.
	 *
	 * @return the header length
	 */
	int getHeaderLength() {
		return getIdentifierLength() + getSizeLength() + getFluffLength();
	}

	/**
	 * Gets the length of the record identifier, eg. "ARMO", this is always 4.
	 *
	 * @return the identifier length
	 */
	final int getIdentifierLength() {
		return 4;
	}

	/**
	 * Gets the size length.
	 *
	 * FIXME this is presumably the number of bytes for the record length, so
	 * probably 1 or 2.
	 *
	 * @return the size length
	 */
	abstract int getSizeLength();

	/**
	 * Gets the fluff length.
	 *
	 * TODO what is this?
	 *
	 * @return the fluff length
	 */
	abstract int getFluffLength();

	/**
	 * Gets the total length.
	 *
	 * @param out
	 *            the out
	 * @return the total length
	 */
	int getTotalLength(ModExporter out) {
		return getContentLength(out) + getHeaderLength();
	}

	/**
	 * Gets the content length.
	 *
	 * @param out
	 *            the out
	 * @return the content length
	 */
	abstract int getContentLength(ModExporter out);

	/**
	 * {@link SPGlobal#newSyncLog(String)}
	 */
	void newSyncLog(String fileName) {
		SPGlobal.newSyncLog(fileName);
	}

	/**
	 * {@link SPGlobal#logging()}
	 */
	boolean logging() {
		return SPGlobal.logging();
	}

	/**
	 * {@link SPGlobal#logMain(String, String...)}
	 */
	void logMain(String header, String... log) {
		SPGlobal.logMain(header, log);
	}

	/**
	 * {@link SPGlobal#logSync(String, String...)}
	 */
	void logSync(String header, String... log) {
		SPGlobal.logSync(getType().toString(), log);
	}

	/**
	 * {@link SPGlobal#logError(String, String...)}
	 */
	void logError(String header, String... log) {
		SPGlobal.logError(header, log);
	}

	/**
	 * {@link SPGlobal#logSpecial(Enum, String, String...)}
	 */
	void logSpecial(@SuppressWarnings("rawtypes") Enum e, String header,
			String... log) {
		SPGlobal.logSpecial(e, header, log);
	}

	/**
	 * {@link SPGlobal#log(String, String...)}
	 */
	void log(String header, String... log) {
		SPGlobal.log(header, log);
	}

	/**
	 * {@link SPGlobal#logMod(Mod, String, String...)}
	 */
	static void logMod(Mod srcMod, String header, String... data) {
		SPGlobal.logMod(srcMod, header, data);
	}

	/**
	 * {@link SPGlobal#newLog(String)}
	 */
	void newLog(String fileName) {
		SPGlobal.newLog(fileName);
	}

	/**
	 * {@link SPGlobal#flush()}
	 */
	void flush() {
		SPGlobal.flush();
	}
}
