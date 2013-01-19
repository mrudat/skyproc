/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.zip.DataFormatException;
import lev.LChannel;
import lev.LExporter;
import lev.LFlags;
import skyproc.exceptions.BadParameter;
import skyproc.exceptions.BadRecord;

/**
 * Race Records
 *
 * @author Justin Swanson
 */
public class RACE extends MajorRecordDescription {

    // Static prototypes and definitions
    static final SubPrototype attackDataProto = new SubPrototype() {
	@Override
	protected void addRecords() {
	    add(new AttackDataInternal());
	    add(SubString.getNew("ATKE", true));
	}
    };
    static final SubPrototype RACEproto = new SubPrototype(MajorRecordDescription.descProto) {
	@Override
	protected void addRecords() {
	    add(new SubListCounted<>("SPCT", 4, new SubForm("SPLO")));
	    add(new SubForm("WNAM"));
	    add(new BodyTemplate());
	    add(new KeywordSet());
	    add(new DATA());
	    SubMarkerSet mfnam = new SubMarkerSet<>(new SubShell(new SubPrototype() {
		@Override
		protected void addRecords() {
		    add(SubString.getNew("ANAM", true));
		    add(new SubData("MODT"));
		}
	    }), "MNAM", "FNAM");
	    mfnam.forceMarkers = true;
	    add(mfnam);
	    add(new SubList<>(SubString.getNew("MTNM", false)));
	    add(new SubFormArray("VTCK", 2));
	    add(new SubFormArray("DNAM", 2));
	    add(new SubFormArray("HCLF", 2));
	    add(new SubData("TINL"));
	    add(new SubData("PNAM"));
	    add(new SubData("UNAM"));
	    add(new SubForm("ATKR"));
	    add(new SubList<>(new AttackData()));
	    add(new SubShellBulkType(new SubPrototype() {
		@Override
		protected void addRecords() {
		    add(new SubData("NAM1"));
		    forceExport("NAM1");

		    SubMarkerSet EGT = new SubMarkerSet(new SubShell(new SubPrototype() {
			@Override
			protected void addRecords() {
			    add(new SubData("INDX"));
			    add(SubString.getNew("MODL", true));
			    add(new SubData("MODT"));
			}
		    }), "MNAM", "FNAM");
		    EGT.forceMarkers = true;
		    add(EGT);
		}
	    }, false));
	    add(new SubForm("GNAM"));
	    add(new SubData("NAM2"));
	    add(new SubShellBulkType(new SubPrototype() {
		@Override
		protected void addRecords() {
		    add(new SubData("NAM3"));
		    forceExport("NAM3");
		    add(new SubMarkerSet(new SubShell(new SubPrototype() {
			@Override
			protected void addRecords() {
			    add(SubString.getNew("MODL", true));
			    add(new SubData("MODT"));
			}
		    }), "MNAM", "FNAM"));
		}
	    }, false));
	    add(new SubForm("NAM4"));
	    add(new SubForm("NAM5"));
	    add(new SubForm("NAM7"));
	    add(new SubForm("ONAM"));
	    add(new SubForm("LNAM"));
	    add(new SubList<>(SubString.getNew("NAME", true)));
	    add(new SubList<>(new SubShell(new SubPrototype() {
		@Override
		protected void addRecords() {
		    add(new SubForm("MTYP"));
		    add(new SubData("SPED"));
		}
	    })));
	    add(new SubData("VNAM"));
	    add(new SubList<>(new SubForm("QNAM")));
	    add(new SubForm("UNES"));
	    add(new SubList<>(SubString.getNew("PHTN", true)));
	    add(new SubList<>(new SubData("PHWT")));
	    add(new SubForm("WKMV"));
	    add(new SubForm("RNMV"));
	    add(new SubForm("SWMV"));
	    add(new SubForm("FLMV"));
	    add(new SubForm("SNMV"));
	    add(new SubForm("SPMV"));
	    add(new SubList<>(new SubShellBulkType(new SubPrototype() {
		@Override
		protected void addRecords() {
		    add(new SubData("NAM0"));
		    add(new SubData("MNAM"));
		    add(new SubData("FNAM"));
		    add(new SubList<>(new SubShell(new SubPrototype() {
			@Override
			protected void addRecords() {
			    add(new SubData("INDX"));
			    add(new SubData("HEAD"));
			}
		    })));
		    add(new SubList<>(new SubShell(new SubPrototype() {
			@Override
			protected void addRecords() {
			    add(new SubData("MPAI"));
			    add(new SubData("MPAV"));
			}
		    })));
		    add(new SubList<>(new SubForm("RPRM")));
		    add(new SubList<>(new SubForm("RPRF")));
		    add(new SubList<>(new SubForm("AHCM")));
		    add(new SubList<>(new SubForm("AHCF")));
		    add(new SubList<>(new SubForm("FTSM")));
		    add(new SubList<>(new SubForm("FTSF")));
		    add(new SubList<>(new SubForm("DFTM")));
		    add(new SubList<>(new SubForm("DFTF")));
		    add(new SubList<>(new SubShell(new SubPrototype() {
			@Override
			protected void addRecords() {
			    add(new SubData("TINI"));
			    add(SubString.getNew("TINT", true));
			    add(new SubData("TINP"));
			    add(new SubForm("TIND"));
			    add(new SubList<>(new SubShell(new SubPrototype() {
				@Override
				protected void addRecords() {
				    add(new SubData("TINC"));
				    add(new SubData("TINV"));
				    add(new SubData("TIRS"));
				}
			    })));
			}
		    })));
		}
	    }, false)));
	    add(new SubForm("NAM8"));
	    add(new SubForm("RNAM"));
	}
    };

