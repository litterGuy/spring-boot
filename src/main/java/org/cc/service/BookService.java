package org.cc.service;

import org.cc.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 
 * @author wangwei
 * @see http://www.javabeat.net/restful-springboot-mongodb/
 * @date 2016年4月8日
 */
public interface BookService extends MongoRepository<Book, String>{

}
