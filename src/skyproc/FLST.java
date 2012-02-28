/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyproc;

/**
 *
 * @author Justin Swanson
 */
public class FLST extends MajorRecord {

    private static Type[] types = { Type.FLST };

    SubList<SubForm> LNAMs = new SubList<SubForm>(new SubForm(Type.LNAM));

    public FLST() {
	super();

	subRecords.add(LNAMs);
    }

    @Override
    Type[] getTypes() {
	return types;
    }

    @Override
    Record getNew() {
	return new FLST();
    }

    public SubList<SubForm> getFromEntries() {
	return LNAMs;
    }

    public void addFormEntry(FormID entry) {
	LNAMs.add(new SubForm(Type.LNAM, entry));

    }

    public void removeFormEntry(FormID entry) {
	LNAMs.remove(new SubForm(Type.LNAM, entry));
    }
}