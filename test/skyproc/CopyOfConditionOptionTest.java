package skyproc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lev.LImport;
import lev.LShrinkArray;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import skyproc.ConditionOption.*;
import skyproc.ConditionOption.Cond_CastingSource;
import skyproc.ConditionOption.Cond_CastingSource_FormID;
import skyproc.ConditionOption.Cond_FormID;
import skyproc.genenums.Axis;
import skyproc.genenums.CastingSource;
import skyproc.genenums.CrimeType;

@RunWith(value = Parameterized.class)
public class CopyOfConditionOptionTest {

	private Class<? extends ConditionOption> klass;

	private String mod_name;

	private ConditionOptionTestParameter<? extends ConditionOption> parm;

	private abstract static class ConditionOptionTestParameter<T extends ConditionOption> {
		public Class<T> klass;

		public ConditionOptionTestParameter(Class<T> class1) {
			this.klass = class1;
		}

		public abstract void getExpectedExport(ByteBuffer buf);

		public abstract T getExportedInstance();
	}

	public CopyOfConditionOptionTest(
			ConditionOptionTestParameter<? extends ConditionOption> parm) {
		super();
		this.parm = parm;
		klass = parm.klass;
		mod_name = klass.getName();
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		List<Object[]> data = new ArrayList<Object[]>();
		data.add(new Object[] { new ConditionOptionTestParameter<ConditionOption>(
				ConditionOption.class) {

			@Override
			public void getExpectedExport(ByteBuffer buf) {
				// Parm1
				buf.putInt(0);
				buf.putInt(0);
				// RunType
				buf.putInt(0);
				// FormID
				buf.putInt(0x00adbeef);
				// Parm3 Placeholder
				buf.putInt(0xffffffff);
			}

			@Override
			public ConditionOption getExportedInstance() {
				return new ConditionOption();
			}
		} });

		data.add(new Object[] { new ConditionOptionTestParameter<ConditionOption.Cond_Axis>(
				ConditionOption.Cond_Axis.class) {

			@Override
			public void getExpectedExport(ByteBuffer buf) {
				// Parm1
				buf.put(new byte[] { 'X', 0, 0, 0 });
				buf.putInt(0);
				// RunType
				buf.putInt(0);
				// FormID
				buf.putInt(0x00adbeef);
				// Parm3 Placeholder
				buf.putInt(0xffffffff);
			}

			@Override
			public Cond_Axis getExportedInstance() {
				return new Cond_Axis(Axis.X);
			}
		} });

		data.add(new Object[] { new ConditionOptionTestParameter<ConditionOption.Cond_CastingSource>(
				ConditionOption.Cond_CastingSource.class) {

			@Override
			public void getExpectedExport(ByteBuffer buf) {
				// Parm1
				buf.putInt(CastingSource.Left.ordinal());
				buf.putInt(0);
				// RunType
				buf.putInt(0);
				// FormID
				buf.putInt(0x00adbeef);
				// Parm3 Placeholder
				buf.putInt(0xffffffff);
			}

			@Override
			public Cond_CastingSource getExportedInstance() {
				return new Cond_CastingSource(CastingSource.Left);
			}
		} });

		data.add(new Object[] { new ConditionOptionTestParameter<ConditionOption.Cond_CastingSource_FormID>(
				ConditionOption.Cond_CastingSource_FormID.class) {

			@Override
			public void getExpectedExport(ByteBuffer buf) {
				// Parm1
				buf.putInt(CastingSource.Left.ordinal());
				buf.putInt(0x00febabe);
				// RunType
				buf.putInt(0);
				// FormID
				buf.putInt(0x00adbeef);
				// Parm3 Placeholder
				buf.putInt(0xffffffff);
			}

			@Override
			public Cond_CastingSource_FormID getExportedInstance() {
				return new Cond_CastingSource_FormID(CastingSource.Left,
						new FormID("cafebabe", klass.getName()));
			}
		} });

		data.add(new Object[] { new ConditionOptionTestParameter<ConditionOption.Cond_FormID>(
				ConditionOption.Cond_FormID.class) {

			@Override
			public void getExpectedExport(ByteBuffer buf) {
				// Parm1
				buf.putInt(0x00febabe);
				buf.putInt(0);
				// RunType
				buf.putInt(0);
				// FormID
				buf.putInt(0x00adbeef);
				// Parm3 Placeholder
				buf.putInt(0xffffffff);
			}

			@Override
			public Cond_FormID getExportedInstance() {
				return new Cond_FormID(new FormID("cafebabe", klass.getName()));
			}
		} });

		data.add(new Object[] { new ConditionOptionTestParameter<ConditionOption.Cond_FormID_Axis>(
				ConditionOption.Cond_FormID_Axis.class) {

			@Override
			public void getExpectedExport(ByteBuffer buf) {
				// Parm1
				buf.putInt(0x00febabe);
				buf.put(new byte[] { 'X', 0, 0, 0 });
				// RunType
				buf.putInt(0);
				// FormID
				buf.putInt(0x00adbeef);
				// Parm3 Placeholder
				buf.putInt(0xffffffff);
			}

			@Override
			public Cond_FormID_Axis getExportedInstance() {
				return new Cond_FormID_Axis(new FormID("cafebabe", klass
						.getName()), Axis.X);
			}
		} });

		data.add(new Object[] { new ConditionOptionTestParameter<ConditionOption.Cond_FormID_CastingSource>(
				ConditionOption.Cond_FormID_CastingSource.class) {

			@Override
			public void getExpectedExport(ByteBuffer buf) {
				// Parm1
				buf.putInt(0x00febabe);
				buf.putInt(CastingSource.Left.ordinal());
				// RunType
				buf.putInt(0);
				// FormID
				buf.putInt(0x00adbeef);
				// Parm3 Placeholder
				buf.putInt(0xffffffff);
			}

			@Override
			public Cond_FormID_CastingSource getExportedInstance() {
				return new Cond_FormID_CastingSource(new FormID("cafebabe",
						klass.getName()), CastingSource.Left);
			}
		} });

		data.add(new Object[] { new ConditionOptionTestParameter<ConditionOption.Cond_FormID_CrimeType>(
				ConditionOption.Cond_FormID_CrimeType.class) {

			@Override
			public void getExpectedExport(ByteBuffer buf) {
				// Parm1
				buf.putInt(0x00febabe);
				buf.putInt(CrimeType.Attack.ordinal());
				// RunType
				buf.putInt(0);
				// FormID
				buf.putInt(0x00adbeef);
				// Parm3 Placeholder
				buf.putInt(0xffffffff);
			}

			@Override
			public Cond_FormID_CrimeType getExportedInstance() {
				return new Cond_FormID_CrimeType(new FormID("cafebabe",
						klass.getName()), CrimeType.Attack);
			}
		} });

		/*
		 * TODO fill out the rest of these.
		 * ConditionOption.Cond_FormID_Float.class,
		 * ConditionOption.Cond_FormID_FormID.class,
		 * ConditionOption.Cond_FormID_Int.class,
		 * ConditionOption.Cond_FormID_String.class,
		 * ConditionOption.Cond_Gender.class, 
		 * ConditionOption.Cond_Int.class,
		 * ConditionOption.Cond_Int_FormID.class,
		 * ConditionOption.Cond_Int_FormID_Int.class,
		 * ConditionOption.Cond_Int_Int.class,
		 * ConditionOption.Cond_String.class,
		 * ConditionOption.Cond_WardState.class
		 */
		return data;
	}