    static final class DATA extends SubRecord {

	byte[] fluff1 = new byte[16];
	float maleHeight = 0;
	float femaleHeight = 0;
	float maleWeight = 0;
	float femaleWeight = 0;
	LFlags flags = new LFlags(4);
	float startingHealth = 0;
	float startingMagicka = 0;
	float startingStamina = 0;
	float baseCarryWeight = 0;
	float baseMass = 0;
	float accelerationRate = 0;
	float decelerationRate = 0;
	Size size = Size.MEDIUM;
	byte[] fluff3 = new byte[8];
	float injuredHealthPct = 0;
	byte[] fluff4 = new byte[4];
	float healthRegen = 0;
	float magickaRegen = 0;
	float staminaRegen = 0;
	float unarmedDamage = 0;
	float unarmedReach = 0;
	byte[] fluff5 = new byte[4];
	float aimAngleTolerance = 0;
	float flightRadius = 0;
	float angularAcceleration = 0;
	float angularTolerance = 0;
	byte[] fluff6 = new byte[4];
	byte[] mountData;

	DATA() {
	    super();
	}

	@Override
	void export(LExporter out, Mod srcMod) throws IOException {
	    super.export(out, srcMod);
	    out.write(fluff1, 16);
	    out.write(maleHeight);
	    out.write(femaleHeight);
	    out.write(maleWeight);
	    out.write(femaleWeight);
	    out.write(flags.export(), 4);
	    out.write(startingHealth);
	    out.write(startingMagicka);
	    out.write(startingStamina);
	    out.write(baseCarryWeight);
	    out.write(baseMass);
	    out.write(accelerationRate);
	    out.write(decelerationRate);
	    out.write(size.ordinal(), 4);
	    out.write(fluff3, 8);
	    out.write(injuredHealthPct);
	    out.write(fluff4, 4);
	    out.write(healthRegen);
	    out.write(magickaRegen);
	    out.write(staminaRegen);
	    out.write(unarmedDamage);
	    out.write(unarmedReach);
	    out.write(fluff5, 4);
	    out.write(aimAngleTolerance);
	    out.write(flightRadius);
	    out.write(angularAcceleration);
	    out.write(angularTolerance);
	    out.write(fluff6, 4);
	    if (mountData != null) {
		out.write(mountData);
	    }
	}

	@Override
	void parseData(LChannel in) throws BadRecord, DataFormatException, BadParameter {
	    super.parseData(in);
	    fluff1 = in.extract(16);
	    maleHeight = in.extractFloat();
	    femaleHeight = in.extractFloat();
	    maleWeight = in.extractFloat();
	    femaleWeight = in.extractFloat();
	    flags.set(in.extract(4));
	    startingHealth = in.extractFloat();
	    startingMagicka = in.extractFloat();
	    startingStamina = in.extractFloat();
	    baseCarryWeight = in.extractFloat();
	    baseMass = in.extractFloat();
	    accelerationRate = in.extractFloat();
	    decelerationRate = in.extractFloat();
	    size = Size.values()[in.extractInt(4)];
	    fluff3 = in.extract(8);
	    injuredHealthPct = in.extractFloat();
	    fluff4 = in.extract(4);
	    healthRegen = in.extractFloat();
	    magickaRegen = in.extractFloat();
	    staminaRegen = in.extractFloat();
	    unarmedDamage = in.extractFloat();
	    unarmedReach = in.extractFloat();
	    fluff5 = in.extract(4);
	    aimAngleTolerance = in.extractFloat();
	    flightRadius = in.extractFloat();
	    angularAcceleration = in.extractFloat();
	    angularTolerance = in.extractFloat();
	    fluff6 = in.extract(4);
	    if (!in.isDone()) {
		mountData = in.extract(36);
	    }
	}

