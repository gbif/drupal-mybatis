/*
 * Copyright 2013 Global Biodiversity Information Facility (GBIF)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gbif.drupal.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class ParticipantSinceTypeHandler implements TypeHandler<Integer> {
  private static Pattern YEAR = Pattern.compile("[0-9]{4}");

  @Override
  public void setParameter(PreparedStatement ps, int i, Integer parameter, JdbcType jdbcType) throws SQLException {
    ps.setObject(i, parameter == null ? null : parameter, Types.CHAR);
  }

  @Override
  public Integer getResult(ResultSet rs, String columnName) throws SQLException {
    return lookup(rs.getString(columnName));
  }

  @Override
  public Integer getResult(ResultSet rs, int columnIndex) throws SQLException {
    return lookup(rs.getString(columnIndex));
  }

  @Override
  public Integer getResult(CallableStatement cs, int columnIndex) throws SQLException {
    return lookup(cs.getString(columnIndex));
  }

  /**
   * Extract only the year as an integer from string based values such as March 2001.
   */
  @VisibleForTesting
  protected Integer lookup(String val) {
    if (!Strings.isNullOrEmpty(val)) {
      Matcher m = YEAR.matcher(val);
      if (m.find()) {
        return Integer.parseInt(m.group());
      }
    }
    return null;
  }
}
