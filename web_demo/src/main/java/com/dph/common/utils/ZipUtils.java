/**
 * @author peihuadeng
 *
 */
package com.dph.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author peihuadeng
 *
 */
public class ZipUtils {

	private final static Logger logger = LoggerFactory.getLogger(ZipUtils.class);

	public static byte[] compress(byte[] data) {
		Deflater compresser = new Deflater();
		compresser.reset();
		compresser.setInput(data);
		compresser.finish();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			byte[] buf = new byte[1024];
			while (!compresser.finished()) {
				int i = compresser.deflate(buf);
				bos.write(buf, 0, i);
			}

			return bos.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("fail to zip data by byte array.", e);
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.warn("success to zip data by byte array, but fail to close output stream.", e);
				}
			}
			compresser.end();
		}
	}

	public static void compress(byte[] data, OutputStream out) {
		Deflater compresser = new Deflater();
		compresser.reset();
		DeflaterOutputStream dout = new DeflaterOutputStream(out, compresser);

		try {
			dout.write(data, 0, data.length);
			dout.finish();
			dout.flush();
		} catch (IOException e) {
			throw new RuntimeException("fail to zip data by stream.", e);
		}
	}

	public static byte[] decompress(byte[] data) {
		Inflater decompresser = new Inflater();
		decompresser.reset();
		decompresser.setInput(data);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			byte[] buf = new byte[1024];
			while (!decompresser.finished()) {
				int i = decompresser.inflate(buf);
				out.write(buf, 0, i);
			}
			return out.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("fail to decompress by byte array", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.warn("success to decompress by byte array, but fail to close output stream.", e);
				}
			}
			decompresser.end();
		}
	}

	public static byte[] decompress(InputStream input) {
		Inflater decompresser = new Inflater();
		decompresser.reset();
		InflaterInputStream iis = new InflaterInputStream(input, decompresser);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		int i = 1024;
		byte[] buf = new byte[i];
		try {
			while ((i = iis.read(buf, 0, i)) > 0) {
				output.write(buf, 0, i);
			}
			return output.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException("fail to decompress by stream", e);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					logger.warn("success to decompress by stream, but fail to close output stream.", e);
				}
			}
		}
	}

	public static void main(String[] args) {
		String data = "abcefg";
		byte[] zipData = ZipUtils.compress(data.getBytes());
		byte[] unzipData = ZipUtils.decompress(zipData);
		byte[] unzipStream = ZipUtils.decompress(new ByteArrayInputStream(zipData));
		System.out.println(String.format("data: %s\nzipData: %s\nunzipData: %s\nunzipStream: %s\n", data, new String(zipData), new String(unzipData), new String(unzipStream)));

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipUtils.compress(data.getBytes(), bos);
		System.out.print(String.format("data: %s\nzip data by stream: %s", data, new String(bos.toByteArray())));
	}

}
