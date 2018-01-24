/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.core.util;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

import org.apache.commons.logging.Log;

import com.glaf.core.config.Configuration;

/**
 * An utility class for I/O related functionality.
 */
public class IOUtils {

	/**
	 * The /dev/null of OutputStreams.
	 */
	public static class NullOutputStream extends OutputStream {
		@Override
		public void write(byte[] b, int off, int len) throws IOException {
		}

		@Override
		public void write(int b) throws IOException {
		}
	}

	public static final int BUFFER_SIZE = 1024 * 8;

	/**
	 * Close the Closeable objects and <b>ignore</b> any {@link IOException} or
	 * null pointers. Must only be used for cleanup in exception handlers.
	 *
	 * @param log
	 *            the log to record problems to at debug level. Can be null.
	 * @param closeables
	 *            the objects to close
	 */
	public static void cleanup(Log log, java.io.Closeable... closeables) {
		for (java.io.Closeable c : closeables) {
			if (c != null) {
				try {
					c.close();
				} catch (IOException e) {
					if (log != null && log.isDebugEnabled()) {
						log.debug("Exception in closing " + c, e);
					}
				}
			}
		}
	}

	/**
	 * Closes the socket ignoring {@link IOException}
	 *
	 * @param sock
	 *            the Socket to close
	 */
	public static void closeSocket(Socket sock) {
		if (sock != null) {
			try {
				sock.close();
			} catch (IOException ignored) {
			}
		}
	}

	/**
	 * Closes the stream ignoring {@link IOException}. Must only be called in
	 * cleaning up from exception handlers.
	 *
	 * @param stream
	 *            the Stream to close
	 */
	public static void closeStream(java.io.Closeable stream) {
		cleanup(null, stream);
	}

	public static void closeStream(java.io.InputStream stream) {
		if (stream != null) {
			try {
				stream.close();
				stream = null;
			} catch (IOException ex) {
			}
		}
	}

	public static void closeStream(java.io.OutputStream stream) {
		if (stream != null) {
			try {
				stream.close();
				stream = null;
			} catch (IOException ex) {
			}
		}
	}

	/**
	 * Copies from one stream to another. <strong>closes the input and output
	 * streams at the end</strong>.
	 *
	 * @param in
	 *            InputStrem to read from
	 * @param out
	 *            OutputStream to write to
	 * @param conf
	 *            the Configuration object
	 */
	public static void copyBytes(InputStream in, OutputStream out, Configuration conf) throws IOException {
		copyBytes(in, out, conf.getInt("io.file.buffer.size", 4096), true);
	}

	/**
	 * Copies from one stream to another.
	 *
	 * @param in
	 *            InputStream to read from
	 * @param out
	 *            OutputStream to write to
	 * @param conf
	 *            the Configuration object
	 * @param close
	 *            whether or not close the InputStream and OutputStream at the
	 *            end. The streams are closed in the finally clause.
	 */
	public static void copyBytes(InputStream in, OutputStream out, Configuration conf, boolean close)
			throws IOException {
		copyBytes(in, out, conf.getInt("io.file.buffer.size", 4096), close);
	}

	/**
	 * Copies from one stream to another.
	 * 
	 * @param in
	 *            InputStrem to read from
	 * @param out
	 *            OutputStream to write to
	 * @param buffSize
	 *            the size of the buffer
	 */
	public static void copyBytes(InputStream in, OutputStream out, int buffSize) throws IOException {
		PrintStream ps = out instanceof PrintStream ? (PrintStream) out : null;
		byte buf[] = new byte[buffSize];
		int bytesRead = in.read(buf);
		while (bytesRead >= 0) {
			out.write(buf, 0, bytesRead);
			if ((ps != null) && ps.checkError()) {
				throw new IOException("Unable to write to output stream.");
			}
			bytesRead = in.read(buf);
		}
	}

	/**
	 * Copies from one stream to another.
	 *
	 * @param in
	 *            InputStrem to read from
	 * @param out
	 *            OutputStream to write to
	 * @param buffSize
	 *            the size of the buffer
	 * @param close
	 *            whether or not close the InputStream and OutputStream at the
	 *            end. The streams are closed in the finally clause.
	 */
	public static void copyBytes(InputStream in, OutputStream out, int buffSize, boolean close) throws IOException {
		try {
			copyBytes(in, out, buffSize);
			if (close) {
				out.close();
				out = null;
				in.close();
				in = null;
			}
		} finally {
			if (close) {
				closeStream(out);
				closeStream(in);
			}
		}
	}

