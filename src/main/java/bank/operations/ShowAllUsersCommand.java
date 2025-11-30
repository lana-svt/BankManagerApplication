package bank.operations;

import bank.user.User;
import bank.user.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShowAllUsersCommand implements OperationCommand {
    private final UserService userService;

    public ShowAllUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute() {
        List<User> users = userService.showAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found");
        } else {
            System.out.println("List of all users:");
            users.forEach(System.out::println);
        }
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.SHOW_ALL_USERS;
    }
}