	@Override
	SubRecord getNew(String type) {
	    return new DATA();
	}

	@Override
	int getContentLength(Mod srcMod) {
	    if (mountData == null) {
		return 128;
	    } else {
		return 164;
	    }
	}

	@Override
	ArrayList<String> getTypes() {
	    return Record.getTypeList("DATA");
	}
    }

    /**
     *
     */
    static final public class AttackDataInternal extends SubRecord {

	float damageMult = 0;
	float attackChance = 0;
	FormID attackSpell = new FormID();
	LFlags flags = new LFlags(4);
	float attackAngle = 0;
	float strikeAngle = 0;
	float stagger = 0;
	FormID attackType = new FormID();
	float knockDown = 0;
	float recoveryTime = 0;
	float fatigueMult = 0;

	void copy(AttackDataInternal rhs) {
	    damageMult = rhs.damageMult;
	    attackChance = rhs.attackChance;
	    attackSpell = new FormID(rhs.attackSpell);
	    flags = new LFlags(rhs.flags);
	    attackAngle = rhs.attackAngle;
	    strikeAngle = rhs.strikeAngle;
	    stagger = rhs.stagger;
	    attackType = new FormID(rhs.attackType);
	    knockDown = rhs.knockDown;
	    recoveryTime = rhs.recoveryTime;
	    fatigueMult = rhs.fatigueMult;
	}

	@Override
	ArrayList<FormID> allFormIDs() {
	    ArrayList<FormID> out = new ArrayList<>(2);
	    out.add(attackSpell);
	    out.add(attackType);
	    return out;
	}

	@Override
	void export(LExporter out, Mod srcMod) throws IOException {
	    super.export(out, srcMod);
	    out.write(damageMult);
	    out.write(attackChance);
	    attackSpell.export(out);
	    out.write(flags.export());
	    out.write(attackAngle);
	    out.write(strikeAngle);
	    out.write(stagger);
	    attackType.export(out);
	    out.write(knockDown);
	    out.write(recoveryTime);
	    out.write(fatigueMult);
	}

	@Override
	void parseData(LChannel in) throws BadRecord, BadParameter, DataFormatException {
	    super.parseData(in);
	    damageMult = in.extractFloat();
	    attackChance = in.extractFloat();
	    attackSpell.setInternal(in.extract(4));
	    flags.set(in.extract(4));
	    attackAngle = in.extractFloat();
	    strikeAngle = in.extractFloat();
	    stagger = in.extractFloat();
	    attackType.setInternal(in.extract(4));
	    knockDown = in.extractFloat();
	    recoveryTime = in.extractFloat();
	    fatigueMult = in.extractFloat();
	}

	@Override
	SubRecord getNew(String type) {
	    return new AttackDataInternal();
	}

	@Override
	ArrayList<String> getTypes() {
	    return Record.getTypeList("ATKD");
	}

	@Override
	int getContentLength(Mod srcMod) {
	    return 44;
	}
    }

    /**
     *
     */
    static final public class AttackData extends SubShell {

	AttackData() {
	    super(attackDataProto);
	}

	/**
	 *
	 * @param eventName
	 */
	public AttackData(String eventName) {
	    this();
	    subRecords.setSubString("ATKE", eventName);
	}

	/**
	 *
	 * @return
	 */
	public String getEventName() {
	    return subRecords.getSubString("ATKE").print();
	}

	AttackDataInternal getATKD() {
	    return (AttackDataInternal) subRecords.get("ATKD");
	}

	/**
	 *
	 * @param rhs
	 */
	public void copyData(AttackData rhs) {
	    getATKD().copy(rhs.getATKD());
	}

	@Override
	SubRecord getNew(String type) {
	    return new AttackData();
	}

	/**
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) {
		return false;
	    }
	    if (getClass() != obj.getClass()) {
		return false;
	    }
	    final AttackData other = (AttackData) obj;
	    if (!Objects.equals(this.getEventName(), other.getEventName())) {
		return false;
	    }
	    return true;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public int hashCode() {
	    int hash = 7;
	    hash = 31 * hash + Objects.hashCode(this.getEventName());
	    return hash;
	}

	/**
	 *
	 * @param in
	 */
	public void setDamageMult(float in) {
	    getATKD().damageMult = in;
	}

