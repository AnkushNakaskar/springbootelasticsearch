package com.elasticsearch.demo;

import com.elasticsearch.demo.vo.User;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * @author ankushnakaskar
 */
@Repository
public class UserService {


    @Value("${elasticsearch.index.name}")
    private String indexName;

    @Value("${elasticsearch.user}")
    private String userTypeName;

    @Autowired
    private ElasticsearchTemplate esTemplate;


    public List<User> getAllUsers() {
        SearchQuery getAllQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery()).build();
        return esTemplate.queryForList(getAllQuery, User.class);
    }

    public User getUserById(String userId) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.matchQuery("userId", userId)).build();
        List<User> users = esTemplate.queryForList(searchQuery, User.class);
        if(!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    public User addNewUser(User user) {

        IndexQuery userQuery = new IndexQuery();
        userQuery.setIndexName(indexName);
        userQuery.setType(userTypeName);
        userQuery.setObject(user);
        esTemplate.index(userQuery);

        esTemplate.refresh(indexName);

        return user;
    }

    public Object getAllUserSettings(String name) {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name", name)).build();

        List<User> users = esTemplate.queryForList(searchQuery, User.class);
        if(!users.isEmpty()) {
            System.out.println("settings: "+users.get(0).getUserSettings().toString());
            return users.get(0).getUserSettings();
        }

        return null;
    }

    public String getUserSetting(String name, String key) {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name", name)).build();
        List<User> users = esTemplate.queryForList(searchQuery, User.class);
        if(!users.isEmpty()) {
            return users.get(0).getUserSettings().get(key);
        }

        return null;
    }

    public String addUserSetting(String name, String key, String value) {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name", name)).build();
        List<User> users = esTemplate.queryForList(searchQuery, User.class);
        if(!users.isEmpty()) {

            User user = users.get(0);
            user.getUserSettings().put(key, value);

            IndexQuery userQuery = new IndexQuery();
            userQuery.setIndexName(indexName);
            userQuery.setType(userTypeName);
            userQuery.setId(user.getUserId());
            userQuery.setObject(user);
            esTemplate.index(userQuery);
            return "Setting added.";
        }

        return null;
    }
}
