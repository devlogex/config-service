package com.tnd.pw.config.user.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.dbservice.DataHelper;
import com.tnd.pw.config.user.dao.UserProfileDao;
import com.tnd.pw.config.user.entity.UserProfileEntity;
import com.tnd.pw.config.user.exception.UserProfileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class UserProfileDaoImpl implements UserProfileDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO user_profile(id, email, password, role, avatar, first_name, last_name, " +
                    "company_name, domain, state, created_at, created_by) " +
                    "values(%d, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', %d, %d, %d)";
    private static final String SQL_UPDATE =
            "UPDATE user_profile SET password = '%s', role = '%s', avatar = '%s', first_name = '%s', " +
                    "last_name = '%s', company_name = '%s', domain = '%s', state = %d, " +
                    "updated_at = %d, updated_by = %d WHERE id = %d";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM user_profile WHERE id = %d";
    private static final String SQL_SELECT_BY_EMAIL =
            "SELECT * FROM user_profile WHERE email = '%s'";
    private static final String SQL_SELECT_BY_LIST_ID =
            "SELECT * FROM user_profile WHERE id IN (%s)";

    @Override
    public void create(UserProfileEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getEmail(), entity.getPassword(),
                entity.getRole(), entity.getAvatar(), entity.getFirstName(), entity.getLastName(),entity.getCompanyName(),
                entity.getDomain(), entity.getState(), entity.getCreatedAt(), entity.getCreatedBy());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<UserProfileEntity> get(UserProfileEntity entity) throws IOException, DBServiceException, UserProfileNotFoundException {
        String query;
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else {
            query = String.format(SQL_SELECT_BY_EMAIL, entity.getEmail());
        }
        List<UserProfileEntity> entities = dataHelper.querySQL(query, UserProfileEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new UserProfileNotFoundException();
        }
        return entities;
    }

    @Override
    public List<UserProfileEntity> get(List<Long> ids) throws IOException, DBServiceException, UserProfileNotFoundException {
        String listId = "";
        for(Long id: ids) {
            listId += String.valueOf(id) + ",";
        }
        listId = listId.substring(0,listId.length()-1);
        String query = String.format(SQL_SELECT_BY_LIST_ID, listId);
        List<UserProfileEntity> entities = dataHelper.querySQL(query, UserProfileEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new UserProfileNotFoundException();
        }
        return entities;
    }

    @Override
    public void update(UserProfileEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getPassword(), entity.getRole(), entity.getAvatar(),
                entity.getFirstName(), entity.getLastName(), entity.getCompanyName(),
                entity.getDomain(), entity.getState(), entity.getUpdatedAt(), entity.getUpdatedBy(),
                entity.getId());
        dataHelper.executeSQL(query);
    }
}
