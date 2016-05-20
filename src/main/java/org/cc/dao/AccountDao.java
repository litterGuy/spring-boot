package org.cc.dao;

import java.util.List;

import org.cc.entity.Account;

public interface AccountDao {
	
	List<Account> queryAll();
}