	/**
	 *
	 * @return
	 */
	public float getDamageMult() {
	    return getATKD().damageMult;
	}

	/**
	 *
	 * @param in
	 */
	public void setAttackChance(float in) {
	    getATKD().attackChance = in;
	}

	/**
	 *
	 * @return
	 */
	public float getAttackChance() {
	    return getATKD().attackChance;
	}

	/**
	 *
	 * @param spell
	 */
	public void setAttackSpell(FormID spell) {
	    getATKD().attackSpell = spell;
	}

	/**
	 *
	 * @return
	 */
	public FormID getAttackSpell() {
	    return getATKD().attackSpell;
	}

	/**
	 *
	 * @param in
	 */
	public void setAttackAngle(float in) {
	    getATKD().attackAngle = in;
	}

	/**
	 *
	 * @return
	 */
	public float getAttackAngle() {
	    return getATKD().attackAngle;
	}

	/**
	 *
	 * @param in
	 */
	public void setStrikeAngle(float in) {
	    getATKD().strikeAngle = in;
	}

	/**
	 *
	 * @return
	 */
	public float getStrikeAngle() {
	    return getATKD().strikeAngle;
	}

	/**
	 *
	 * @param in
	 */
	public void setStagger(float in) {
	    getATKD().stagger = in;
	}

	/**
	 *
	 * @return
	 */
	public float getStagger() {
	    return getATKD().stagger;
	}

	/**
	 *
	 * @param id
	 */
	public void setAttackType(FormID id) {
	    getATKD().attackType = id;
	}

	/**
	 *
	 * @return
	 */
	public FormID getAttackType() {
	    return getATKD().attackType;
	}

	/**
	 *
	 * @param in
	 */
	public void setKnockDown(float in) {
	    getATKD().knockDown = in;
	}

	/**
	 *
	 * @return
	 */
	public float getKnockDown() {
	    return getATKD().knockDown;
	}

	/**
	 *
	 * @param in
	 */
	public void setRecoveryTime(float in) {
	    getATKD().recoveryTime = in;
	}

	/**
	 *
	 * @return
	 */
	public float getRecoveryTime() {
	    return getATKD().recoveryTime;
	}

	/**
	 *
	 * @param in
	 */
	public void setFatigueMult(float in) {
	    getATKD().fatigueMult = in;
	}

	/**
	 *
	 * @return
	 */
	public float getFatigueMult() {
	    return getATKD().fatigueMult;
	}
    }

    // Enums
    /**
     *
     */
    public static enum RACEFlags {

	/**
	 *
	 */
	Playable,
	/**
	 *
	 */
	FaceGenHead,
	/**
	 *
	 */
	Child,
	/**
	 *
	 */
	TiltFrontBack,
	/**
	 *
	 */
	TiltLeftRight,
	/**
	 *
	 */
	NoShadow,
	/**
	 *
	 */
	Swims,
	/**
	 *
	 */
	Flies,
	/**
	 *
	 */
	Walks,
	/**
	 *
	 */
	Immobile,
	/**
	 *
	 */
	NotPushable,
	/**
	 *
	 */
	NoCombatInWater,
	/**
	 *
	 */
	NoRotatingToHeadTrack,
	/**
	 *
	 */
	DontShowBloodSpray,
	/**
	 *
	 */
	DontShowBloodDecal,
	/**
	 *
	 */
	UsesHeadTrackAnims,
	/**
	 *
	 */
	SpellsAlignWithMagicNode,
	/**
	 *
	 */
	UseWorldRaycastsForFootIK,
	/**
	 *
	 */
	AllowRagdollCollision,
	/**
	 *
	 */
	RegenHPInCombat,
	/**
	 *
	 */
	CantOpenDoors,
	/**
	 *
	 */
	AllowPCDialogue,
	/**
	 *
	 */
	NoKnockdowns,
	/**
	 *
	 */
	AllowPickpocket,
	/**
	 *
	 */
	AlwaysUseProxyController,
	/**
	 *
	 */
	DontShowWeaponBlood,
	/**
	 *
	 */
	OverlayHeadPartList,
	/**
	 *
	 */
	OverrideHeadPartList,
	/**
	 *
	 */
	CanPickupItems,
	/**
	 *
	 */
	AllowMultipleMembraneShaders,
	/**
	 *
	 */
	CanDualWeild,
	/**
	 *
	 */
	AvoidsRoads;
    }

