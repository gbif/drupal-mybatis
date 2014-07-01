package org.gbif.drupal.util;

import java.util.Collection;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(value = Parameterized.class)
public class DrupalEncoderTest {

  private DrupalEncoder encoder = new DrupalEncoder();
  private String password;
  private String hash;

  public DrupalEncoderTest(String password, String hash) {
    this.password = password;
    this.hash = hash;
  }

  @Parameterized.Parameters
	 public static Collection<Object[]> testData() {
    return Lists.newArrayList(
      new Object[]{"adrian", "$S$DNbBTrkalsPChLsqajHUQS18pBBxzSTQW0310SzivTy7HDQ.zgyG"},
      new Object[]{"test", "$S$DxVn7wubSRzoK9X2pkGx4njeDRkLEgdqPphc2ZXkkb8Viy8JEGf3"},
      new Object[]{"markus", "$S$Dy6/BMI3AoImMSlZGHhEkXgKbUenX7yS2SNgmj7NsrA9JPqH01kW"},
      new Object[]{"carla", "$S$DN6evrfX6JzGw./kDKHhON8VEUp7tDbF0gyZYhX1Uw4Y3udFN47w"},
      new Object[]{"pia", "$S$DnuHhGOmUVLBYF.kXnE9ZD6ffqnFz7GPxWaikVFLh1JAymnJkdB3"}
    );
	 }

  @Test
  public void testEncoding() throws Exception {
    String encoded = encoder.encode(password, hash);
    assertEquals(hash, encoded);
  }
}
