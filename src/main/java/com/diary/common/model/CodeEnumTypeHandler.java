package com.diary.common.model;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

public abstract class CodeEnumTypeHandler<E extends Enum<E>> implements TypeHandler<CodeEnum>  {
	
	private Class<E> type;

    public CodeEnumTypeHandler(Class<E> type) {
        this.type = type;
    }

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, CodeEnum codeEnum, JdbcType jdbcType) throws
            SQLException {
        preparedStatement.setString(i, codeEnum.getCode());
    }

    @Override
    public CodeEnum getResult(ResultSet resultSet, String s) throws SQLException {
        return getCodeEnum(resultSet.getString(s));
    }

    @Override
    public CodeEnum getResult(ResultSet resultSet, int i) throws SQLException {
        return getCodeEnum(resultSet.getString(i));
    }

    @Override
    public CodeEnum getResult(CallableStatement callableStatement, int i) throws SQLException {
        return getCodeEnum(callableStatement.getString(i));
    }

    private CodeEnum getCodeEnum(String code) {
        try {
            CodeEnum[] enumConstants = (CodeEnum[])type.getEnumConstants();
            return Arrays.stream(enumConstants)
                    .filter(codeEnum -> codeEnum.getCode().equals(code))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            throw new TypeException(new StringBuilder("Can't make enum object '")
                            .append(type)
                            .append("'\n")
                            .append(e)
                            .toString());
        }
    }

}
