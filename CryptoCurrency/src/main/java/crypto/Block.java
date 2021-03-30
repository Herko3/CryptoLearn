package crypto;

import cryptocurrency.CryptographyHelper;
import cryptocurrency.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Block {

    private int id;
    private int nonce;
    private long timeStamp;
    private String hash;
    private String previousHash;
    private List<Transaction> transactions = new ArrayList<>();

    public Block(int id, String previousHash) {
        this.id = id;
        this.previousHash = previousHash;
        timeStamp = new Date().getTime();
        generateHash();
    }

    public void generateHash() {
        String dataToHash = id + previousHash + timeStamp + nonce + transactions;
        hash = CryptographyHelper.generateHash(dataToHash);
    }

    public void incrementNonce() {
        nonce++;
    }

    public String getHash() {
        return hash;
    }

    public boolean addTransaction(Transaction transaction){
        if(transaction == null){
            return false;
        }

        if(!previousHash.equals(Constants.GENESIS_PREV_HASH)){
            if(!transaction.verifyTransaction()){
                System.out.println("Invalid transaction");
                return false;
            }
        }

        transactions.add(transaction);
        System.out.println("Transaction is valid and was added to the block");
        return true;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + "-" + hash + "-" + previousHash;
    }
}
