package cryptocurrency;

import crypto.Blockchain;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Wallet {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    Blockchain chain;

    public Wallet(Blockchain chain) {
        this.chain = chain;
        KeyPair keyPair = CryptographyHelper.ellipticCurveCrypto();
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }

    public double calculateBalance() {
        double balance = 0;

        for (Map.Entry<String, TransactionOutput> item : chain.getUTXOs().entrySet()) {
            TransactionOutput output = item.getValue();
            if (output.isMine(publicKey)) {
                balance += output.getAmount();
            }
        }
        return balance;
    }

    public Transaction transferMoney(PublicKey receiver, double amount) {
        if (calculateBalance() < amount) {
            System.out.println("Not enough money");
            return null;
        }

        List<TransactionInput> inputs = new ArrayList<>();
        for (Map.Entry<String, TransactionOutput> item : chain.getUTXOs().entrySet()) {
            TransactionOutput output = item.getValue();
            if (output.isMine(publicKey)) {
                inputs.add(new TransactionInput(output.getId()));
            }
        }

        Transaction transaction = new Transaction(publicKey,receiver,amount,inputs,chain);
        transaction.generateSignature(privateKey);

        return transaction;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
