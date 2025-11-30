package bank.user;

import bank.account.AcccountService;
import bank.account.Account;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {
    private final AtomicLong id;
    private final List<User> users;
    private final AcccountService acccountService;
    private final Set<String> logins;

    public UserService(AcccountService acccountService) {
        this.acccountService = acccountService;
        id = new AtomicLong();
        users = new ArrayList<>();
        logins = new HashSet<>();
    }

    public User createUser(String login) {
        if (logins.contains(login)) {
            throw new IllegalArgumentException("User with login '" + login + "' already exists");
        }
        User user = new User(id.incrementAndGet(), login);
        users.add(user);
        Account account = acccountService.createAccount(user.getId());
        user.setAccountList(account);
        logins.add(login);
        return user;
    }

    public List<User> showAllUsers() {
        return users;
    }

    public Account createAccountForUser(long userId) {
        User user = findUserById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " doesn't exist"));
        Account account = acccountService.createAccount(userId);

        user.setAccountList(account);
        return account;
    }

    public void closeAccount(Account account) {
        User user = findUserById(account.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User with id " + account.getUserId() + " doesn't exist"));

        List<Account> accounts = user.getAccountList();
        if (accounts.size() < 2) {
            throw new IllegalArgumentException("Can't close last account");
        }
        accounts.remove(account);
    }

    public void closeAccount(long accountId) {
        Account account = acccountService.closeAccount(accountId);
        User user = findUserById(account.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User with id " + account.getUserId() + " doesn't exist"));

        List<Account> accounts = user.getAccountList();
        if (accounts.size() < 2) {
            throw new IllegalArgumentException("Can't close last account");
        }
        accounts.remove(account);
    }

    private Optional<User> findUserById(long userId) {
        return users.stream().filter(u -> u.getId() == userId).findFirst();
    }
}
