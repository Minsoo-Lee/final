package Account;

public class TransactionData {
    private String type;
    private double amount;

    public TransactionData(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}
