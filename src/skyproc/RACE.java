/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyproc;

import java.util.zip.DataFormatException;
import lev.LShrinkArray;
import skyproc.exceptions.BadParameter;
import skyproc.exceptions.BadRecord;

/**
 * Race
 * @author Justin Swanson
 */
public class RACE extends MajorRecordDescription {

    private static final Type[] type = {Type.RACE};
    SubList<SubForm> spells = new SubList<SubForm>(Type.SPCT, 4, new SubForm(Type.SPLO));
    SubForm WNAM = new SubForm(Type.WNAM);
    SubData BODT = new SubData(Type.BODT);
    Keywords keywords = new Keywords();
    SubData DATA = new SubData(Type.DATA);
    SubMarkerSet MFNAM = new SubMarkerSet(new MFNAMdata(), Type.MNAM, Type.FNAM);
    SubList<SubString> MTNMs = new SubList<SubString>(new SubString(Type.MTNM, false));
    SubFormArray VTCK = new SubFormArray(Type.VTCK, 2);
    SubFormArray DNAM = new SubFormArray(Type.DNAM, 2);
    SubFormArray HCLF = new SubFormArray(Type.HCLF, 2);
    SubData TINL = new SubData(Type.TINL);
    SubData PNAM = new SubData(Type.PNAM);
    SubData UNAM = new SubData(Type.UNAM);
    SubList<ATKDpackage> ATKDs = new SubList<ATKDpackage>(new ATKDpackage());
    SubData NAM1 = new SubData(Type.NAM1);
    SubMarkerSet EGTrecords = new SubMarkerSet(new EGTmodel(), Type.MNAM, Type.FNAM);
    SubForm GNAM = new SubForm(Type.GNAM);
    SubData NAM3 = new SubData(Type.NAM3);
    SubMarkerSet HKXrecords = new SubMarkerSet(new HKXmodel(), Type.MNAM, Type.FNAM);
    SubForm NAM4 = new SubForm(Type.NAM4);
    SubForm NAM5 = new SubForm(Type.NAM5);
    SubForm NAM7 = new SubForm(Type.NAM7);
    SubForm ONAM = new SubForm(Type.ONAM);
    SubForm LNAM = new SubForm(Type.LNAM);
    SubList<SubString> NAMEs = new SubList<SubString>(new SubString(Type.NAME, true));
    SubList<MTYPpackage> MTYPs = new SubList<MTYPpackage>(new MTYPpackage());
    SubData VNAM = new SubData(Type.VNAM);
    SubList<SubForm> QNAM = new SubList<SubForm>(new SubForm(Type.QNAM));
    SubForm UNES = new SubForm(Type.UNES);
    SubList<SubString> PHTN = new SubList<SubString>(new SubString(Type.PHTN, true));
    SubList<SubData> PHWT = new SubList<SubData>(new SubData(Type.PHWT));
    SubList<HeadData> headData = new SubList<HeadData>(new HeadData());
    SubForm WKMV = new SubForm(Type.WKMV);
    SubForm RNMV = new SubForm(Type.RNMV);
    SubForm SWMV = new SubForm(Type.SWMV);
    SubForm FLMV = new SubForm(Type.FLMV);
    SubForm SNMV = new SubForm(Type.SNMV);

    /**
     *
     */
    RACE() {
        super();

        subRecords.add(spells);
        subRecords.add(WNAM);
        subRecords.add(BODT);
        subRecords.add(keywords);
        subRecords.add(DATA);
        MFNAM.forceMarkers = true;
        subRecords.add(MFNAM);
        subRecords.add(MTNMs);
        subRecords.add(VTCK);
        subRecords.add(DNAM);
        subRecords.add(HCLF);
        subRecords.add(TINL);
        subRecords.add(PNAM);
        subRecords.add(UNAM);
        subRecords.add(ATKDs);
        NAM1.forceExport(true);
        subRecords.add(NAM1);
        EGTrecords.forceMarkers = true;
        subRecords.addSilent(EGTrecords);
        subRecords.add(GNAM);
        NAM3.forceExport(true);
        subRecords.add(NAM3);
        HKXrecords.forceMarkers = true;
        subRecords.addSilent(HKXrecords);
        subRecords.add(NAM4);
        subRecords.add(NAM5);
        subRecords.add(NAM7);
        subRecords.add(ONAM);
        subRecords.add(LNAM);
        subRecords.add(NAMEs);
        subRecords.add(MTYPs);
        subRecords.add(VNAM);
        subRecords.add(QNAM);
        subRecords.add(UNES);
        subRecords.add(PHTN);
        subRecords.add(PHWT);
        subRecords.add(headData);
        subRecords.add(WKMV);
        subRecords.add(RNMV);
        subRecords.add(SWMV);
        subRecords.add(FLMV);
        subRecords.add(SNMV);
    }

