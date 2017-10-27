package common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption
{
	private Encryption() {}
	
	public static String getEncSHA256(String inputStr)
	{
		StringBuffer outputStr = new StringBuffer();
		try
		{
			MessageDigest encrypt;
			
			encrypt = MessageDigest.getInstance("SHA-256");
			encrypt.update(inputStr.getBytes());

			byte bStr[] = encrypt.digest();
			for (int i = 0; i < bStr.length; i++)
			{
				outputStr.append(Integer.toString((bStr[i] & 0xff) + 0x100, 16).substring(1));
			}
		}
		catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
		return outputStr.toString();
	}
}