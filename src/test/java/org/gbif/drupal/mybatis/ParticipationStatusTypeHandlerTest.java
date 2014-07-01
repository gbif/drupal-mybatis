package org.gbif.drupal.mybatis;

import org.gbif.api.vocabulary.ParticipationStatus;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(Parameterized.class)
public class ParticipationStatusTypeHandlerTest {

  private static ParticipationStatusTypeHandler h = new ParticipationStatusTypeHandler();
  private String value;
  private ParticipationStatus expected;

  public ParticipationStatusTypeHandlerTest(String value, ParticipationStatus expected) {
    this.value = value;
    this.expected = expected;
  }

  @Parameterized.Parameters
  public static Iterable<Object[]> data() {
    return ImmutableList.<Object[]>of(
      new Object[] {"associate",ParticipationStatus.ASSOCIATE},
      new Object[] {"associate country participant",ParticipationStatus.ASSOCIATE},
      new Object[] {"Associate Country Participant",ParticipationStatus.ASSOCIATE},
      new Object[] {"voting participant",ParticipationStatus.VOTING},
      new Object[] {"VOTING PARTICIPANT",ParticipationStatus.VOTING},
      new Object[] {" voting participant ",ParticipationStatus.VOTING},
      new Object[] {"votting",null},
      new Object[] {null,null},
      new Object[] {"",null},
      new Object[] {" ",null}
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
