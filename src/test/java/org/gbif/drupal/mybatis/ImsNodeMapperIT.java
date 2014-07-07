package org.gbif.drupal.mybatis;

import org.gbif.api.model.registry.Node;
import org.gbif.drupal.guice.DrupalMyBatisModule;
import org.gbif.utils.file.properties.PropertiesUtil;

import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
@Ignore("A manual test for the Drupal db with IMS tables only")
public class ImsNodeMapperIT {

  private ImsNodeMapper mapper;

  @Before
  public void testSetup() throws Exception {
    Properties props = PropertiesUtil.loadProperties("drupal.properties");
    Module mod = new DrupalMyBatisModule(props);
    Injector inj = Guice.createInjector(mod);
    mapper = inj.getInstance(ImsNodeMapper.class);
  }

  @Test
  public void testGet() throws Exception {
    // Spain
    Node n = mapper.get(1);
    System.out.print(n);
    assertEquals(3, n.getAddress().size());
    assertEquals("Madrid", n.getCity());
  }
}
