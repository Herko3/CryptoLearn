package blockchain;

import java.util.Date;

public class Block {

    private int id;
    private int nonce;
    private long timeStamp;
    private String hash;
    private String previousHash;
    private String transactions;

    public Block(int id, String previousHash, String transactions) {
        this.id = id;
        this.previousHash = previousHash;
        this.transactions = transactions;
        timeStamp = new Date().getTime();
        generateHash();
    }

    public void generateHash() {
        String dataToHash = id + previousHash + timeStamp + nonce + transactions;
        hash = SHA256HELPER.generateHash(dataToHash);
    }

    public void incrementNonce() {
        nonce++;
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    @Override
    public String toString() {
        return id + "-" + transactions + "-" + hash + "-" + previousHash;
    }
}