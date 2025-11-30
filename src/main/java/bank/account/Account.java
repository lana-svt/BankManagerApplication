package bank.account;

public class Account {
    private final long id;
    private final long userId;
    private double moneyAmount;

    public Account(long id, long userId, long moneyAmount) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = moneyAmount;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public long getUserId() {
        return userId;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Account{id=" + id + ", userId=" + userId + ", moneyAmount=" + moneyAmount + "}";
    }
}
