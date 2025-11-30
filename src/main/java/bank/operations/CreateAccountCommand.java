package bank.operations;

import bank.ConsoleScanner;
import bank.account.Account;
import bank.user.UserService;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Scanner;

@Component
public class CreateAccountCommand implements OperationCommand {
    private final UserService userService;
    private final Scanner scanner;

    public CreateAccountCommand(UserService userService, ConsoleScanner consoleScanner) {
        this.userService = userService;
        this.scanner = consoleScanner.getScanner();
    }

    @Override
    public void execute() {
        System.out.println("Enter the user id for which to create an account:");
        try {
            long userId = Long.parseLong(scanner.nextLine().trim());
            Account account = userService.createAccountForUser(userId);
            System.out.println("New account created with ID: " + account.getId());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format - please enter valid numbers");
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.ACCOUNT_CREATE;
    }
}
