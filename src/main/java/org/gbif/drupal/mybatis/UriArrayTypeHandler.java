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

import java.net.URI;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.google.common.collect.Lists;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reads and writes single string columns as if they were java URI lists.
 */
public class UriArrayTypeHandler extends BaseTypeHandler<List<URI>> {
  private static final Logger LOG = LoggerFactory.getLogger(UriArrayTypeHandler.class);

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, List<URI> parameter, JdbcType jdbcType) throws SQLException {
    Array array = ps.getConnection().createArrayOf("text", parameter.toArray());
    ps.setArray(i, array);
  }

  @Override
  public List<URI> getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return toList(rs.getString(columnName));
  }

  @Override
  public List<URI> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return toList(rs.getString(columnIndex));
  }

  @Override
  public List<URI> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return toList(cs.getString(columnIndex));
  }

  private List<URI> toList(String str) throws SQLException {
    if (str == null) return Lists.newArrayList();

    List<URI> uris = Lists.newArrayList();
    try {
      uris.add(URI.create(str));
    } catch (Exception e) {
      LOG.error("Failed to convert pg array value {} to URI", str);
    }

    return uris;
  }
}