package com.samsung.fas.pir.login.listeners;

import com.samsung.fas.pir.login.persistence.models.entity.Account;

import javax.persistence.PostUpdate;
import java.util.Observable;

// A little test with listeners (not implemented)
public class AccountChanged extends Observable {
	@PostUpdate
	public void postUpdate(Account account) {
		System.out.println(account.getUsername());
		notifyObservers(account);
	}
}
