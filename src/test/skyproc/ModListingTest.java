package skyproc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.commons.codec.binary.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class ModListingTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testModListingStringBoolean() throws Exception {
		ModListing foo_master_mod = new ModListing("Foo", true);
		testMasterModListing(foo_master_mod, "Foo");

		ModListing foo_patch_mod = new ModListing("Foo", false);
		testPatchModListing(foo_patch_mod, "Foo");
	}

	@Test
	public void testModListingString() throws Exception {
		ModListing master = new ModListing("Foo.esm");
		testMasterModListing(master, "Foo");

		ModListing patch = new ModListing("Foo.esp");
		testPatchModListing(patch, "Foo");
	}

	@Test
	public void testSetString() throws Exception {
		ModListing master = new ModListing();
		master.setString("Foo.esm");
		testMasterModListing(master, "Foo");

		ModListing patch1 = new ModListing();
		patch1.setString("Foo.esp");
		testPatchModListing(patch1, "Foo");

		ModListing patch2 = new ModListing();
		patch2.setString("Foo");
		testPatchModListing(patch2, "Foo");
	}

	private void testMasterModListing(ModListing master, String name) {
		assertTrue(master.getMasterTag());
		assertEquals(name + ".esm", master.print());
		assertEquals(name + ".esm", master.toString());
		assertEquals(name, master.printNoSuffix());
		assertTrue(master.isValid());
	}

	private void testPatchModListing(ModListing patch, String name) {
		assertFalse(patch.getMasterTag());
		assertEquals(name + ".esp", patch.print());
		assertEquals(name + ".esp", patch.toString());
		assertEquals(name, patch.printNoSuffix());
		assertTrue(patch.isValid());
	}

	@Test
	public void testExport() throws Exception {
		ModListing export_esm = new ModListing("Export.esm");
		ModExporter out = mock(ModExporter.class);
		export_esm.export(out);
		InOrder io = inOrder(out);
		io.verify(out).write("MAST");
		io.verify(out).write("Export.esm".length() + 1, 2);
		io.verify(out).write("Export.esm");
		io.verify(out).write(0, 1);
		io.verify(out).write("DATA");
		io.verify(out).write(8, 2);
		io.verify(out).write(new byte[8], 0);
		io.verifyNoMoreInteractions();
	}

	@Test
	public void testParseData() throws Exception {
		ModListing import_esm = new ModListing();
		ByteBuffer bb = ByteBuffer.allocate(128);
		Mod mod = mock(Mod.class);

		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put(StringUtils.getBytesUsAscii("MAST"));
		bb.putShort((short) ("Import.esm".length() + 1));
		bb.put(StringUtils.getBytesUsAscii("Import.esm"));
		bb.put((byte) 0);
		bb.put(StringUtils.getBytesUsAscii("DATA"));
		bb.putShort((short) 8);
		bb.put(new byte[8]);
		bb.rewind();

		import_esm.parseData(bb, mod);

		assertThat(import_esm.mast.string, equalTo("Import"));
		assertTrue(import_esm.getMasterTag());
	}

	@Test
	public void testGetNew() throws Exception {
		ModListing mod_listing1 = new ModListing();
		ModListing mod_listing2 = (ModListing) mod_listing1.getNew();
		assertEquals(ModListing.class, mod_listing2.getClass());
	}

	@Test
	public void testGetContentLength() throws Exception {
		ModListing test_esm = new ModListing("test.esm");
		assertEquals(23, test_esm.getContentLength(null));
	}

	@Test
	public void testIsValid() throws Exception {
		ModListing mod_listing = new ModListing();
		assertFalse(mod_listing.isValid());
	}

	@Test
	public void testEquals() throws Exception {
		ModListing foo_esm = new ModListing("Foo.esm");
		ModListing foo_esm2 = new ModListing("Foo.esm");
		ModListing foo_esp = new ModListing("Foo.esp");
		ModListing bar_esm = new ModListing("Bar.esm");
		Mod bar_esm2 = mock(Mod.class);
		ModListing bar_esm_2 = new ModListing("Bar.esm");
		when(bar_esm2.getInfo()).thenReturn(bar_esm_2);

		assertTrue(foo_esm.equals(foo_esm));
		assertTrue(foo_esm.equals(foo_esm2));
		assertTrue(bar_esm.equals(bar_esm2));
		assertFalse(foo_esm.equals(foo_esp));
		assertFalse(foo_esm.equals(bar_esm));
		assertFalse(foo_esm.equals(null));
		assertFalse(foo_esm.equals("Foo.esm"));
	}

	@Test
	public void testHashCode() throws Exception {
		ModListing foo_esm = new ModListing("Foo.esm");
		ModListing foo_esm2 = new ModListing("Foo.esm");
		ModListing foo_esp = new ModListing("Foo.esp");
		ModListing bar_esm = new ModListing("Bar.esm");

		assertEquals(foo_esm.hashCode(), foo_esm.hashCode());
		assertEquals(foo_esm.hashCode(), foo_esm2.hashCode());
		assertNotEquals(foo_esm.hashCode(), foo_esp.hashCode());
		assertNotEquals(foo_esm.hashCode(), bar_esm.hashCode());
	}

	@Test
	public void testCompareTo() throws Exception {
		Mod global_patch = mock(Mod.class);

		ModListing global_esm = new ModListing("Global.esm");
		when(global_patch.getInfo()).thenReturn(global_esm);

		ModListing foo_esm = new ModListing("Foo.esm");
		ModListing foo_esp = new ModListing("Foo.esp");
		ModListing bar_esm = new ModListing("Bar.esm");

		ModListing[] all = new ModListing[] { foo_esm, foo_esp, bar_esm,
				global_esm };

		ModListing[] non_global = new ModListing[] { foo_esm, foo_esp, bar_esm };

		ModListing[] masters = new ModListing[] { foo_esm, bar_esm, global_esm };

		ModListing[] patches = new ModListing[] { foo_esp };

		ModListing[] active_plugins = new ModListing[] { bar_esm };

		// object compares to itself as the same
		for (ModListing i : all) {
			assertEquals(0, i.compareTo(i));
		}

		// object compares to other object as not the same
		for (ModListing i : all) {
			for (ModListing j : all) {
				if (i != j) {
					assertNotEquals(0, i.compareTo(j));
				}
			}
		}

		// case 1 from compare function.
		for (ModListing master : masters) {
			for (ModListing patch : patches) {
				assertThat(master, lessThan(patch));
				assertThat(patch, greaterThan(master));
			}
		}

		SPGlobal.setGlobalPatch(global_patch);

		// case 2 from compare function.
		for (ModListing i : non_global) {
			if (i.getMasterTag() == true) {
				assertThat(global_esm, greaterThan(i));
				assertThat(i, lessThan(global_esm));
			}
		}

		global_esm.setMasterTag(false);

		// case 2 from compare function.
		for (ModListing i : non_global) {
			assertThat(global_esm, greaterThan(i));
			assertThat(i, lessThan(global_esm));
		}

		// skyrim/update are less than everything else.
		for (ModListing i : all) {
			for (ModListing j : new ModListing[] { ModListing.skyrim,
					ModListing.update }) {
				assertThat(j, lessThan(i));
				assertThat(i, greaterThan(j));
			}
		}

		for (ModListing i : active_plugins) {
			SPDatabase.activePlugins.add(i);
		}

		// TODO active Plugins
	}

}
