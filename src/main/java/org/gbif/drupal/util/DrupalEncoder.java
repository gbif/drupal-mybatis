package org.gbif.drupal.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Java version of password encoding as done natively by Drupal 7.
 * Drupal stores encoding settings inside the first 12 characters of a password hash, so to encode a
 * password an existing drupal hash is needed.
 * Mostly copied from http://stackoverflow.com/questions/11736555/java-autentication-of-drupal-passwords
 */
public class DrupalEncoder {
  private static final Logger LOG = LoggerFactory.getLogger(DrupalEncoder.class);
  private static final String ALGORITHM = "SHA-512";

  private final int DRUPAL_HASH_LENGTH;

  public DrupalEncoder() {
    DRUPAL_HASH_LENGTH = 55;
  }

  public DrupalEncoder(int hashLength) {
    DRUPAL_HASH_LENGTH = hashLength;
  }

  private static String _password_itoa64() {
    return "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  }

  private static int password_get_count_log2(String setting) {
    return _password_itoa64().indexOf(setting.charAt(3));
  }


  private static byte[] sha512(String input) {
    try {
      return MessageDigest.getInstance(ALGORITHM).digest(input.getBytes());
    } catch (NoSuchAlgorithmException e) {
      LOG.error("Missing required message digest algorithm {}", ALGORITHM, e);
    }
    return new byte[0];
  }

  private static byte[] sha512(byte[] input) {
    try {
      return MessageDigest.getInstance(ALGORITHM).digest(input);
    } catch (NoSuchAlgorithmException e) {
      LOG.error("Missing required message digest algorithm {}", ALGORITHM, e);
    }
    return new byte[0];
  }

  /**
   * @param drupalHash the existing drupal hash storing individual hashing settings in its first 12 chars
   *
   * @return the encoded password using the existing hash settings
   */
  public String encode(final String password, String drupalHash) {
    // The first 12 characters of an existing hash are its setting string.
    drupalHash = drupalHash.substring(0, 12);
    int count_log2 = password_get_count_log2(drupalHash);
    String salt = drupalHash.substring(4, 12);
    // Hashes must have an 8 character salt.
    if (salt.length() != 8) {
      return null;
    }

    int count = 1 << count_log2;


    byte[] hash;
    try {
      hash = sha512(salt.concat(password));

      do {
        hash = sha512(joinBytes(hash, password.getBytes("UTF-8")));
      } while (--count > 0);
    } catch (Exception e) {
      LOG.error("Drupal password encoding exception", e);
      return null;
    }

    String output = drupalHash + _password_base64_encode(hash, hash.length);
    return (output.length() > 0) ? output.substring(0, DRUPAL_HASH_LENGTH) : null;
  }

  private static byte[] joinBytes(byte[] a, byte[] b) {
    byte[] combined = new byte[a.length + b.length];

    System.arraycopy(a, 0, combined, 0, a.length);
    System.arraycopy(b, 0, combined, a.length, b.length);
    return combined;
  }


  private static String _password_base64_encode(byte[] input, int count) {

    StringBuffer output = new StringBuffer();
    int i = 0;
    CharSequence itoa64 = _password_itoa64();
    do {
      long value = SignedByteToUnsignedLong(input[i++]);

      output.append(itoa64.charAt((int) value & 0x3f));
      if (i < count) {
        value |= SignedByteToUnsignedLong(input[i]) << 8;
      }
      output.append(itoa64.charAt((int) (value >> 6) & 0x3f));
      if (i++ >= count) {
        break;
      }
      if (i < count) {
        value |= SignedByteToUnsignedLong(input[i]) << 16;
      }

      output.append(itoa64.charAt((int) (value >> 12) & 0x3f));
      if (i++ >= count) {
        break;
      }
      output.append(itoa64.charAt((int) (value >> 18) & 0x3f));
    } while (i < count);

    return output.toString();
  }


  public static long SignedByteToUnsignedLong(byte b) {
    return b & 0xFF;
  }

}
