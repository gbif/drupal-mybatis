package org.gbif.drupal.mybatis;

import org.gbif.api.exception.ServiceUnavailableException;
import org.gbif.api.vocabulary.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRoleTypeHandlerTest {

  @Mock
  private ResultSet mockedRs;
  private UserRoleTypeHandler th = new UserRoleTypeHandler();

  @Test
  public void testGetResult() throws ServiceUnavailableException {
    try {
      when(mockedRs.getObject("rid")).thenReturn(3l);
      Assert.assertEquals(UserRole.ADMIN, th.getResult(mockedRs, "rid"));
    } catch (SQLException e) {
    }
  }

  @Test
  public void testGetNull() throws ServiceUnavailableException {
    try {
      when(mockedRs.getObject("rid")).thenReturn(null);
      Assert.assertNull(th.getResult(mockedRs, "rid"));
    } catch (SQLException e) {
    }
  }

}
