//package com.bsol.iri.fileSharing.util;
//
///**
// * 
// * @author rupesh
// *	
// * This class is used for Encrypting / decrypting any string
// */
//import java.security.Key;
//import java.util.Base64;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.SecretKeySpec;
//
//public class ClientAppDataEncryptionDecryption {
//
//	private final String ALGO = "AES";
//	private final String secretKey = "rupeshSecretKeya";
//
//	private String encrypt(String Data, String secret) throws Exception {
//		Key key = generateKey(secret);
//		Cipher c = Cipher.getInstance(ALGO);
//		c.init(Cipher.ENCRYPT_MODE, key);
//		byte[] encVal = c.doFinal(Data.getBytes());
//		String encryptedValue = Base64.getEncoder().encodeToString(encVal);
//		return encryptedValue;
//	}
//
//	private String decrypt(String strToDecrypt, String secret) {
//		try {
//			Key key = generateKey(secret);
//			Cipher cipher = Cipher.getInstance(ALGO);
//			cipher.init(Cipher.DECRYPT_MODE, key);
//			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
//		} catch (Exception e) {
//			System.out.println("Error while decrypting:" + e.toString());
//		}
//		return null;
//	}
//
//	private Key generateKey(String secret) throws Exception {
//		byte[] decoded = Base64.getDecoder().decode(secret.getBytes());
//		Key key = new SecretKeySpec(decoded, ALGO);
//		return key;
//	}
//
//	/*
//	 * private String decodeKey(String str) { byte[] decoded =
//	 * Base64.getDecoder().decode(str.getBytes()); return new String(decoded); }
//	 */
//
//	private String encodeKey(String str) {
//		byte[] encoded = Base64.getEncoder().encode(str.getBytes());
//		return new String(encoded);
//	}
//
//	public String encrypt(String rawString) throws Exception {
//		return encrypt(rawString, encodeKey(secretKey));
//	}
//
//	public String decrypt(String encryptedString) throws Exception {
//		return decrypt(encryptedString, encodeKey(secretKey));
//	}
//}
