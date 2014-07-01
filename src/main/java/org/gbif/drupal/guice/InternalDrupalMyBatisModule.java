package org.gbif.drupal.guice;

import org.gbif.api.model.common.User;
import org.gbif.api.model.registry.Contact;
import org.gbif.api.model.registry.Node;
import org.gbif.api.service.common.UserService;
import org.gbif.api.vocabulary.ContactType;
import org.gbif.api.vocabulary.Country;
import org.gbif.api.vocabulary.Language;
import org.gbif.api.vocabulary.ParticipationStatus;
import org.gbif.api.vocabulary.UserRole;
import org.gbif.drupal.mybatis.ContactTypeHandler;
import org.gbif.drupal.mybatis.DrupalTimestampTypeHandler;
import org.gbif.drupal.mybatis.ImsNodeMapper;
import org.gbif.drupal.mybatis.ParticipationStatusTypeHandler;
import org.gbif.drupal.mybatis.UserMapper;
import org.gbif.drupal.mybatis.UserRoleTypeHandler;
import org.gbif.drupal.mybatis.UserServiceImpl;
import org.gbif.mybatis.guice.MyBatisModule;
import org.gbif.mybatis.type.CountryTypeHandler;
import org.gbif.mybatis.type.LanguageTypeHandler;
import org.gbif.mybatis.type.UriTypeHandler;
import org.gbif.mybatis.type.UuidTypeHandler;

import java.net.URI;
import java.util.Date;
import java.util.UUID;

import com.google.inject.Scopes;

/**
 * This Module should not be used, use the {@link DrupalMyBatisModule} instead.
 */
public class InternalDrupalMyBatisModule extends MyBatisModule {

  public static final String DATASOURCE_BINDING_NAME = "drupal";

  public InternalDrupalMyBatisModule() {
    super(DATASOURCE_BINDING_NAME);
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
    // IMS
    addAlias("Node").to(Node.class);
    addAlias("Contact").to(Contact.class);
    addAlias("Country").to(Country.class);
    addAlias("Language").to(Language.class);

    // mybatis mapper
    addMapperClass(UserMapper.class);
    addMapperClass(ImsNodeMapper.class);
  }

  @Override
  protected void bindTypeHandlers() {
    // type handler
    handleType(UserRole.class).with(UserRoleTypeHandler.class);
    handleType(UUID.class).with(UuidTypeHandler.class);
    handleType(Date.class).with(DrupalTimestampTypeHandler.class);
    handleType(URI.class).with(UriTypeHandler.class);
    handleType(Country.class).with(CountryTypeHandler.class);
    handleType(Language.class).with(LanguageTypeHandler.class);
    handleType(ContactType.class).with(ContactTypeHandler.class);
    handleType(ParticipationStatus.class).with(ParticipationStatusTypeHandler.class);
  }
}
