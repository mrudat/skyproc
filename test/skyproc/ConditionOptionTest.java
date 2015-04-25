package skyproc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

import skyproc.ConditionOption.Cond_FormID_String;
import skyproc.ConditionOption.Cond_String;
import skyproc.genenums.Axis;
import skyproc.genenums.CastingSource;
import skyproc.genenums.CrimeType;
import skyproc.genenums.Gender;
import skyproc.genenums.WardState;

@RunWith(value = Parameterized.class)
public class ConditionOptionTest {

	private Class<? extends ConditionOption> klass;

	private String mod_name;

	private List<IFillBuffer> serialised;

	private Object[] parms;

	private Class<?>[] types;

	private interface IFillBuffer {
		void fillBuffer(ByteBuffer buf);
	}

	private interface IParm {
		Object buildParm(Parms parms);
	}

	private static class Parms {
		public Parms(Class<? extends ConditionOption> klass) {
			this.klass = klass;
		}

		private Class<? extends ConditionOption> klass;

		private ArrayList<IFillBuffer> serialised = new ArrayList<IFillBuffer>();

		private ArrayList<Object> parms = new ArrayList<Object>();

		private ArrayList<Class<?>> types = new ArrayList<Class<?>>();

		Parms fillBuffer(IFillBuffer fb) {
			serialised.add(fb);
			return this;
		}

		Parms parms(Object arg) {
			parms.add(arg);
			types.add(arg.getClass());
			return this;
		}

		<T> Parms parms(T arg, Class<T> klass) {
			parms.add(arg);
			types.add(klass);
			return this;
		}

		private Object[] toArray() {
			return new Object[] { klass, serialised, parms.toArray(),
					types.toArray(new Class<?>[0]) };
		}

		void addTo(List<Object[]> data) {
			data.add(toArray());
		}

		public Parms parms(IParm arg) {
			return parms(arg.buildParm(this));
		}
	}

	public ConditionOptionTest(Class<? extends ConditionOption> klass,
			List<IFillBuffer> serialised, Object[] parms, Class<?>[] types) {
		super();
		this.klass = klass;
		mod_name = klass.getName();
		this.serialised = serialised;
		this.parms = parms;
		this.types = types;
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		List<Object[]> data = new ArrayList<Object[]>();

		new Parms(ConditionOption.class).addTo(data);

		new Parms(ConditionOption.Cond_Axis.class)
				.fillBuffer(buf -> buf.put(new byte[] { 'X', 0, 0, 0 }))
				.parms(Axis.X).addTo(data);

		new Parms(ConditionOption.Cond_CastingSource.class)
				.fillBuffer(buf -> buf.putInt(CastingSource.Left.ordinal()))
				.parms(CastingSource.Left).addTo(data);

		new Parms(ConditionOption.Cond_CastingSource_FormID.class)
				.fillBuffer(buf -> buf.putInt(CastingSource.Left.ordinal()))
				.fillBuffer(buf -> buf.putInt(0x00febabe))
				.parms(CastingSource.Left)
				.parms(p -> new FormID("cafebabe", p.klass.getName()))
				.addTo(data);

		new Parms(ConditionOption.Cond_FormID.class)
				.fillBuffer(buf -> buf.putInt(0x00febabe))
				.parms(p -> new FormID("cafebabe", p.klass.getName()))
				.addTo(data);

		new Parms(ConditionOption.Cond_FormID_Axis.class)
				.fillBuffer(buf -> buf.putInt(0x00febabe))
				.fillBuffer(buf -> buf.put(new byte[] { 'X', 0, 0, 0 }))
				.parms(p -> new FormID("cafebabe", p.klass.getName()))
				.parms(Axis.X).addTo(data);

		new Parms(ConditionOption.Cond_FormID_CastingSource.class)
				.fillBuffer(buf -> buf.putInt(0x00febabe))
				.fillBuffer(buf -> buf.putInt(CastingSource.Left.ordinal()))
				.parms(p -> new FormID("cafebabe", p.klass.getName()))
				.parms(CastingSource.Left).addTo(data);

		new Parms(ConditionOption.Cond_FormID_CrimeType.class)
				.fillBuffer(buf -> buf.putInt(0x00febabe))
				.fillBuffer(buf -> buf.putInt(CrimeType.Attack.ordinal()))
				.parms(p -> new FormID("cafebabe", p.klass.getName()))
				.parms(CrimeType.Attack).addTo(data);

		new Parms(ConditionOption.Cond_FormID_Float.class)
				.fillBuffer(buf -> buf.putInt(0x00febabe))
				.fillBuffer(buf -> buf.putFloat((float) 0.7734))
				.parms(p -> new FormID("cafebabe", p.klass.getName()))
				.parms((float) 0.7734, float.class).addTo(data);

		new Parms(ConditionOption.Cond_FormID_FormID.class)
				.fillBuffer(buf -> buf.putInt(0x00febabe))
				.fillBuffer(buf -> buf.putInt(0x00fed00d))
				.parms(p -> new FormID("cafebabe", p.klass.getName()))
				.parms(p -> new FormID("cafed00d", p.klass.getName()))
				.addTo(data);

		new Parms(ConditionOption.Cond_FormID_Int.class)
				.fillBuffer(buf -> buf.putInt(0x00febabe))
				.fillBuffer(buf -> buf.putInt(0xfeedface))
				.parms(p -> new FormID("cafebabe", p.klass.getName()))
				.parms(0xfeedface, int.class).addTo(data);

		new Parms(ConditionOption.Cond_FormID_String.class)
				.fillBuffer(buf -> buf.putInt(0x00febabe))
				.fillBuffer(buf -> buf.putInt(0xf09de22e))
				.parms(p -> new FormID("cafebabe", p.klass.getName()))
				.parms("Why do we specify this when it's ignored?").addTo(data);

		new Parms(ConditionOption.Cond_Gender.class)
				.fillBuffer(buf -> buf.putInt(Gender.FEMALE.ordinal()))
				.parms(Gender.FEMALE).addTo(data);

		new Parms(ConditionOption.Cond_Int.class)
				.fillBuffer(buf -> buf.putInt(0xfeedface))
				.parms(0xfeedface, int.class).addTo(data);

		new Parms(ConditionOption.Cond_Int_FormID.class)
				.fillBuffer(buf -> buf.putInt(0xfeedface))
				.fillBuffer(buf -> buf.putInt(0x00febabe))
				.parms(0xfeedface, int.class)
				.parms(p -> new FormID("cafebabe", p.klass.getName()))
				.addTo(data);

		new Parms(ConditionOption.Cond_Int_FormID_Int.class)
				.fillBuffer(buf -> buf.putInt(0xfeedface))
				.fillBuffer(buf -> buf.putInt(0x00febabe))
				.fillBuffer(buf -> buf.putInt(0xdeadfa11))
				.parms(0xfeedface, int.class)
				.parms(p -> new FormID("cafebabe", p.klass.getName()))
				.parms(0xdeadfa11, int.class).addTo(data);

		new Parms(ConditionOption.Cond_Int_Int.class)
				.fillBuffer(buf -> buf.putInt(0xfeedface))
				.fillBuffer(buf -> buf.putInt(0xdeadfa11))
				.parms(0xfeedface, int.class).parms(0xdeadfa11, int.class)
				.addTo(data);

		new Parms(ConditionOption.Cond_String.class)
				.fillBuffer(buf -> buf.putInt(0xf09de22e))
				.parms("Why do we specify this when it's ignored?").addTo(data);

		new Parms(ConditionOption.Cond_WardState.class)
				.fillBuffer(buf -> buf.putInt(WardState.Absorb.ordinal()))
				.parms(WardState.Absorb).addTo(data);

		return data;
	}

