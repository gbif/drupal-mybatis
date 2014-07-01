package org.gbif.drupal.mybatis;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(Parameterized.class)
public class ParticipantSinceTypeHandlerTest {

  private static ParticipantSinceTypeHandler h = new ParticipantSinceTypeHandler();
  private String value;
  private Integer expected;

  public ParticipantSinceTypeHandlerTest(String value, Integer expected) {
    this.value = value;
    this.expected = expected;
  }

  @Parameterized.Parameters
  public static Iterable<Object[]> data() {
    return ImmutableList.<Object[]>of(
      new Object[] {"March 2001", 2001},
      new Object[] {"2012", 2012},
      new Object[] {" 2 012", null},
      new Object[] {"21-04-1999", 1999},
      new Object[] {"1989, 1st May", 1989},
      new Object[] {"04/21/1999", 1999},
      new Object[] {"30189", 3018},
      new Object[] {null, null},
      new Object[] {"", null},
      new Object[] {" ", null}
    );
  }


  @Test
  public void testLookup() {
    if (expected == null) {
      assertNull(h.lookup(value));
    } else {
      assertEquals(expected, h.lookup(value));
    }
  }
}
