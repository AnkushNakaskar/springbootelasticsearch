package com.elasticsearch.demo.repo;

import com.elasticsearch.demo.vo.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ankushnakaskar
 */
@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {
}