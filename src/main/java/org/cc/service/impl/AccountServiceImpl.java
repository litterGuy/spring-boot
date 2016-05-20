package org.cc.service.impl;

import java.util.List;

import org.cc.dao.AccountDao;
import org.cc.entity.Account;
import org.cc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AccountDao dao;
	
	@Cacheable(value = "accountcache",keyGenerator = "keyGenerator")
	public List<Account> getList() {
		return dao.queryAll();
	}

}
