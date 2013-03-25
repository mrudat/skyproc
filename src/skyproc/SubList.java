/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.zip.DataFormatException;
import lev.LChannel;
import lev.LExporter;
import lev.Ln;
import skyproc.exceptions.BadParameter;
import skyproc.exceptions.BadRecord;

/**
 * A specialized collection of subrecords. Think of it as a special SkyProc
 * ArrayList used for subrecords.
 *
 * @param <T> The type of subrecord the group contains.
 * @author Justin Swanson
 */
class SubList<T extends SubRecord> extends SubRecord implements Iterable<T> {

    ArrayList<T> collection = new ArrayList<>();
    T prototype;

    SubList(T prototype_) {
	super();
	prototype = prototype_;
    }

    SubList(SubList rhs) {
	super();
	prototype = (T) rhs.prototype;
	collection.addAll(rhs.collection);
    }

    @Override
    int getHeaderLength() {
	return 0;
    }

    @Override
    boolean isValid() {
	return !collection.isEmpty();
    }

    /**
     *
     * @param s Record to check for containment.
     * @return True if an equal() record exists within the SubRecordList.
     */
    public boolean contains(T s) {
	return collection.contains(s);
    }

    /**
     *
     * @param i Index of the item to retrieve.
     * @return The ith item.
     */
    public T get(int i) {
	return collection.get(i);
    }

    /**
     * Adds an item to the list. Some groups allow duplicate items, some do not,
     * depending on the internal specifications and context. This function
     * returns true if the item was successfully added to list, or false if an
     * equal one already existed, and the group did not allow duplicates.
     *
     * @param item Item to add to the list.
     * @return true if the item was added to the list. False if an equal item
     * already existed in the list, and duplicates were not allowed.
     */
    public boolean add(T item) {
	if (allow(item)) {
	    collection.add(item);
	    return true;
	} else {
	    return false;
	}
    }

    boolean allow(T item) {
	return true;
    }

    public boolean addAtIndex(T item, int i) {
	if (allow(item)) {
	    collection.add(i, item);
	    return true;
	} else {
	    return false;
	}
    }

    /**
     *
     * @param item Item to remove from the list.
     * @return True if an item was removed.
     */
    public boolean remove(T item) {
	if (collection.contains(item)) {
	    collection.remove(item);
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * Removes an item based on its index in the list.
     *
     * @param i Index of the item to remove.
     */
    public void remove(int i) {
	collection.remove(i);
    }

    /**
     *
     * @return The number of items currently in the list.
     */
    public int size() {
	return collection.size();
    }

    public void clear() {
	collection.clear();
    }

    /**
     *
     * @return True if list is empty, and size == 0.
     */
    public boolean isEmpty() {
	return collection.isEmpty();
    }

    /**
     * This function will replace all records in the SubRecordList with the ones
     * given.<br><br> WARNING: All existing records will be lost.
     *
     * @param in ArrayList of records to replace the current ones.
     */
    public void setRecordsTo(ArrayList<T> in) {
	collection = in;
    }

    /**
     * This function will add all records given to the list using add().
     *
     * @param in ArrayList of records to add in.
     */
    public void addRecordsTo(ArrayList<T> in) {
	for (T t : collection) {
	    collection.add(t);
	}
    }

    @Override
    void parseData(LChannel in) throws BadRecord, DataFormatException, BadParameter {
	parseData(in, getNextType(in));
    }

    void parseData(LChannel in, String nextType) throws BadRecord, DataFormatException, BadParameter {
	if (nextType.equals(getType())) {
	    T newRecord = (T) prototype.getNew(getType());
	    newRecord.parseData(in);
	    add(newRecord);
	} else {
	    get(size() - 1).parseData(in);
	}
    }

    @Override
    SubRecord getNew(String type) {
	return new SubList(this);
    }

    @Override
    int getContentLength(Mod srcMod) {
	int length = 0;
	for (SubRecord r : collection) {
	    length += r.getTotalLength(srcMod);
	}
	return length;
    }

    @Override
    void export(LExporter out, Mod srcMod) throws IOException {
	if (isValid()) {
	    Iterator<T> iter = iterator();
	    while (iter.hasNext()) {
		iter.next().export(out, srcMod);
	    }
	}
    }

    static ArrayList<FormID> subFormToPublic(SubList<SubForm> in) {
	ArrayList<FormID> out = new ArrayList<>(in.size());
	for (SubForm s : in) {
	    out.add(s.ID);
	}
	return out;
    }

    static ArrayList<SubFormInt> subFormIntToPublic(SubList<SubFormInt> in) {
	ArrayList<SubFormInt> out = new ArrayList<>(in.size());
	for (SubFormInt s : in) {
	    out.add(s);
	}
	return out;
    }

    static ArrayList<Integer> subIntToPublic(SubList<SubInt> in) {
	ArrayList<Integer> out = new ArrayList<>(in.size());
	for (SubInt s : in) {
	    out.add(s.get());
	}
	return out;
    }

    static ArrayList<String> subStringToPublic(SubList<SubString> in) {
	ArrayList<String> out = new ArrayList<>(in.size());
	for (SubString s : in) {
	    out.add(s.string);
	}
	return out;
    }

    static ArrayList<byte[]> subDataToPublic(SubList<SubData> in) {
	ArrayList<byte[]> out = new ArrayList<>(in.size());
	for (SubData s : in) {
	    out.add(s.data);
	}
	return out;
    }

    ArrayList<T> toPublic() {
	return collection;
    }

    /**
     *
     * @return An iterator of all records in the SubRecordList.
     */
    @Override
    public Iterator<T> iterator() {
	return collection.listIterator();
    }

    @Override
    ArrayList<FormID> allFormIDs() {
	ArrayList<FormID> out = new ArrayList<>();
	for (T item : collection) {
	    out.addAll(item.allFormIDs());
	}
	return out;
    }

    @Override
    void fetchStringPointers(Mod srcMod) {
	for (SubRecord s : collection) {
	    s.fetchStringPointers(srcMod);
	}
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 89 * hash + Objects.hashCode(this.collection);
	return hash;
    }

    @Override
    public int getRecordLength(LChannel in) {
	return prototype.getRecordLength(in);
    }

    @Override
    public boolean equals(Object o) {
	if (this == o) {
	    return true;
	}
	if (o == null) {
	    return false;
	}
	if (!(o instanceof SubList)) {
	    return false;
	}
	SubList s = (SubList) o;
	return (Ln.equals(collection, s.collection, true));
    }

    @Override
    ArrayList<String> getTypes() {
	return prototype.getTypes();
    }
}
