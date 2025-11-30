package bank.operations;

import bank.ConsoleScanner;
import bank.user.UserService;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Scanner;

@Component
public class CloseAccountCommnad implements OperationCommand {
    private final UserService userService;
    private final Scanner scanner;

    public CloseAccountCommnad(UserService userService, ConsoleScanner consoleScanner) {
        this.userService = userService;
        scanner = consoleScanner.getScanner();
    }

    @Override
    public void execute() {
        System.out.println("Enter account ID to close:");
        try {
            long accountId = Long.parseLong(scanner.nextLine().trim());
            userService.closeAccount(accountId);
            System.out.println("Account with ID " + accountId + " has been closed.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format - please enter valid numbers");
        } catch (NoSuchElementException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.ACCOUNT_CLOSE;
    }
}
