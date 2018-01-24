package jni;

public class FlkWebCrypt {

	// http type;
	private static final int TYPE_HTTP = 0;
	private static final int TYPE_HTTPS = 1;

	// coder type
	private static final int TYPE_CODER_BINARY = 0;
	private static final int TYPE_CODER_HEX = 1;
	private static final int TYPE_CODER_BASE64 = 2;

	static {
		try {
			System.loadLibrary("webCryptJni");
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Cannot load jni_FlkWebCrypt library:\n " + e.toString());
		}
	}

	public native int FLK_InitwebCrype();

	public native int FLK_SetKmsAddr(String addr, int port, int httptype);

	public native int FLK_GetKmsKey(String appid, String memberid, String token);

	public native int FLK_Sm4CbcEncrypt(byte[] plain, int plainLen, byte[] cipher, int[] cipherLen, int type);

	public native int FLK_Sm4CbcDecrypt(byte[] cipher, int cipherLen, byte[] plain, int[] plainLen, int type);

	private native int FLK_Sm4CbcEncrypt_Salt(byte[] plain, int plainLen, byte[] cipher, int[] cipherLen);

	private native int FLK_Sm4CbcDecrypt_Salt(byte[] cipher, int cipherLen, byte[] plain, int[] plainLen);

	private native int FLK_Sm4CbcEncrypt_External(byte[] key, byte[] iv, byte[] plain, int plainLen, byte[] cipher,
			int[] cipherLen, int type);

	private native int FLK_Sm4CbcDecrypt_External(byte[] key, byte[] iv, byte[] cipher, int cipherLen, byte[] plain,
			int[] plainLen, int type);

}