	/**
	 * Copies count bytes from one stream to another.
	 *
	 * @param in
	 *            InputStream to read from
	 * @param out
	 *            OutputStream to write to
	 * @param count
	 *            number of bytes to copy
	 * @param close
	 *            whether to close the streams
	 * @throws IOException
	 *             if bytes can not be read or written
	 */
	public static void copyBytes(InputStream in, OutputStream out, long count, boolean close) throws IOException {
		byte buf[] = new byte[4096];
		long bytesRemaining = count;
		int bytesRead;

		try {
			while (bytesRemaining > 0) {
				int bytesToRead = (int) (bytesRemaining < buf.length ? bytesRemaining : buf.length);

				bytesRead = in.read(buf, 0, bytesToRead);
				if (bytesRead == -1)
					break;

				out.write(buf, 0, bytesRead);
				bytesRemaining -= bytesRead;
			}
			if (close) {
				out.close();
				out = null;
				in.close();
				in = null;
			}
		} finally {
			if (close) {
				closeStream(out);
				closeStream(in);
			}
		}
	}

	/**
	 * read string.
	 * 
	 * @param reader
	 *            Reader instance.
	 * @return String.
	 * @throws IOException
	 */
	public static String read(Reader reader) throws IOException {
		StringWriter writer = new StringWriter();
		try {
			write(reader, writer);
			return writer.getBuffer().toString();
		} finally {
			writer.close();
		}
	}

	/**
	 * Reads len bytes in a loop using the channel of the stream
	 * 
	 * @param fileChannel
	 *            a FileChannel to read len bytes into buf
	 * @param buf
	 *            The buffer to fill
	 * @param off
	 *            offset from the buffer
	 * @param len
	 *            the length of bytes to read
	 * @throws IOException
	 *             if it could not read requested number of bytes for any reason
	 *             (including EOF)
	 */
	public static void readFileChannelFully(FileChannel fileChannel, byte buf[], int off, int len) throws IOException {
		int toRead = len;
		ByteBuffer byteBuffer = ByteBuffer.wrap(buf, off, len);
		while (toRead > 0) {
			int ret = fileChannel.read(byteBuffer);
			if (ret < 0) {
				throw new IOException("Premeture EOF from inputStream");
			}
			toRead -= ret;
			off += ret;
		}
	}

	/**
	 * Reads len bytes in a loop.
	 *
	 * @param in
	 *            InputStream to read from
	 * @param buf
	 *            The buffer to fill
	 * @param off
	 *            offset from the buffer
	 * @param len
	 *            the length of bytes to read
	 * @throws IOException
	 *             if it could not read requested number of bytes for any reason
	 *             (including EOF)
	 */
	public static void readFully(InputStream in, byte buf[], int off, int len) throws IOException {
		int toRead = len;
		while (toRead > 0) {
			int ret = in.read(buf, off, toRead);
			if (ret < 0) {
				throw new IOException("Premature EOF from inputStream");
			}
			toRead -= ret;
			off += ret;
		}
	}

	/**
	 * Similar to readFully(). Skips bytes in a loop.
	 * 
	 * @param in
	 *            The InputStream to skip bytes from
	 * @param len
	 *            number of bytes to skip.
	 * @throws IOException
	 *             if it could not skip requested number of bytes for any reason
	 *             (including EOF)
	 */
	public static void skipFully(InputStream in, long len) throws IOException {
		long amt = len;
		while (amt > 0) {
			long ret = in.skip(amt);
			if (ret == 0) {
				// skip may return 0 even if we're not at EOF. Luckily, we can
				// use the read() method to figure out if we're at the end.
				int b = in.read();
				if (b == -1) {
					throw new EOFException(
							"Premature EOF from inputStream after " + "skipping " + (len - amt) + " byte(s).");
				}
				ret = 1;
			}
			amt -= ret;
		}
	}