    @Override
    Type[] getTypes() {
        return type;
    }

    @Override
    Record getNew() {
        return new RACE();
    }

    @Override
    void importSubRecords(LShrinkArray in, Mask mask) throws BadRecord, DataFormatException, BadParameter {
        Type nextType;
        while (!in.isEmpty() && (mask == null || !mask.done())) {
            nextType = getNextType(in);
            boolean allowed = mask == null || mask.allowed.get(nextType);
            if (nextType == Type.NAM1) {
                subRecords.importSubRecord(in, mask); // import NAM1
                for (int i = 0; i < 8; i++) {
                    if (allowed) {
                        EGTrecords.parseData(EGTrecords.extractRecordData(in));
                    } else {
                        EGTrecords.extractRecordData(in);
                    }
                }
            } else if (nextType == Type.NAM3) {
                subRecords.importSubRecord(in, mask); // import NAM3
                for (int i = 0; i < 6; i++) {
                    if (allowed) {
                        HKXrecords.parseData(HKXrecords.extractRecordData(in));
                    } else {
                        HKXrecords.extractRecordData(in);
                    }
                }
            } else if (nextType == Type.NAM0) {
                subRecords.importSubRecord(in, mask); // import NAM0
                while (!in.isEmpty() && getNextType(in) != Type.WKMV) {
                    if (allowed) {
                        headData.parseData(headData.extractRecordData(in));
                    } else {
                        headData.extractRecordData(in);
                    }
                }
            } else {
                subRecords.importSubRecord(in, mask);
            }
        }
    }

    static class MFNAMdata extends SubShell {

        SubString ANAM = new SubString(Type.ANAM, true);
        SubData MODT = new SubData(Type.MODT);
        private static Type[] types = {Type.ANAM, Type.MODT};

        public MFNAMdata() {
            super(types);
            subRecords.add(ANAM);
            subRecords.add(MODT);
        }

        @Override
        SubRecord getNew(Type type) {
            return new MFNAMdata();
        }
    }

    static class ATKDpackage extends SubShell {

        SubData ATKD = new SubData(Type.ATKD);
        SubString ATKE = new SubString(Type.ATKE, true);
        private static Type[] types = {Type.ATKD, Type.ATKE};

        public ATKDpackage() {
            super(types);
            subRecords.add(ATKD);
            subRecords.add(ATKE);
        }

        @Override
        SubRecord getNew(Type type) {
            return new ATKDpackage();
        }
    }

    static class EGTmodel extends SubShell {

        SubData INDX = new SubData(Type.INDX);
        SubString MODL = new SubString(Type.MODL, true);
        SubData MODT = new SubData(Type.MODT);
        private static Type[] types = {Type.INDX, Type.MODL, Type.MODT};

        public EGTmodel() {
            super(types);
            subRecords.add(INDX);
            subRecords.add(MODL);
            subRecords.add(MODT);
        }

        @Override
        SubRecord getNew(Type type) {
            return new EGTmodel();
        }
    }

    static class HKXmodel extends SubShell {

        SubString MODL = new SubString(Type.MODL, true);
        SubData MODT = new SubData(Type.MODT);
        private static Type[] types = {Type.MODL, Type.MODT};

        public HKXmodel() {
            super(types);
            subRecords.add(MODL);
            subRecords.add(MODT);
        }

        @Override
        SubRecord getNew(Type type) {
            return new HKXmodel();
        }
    }

    static class MTYPpackage extends SubShell {

        SubForm MTYP = new SubForm(Type.MTYP);
        SubData SPED = new SubData(Type.SPED);
        private static Type[] types = {Type.MTYP, Type.SPED};

        public MTYPpackage() {
            super(types);
            subRecords.add(MTYP);
            subRecords.add(SPED);
        }

        @Override
        SubRecord getNew(Type type) {
            return new MTYPpackage();
        }
    }

    static class HeadData extends SubShell {

