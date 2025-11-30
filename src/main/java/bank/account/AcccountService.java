package bank.account;

import bank.config.AccountProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AcccountService {
    private final AtomicLong id;
    private final List<Account> accounts;
    private final AccountProperties accountProperties;

    public AcccountService(AccountProperties accountProperties) {
        this.accountProperties = accountProperties;
        id = new AtomicLong();
        accounts = new ArrayList<>();
    }

    public Account createAccount(Long userId) {
        Account account = new Account(id.incrementAndGet(), userId, accountProperties.getDefaultAmount());
        accounts.add(account);
        return account;
    }

    public Account closeAccount(long accountId) {
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new NoSuchElementException("Account with id " + accountId + " doesn't exist"));

        accounts.remove(account);
        return account;
    }

    public void deposit(long accountId, double moneyAmount) {
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new NoSuchElementException("Account with id " + accountId + " doesn't exist"));

        if (moneyAmount <= 0) {
            throw new IllegalArgumentException("Amount should be positive");
        }
        account.setMoneyAmount(account.getMoneyAmount() + moneyAmount);
    }

    public void transfer(long accountIdFrom, long accountIdTo, double moneyAmount) {
        if (accountIdFrom == accountIdTo) {
            throw new IllegalArgumentException("The accounts with the IDs " + accountIdTo + " are the same");
        }
        Account accountSender = findAccountById(accountIdFrom)
                .orElseThrow(() -> new NoSuchElementException("Account with id " + accountIdFrom + " doesn't exist"));

        Account accountRecipient = findAccountById(accountIdTo)
                .orElseThrow(() -> new NoSuchElementException("Account with id " + accountIdTo + " doesn't exist"));

        if (moneyAmount <= 0) {
            throw new IllegalArgumentException("Amount should be positive");
        }

        if (accountSender.getMoneyAmount() < moneyAmount) {
            throw new IllegalArgumentException("No such money to withdraw from account: id=" + accountSender.getId() +
                    ", moneyAmount=" + accountSender.getMoneyAmount() +
                    ", attemptedWithdraw=" + moneyAmount);
        }

        accountSender.setMoneyAmount(accountSender.getMoneyAmount() - moneyAmount);
        double commission = moneyAmount * accountProperties.getTransferCommission();
        double amountToTransfer = moneyAmount - commission;
        accountRecipient.setMoneyAmount(accountRecipient.getMoneyAmount() + amountToTransfer);
    }

    public void withdraw(long accountId, double moneyAmount) {
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new NoSuchElementException("Account with id " + accountId + " doesn't exist"));

        if (account.getMoneyAmount() < moneyAmount) {
            throw new IllegalArgumentException("No such money to withdraw from account: id=" + account.getId() +
                    ", moneyAmount=" + account.getMoneyAmount() +
                    ", attemptedWithdraw=" + moneyAmount);
        }
        if (moneyAmount <= 0) {
            throw new IllegalArgumentException("Amount should be positive");
        }

        account.setMoneyAmount(account.getMoneyAmount() - moneyAmount);
    }

    private Optional<Account> findAccountById(long accountId) {
        return accounts.stream().filter(a -> a.getId() == accountId).findFirst();
    }
}
