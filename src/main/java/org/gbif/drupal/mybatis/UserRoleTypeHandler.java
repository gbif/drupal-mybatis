package org.gbif.drupal.mybatis;

import org.gbif.api.vocabulary.UserRole;
import org.gbif.mybatis.type.BaseEnumTypeHandler;

public class UserRoleTypeHandler extends BaseEnumTypeHandler<Long, UserRole> {

  public UserRoleTypeHandler() {
    super(new UserRoleTypeConverter());
  }
}