	private void applyFillBuffer(ByteBuffer buf, List<IFillBuffer> fb,
			int index, IFillBuffer dfb) {
		if (fb.size() >= index + 1) {
			fb.get(index).fillBuffer(buf);
		} else {
			dfb.fillBuffer(buf);
		}
	}

	public byte[] getExpectedExport() {
		ByteBuffer buf = ByteBuffer.allocate(512);
		buf.order(ByteOrder.LITTLE_ENDIAN);

		// Parm1
		applyFillBuffer(buf, serialised, 0, b -> b.putInt(0));
		
		if (klass.equals(Cond_String.class)) {
			/*
			 * TODO Find out if we should be skipping Parm2 or not in this
			 * case...
			 */
		} else {
			// Parm2
			applyFillBuffer(buf, serialised, 1, b -> b.putInt(0));
		}
		
		// RunType
		buf.putInt(0);
		
		// FormID
		buf.putInt(0x00adbeef);
		
		// Parm3 Placeholder
		applyFillBuffer(buf, serialised, 2, b -> b.putInt(0xffffffff));

		byte[] array = new byte[buf.position()];
		buf.rewind();
		buf.get(array);
		return array;
	}

	private ConditionOption getExportedInstance() throws Exception {
		return klass.getDeclaredConstructor(types).newInstance(parms);
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
		ConditionOption that = getExportedInstance();
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

		ConditionOption expected = getExportedInstance();
		expected.reference = new FormID("deadbeef", mod_name);

		assertThat(that, equalTo(expected));
	}

	@Test
	public void testAllFormIDs() throws Exception {
		ConditionOption that = getExportedInstance();
		ArrayList<FormID> actual = that.allFormIDs();

		List<FormID> expected = Stream.concat(
				Arrays.stream(new FormID[] { FormID.NULL }),
				Arrays.stream(parms)
						.filter(p -> p.getClass().equals(FormID.class))
						.map(FormID.class::cast)).collect(Collectors.toList());

		assertThat(actual, equalTo(expected));
	}

	@Test
	public void testGetParam() throws Exception {
		ConditionOption that = getExportedInstance();

		switch (parms.length) {
		case 3:
			assertThat(that.getParam3(), equalTo(parms[2]));
		case 2:
			if (klass.equals(Cond_FormID_String.class)) {
				// TODO find out if a string should return a null value or a byte pattern
				assertThat(that.getParam2(), equalTo(new byte[] { (byte) 0x2e,
						(byte) 0xe2, (byte) 0x9d, (byte) 0xf0 }));
			} else {
				assertThat(that.getParam2(), equalTo(parms[1]));
			}
		case 1:
			if (klass.equals(Cond_String.class)) {
				// TODO find out if a string should return a null value or a byte pattern
				assertThat(that.getParam2(), nullValue());
			} else {
				assertThat(that.getParam1(), equalTo(parms[0]));
			}
		case 0:
		default:

		}

		switch (parms.length) {
		case 0:
			assertThat(that.getParam1(), nullValue());
		case 1:
			assertThat(that.getParam2(), nullValue());
		case 2:
			assertThat(that.getParam3(), nullValue());
		case 3:
		default:
		}
	}

	@Test
	public void testGetOption() throws Exception {
		// FIXME how to test this?
		throw new RuntimeException("not yet implemented");
	}
}
