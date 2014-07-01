package org.gbif.drupal.mybatis;

import org.gbif.api.service.common.UserService;
import org.gbif.drupal.guice.DrupalMyBatisModule;
import org.gbif.utils.file.properties.PropertiesUtil;

import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class UserServiceImplIT {

  private UserService service;

  @Before
  public void testSetup() throws Exception {
    Properties props = PropertiesUtil.loadProperties("drupal.properties");
    Module mod = new DrupalMyBatisModule(props);
    Injector inj = Guice.createInjector(mod);
    service = inj.getInstance(UserService.class);
  }

  @Test
  public void testGet() throws Exception {
    service.get("test");
  }
}
