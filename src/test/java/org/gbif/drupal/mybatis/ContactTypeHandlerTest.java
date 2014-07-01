package org.gbif.drupal.mybatis;

import org.gbif.api.vocabulary.ContactType;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(Parameterized.class)
public class ContactTypeHandlerTest {

  private static ContactTypeHandler h = new ContactTypeHandler();
  private String value;
  private ContactType expected;

  public ContactTypeHandlerTest(String value, ContactType expected) {
    this.value = value;
    this.expected = expected;
  }

  @Parameterized.Parameters
  public static Iterable<Object[]> data() {
    return ImmutableList.<Object[]>of(
      new Object[] {"Head of  Delegation",ContactType.HEAD_OF_DELEGATION},
      new Object[] {"head",null},
      new Object[] {"node_manager",ContactType.NODE_MANAGER},
      new Object[] {"node staff",ContactType.NODE_STAFF},
      new Object[] {"nodes staff",ContactType.NODE_STAFF},
      new Object[] {"nodestaff",ContactType.NODE_STAFF},
      new Object[] {"REGIONAL NODE REPRESENTATIVE",ContactType.REGIONAL_NODE_REPRESENTATIVE},
      new Object[] {"additional_delegate",ContactType.ADDITIONAL_DELEGATE},
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