    /**
     *
     */
    public enum Size {

	/**
	 *
	 */
	SMALL,
	/**
	 *
	 */
	MEDIUM,
	/**
	 *
	 */
	LARGE,
	/**
	 *
	 */
	EXTRALARGE;
    }

    // Common Functions
    /**
     *
     */
    RACE() {
	super();
	subRecords.setPrototype(RACEproto);
    }

    @Override
    ArrayList<String> getTypes() {
	return Record.getTypeList("RACE");
    }

    @Override
    Record getNew() {
	return new RACE();
    }

    // Get / set
    DATA getDATA() {
	return (DATA) subRecords.get("DATA");
    }

    /**
     *
     * @param flag
     * @param on
     */
    public void set(RACEFlags flag, boolean on) {
	getDATA().flags.set(flag.ordinal(), on);
    }

    /**
     *
     * @param flag
     * @return
     */
    public boolean get(RACEFlags flag) {
	return getDATA().flags.get(flag.ordinal());
    }

    /**
     *
     * @return FormID of the ARMO record that is worn.
     */
    public FormID getWornArmor() {
	return subRecords.getSubForm("WNAM").getForm();
    }

    /**
     *
     * @param id FormID to set the worn ARMO record to.
     */
    public void setWornArmor(FormID id) {
	subRecords.setSubForm("WNAM", id);
    }

    /**
     *
     * @return
     */
    public ArrayList<FormID> getSpells() {
	return SubList.subFormToPublic(subRecords.getSubList("SPLO"));
    }

    /**
     *
     * @param spell
     */
    public void addSpell(FormID spell) {
	subRecords.getSubList("SPLO").add(new SubForm("SPLO", spell));
    }

    /**
     *
     * @param spell
     */
    public void removeSpell(FormID spell) {
	subRecords.getSubList("SPLO").remove(new SubForm("SPLO", spell));
    }

    /**
     *
     */
    public void clearSpells() {
	subRecords.getSubList("SPLO").clear();
    }

    /**
     *
     * @param gender
     * @param model
     */
    public void setModel(Gender gender, String model) {
	getMFData(gender).subRecords.setSubString("ANAM", model);
    }

    SubShell getMFData(Gender gender) {
	SubMarkerSet<SubShell> MFNAM = subRecords.getSubMarker("ANAM");
	switch (gender) {
	    case MALE:
		return MFNAM.get("MNAM");
	    default:
		return MFNAM.get("FNAM");
	}
    }

    /**
     *
     * @param gender
     * @return
     */
    public String getModel(Gender gender) {
	return getMFData(gender).subRecords.getSubString("ANAM").print();
    }

    /**
     *
     * @param gender
     * @param voice
     */
    public void setVoiceType(Gender gender, FormID voice) {
	SubFormArray VTCK = (SubFormArray) subRecords.get("VTCK");
	switch (gender) {
	    case MALE:
		VTCK.IDs.set(0, voice);
		break;
	    default:
		VTCK.IDs.set(1, voice);
		break;
	}
    }

    /**
     *
     * @param gender
     * @return
     */
    public FormID getVoiceType(Gender gender) {
	SubFormArray VTCK = (SubFormArray) subRecords.get("VTCK");
	switch (gender) {
	    case MALE:
		return VTCK.IDs.get(0);
	    default:
		return VTCK.IDs.get(1);
	}
    }

    /**
     *
     * @param gender
     * @param color
     */
    public void setHairColor(Gender gender, FormID color) {
	SubFormArray HCLF = (SubFormArray) subRecords.get("HCLF");
	switch (gender) {
	    case MALE:
		HCLF.IDs.set(0, color);
		break;
	    default:
		HCLF.IDs.set(1, color);
		break;
	}
    }

    /**
     *
     * @param gender
     * @return
     */
    public FormID getHairColor(Gender gender) {
	SubFormArray HCLF = (SubFormArray) subRecords.get("HCLF");
	switch (gender) {
	    case MALE:
		return HCLF.IDs.get(0);
	    default:
		return HCLF.IDs.get(1);
	}
    }

    /**
     *
     * @param gender
     * @param part
     */
    public void setDecapHeadPart(Gender gender, FormID part) {
	SubFormArray DNAM = (SubFormArray) subRecords.get("DNAM");
	switch (gender) {
	    case MALE:
		DNAM.IDs.set(0, part);
		break;
	    default:
		DNAM.IDs.set(1, part);
		break;
	}
    }

