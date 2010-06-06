/**
* My Net is free software, you can redistribute it and/or modify
* it under the terms of GNU Affero General Public License
* as published by the Free Software Foundation, either version 3
* of the License, or (at your option) any later version.
*
* You should have received a copy of the the GNU Affero
* General Public License, along with My Net. If not, see
*
* http://www.gnu.org/licenses/agpl.txt
*/

package com.perevillega.mynet.action.passwordsupport;

import java.security.MessageDigest;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.util.Hex;
import org.jboss.seam.util.Base64;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;

import com.perevillega.mynet.action.settings.Settings;

@Name("passwordManager")
@BypassInterceptors
public class PasswordManager {
	private String digestAlgorithm = Settings.DIGEST_ALG;
	private String charset = Settings.CHARSET;
	private String saltPhrase = Settings.SALT;
	public enum Encoding { base64, hex }
	private Encoding encoding = Encoding.hex;

	public String getDigestAlgorithm() {
		return this.digestAlgorithm;
	}

	public void setDigestAlgorithm(String algorithm) {
		this.digestAlgorithm = algorithm;
	}

	public String getCharset() {
		return this.charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Encoding getEncoding() {
		return this.encoding;
	}

	public void setEncoding(Encoding encoding) {
		this.encoding = encoding;
	}

	public String getSaltPhrase() {
		return this.saltPhrase;
	}

	public void setSaltPhrase(String saltPhrase) {
		this.saltPhrase = saltPhrase;
	}

	public String hash(String plainTextPassword) {
		try {
			MessageDigest digest = MessageDigest.getInstance(digestAlgorithm);
			if (saltPhrase != null) {
				digest.update(saltPhrase.getBytes(charset));
				byte[] salt = digest.digest();
				digest.reset();
				digest.update(plainTextPassword.getBytes(charset));
				digest.update(salt);
			}
			else {
				digest.update(plainTextPassword.getBytes(charset));
			}
			byte[] rawHash = digest.digest();
			if (encoding != null && encoding.equals(Encoding.base64)) {
				return Base64.encodeBytes(rawHash);
			}
			else {
				return new String(Hex.encodeHex(rawHash));
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static PasswordManager instance() {
		return (PasswordManager) Component.getInstance(PasswordManager.class, ScopeType.EVENT);
	}
}
