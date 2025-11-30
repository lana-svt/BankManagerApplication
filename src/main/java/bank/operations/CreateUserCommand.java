package bank.operations;

import bank.ConsoleScanner;
import bank.user.User;
import bank.user.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateUserCommand implements OperationCommand {
    private final UserService userService;
    private final Scanner scanner;

    public CreateUserCommand(UserService userService, ConsoleScanner consoleScanner) {
        this.userService = userService;
        scanner = consoleScanner.getScanner();
    }

    @Override
    public void execute() {
        System.out.println("Enter login for new user:");
        String login = scanner.nextLine().trim();
        if (login.isEmpty()) {
            System.out.println("Error: Login cannot be empty");
            return;
        }

        try {
            User user = userService.createUser(login);
            System.out.println("User created: " + user);
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.USER_CREATE;
    }
}
