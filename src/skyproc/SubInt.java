/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.DataFormatException;
import lev.LExporter;
import lev.LShrinkArray;
import skyproc.exceptions.BadParameter;
import skyproc.exceptions.BadRecord;

/**
 *
 * @author Justin Swanson
 */
class SubInt extends SubRecord {

    int data;
    boolean valid = false;

    SubInt(Type type) {
	super(type);
    }

    @Override
    SubRecord getNew(Type type) {
	return new SubFloat(type);
    }

    @Override
    int getContentLength(Mod srcMod) {
	return 4;
    }

    @Override
    void parseData(LShrinkArray in) throws BadRecord, DataFormatException, BadParameter {
	super.parseData(in);
	data = in.extractInt(4);
	if (logging()) {
	    logSync(toString(), "Setting " + toString() + " to : " + print());
	}
	valid = true;
    }

    @Override
    public String print() {
	if (isValid()) {
	    return String.valueOf(data);
	} else {
	    return super.toString();
	}
    }

    @Override
    void export(LExporter out, Mod srcMod) throws IOException {
	if (isValid()) {
	    super.export(out, srcMod);
	    out.write(data);
	}
    }

    @Override
    public void clear() {
	data = 0;
    }

    @Override
    Boolean isValid() {
	return valid;
    }

    @Override
    ArrayList<FormID> allFormIDs (boolean deep) {
	return new ArrayList<FormID>(0);
    }
}
