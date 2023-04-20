package com.bsol.iri.fileSharing.clamAv.impl;

/**
 * 
 * @author rupesh
 *	THis class is used to scan the file by calling the ClamAv scan engine. From here we will get to know from scan engine serve , the file contains virus or not
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bsol.iri.fileSharing.clamAv.dto.FileScanResponseDto;
import com.bsol.iri.fileSharing.clamAv.exception.ClamAVSizeLimitException;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class ClamAV {

	private static Logger log = LoggerFactory.getLogger(ClamAV.class);

	private String hostName;
	private int port;
	private int timeout;

	// "do not exceed StreamMaxLength as defined in clamd.conf"
	private static final int CHUNK_SIZE = 2048;
	private static final int DEFAULT_TIMEOUT = 20000;
	private static final int PONG_REPLY_LEN = 4;

	public ClamAV(String hostName, int port, int timeout) {
		if (timeout < 0) {
			throw new IllegalArgumentException("Negative timeout value does not make sense.");
		}
		this.hostName = hostName;
		this.port = port;
		this.timeout = timeout;
	}

	public ClamAV(String hostName, int port) {
		this(hostName, port, DEFAULT_TIMEOUT);
	}

	public boolean ping() throws IOException {
		Socket socket = null;
		OutputStream outStream = null;
		InputStream inStream = null;
		try {
			socket = new Socket(hostName, port);
			outStream = socket.getOutputStream();
			socket.setSoTimeout(timeout);
			outStream.write("zPING\0".getBytes(StandardCharsets.US_ASCII));
			outStream.flush();
			byte[] b = new byte[PONG_REPLY_LEN];
			inStream = socket.getInputStream();
			int copyIndex = 0;
			int readResult;
			do {
				readResult = inStream.read(b, copyIndex, Math.max(b.length - copyIndex, 0));
				copyIndex += readResult;
			} while (readResult > 0);
			return Arrays.equals(b, "PONG".getBytes(StandardCharsets.US_ASCII));
		} finally {
			closeInputStream(inStream);
			closeOutputStream(outStream);
			closeSocket(socket);
		}
	}

	private void closeInputStream(InputStream inStream) {
		try {
			if (inStream != null)
				inStream.close();
		} catch (IOException e) {
			log.error("Exception occurred while closing input streams = {} ", e.getMessage());
		}
	}

	private void closeOutputStream(OutputStream outStream) {
		try {
			if (outStream != null)
				outStream.close();
		} catch (IOException e) {
			log.error("Exception occurred while closing output streams = {} ", e.getMessage());
		}
	}

	public byte[] scan(InputStream is, FileScanResponseDto fileScanResponseDto)
			throws IOException, NoSuchAlgorithmException {
		log.info("Inside clamAv Scan.. Scanning the file");
		MessageDigest md5Digest = MessageDigest.getInstance("MD5");
		Socket socket = null;
		OutputStream outStream = null;
		InputStream inStream = null;
		try {
			socket = new Socket(hostName, port);
			outStream = new BufferedOutputStream(socket.getOutputStream());
			log.info("1Socket information = {} connected = {} ", socket, socket.isConnected());
			socket.setSoTimeout(timeout);

			// handshake
			outStream.write("zINSTREAM\0".getBytes(StandardCharsets.US_ASCII));
			outStream.flush();
			log.info("2Socket information = {} connected = {} ", socket, socket.isConnected());
			byte[] chunk = new byte[CHUNK_SIZE];

			try {
				inStream = socket.getInputStream();
				// send data
				int read = is.read(chunk);
				while (read >= 0) {
					byte[] chunkSize = ByteBuffer.allocate(4).putInt(read).array();
					outStream.write(chunkSize);
					outStream.write(chunk, 0, read);
					md5Digest.update(chunk, 0, read);
					if (inStream.available() > 0) {
						// reply from server before scan command has been terminated.
						byte[] reply = assertSizeLimit(readAll(inStream));
						throw new IOException(
								"Scan aborted. Reply from server: " + new String(reply, StandardCharsets.US_ASCII));
					}
					read = is.read(chunk);
				}

				// terminate scan
				outStream.write(new byte[] { 0, 0, 0, 0 });
				outStream.flush();

				// read reply
				return assertSizeLimit(readAll(inStream));
			} finally {

			}
		} finally {
			closeInputStream(inStream);
			closeOutputStream(outStream);
			closeSocket(socket);
		}
	}

	private void closeSocket(Socket socket) {
		try {
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			log.error("Exception occurred while closing socket = {} ", e.getMessage());
		}
	}

	public static boolean isCleanReply(byte[] reply) {
		String result = new String(reply, StandardCharsets.US_ASCII);
		log.info("Clam AV Response = {} ", result);
		return (result.contains("OK") && !result.contains("FOUND"));
	}

	private byte[] assertSizeLimit(byte[] reply) {
		String r = new String(reply, StandardCharsets.US_ASCII);
		if (r.startsWith("INSTREAM size limit exceeded."))
			throw new ClamAVSizeLimitException("Clamd size limit exceeded. Full reply from server: " + r);
		return reply;
	}

	// reads all available bytes from the stream
	private static byte[] readAll(InputStream is) throws IOException {
		ByteArrayOutputStream tmp = new ByteArrayOutputStream();

		byte[] buf = new byte[2000];
		int read = 0;
		do {
			read = is.read(buf);
			tmp.write(buf, 0, read);
		} while ((read > 0) && (is.available() > 0));
		return tmp.toByteArray();
	}
}
