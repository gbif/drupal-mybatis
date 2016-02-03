package org.gbif.drupal.guice;

import org.gbif.api.model.common.User;
import org.gbif.api.service.common.UserService;

import org.gbif.api.vocabulary.UserRole;
import org.gbif.drupal.mybatis.DrupalTimestampTypeHandler;
import org.gbif.drupal.mybatis.UserMapper;
import org.gbif.drupal.mybatis.UserRoleTypeHandler;
import org.gbif.drupal.mybatis.UserServiceImpl;
import org.gbif.mybatis.guice.MyBatisModule;

import java.util.Date;
import java.util.Properties;

import com.google.inject.Scopes;

/**
 * This Module should not be used, use the {@link DrupalMyBatisModule} instead.
 */
public class InternalDrupalMyBatisModule extends MyBatisModule {

  public static final String DATASOURCE_BINDING_NAME = "drupal";

  public InternalDrupalMyBatisModule(Properties props) {
    super(DATASOURCE_BINDING_NAME, props);
  }

  @Override
  protected void bindManagers() {
    // services. Make sure they are also exposed in the public module!
    bind(UserService.class).to(UserServiceImpl.class).in(Scopes.SINGLETON);
  }

  @Override
  protected void bindMappers() {
    addAlias("User").to(User.class);
    addAlias("UserRole").to(UserRole.class);
    // mybatis mapper
    addMapperClass(UserMapper.class);
  }

  @Override
  protected void bindTypeHandlers() {
    // type handler
    handleType(UserRole.class).with(UserRoleTypeHandler.class);
    handleType(Date.class).with(DrupalTimestampTypeHandler.class);
  }
}
