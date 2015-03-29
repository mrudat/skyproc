/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

import lev.LImport;
import skyproc.exceptions.BadParameter;
import skyproc.exceptions.BadRecord;
import skyproc.genenums.Gender;

/**
 * Armor Records
 *
 * @author Justin Swanson
 */
@SuppressWarnings("serial")
public class ARMO extends MajorRecordDescription {

	// Static prototypes and definitions
	static final SubPrototype ARMOprototype = new SubPrototype(
			MajorRecordDescription.descProto) {
		@Override
		protected void addRecords() {
			add(new ScriptPackage());
			add(new SubData("OBND", new byte[12]));
			reposition("FULL");
			add(new SubForm("EITM"));
			add(SubString.getNew("MOD2", true));
			add(new SubData("MO2T"));
			add(new AltTextures("MO2S"));
			add(SubString.getNew("ICON", true));
			add(SubString.getNew("MOD4", true));
			add(new SubData("MO4T"));
			add(new AltTextures("MO4S"));
			add(SubString.getNew("ICO2", true));
			add(new BodyTemplate());
			add(new DestructionData());
			add(new SubForm("YNAM"));
			add(new SubForm("ZNAM"));
			add(new SubString("BMCT"));
			add(new SubForm("ETYP"));
			add(new SubForm("BIDS"));
			add(new SubForm("BAMT"));
			add(new SubForm("RNAM"));
			add(new KeywordSet());
			reposition("DESC");
			add(new SubList<>(new SubForm("MODL")));
			add(new DATA());
			add(new SubData("DNAM"));
			add(new SubForm("TNAM"));
		}
	};

	/**
	 * Armor Major Record
	 */
	ARMO() {
		super();
		subRecords.setPrototype(ARMOprototype);
	}

	@Override
	ArrayList<String> getTypes() {
		return Record.getTypeList("ARMO");
	}

	@Override
	ARMO getNew() {
		return new ARMO();
	}

	static class DATA extends SubRecord<DATA> {

		int value;
		float weight;

		DATA() {
			super();
		}

		@Override
		void export(ModExporter out) throws IOException {
			super.export(out);
			out.write(value);
			out.write(weight);
		}

		@Override
		void parseData(LImport in, Mod srcMod) throws BadRecord,
				DataFormatException, BadParameter {
			super.parseData(in, srcMod);
			value = in.extractInt(4);
			weight = in.extractFloat();
			if (logging()) {
				logMod(srcMod, "", "Value: " + value + ", weight " + weight);
			}
		}

		@Override
		DATA getNew(String type) {
			return new DATA();
		}

		@Override
		boolean isValid() {
			return true;
		}

		@Override
		int getContentLength(ModExporter out) {
			return 8;
		}

		@Override
		ArrayList<String> getTypes() {
			return Record.getTypeList("DATA");
		}
	}

	// Get/Set
	/**
	 *
	 * @return
	 */
	public KeywordSet getKeywordSet() {
		return subRecords.getKeywords();
	}

	/**
	 * @return Returns the list of ARMA records associated with the ARMO.
	 */
	public ArrayList<FormID> getArmatures() {
		return subRecords.getSubList("MODL").toPublic();
	}

	/**
	 * 
	 * @param id
	 *            Adds an ARMA record to the MODL list.
	 */
	public void addArmature(FormID id) {
		subRecords.getSubList("MODL").add(id);
	}

	/**
	 * 
	 * @param id
	 *            Removes an ARMA record from the MODL list if it exists.
	 */
	public void removeArmature(FormID id) {
		subRecords.getSubList("MODL").remove(id);
	}

	/**
	 *
	 * @param id
	 */
	public void setEnchantment(FormID id) {
		subRecords.setSubForm("EITM", id);
	}

	/**
	 *
	 * @return
	 */
	public FormID getEnchantment() {
		return subRecords.getSubForm("EITM").getForm();
	}

	String getAltTexType(Gender gender) {
		switch (gender) {
		case MALE:
			return "MO2S";
		default:
			return "MO4S";
		}
	}

	/**
	 * Returns the set of AltTextures applied to a specified gender and
	 * perspective.
	 *
	 * @param gender
	 *            Gender of the AltTexture set to query.
	 * @return List of the AltTextures applied to the gender/perspective.
	 */
	public ArrayList<AltTextures.AltTexture> getAltTextures(Gender gender) {
		AltTextures t = (AltTextures) subRecords.get(getAltTexType(gender));
		return t.altTextures;
	}

