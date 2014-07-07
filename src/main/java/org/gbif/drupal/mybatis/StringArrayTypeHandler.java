/*
 * Copyright 2013 Global Biodiversity Information Facility (GBIF)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
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
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * Reads and writes single string columns as if they were java lists.
 * New lines (\n) are taken as line delimiters in both reads and writes.
 */
public class StringArrayTypeHandler extends BaseTypeHandler<List<String>> {
  private final Joiner NEW_LINE_JOINER = Joiner.on("\n").skipNulls();
  private final Splitter NEW_LINE_SPLITTER = Splitter.on("\n").omitEmptyStrings().trimResults();

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
    ps.setString(i, NEW_LINE_JOINER.join(parameter));
  }

  @Override
  public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return toList(rs.getString(columnName));
  }

  @Override
  public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return toList(rs.getString(columnIndex));
  }

  @Override
  public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return toList(cs.getString(columnIndex));
  }

  private List<String> toList(String str) throws SQLException {
    if (Strings.isNullOrEmpty(str)) return Lists.newArrayList();
    return Lists.newArrayList(NEW_LINE_SPLITTER.split(str));
  }

}