    /**
     *
     * @param gender
     * @return
     */
    public FormID getDecapHeadPart(Gender gender) {
	SubFormArray DNAM = (SubFormArray) subRecords.get("DNAM");
	switch (gender) {
	    case MALE:
		return DNAM.IDs.get(0);
	    default:
		return DNAM.IDs.get(1);
	}
    }

    /**
     *
     * @param gender
     * @param value
     */
    public void setHeight(Gender gender, float value) {
	DATA DATA = getDATA();
	switch (gender) {
	    case MALE:
		DATA.maleHeight = value;
	    case FEMALE:
		DATA.femaleHeight = value;
	}
    }

    /**
     *
     * @param gender
     * @return
     */
    public float getHeight(Gender gender) {
	DATA DATA = getDATA();
	switch (gender) {
	    case MALE:
		return DATA.maleHeight;
	    default:
		return DATA.femaleHeight;
	}
    }

    /**
     *
     * @return
     */
    public float getAccelerationRate() {
	return getDATA().accelerationRate;
    }

    /**
     *
     * @param accelerationRate
     */
    public void setAccelerationRate(float accelerationRate) {
	getDATA().accelerationRate = accelerationRate;
    }

    /**
     *
     * @return
     */
    public float getAimAngleTolerance() {
	return getDATA().aimAngleTolerance;
    }

    /**
     *
     * @param aimAngleTolerance
     */
    public void setAimAngleTolerance(float aimAngleTolerance) {
	getDATA().aimAngleTolerance = aimAngleTolerance;
    }

    /**
     *
     * @return
     */
    public float getAngularAcceleration() {
	return getDATA().angularAcceleration;
    }

    /**
     *
     * @param angularAcceleration
     */
    public void setAngularAcceleration(float angularAcceleration) {
	getDATA().angularAcceleration = angularAcceleration;
    }

    /**
     *
     * @return
     */
    public float getAngularTolerance() {
	return getDATA().angularTolerance;
    }

    /**
     *
     * @param angularTolerance
     */
    public void setAngularTolerance(float angularTolerance) {
	getDATA().angularTolerance = angularTolerance;
    }

    /**
     *
     * @return
     */
    public float getBaseCarryWeight() {
	return getDATA().baseCarryWeight;
    }

    /**
     *
     * @param baseCarryWeight
     */
    public void setBaseCarryWeight(float baseCarryWeight) {
	getDATA().baseCarryWeight = baseCarryWeight;
    }

    /**
     *
     * @return
     */
    public float getBaseMass() {
	return getDATA().baseMass;
    }

    /**
     *
     * @param baseMass
     */
    public void setBaseMass(float baseMass) {
	getDATA().baseMass = baseMass;
    }

    /**
     *
     * @return
     */
    public float getDecelerationRate() {
	return getDATA().decelerationRate;
    }

    /**
     *
     * @param decelerationRate
     */
    public void setDecelerationRate(float decelerationRate) {
	getDATA().decelerationRate = decelerationRate;
    }

    /**
     *
     * @return
     */
    public float getFemaleWeight() {
	return getDATA().femaleWeight;
    }

    /**
     *
     * @param femaleWeight
     */
    public void setFemaleWeight(float femaleWeight) {
	getDATA().femaleWeight = femaleWeight;
    }

    /**
     *
     * @return
     */
    public float getFlightRadius() {
	return getDATA().flightRadius;
    }

    /**
     *
     * @param flightRadius
     */
    public void setFlightRadius(float flightRadius) {
	getDATA().flightRadius = flightRadius;
    }

    /**
     *
     * @return
     */
    public float getHealthRegen() {
	return getDATA().healthRegen;
    }

    /**
     *
     * @param healthRegen
     */
    public void setHealthRegen(float healthRegen) {
	getDATA().healthRegen = healthRegen;
    }

    /**
     *
     * @return
     */
    public float getInjuredHealthPct() {
	return getDATA().injuredHealthPct;
    }

    /**
     *
     * @param injuredHealthPct
     */
    public void setInjuredHealthPct(float injuredHealthPct) {
	getDATA().injuredHealthPct = injuredHealthPct;
    }

    /**
     *
     * @return
     */
    public float getMagickaRegen() {
	return getDATA().magickaRegen;
    }

    /**
     *
     * @param magickaRegen
     */
    public void setMagickaRegen(float magickaRegen) {
	getDATA().magickaRegen = magickaRegen;
    }

    /**
     *
     * @return
     */
    public float getMaleHeight() {
	return getDATA().maleHeight;
    }

