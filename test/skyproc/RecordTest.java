package skyproc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import lev.LImport;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import skyproc.exceptions.BadRecord;

@RunWith(MockitoJUnitRunner.class)
public class RecordTest {

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
			temp.add("DummyType");
			return temp;
		}

		@Override
		int getSizeLength() {
			return 4567;
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
		LImport in = mock(LImport.class);
		record.parseData(in, srcMod);
	}

	@Test
	public void testParseData2() throws Exception {
		ByteBuffer in = ByteBuffer.allocate(1024);
		Mod srcMod = mock(Mod.class);
		record.parseData(in, srcMod);
	}

	@Test
	public void testPrint() {
		assertEquals("Record.print", record.print());
	}

	@Test
	public void testGetTypes() {
		ArrayList<String> types = record.getTypes();
		assertEquals(1, types.size());
		assertEquals("DummyType", types.get(0));
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
		assertEquals("DummyType", type);
	}

	@Test
	public void testGetNew() {
		try {
			@SuppressWarnings("unused")
			Record new_record = record.getNew();
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals("Not supported yet.", e.getMessage());
		}
	}

	@Test
	public void testGetNewRecord() {
		try {
			@SuppressWarnings("unused")
			Record new_record = record.getNewRecord();
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals("Not supported yet.", e.getMessage());
		}
	}

	@Test
	public void testGetNextType() throws BadRecord {
		LImport in = mock(LImport.class);
		String type = Record.getNextType(in);
	}

	@Test
	public void testExport() throws IOException {
		ModExporter out = mock(ModExporter.class);
		record.export(out);
	}

	@Test
	public void testGetRecordLength() throws IOException {
		LImport in = mock(LImport.class);
		assertEquals(3000, record.getRecordLength(in));
	}

	@Test
	public void testExtractRecordData() {
		LImport in = mock(LImport.class);
		LImport temp = record.extractRecordData(in);
	}

	@Test
	public void testExtractData() {
		LImport in = mock(LImport.class);
		LImport temp = record.extractData(in, 1024);
	}

	@Test
	public void testGetHeaderLength() {
		assertEquals(5805, record.getHeaderLength());
	}

	@Test
	public void testGetIdentifierLength() {
		assertEquals(4, record.getIdentifierLength());
	}

	@Test
	public void testGetSizeLength() {
		assertEquals(4567, record.getSizeLength());
	}

	@Test
	public void testGetFluffLength() {
		assertEquals(1234, record.getFluffLength());
	}

	@Test
	public void testGetTotalLength() {
		ModExporter out = mock(ModExporter.class);
		assertEquals(10261, record.getTotalLength(out));
	}

	@Test
	public void testGetContentLength() {
		ModExporter out = mock(ModExporter.class);
		assertEquals(4456, record.getContentLength(out));
	}

	@Test
	public void testNewSyncLog() {
		record.newSyncLog("syncLog");
	}

	@Test
	public void testLogging() {
		assertFalse(record.logging());
	}

	@Test
	public void testLogMain() {
		record.logMain("header", "log");
	}

	@Test
	public void testLogSync() {
		record.logSync("header", "log");
	}

	@Test
	public void testLogError() {
		record.logError("header", "log");
	}

	enum FooEnum {
		SPECIAL_LOG,
	}

	@Test
	public void testLogSpecial() {
		record.logSpecial(FooEnum.SPECIAL_LOG, "header", "log");
	}

	@Test
	public void testLog() {
		record.log("header", "log");
	}

	@Test
	public void testLogMod() {
		Mod srcMod = mock(Mod.class);
		Record.logMod(srcMod, "header", "data");
	}

	@Test
	public void testNewLog() {
		record.newLog("newLog");
	}

	@Test
	public void testFlush() {
		record.flush();
	}

}