	/**
	 * Utility wrapper for reading from {@link InputStream}. It catches any
	 * errors thrown by the underlying stream (either IO or
	 * decompression-related), and re-throws as an IOException.
	 * 
	 * @param is
	 *            - InputStream to be read from
	 * @param buf
	 *            - buffer the data is read into
	 * @param off
	 *            - offset within buf
	 * @param len
	 *            - amount of data to be read
	 * @return number of bytes read
	 */
	public static int wrappedReadForCompressedData(InputStream is, byte[] buf, int off, int len) throws IOException {
		try {
			return is.read(buf, off, len);
		} catch (IOException ie) {
			throw ie;
		} catch (Throwable t) {
			throw new IOException("Error while reading compressed data", t);
		}
	}

	/**
	 * write.
	 * 
	 * @param is
	 *            InputStream instance.
	 * @param os
	 *            OutputStream instance.
	 * @return count.
	 * @throws IOException.
	 */
	public static long write(InputStream is, OutputStream os) throws IOException {
		return write(is, os, BUFFER_SIZE);
	}

	/**
	 * write.
	 * 
	 * @param is
	 *            InputStream instance.
	 * @param os
	 *            OutputStream instance.
	 * @param bufferSize
	 *            buffer size.
	 * @return count.
	 * @throws IOException.
	 */
	public static long write(InputStream is, OutputStream os, int bufferSize) throws IOException {
		int read;
		long total = 0;
		byte[] buff = new byte[bufferSize];
		while (is.available() > 0) {
			read = is.read(buff, 0, buff.length);
			if (read > 0) {
				os.write(buff, 0, read);
				total += read;
			}
		}
		return total;
	}

	/**
	 * write.
	 * 
	 * @param reader
	 *            Reader.
	 * @param writer
	 *            Writer.
	 * @return count.
	 * @throws IOException
	 */
	public static long write(Reader reader, Writer writer) throws IOException {
		return write(reader, writer, BUFFER_SIZE);
	}

	/**
	 * write.
	 * 
	 * @param reader
	 *            Reader.
	 * @param writer
	 *            Writer.
	 * @param bufferSize
	 *            buffer size.
	 * @return count.
	 * @throws IOException
	 */
	public static long write(Reader reader, Writer writer, int bufferSize) throws IOException {
		int read;
		long total = 0;
		char[] buf = new char[BUFFER_SIZE];
		while ((read = reader.read(buf)) != -1) {
			writer.write(buf, 0, read);
			total += read;
		}
		return total;
	}

	/**
	 * write string.
	 * 
	 * @param writer
	 *            Writer instance.
	 * @param string
	 *            String.
	 * @throws IOException
	 */
	public static long write(Writer writer, String string) throws IOException {
		Reader reader = new StringReader(string);
		try {
			return write(reader, writer);
		} finally {
			reader.close();
		}
	}

	/**
	 * Write a ByteBuffer to a FileChannel at a given offset, handling short
	 * writes.
	 * 
	 * @param fc
	 *            The FileChannel to write to
	 * @param buf
	 *            The input buffer
	 * @param offset
	 *            The offset in the file to start writing at
	 * @throws IOException
	 *             On I/O error
	 */
	public static void writeFully(FileChannel fc, ByteBuffer buf, long offset) throws IOException {
		do {
			offset += fc.write(buf, offset);
		} while (buf.remaining() > 0);
	}

	/**
	 * Write a ByteBuffer to a WritableByteChannel, handling short writes.
	 * 
	 * @param bc
	 *            The WritableByteChannel to write to
	 * @param buf
	 *            The input buffer
	 * @throws IOException
	 *             On I/O error
	 */
	public static void writeFully(WritableByteChannel bc, ByteBuffer buf) throws IOException {
		do {
			bc.write(buf);
		} while (buf.remaining() > 0);
	}

	/**
	 * write lines.
	 * 
	 * @param file
	 *            file.
	 * @param lines
	 *            lines.
	 * @throws IOException
	 */
	public static void writeLines(File file, String[] lines) throws IOException {
		if (file == null)
			throw new IOException("File is null.");
		writeLines(new FileOutputStream(file), lines);
	}

	/**
	 * write lines.
	 * 
	 * @param os
	 *            output stream.
	 * @param lines
	 *            lines.
	 * @throws IOException
	 */
	public static void writeLines(OutputStream os, String[] lines) throws IOException {
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));
		try {
			for (String line : lines)
				writer.println(line);
			writer.flush();
		} finally {
			writer.close();
		}
	}
}