    /**
     *
     * @param maleHeight
     */
    public void setMaleHeight(float maleHeight) {
	getDATA().maleHeight = maleHeight;
    }

    /**
     *
     * @return
     */
    public float getMaleWeight() {
	return getDATA().maleWeight;
    }

    /**
     *
     * @param maleWeight
     */
    public void setMaleWeight(float maleWeight) {
	getDATA().maleWeight = maleWeight;
    }

    /**
     *
     * @return
     */
    public Size getSize() {
	return getDATA().size;
    }

    /**
     *
     * @param size
     */
    public void setSize(Size size) {
	getDATA().size = size;
    }

    /**
     *
     * @return
     */
    public float getStaminaRegen() {
	return getDATA().staminaRegen;
    }

    /**
     *
     * @param staminaRegen
     */
    public void setStaminaRegen(float staminaRegen) {
	getDATA().staminaRegen = staminaRegen;
    }

    /**
     *
     * @return
     */
    public float getStartingHealth() {
	return getDATA().startingHealth;
    }

    /**
     *
     * @param startingHealth
     */
    public void setStartingHealth(float startingHealth) {
	getDATA().startingHealth = startingHealth;
    }

    /**
     *
     * @return
     */
    public float getStartingMagicka() {
	return getDATA().startingMagicka;
    }

    /**
     *
     * @param startingMagicka
     */
    public void setStartingMagicka(float startingMagicka) {
	getDATA().startingMagicka = startingMagicka;
    }

    /**
     *
     * @return
     */
    public float getStartingStamina() {
	return getDATA().startingStamina;
    }

    /**
     *
     * @param startingStamina
     */
    public void setStartingStamina(float startingStamina) {
	getDATA().startingStamina = startingStamina;
    }

    /**
     *
     * @return
     */
    public float getUnarmedDamage() {
	return getDATA().unarmedDamage;
    }

    /**
     *
     * @param unarmedDamage
     */
    public void setUnarmedDamage(float unarmedDamage) {
	getDATA().unarmedDamage = unarmedDamage;
    }

    /**
     *
     * @return
     */
    public float getUnarmedReach() {
	return getDATA().unarmedReach;
    }

    /**
     *
     * @param unarmedReach
     */
    public void setUnarmedReach(float unarmedReach) {
	getDATA().unarmedReach = unarmedReach;
    }

    /**
     *
     */
    public void clearAttackData() {
	subRecords.getSubList("ATKD").clear();
    }

    /**
     *
     * @return
     */
    public ArrayList<AttackData> getAttackData() {
	return subRecords.getSubList("ATKD").toPublic();
    }

    /**
     *
     * @param data
     */
    public void addAttackData(AttackData data) {
	subRecords.getSubList("ATKD").add(data);
    }

    /**
     *
     * @param data
     */
    public void removeAttackData(AttackData data) {
	subRecords.getSubList("ATKD").remove(data);
    }

    /**
     *
     * @param rhs
     */
    public void copyAttackData(RACE rhs) {
	ArrayList<AttackData> attackList = this.getAttackData();
	attackList.clear();
	ArrayList<AttackData> rhsAttackList = rhs.getAttackData();
	for (AttackData rhsData : rhsAttackList) {
	    AttackData newData = new AttackData(rhsData.getEventName());
	    newData.copyData(rhsData);
	    attackList.add(newData);
	}
    }

    /**
     *
     * @return
     */
    public FormID getMaterialType() {
	return subRecords.getSubForm("NAM4").getForm();
    }

    /**
     *
     * @param id
     */
    public void setMaterialType(FormID id) {
	subRecords.setSubForm("NAM4", id);
    }

    /**
     *
     * @return
     */
    public FormID getImpactDataSet() {
	return subRecords.getSubForm("NAM5").getForm();
    }

    /**
     *
     * @param id
     */
    public void setImpactDataSet(FormID id) {
	subRecords.setSubForm("NAM5", id);
    }

    /**
     *
     * @return
     */
    public FormID getDecapitationFX() {
	return subRecords.getSubForm("NAM7").getForm();
    }

    /**
     *
     * @param id
     */
    public void setDecapitationFX(FormID id) {
	subRecords.setSubForm("NAM7", id);
    }

    /**
     *
     * @return
     */
    public FormID getOpenLootSound() {
	return subRecords.getSubForm("ONAM").getForm();
    }

    /**
     *
     * @param id
     */
    public void setOpenLootSound(FormID id) {
	subRecords.setSubForm("ONAM", id);
    }

    /**
     *
     * @return
     */
    public FormID getCloseLootSound() {
	return subRecords.getSubForm("LNAM").getForm();
    }

