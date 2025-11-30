package bank.operations;

import bank.ConsoleScanner;
import bank.account.AcccountService;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Scanner;

@Component
public class TransferAccountCommand implements OperationCommand {
    private final AcccountService acccountService;
    private final Scanner scanner;

    public TransferAccountCommand(AcccountService acccountService, ConsoleScanner consoleScanner) {
        this.acccountService = acccountService;
        scanner = consoleScanner.getScanner();
    }

    @Override
    public void execute() {
        try {
            System.out.println("Enter source account ID:");
            long fromAccountId = Long.parseLong(scanner.nextLine().trim());
            System.out.println("Enter target account ID:");
            long toAccountId = Long.parseLong(scanner.nextLine().trim());
            System.out.println("Enter amount to transfer:");
            double amount = Double.parseDouble(scanner.nextLine().trim());
            acccountService.transfer(fromAccountId, toAccountId, amount);
            System.out.println("Amount " + amount + " transferred from account ID " + fromAccountId +
                    " to account ID " + toAccountId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format - please enter valid numbers");
        } catch (NoSuchElementException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.ACCOUNT_TRANSFER;
    }
}
