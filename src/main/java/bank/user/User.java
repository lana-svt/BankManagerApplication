package bank.user;

import bank.account.Account;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final long id;
    private final String login;
    private final List<Account> accountList;

    public User(long id, String login) {
        this.id = id;
        this.login = login;
        this.accountList = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(Account account) {
        accountList.add(account);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", login='" + login + "', accountList=" + accountList + "}";
    }
}
