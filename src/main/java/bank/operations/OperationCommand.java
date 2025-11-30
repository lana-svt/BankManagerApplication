package bank.operations;

public interface OperationCommand {
    void execute();

    OperationType getOperationType();
}
