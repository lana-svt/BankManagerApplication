package bank;

import bank.operations.OperationCommand;
import bank.operations.OperationType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class OperationsConsoleListener implements Runnable {
    private final Map<OperationType, OperationCommand> commandMap;
    private final Scanner scanner;
    private final ConsoleScanner consoleScanner;
    private boolean exit = false;

    public OperationsConsoleListener(List<OperationCommand> commands, ConsoleScanner consoleScanner) {
        commandMap = new HashMap<>();
        commands.forEach(command -> commandMap.put(command.getOperationType(), command));
        scanner = consoleScanner.getScanner();
        this.consoleScanner = consoleScanner;
    }

    @Override
    public void run() {
        while (!exit) {
            printAvailableOperations();
            String operation = scanner.nextLine();
            try {
                OperationType type = OperationType.valueOf(operation);
                OperationCommand command = commandMap.get(type);
                if(type == OperationType.EXIT){
                    exit = true;
                } else if (command != null) {
                    command.execute();
                } else {
                    System.out.println("Command not implemented: " + type);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Unknown operation: " + operation);
            }

            System.out.println();
        }
        consoleScanner.close();
    }

    private void printAvailableOperations() {
        System.out.println("Please enter one of operation type:");
        for (OperationType type : OperationType.values()) {
            System.out.println("-" + type);
        }
    }
}
