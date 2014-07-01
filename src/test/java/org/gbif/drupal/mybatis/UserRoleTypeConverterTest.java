package org.gbif.drupal.mybatis;

import org.gbif.api.vocabulary.UserRole;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class UserRoleTypeConverterTest {

  UserRoleTypeConverter conv = new UserRoleTypeConverter();

  @Test
  public void testCompleteness() {
    for (UserRole t : UserRole.values()) {
      assertTrue(conv.fromEnum(t) >= 0);
    }
  }

  @Test
  public void testTo() {
    assertEquals(UserRole.ADMIN, conv.toEnum(3l));
    assertNull(conv.toEnum(null));
    assertNull(conv.toEnum(-12l));
  }
}
