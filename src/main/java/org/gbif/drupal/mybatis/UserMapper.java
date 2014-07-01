package org.gbif.drupal.mybatis;

import org.gbif.api.model.common.User;

public interface UserMapper {

  /**
   * Retrieves a user by its case insensitive username.
   */
  User get(String name);

  /**
   * Retrieves a user by its current drupal session name (SID).
   */
  User getBySession(String session);
}
