package bank.operations;

import bank.ConsoleScanner;
import bank.account.AcccountService;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Scanner;

@Component
public class WithdrawAccountCommand implements OperationCommand {
    private final AcccountService acccountService;
    private final Scanner scanner;

    public WithdrawAccountCommand(AcccountService acccountService, ConsoleScanner consoleScanner) {
        this.acccountService = acccountService;
        scanner = consoleScanner.getScanner();
    }

    @Override
    public void execute() {
        System.out.println("Enter account ID to withdraw from:");
        try {
            long accountId = Long.parseLong(scanner.nextLine().trim());
            System.out.println("Enter amount to withdraw:");
            double amount = Double.parseDouble(scanner.nextLine().trim());
            acccountService.withdraw(accountId, amount);
            System.out.println("Amount " + amount + " withdrawn from account ID: " + accountId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format - please enter valid numbers");
        } catch (NoSuchElementException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.ACCOUNT_WITHDRAW;
    }
}
