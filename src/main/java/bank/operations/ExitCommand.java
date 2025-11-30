package bank.operations;

import org.springframework.stereotype.Component;

@Component
public class ExitCommand implements OperationCommand{
    @Override
    public void execute() {
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.EXIT;
    }
}
