package cryptocurrency;

import java.security.PublicKey;

public class TransactionOutput {

    private String id;
    private String parentTransactionId;
    private PublicKey receiver;
    private double amount;

    public TransactionOutput(String parentTransactionId, PublicKey receiver, double amount) {
        this.parentTransactionId = parentTransactionId;
        this.receiver = receiver;
        this.amount = amount;
        generateId();
    }

    private void generateId() {
        id = CryptographyHelper.generateHash(receiver.toString() + amount + parentTransactionId);
    }

    public boolean isMine(PublicKey publicKey) {
        return publicKey == receiver;
    }

    public String getId() {
        return id;
    }

    public String getParentTransactionId() {
        return parentTransactionId;
    }

    public PublicKey getReceiver() {
        return receiver;
    }

    public double getAmount() {
        return amount;
    }
}
