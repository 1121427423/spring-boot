package com.springboot.oauth.server.mapper;

import com.springboot.oauth.server.domain.table.OauthClientDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OauthClientDetailMapper {
    int deleteByPrimaryKey(String clientId);

    int insert(OauthClientDetail row);

    int insertSelective(OauthClientDetail row);

    OauthClientDetail selectByPrimaryKey(String clientId);

    int updateByPrimaryKeySelective(OauthClientDetail row);

    int updateByPrimaryKey(OauthClientDetail row);

}