	/**
	 *
	 * @param rhs
	 *            Other ARMA record.
	 * @param gender
	 *            Gender of the pack to compare.
	 * @return true if:<br>
	 *         Both sets are empty.<br>
	 *         or <br>
	 *         Each set contains matching Alt Textures with the same name and
	 *         TXST formID reference, in the same corresponding indices.
	 */
	public boolean equalAltTextures(ARMO rhs, Gender gender) {
		return AltTextures.equal(getAltTextures(gender),
				rhs.getAltTextures(gender));
	}

	/**
	 *
	 * @param path
	 * @param g
	 */
	public void setModel(String path, Gender g) {
		switch (g) {
		case MALE:
			subRecords.setSubString("MOD2", path);
			break;
		case FEMALE:
			subRecords.setSubString("MOD4", path);
			break;
		}
	}

	/**
	 *
	 * @param g
	 * @return
	 */
	public String getModel(Gender g) {
		switch (g) {
		case MALE:
			return subRecords.getSubString("MOD2").print();
		default:
			return subRecords.getSubString("MOD4").print();
		}
	}

	/**
	 *
	 * @param sound
	 */
	public void setPickupSound(FormID sound) {
		subRecords.setSubForm("YNAM", sound);
	}

	/**
	 *
	 * @return
	 */
	public FormID getPickupSound() {
		return subRecords.getSubForm("YNAM").getForm();
	}

	/**
	 *
	 * @param sound
	 */
	public void setDropSound(FormID sound) {
		subRecords.setSubForm("ZNAM", sound);
	}

	/**
	 *
	 * @return
	 */
	public FormID getDropSound() {
		return subRecords.getSubForm("ZNAM").getForm();
	}

	/**
	 *
	 * @param slot
	 */
	public void setEquipSlot(FormID slot) {
		subRecords.setSubForm("ETYP", slot);
	}

	/**
	 *
	 * @return
	 */
	public FormID getEquipSet() {
		return subRecords.getSubForm("ETYP").getForm();
	}

	/**
	 *
	 * @param set
	 */
	public void setBashImpactData(FormID set) {
		subRecords.setSubForm("BIDS", set);
	}

	/**
	 *
	 * @return
	 */
	public FormID getBashImpactData() {
		return subRecords.getSubForm("BIDS").getForm();
	}

	/**
	 *
	 * @param race
	 */
	public void setRace(FormID race) {
		subRecords.setSubForm("RNAM", race);
	}

	/**
	 *
	 * @return
	 */
	public FormID getRace() {
		return subRecords.getSubForm("RNAM").getForm();
	}

	DATA getDATA() {
		return (DATA) subRecords.get("DATA");
	}

	/**
	 *
	 * @param value
	 */
	public void setValue(int value) {
		getDATA().value = value;
	}

	/**
	 *
	 * @return
	 */
	public int getValue() {
		return getDATA().value;
	}

	/**
	 *
	 * @param weight
	 */
	public void setWeight(float weight) {
		getDATA().weight = weight;
	}

	/**
	 *
	 * @return
	 */
	public float getWeight() {
		return getDATA().weight;
	}

	/**
	 *
	 * @param rating
	 */
	public void setArmorRatingFloat(float rating) {
		subRecords.setSubData("DNAM", (int) rating * 100);
	}

	/**
	 *
	 * @return
	 */
	public float getArmorRatingFloat() {
		return (float) (subRecords.getSubData("DNAM").toInt() / 100.0);
	}

	/**
	 *
	 * @param rating
	 */
	public void setArmorRating(int rating) {
		subRecords.setSubData("DNAM", rating);
	}

	/**
	 *
	 * @return
	 */
	public int getArmorRating() {
		return subRecords.getSubData("DNAM").toInt();
	}

	/**
	 *
	 * @param template
	 */
	public void setTemplate(FormID template) {
		subRecords.setSubForm("TNAM", template);
	}

	/**
	 *
	 * @return
	 */
	public FormID getTemplate() {
		return subRecords.getSubForm("TNAM").getForm();
	}

	/**
	 *
	 * @return
	 */
	public ScriptPackage getScriptPackage() {
		return subRecords.getScripts();
	}

	/**
	 *
	 * @return
	 */
	public BodyTemplate getBodyTemplate() {
		return subRecords.getBodyTemplate();
	}
}
