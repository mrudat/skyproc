package skyproc;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ModExporterAbstractTest<T extends ModExporter> {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testModExporter() throws Exception {
		File path = folder.newFile("export-test.esm");
		Mod mod = mock(Mod.class);

		T exporter = newModExporter(path, mod);

		assertSame(exporter.getExportMod(), mod);
		assertThat(slurpAsByteArray(path, exporter),
				equalTo(Hex.decodeHex("".toCharArray())));
	}

	@Test
	public void testSourceMod() throws Exception {
		File path = folder.newFile("export-test.esm");
		Mod mod = mock(Mod.class);

		ModExporter exporter = newModExporter(path, mod);

		Mod srcMod = mock(Mod.class);

		exporter.setSourceMod(srcMod);

		assertSame(srcMod, exporter.getSourceMod());
	}

	@Test
	public void testSourceMajor() throws Exception {
		File path = folder.newFile("export-test.esm");
		Mod mod = mock(Mod.class);

		ModExporter exporter = newModExporter(path, mod);

		MajorRecord<?> src = mock(MajorRecord.class);

		exporter.setSourceMajor(src);

		assertSame(src, exporter.getSourceMajor());
	}

	@Test
	public void testWriteInteger() throws Exception {
		File path = folder.newFile("export-test.esm");
		Mod mod = mock(Mod.class);

		T exporter = newModExporter(path, mod);
		exporter.write(1);
		exporter.close();
		assertThat(slurpAsByteArray(path, exporter),
				equalTo(Hex.decodeHex("01000000".toCharArray())));
	}

	@Test
	public void testWriteIntInt() throws Exception {
		File path = folder.newFile("export-test.esm");
		Mod mod = mock(Mod.class);

		T exporter = newModExporter(path, mod);
		exporter.write(1, 1);
		exporter.write(2, 2);
		exporter.write(3, 3);
		exporter.write(4, 4);
		exporter.close();
		assertThat(slurpAsByteArray(path, exporter),
				equalTo(Hex.decodeHex(("01" + "0200" + "030000" + "04000000")
						.toCharArray())));
	}

	@Test
	public void testWriteByte() throws Exception {
		File path = folder.newFile("export-test.esm");
		Mod mod = mock(Mod.class);

		T exporter = newModExporter(path, mod);
		byte b = 0x42;
		exporter.write(b);
		exporter.close();
		assertThat(slurpAsByteArray(path, exporter), equalTo(new byte[] { b }));
	}

	@Test
	public void testWriteByteArray() throws Exception {
		File path = folder.newFile("export-test.esm");
		Mod mod = mock(Mod.class);

		T exporter = newModExporter(path, mod);
		byte[] b = Hex.decodeHex("deadbeef".toCharArray());
		exporter.write(b);
		exporter.close();
		assertThat(slurpAsByteArray(path, exporter), equalTo(b));
	}

	@Test
	public void testWriteByteArrayInteger() throws Exception {
		File path = folder.newFile("export-test.esm");
		Mod mod = mock(Mod.class);

		T exporter = newModExporter(path, mod);
		byte[] b = Hex.decodeHex("deadbeef".toCharArray());
		exporter.write(b, 8);
		// NOTE this still writes 4 bytes!
		exporter.write(b, 1);
		exporter.close();
		assertThat(
				slurpAsByteArray(path, exporter),
				equalTo(Hex.decodeHex("deadbeef00000000deadbeef".toCharArray())));
	}

	@Test
	public void testWriteFloat() throws Exception {
		File path = folder.newFile("export-test.esm");
		Mod mod = mock(Mod.class);

		T exporter = newModExporter(path, mod);
		Float f = (float) 1.0;
		exporter.write(f);
		exporter.close();
		assertThat(slurpAsByteArray(path, exporter), equalTo(new byte[] { 0, 0,
				-128, 63 }));
	}

	@Test
	public void testWriteString() throws Exception {
		File path = folder.newFile("export-test.esm");
		Mod mod = mock(Mod.class);

		T exporter = newModExporter(path, mod);
		String s = "✓";
		exporter.write(s);
		exporter.close();
		assertThat(slurpAsByteArray(path, exporter), equalTo(new byte[] { 19 }));
	}

	@Test
	public void testWriteStringInt() throws Exception {
		File path = folder.newFile("export-test.esm");
		Mod mod = mock(Mod.class);

		T exporter = newModExporter(path, mod);
		String s = "✓";
		exporter.write(s, 5);
		exporter.close();
		assertThat(slurpAsByteArray(path, exporter), equalTo(new byte[] { 19,
				0, 0, 0, 0 }));
	}

	@Test
	public void testWriteBoolInt() throws Exception {
		File path = folder.newFile("export-test.esm");
		Mod mod = mock(Mod.class);

		T exporter = newModExporter(path, mod);
		boolean b = true;
		exporter.write(b, 1);
		exporter.write(b, 4);
		exporter.close();
		assertThat(slurpAsByteArray(path, exporter), equalTo(new byte[] { 1, 1,
				0, 0, 0 }));
	}

	@Test
	public void testWriteZeros() throws Exception {
		File path = folder.newFile("export-test.esm");
		Mod mod = mock(Mod.class);

		T exporter = newModExporter(path, mod);
		exporter.writeZeros(3);
		exporter.close();
		assertThat(slurpAsByteArray(path, exporter), equalTo(new byte[] { 0, 0,
				0 }));
	}

	interface IModExporterTest<T extends ModExporter> {

		abstract T newModExporter(File path, Mod mod)
				throws FileNotFoundException;

		abstract byte[] slurpAsByteArray(File path, T exporter)
				throws IOException;

	}

	public ModExporterAbstractTest(IModExporterTest<T> imet) {
		super();
		this.imet = imet;
	}

	IModExporterTest<T> imet;

	T newModExporter(File path, Mod mod) throws FileNotFoundException {
		return imet.newModExporter(path, mod);
	}

	byte[] slurpAsByteArray(File path, T exporter) throws IOException {
		return imet.slurpAsByteArray(path, exporter);
	}

	@Parameters
	public static Collection<Object[]> data() {
		List<Object[]> data = new ArrayList<Object[]>();
		data.add(new Object[] { new IModExporterTest<ModExporter>() {

			@Override
			public ModExporter newModExporter(File path, Mod mod)
					throws FileNotFoundException {
				return new ModExporter(path, mod);
			}

			@Override
			public byte[] slurpAsByteArray(File path, ModExporter exporter)
					throws IOException {
				return IOUtils.toByteArray(org.apache.commons.io.FileUtils
						.openInputStream(path));
			}

		} });
		data.add(new Object[] { new IModExporterTest<MockModExporter>() {

			@Override
			public MockModExporter newModExporter(File path, Mod mod)
					throws FileNotFoundException {
				return new MockModExporter(path, mod);
			}

			@Override
			public byte[] slurpAsByteArray(File path, MockModExporter exporter)
					throws IOException {
				return exporter.toByteArray();
			}

		} });
		return data;
	}

}
