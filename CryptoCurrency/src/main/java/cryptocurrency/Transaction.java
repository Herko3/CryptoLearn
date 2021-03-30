package cryptocurrency;

import crypto.Blockchain;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Transaction {

    private String transactionId;
    private PublicKey sender;
    private PublicKey receiver;
    private double amount;
    private byte[] signature;
    Blockchain chain;

    private List<TransactionInput> inputs;
    private List<TransactionOutput> outputs = new ArrayList<>();

    public Transaction(PublicKey sender, PublicKey receiver, double amount, List<TransactionInput> inputs, Blockchain chain) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.inputs = inputs;
        this.chain = chain;
        calculateHash();
    }

    private void calculateHash() {
        transactionId = CryptographyHelper.generateHash(data());
    }

    public void generateSignature(PrivateKey privateKey) {
        signature = CryptographyHelper.applyECDSASignature(privateKey, data());
    }

    public boolean verifySignature() {
        return CryptographyHelper.verifyECDSASignature(sender, data(), signature);
    }

    private String data() {
        return sender.toString() + receiver.toString() + amount;
    }

    public boolean verifyTransaction() {
        if (!verifySignature()) {
            System.out.println("Due to invalid signature, the transaction failed");
            return false;
        }

        for (TransactionInput transactionInput : inputs) {
            transactionInput.setUTXO(chain.getUTXOs().get(transactionInput.getTransactionOutputId()));
        }

        outputs.add(new TransactionOutput(transactionId, receiver, amount));
        outputs.add(new TransactionOutput(transactionId, sender, getInputSum() - amount));


        for(TransactionInput input : inputs){
            if(input.getUTXO() != null){
                chain.getUTXOs().remove(input.getUTXO().getId());
            }
        }

        for(TransactionOutput output : outputs){
            chain.getUTXOs().put(output.getId(),output);
        }

        return true;
    }

    private double getInputSum() {
        double sum = 0;

        for (TransactionInput transactionInput : inputs) {
            if (transactionInput.getUTXO() != null){
                sum += transactionInput.getUTXO().getAmount();
            }
        }
        return sum;
    }

    public void addTransactionOutput(TransactionOutput output){
        outputs.add(output);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public PublicKey getSender() {
        return sender;
    }

    public PublicKey getReceiver() {
        return receiver;
    }

    public double getAmount() {
        return amount;
    }

    public byte[] getSignature() {
        return signature;
    }

    public List<TransactionInput> getInputs() {
        return inputs;
    }

    public List<TransactionOutput> getOutputs() {
        return outputs;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
