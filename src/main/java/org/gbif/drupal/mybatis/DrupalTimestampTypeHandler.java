package org.gbif.drupal.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class DrupalTimestampTypeHandler implements TypeHandler<Date> {

  @Override
  public void setParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
    ps.setObject(i, convert(parameter));
  }

  @Override
  public Date getResult(ResultSet rs, String columnName) throws SQLException {
    return convert((Integer) rs.getObject(columnName));
  }

  @Override
  public Date getResult(ResultSet rs, int columnIndex) throws SQLException {
    return convert((Integer) rs.getObject(columnIndex));
  }

  @Override
  public Date getResult(CallableStatement cs, int columnIndex) throws SQLException {
    return convert((Integer) cs.getObject(columnIndex));
  }

  private Date convert(Integer sec) {
    if (sec == null || sec <= 0) {
      return null;
    }
    // php timestamps use unix epoche which is in seconds - java uses milliseconds
    return new Date(1000l * sec);
  }

  private int convert(Date phpDate) {
    if (phpDate == null) {
      return 0;
    }
    // php timestamps use unix epoche which is in seconds - java uses milliseconds
    return (int) (phpDate.getTime() / 1000);
  }
}
