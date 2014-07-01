package org.gbif.drupal.guice;

import org.gbif.api.service.common.UserService;
import org.gbif.drupal.mybatis.ImsNodeMapper;
import org.gbif.service.guice.PrivateServiceModule;

import java.util.Properties;

public class DrupalMyBatisModule extends PrivateServiceModule {

  private static final String PREFIX = "drupal.db.";

  /**
   * Uses the given properties to configure the service.
   * 
   * @param properties to use
   */
  public DrupalMyBatisModule(Properties properties) {
    super(PREFIX, properties);
  }

  @Override
  protected void configureService() {
    // bind classes
    InternalDrupalMyBatisModule mod = new InternalDrupalMyBatisModule();
    install(mod);

    // expose named datasource binding
    expose(mod.getDatasourceKey());

    // expose services
    expose(UserService.class);
    expose(ImsNodeMapper.class);
  }
}
