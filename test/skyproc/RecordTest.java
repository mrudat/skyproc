package skyproc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import lev.LImport;
import lev.LShrinkArray;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import skyproc.exceptions.BadRecord;

@RunWith(MockitoJUnitRunner.class)
public class RecordTest {
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	
	@SuppressWarnings("serial")
	private final class RecordTestRecord extends Record {
		@Override
		public String toString() {
			return "Record.toString";
		}

		@Override
		public String print() {
			return "Record.print";
		}

		@Override
		ArrayList<String> getTypes() {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add("FAKE");
			return temp;
		}

		@Override
		int getSizeLength() {
			return 4;
		}

		@Override
		int getFluffLength() {
			return 1234;
		}

		@Override
		int getContentLength(ModExporter out) {
			return 4456;
		}
	}

	private Record record;

	@Before
	public void setUp() throws Exception {
		record = new RecordTestRecord();
	}

	@Test
	public void testIsValid() {
		assertEquals(true, record.isValid());
	}

	@Test
	public void testToString() {
		assertEquals("Record.toString", record.toString());
	}

	@Test
	public void testParseData() throws Exception {
		Mod srcMod = mock(Mod.class);
		record.parseData(new LShrinkArray(getExpectedExport()), srcMod);
		throw new RuntimeException("Not implemented yet");
	}

	@Test
	public void testParseData2() throws Exception {
		ByteBuffer in = ByteBuffer.wrap(getExpectedExport());
		Mod srcMod = mock(Mod.class);
		record.parseData(in, srcMod);
		throw new RuntimeException("Not implemented yet");
	}

	@Test
	public void testPrint() {
		assertEquals("Record.print", record.print());
	}

	@Test
	public void testGetTypes() {
		ArrayList<String> types = record.getTypes();
		assertEquals(1, types.size());
		assertEquals("FAKE", types.get(0));
	}

	@Test
	public void testGetTypeList() {
		ArrayList<String> type_list = Record.getTypeList("Foo");
		assertEquals(1, type_list.size());
		assertEquals("Foo", type_list.get(0));
	}

	@Test
	public void testGetType() {
		String type = record.getType();
		assertEquals("FAKE", type);
	}

	@Test
	public void testGetNew() {
		try {
			record.getNew();
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals("Not supported yet.", e.getMessage());
		}
	}

	@Test
	public void testGetNewRecord() {
		try {
			record.getNewRecord();
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals("Not supported yet.", e.getMessage());
		}
	}

	@Test
	public void testGetNextType() throws BadRecord {
		LImport in = new LShrinkArray(getExpectedExport());
		String type = Record.getNextType(in);
		assertThat(type, equalTo("FAKE"));
	}

	@Test
	public void testExport() throws IOException {
		File path = folder.newFile("Record.esp");
		Mod mod = new Mod("Record.esp", false);
		MockModExporter out = new MockModExporter(path, mod);
		record.export(out);
		out.close();
		
		assertThat(out.toByteArray(), equalTo(getExpectedExport()));
	}

	private byte[] getExpectedExport() {
		byte[] expected;
		ByteBuffer buf = ByteBuffer.allocate(512);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		for (char c : "FAKE".toCharArray()) {
			buf.put((byte)c);
		}
		buf.putInt(4456);
		expected = new byte[buf.position()];
		buf.rewind();
		buf.get(expected);
		return expected;
	}

	@Test
	public void testGetRecordLength() throws IOException {
		LImport in = new LShrinkArray(getExpectedExport());
		assertEquals(5698, record.getRecordLength(in));
	}

	@Test
	public void testExtractRecordData() {
		LImport in = new LShrinkArray(getExpectedExport());
		LImport temp = record.extractRecordData(in);
		assertThat(temp.getAllBytes(), equalTo(new byte[0]));
		throw new RuntimeException("Not implemented yet");
	}

	@Test
	public void testExtractData() {
		LImport in = new LShrinkArray(getExpectedExport());
		LImport temp = record.extractData(in, 1024);
		throw new RuntimeException("Not implemented yet");
	}

	@Test
	public void testGetHeaderLength() {
		assertEquals(1242, record.getHeaderLength());
	}

	@Test
	public void testGetIdentifierLength() {
		assertEquals(4, record.getIdentifierLength());
	}

	@Test
	public void testGetSizeLength() {
		assertEquals(4, record.getSizeLength());
	}

	@Test
	public void testGetFluffLength() {
		assertEquals(1234, record.getFluffLength());
	}

	@Test
	public void testGetTotalLength() {
		ModExporter out = mock(ModExporter.class);
		assertEquals(5698, record.getTotalLength(out));
	}

	@Test
	public void testGetContentLength() {
		ModExporter out = mock(ModExporter.class);
		assertEquals(4456, record.getContentLength(out));
	}

	@Test
	public void testNewSyncLog() {
		record.newSyncLog("syncLog");
		throw new RuntimeException("Not implemented yet");
	}

	@Test
	public void testLogging() {
		assertFalse(record.logging());
	}

	@Test
	public void testLogMain() {
		record.logMain("header", "log");
		throw new RuntimeException("Not implemented yet");
	}

	@Test
	public void testLogSync() {
		record.logSync("header", "log");
		throw new RuntimeException("Not implemented yet");
	}

	@Test
	public void testLogError() {
		record.logError("header", "log");
		throw new RuntimeException("Not implemented yet");
	}

	enum FooEnum {
		SPECIAL_LOG,
	}

	@Test
	public void testLogSpecial() {
		record.logSpecial(FooEnum.SPECIAL_LOG, "header", "log");
		throw new RuntimeException("Not implemented yet");
	}

	@Test
	public void testLog() {
		record.log("header", "log");
		throw new RuntimeException("Not implemented yet");
	}

	@Test
	public void testLogMod() {
		Mod srcMod = mock(Mod.class);
		Record.logMod(srcMod, "header", "data");
		throw new RuntimeException("Not implemented yet");
	}

	@Test
	public void testNewLog() {
		record.newLog("newLog");
		throw new RuntimeException("Not implemented yet");
	}

	@Test
	public void testFlush() {
		record.flush();
		throw new RuntimeException("Not implemented yet");
	}

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(Record.class)
				.verify();
	}

}