	public byte[] getExpectedExport() {
		ByteBuffer temp = ByteBuffer.allocate(512);
		temp.order(ByteOrder.LITTLE_ENDIAN);
		parm.getExpectedExport(temp);
		byte[] array = new byte[temp.position()];
		temp.rewind();
		temp.get(array);
		return array;
	}

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void equalsContract() {
		EqualsVerifier
				.forClass(klass)
				.usingGetClass()
				.suppress(Warning.NONFINAL_FIELDS)
				.suppress(Warning.REFERENCE_EQUALITY)
				.withPrefabValues(FormID.class,
						new FormID("deadbeef", "Mod1.esp"),
						new FormID("cafebabe", "Mod2.esp")).verify();
	}

	@Test
	public void testExport() throws Exception {
		Mod mod = new Mod(mod_name, false);
		File file = folder.newFile(mod_name + ".esp");
		MockModExporter out = new MockModExporter(file, mod);
		ConditionOption that = parm.getExportedInstance();
		that.reference = new FormID("deadbeef", mod_name);
		that.export(out);

		out.close();

		assertThat(out.toByteArray(), equalTo(getExpectedExport()));
	}

	@Test
	public void testParseData() throws Exception {
		Mod mod = new Mod(mod_name, false);

		LImport in = new LShrinkArray(getExpectedExport());
		ConditionOption that = klass.newInstance();
		that.parseData(in, mod);

		ConditionOption expected = parm.getExportedInstance();
		expected.reference = new FormID("deadbeef", mod_name);

		assertThat(that, equalTo(expected));
	}
}
