package skyproc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.commons.io.output.ByteArrayOutputStream;

public class MockModExporter extends ModExporter {

	ByteArrayOutputStream baos;
	BufferedOutputStream output;

	public MockModExporter(File path, Mod mod) throws FileNotFoundException {
		super(path, mod);
	}

	@Override
	public void close() throws IOException {
		output.close();
	}

	@Override
	public void openOutput(String path) throws FileNotFoundException {
		// super.openOutput(path);
		baos = new ByteArrayOutputStream();
		output = new BufferedOutputStream(baos);
	}

	@Override
	public void write(byte b) throws IOException {
		output.write(b);
	}

	@Override
	public void write(byte[] array) throws IOException {
		output.write(array);
	}

	@Override
	public void write(boolean input, int size) throws IOException {
		if (input) {
			output.write(1);
		} else {
			output.write(0);
		}
		if (size > 1) {
			writeZeros(size - 1);
		}
	}

	@Override
	public void write(byte[] array, int size) throws IOException {
		output.write(array);
		if (array.length < size) {
			writeZeros(size - array.length);
		}
	}

	@Override
	public void write(float input) throws IOException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.putFloat(input);
		write(bb.array());
	}

	@Override
	public void write(int input, int size) throws IOException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.putInt(input);
		if (size < 4) {
			output.write(bb.array(), 0, size);
		} else {
			output.write(bb.array());
			writeZeros(size - 4);
		}
	}

	@Override
	public void write(int input) throws IOException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.putInt(input);
		write(bb.array());
	}

	@Override
	public void write(String input, int size) throws IOException {
		write(input);
		if (input.length() < size) {
			writeZeros(size - input.length());
		}
	}

	@Override
	public void write(String input) throws IOException {
		for (char c : input.toCharArray()) {
			output.write(c);
		}
	}

	@Override
	public void writeZeros(int size) throws IOException {
		byte[] buf = new byte[64];
		while (size >= 64) {
			output.write(buf);
			size -= 64;
		}
		if (size == 0) {
			return;
		}
		output.write(buf, 0, size);
	}

	public byte[] toByteArray() {
		return baos.toByteArray();
	}

}