    /**
     *
     * @param id
     */
    public void setCloseLootSound(FormID id) {
	subRecords.setSubForm("LNAM", id);
    }

    /**
     *
     * @return
     */
    public FormID getUnarmedEquipSlot() {
	return subRecords.getSubForm("UNES").getForm();
    }

    /**
     *
     * @param id
     */
    public void setUnarmedEquipSlot(FormID id) {
	subRecords.setSubForm("UNES", id);
    }

    /**
     *
     */
    public void clearTinting() {
	subRecords.getSubList("NAM0").clear();
    }

    SubMarkerSet<SubShell> getEGT() {
	return subRecords.getSubShell("NAM1").subRecords.getSubMarker("INDX");
    }

    SubShell getEGTmodel(Gender gender) {
	switch (gender) {
	    case MALE:
		return getEGT().set.get("MNAM");
	    default:
		return getEGT().set.get("FNAM");
	}
    }

    /**
     *
     * @param gender
     * @return
     */
    public String getLightingModels(Gender gender) {
	return getEGTmodel(gender).subRecords.getSubString("MODL").print();
    }

    /**
     *
     * @param gender
     * @param s
     */
    public void setLightingModels(Gender gender, String s) {
	getEGTmodel(gender).subRecords.setSubString("MODL", s);
    }

    SubMarkerSet<SubShell> getHKX() {
	return subRecords.getSubShell("NAM3").subRecords.getSubMarker("MODL");
    }

    SubShell getHKXmodel(Gender gender) {
	switch (gender) {
	    case MALE:
		return getHKX().set.get("MNAM");
	    default:
		return getHKX().set.get("FNAM");
	}
    }

    /**
     *
     * @param gender
     * @return
     */
    public String getPhysicsModels(Gender gender) {
	return getHKXmodel(gender).subRecords.getSubString("MODL").print();
    }

    /**
     *
     * @param gender
     * @param s
     */
    public void setPhysicsModels(Gender gender, String s) {
	getHKXmodel(gender).subRecords.setSubString("MODL", s);
    }

    /**
     *
     * @return
     */
    public ArrayList<FormID> getEquipSlots() {
	return SubList.subFormToPublic(subRecords.getSubList("QNAM"));
    }

    /**
     *
     * @param in
     */
    public void addEquipSlot(FormID in) {
	subRecords.getSubList("QNAM").add(new SubForm("QNAM", in));
    }

    /**
     *
     * @param in
     */
    public void removeEquipSlot(FormID in) {
	subRecords.getSubList("QNAM").remove(new SubForm("QNAM", in));
    }

    /**
     *
     */
    public void clearEquipSlots() {
	subRecords.getSubList("QNAM").clear();
    }

    /**
     *
     * @return
     */
    public KeywordSet getKeywordSet() {
	return subRecords.getKeywords();
    }

    /**
     *
     * @param flag
     * @param on
     */
    public void set(BodyTemplate.FirstPersonFlags flag, boolean on) {
	subRecords.getBodyTemplate().set(flag, on);
    }

    /**
     *
     * @param flag
     * @return
     */
    public boolean get(BodyTemplate.FirstPersonFlags flag) {
	return subRecords.getBodyTemplate().get(flag);
    }

    /**
     *
     * @param flag
     * @param on
     */
    public void set(BodyTemplate.GeneralFlags flag, boolean on) {
	subRecords.getBodyTemplate().set(flag, on);
    }

    /**
     *
     * @param flag
     * @return
     */
    public boolean get(BodyTemplate.GeneralFlags flag) {
	return subRecords.getBodyTemplate().get(flag);
    }

    /**
     *
     * @param type
     */
    public void setArmorType(ArmorType type) {
	subRecords.getBodyTemplate().setArmorType(type);
    }

    /**
     *
     * @return
     */
    public ArmorType getArmorType() {
	return subRecords.getBodyTemplate().getArmorType();
    }

    /**
     *
     * @param race
     */
    public void setArmorRace(FormID race) {
	subRecords.setSubForm("RNAM", race);
    }

    /**
     *
     * @return
     */
    public FormID getArmorRace() {
	return subRecords.getSubForm("RNAM").getForm();
    }

    /**
     *
     * @param race
     */
    public void setMorphRace(FormID race) {
	subRecords.setSubForm("NAM8", race);
    }

    /**
     *
     * @return
     */
    public FormID getMorphRace() {
	return subRecords.getSubForm("NAM8").getForm();
    }
}