        private static Type[] types = {Type.NAM0};
        SubData NAM0 = new SubData(Type.NAM0);
        SubData MNAM = new SubData(Type.MNAM);
        SubData FNAM = new SubData(Type.FNAM);
        SubList<HEADs> INDXs = new SubList<HEADs>(new HEADs());
        SubList<MPAVs> MPAVs = new SubList<MPAVs>(new MPAVs());
        SubList<SubForm> RPRM = new SubList<SubForm>(new SubForm(Type.RPRM));
        SubList<SubForm> RPRF = new SubList<SubForm>(new SubForm(Type.RPRF));
        SubList<SubForm> AHCM = new SubList<SubForm>(new SubForm(Type.AHCM));
        SubList<SubForm> AHCF = new SubList<SubForm>(new SubForm(Type.AHCF));
        SubList<SubForm> FTSM = new SubList<SubForm>(new SubForm(Type.FTSM));
        SubList<SubForm> FTSF = new SubList<SubForm>(new SubForm(Type.FTSF));
        SubList<SubForm> DFTM = new SubList<SubForm>(new SubForm(Type.DFTM));
        SubList<SubForm> DFTF = new SubList<SubForm>(new SubForm(Type.DFTF));
        SubList<TINIs> TINIs = new SubList<TINIs>(new TINIs());
        SubForm NAM8 = new SubForm(Type.NAM8);
        SubForm RNAM = new SubForm(Type.RNAM);

        public HeadData() {
            super(types);
            subRecords.add(NAM0);
            subRecords.add(MNAM);
            subRecords.add(FNAM);
            subRecords.add(INDXs);
            subRecords.add(MPAVs);
            subRecords.add(RPRM);
            subRecords.add(RPRF);
            subRecords.add(AHCM);
            subRecords.add(AHCF);
            subRecords.add(FTSM);
            subRecords.add(FTSF);
            subRecords.add(DFTM);
            subRecords.add(DFTF);
            subRecords.add(TINIs);
            subRecords.add(NAM8);
            subRecords.add(RNAM);
        }

        @Override
        SubRecord getNew(Type type) {
            return new HeadData();
        }

        @Override
        Boolean isValid() {
            return true;
        }
    }

    static class HEADs extends SubShell {

        SubData INDX = new SubData(Type.INDX);
        SubData HEAD = new SubData(Type.HEAD);
        private static Type[] types = {Type.INDX, Type.HEAD};

        public HEADs() {
            super(types);
            subRecords.add(INDX);
            subRecords.add(HEAD);
        }

        @Override
        SubRecord getNew(Type type) {
            return new HEADs();
        }

        @Override
        Boolean isValid() {
            return true;
        }
    }

    static class MPAVs extends SubShell {

        SubData MPAI = new SubData(Type.MPAI);
        SubData MPAV = new SubData(Type.MPAV);
        private static Type[] types = {Type.MPAI, Type.MPAV};

        public MPAVs() {
            super(types);
            subRecords.add(MPAI);
            subRecords.add(MPAV);
        }

        @Override
        SubRecord getNew(Type type) {
            return new MPAVs();
        }

        @Override
        Boolean isValid() {
            return true;
        }
    }

    static class TINIs extends SubShell {

        SubData TINI = new SubData(Type.TINI);
        SubString TINT = new SubString(Type.TINT, true);
        SubData TINP = new SubData(Type.TINP);
        SubForm TIND = new SubForm(Type.TIND);
        SubList<TINCs> TINCs = new SubList<TINCs>(new TINCs());
        private static Type[] types = {Type.TINI, Type.TINT, Type.TINP, Type.TIND, Type.TINC, Type.TINV, Type.TIRS};

        public TINIs() {
            super(types);
            subRecords.add(TINI);
            subRecords.add(TINT);
            subRecords.add(TINP);
            subRecords.add(TIND);
            subRecords.add(TINCs);
        }

        @Override
        SubRecord getNew(Type type) {
            return new TINIs();
        }

        @Override
        Boolean isValid() {
            return true;
        }
    }

    static class TINCs extends SubShell {

        SubData TINC = new SubData(Type.TINC);
        SubData TINV = new SubData(Type.TINV);
        SubData TIRS = new SubData(Type.TIRS);
        private static Type[] types = {Type.TINC, Type.TINV, Type.TIRS};

        public TINCs() {
            super(types);
            subRecords.add(TINC);
            subRecords.add(TINV);
            subRecords.add(TIRS);
        }

        @Override
        SubRecord getNew(Type type) {
            return new TINCs();
        }

        @Override
        Boolean isValid() {
            return true;
        }
    }

    // Get / set
    /**
     *
     * @return FormID of the ARMO record that is worn.
     */
    public FormID getWornArmor () {
        return WNAM.getForm();
    }

    /**
     *
     * @param id FormID to set the worn ARMO record to.
     */
    public void setWornArmor(FormID id) {
        WNAM.setForm(id);
    }
//
//    public SubList<SubForm> getKeywordIDs () {
//	return keywords;
//    }
}
