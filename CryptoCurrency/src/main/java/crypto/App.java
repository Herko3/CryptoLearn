package crypto;

import cryptocurrency.Miner;
import cryptocurrency.Transaction;
import cryptocurrency.TransactionOutput;
import cryptocurrency.Wallet;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class App {

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());

        Blockchain chain = new Blockchain();
        Wallet userA = new Wallet(chain);
        Wallet userB = new Wallet(chain);
        Wallet lender = new Wallet(chain);
        Miner miner = new Miner();

        Transaction genesisTransaction = new Transaction(lender.getPublicKey(), userA.getPublicKey(), 500, null,chain);
        genesisTransaction.generateSignature(lender.getPrivateKey());
        genesisTransaction.setTransactionId("0");
        genesisTransaction.addTransactionOutput(new TransactionOutput(genesisTransaction.getTransactionId(), genesisTransaction.getReceiver(), genesisTransaction.getAmount()));
        chain.getUTXOs().put(genesisTransaction.getOutputs().get(0).getId(), genesisTransaction.getOutputs().get(0));

        System.out.println("Constructing the genesis block...");
        Block genesis = new Block(0, Constants.GENESIS_PREV_HASH);
        genesis.addTransaction(genesisTransaction);
        miner.mine(genesis, chain);

        Block block1 = new Block(genesis.getId() + 1, genesis.getHash());
        System.out.println("\nuserA's balance is: " + userA.calculateBalance());
        System.out.println("\nuserA tries to send money (120 coins) to userB...");
        block1.addTransaction(userA.transferMoney(userB.getPublicKey(), 120));
        miner.mine(block1, chain);
        System.out.println("\nuserA's balance is: " + userA.calculateBalance());
        System.out.println("userB's balance is: " + userB.calculateBalance());

        Block block2 = new Block(block1.getId() + 1, block1.getHash());
        System.out.println("\nuserA sends more funds (600) than it has...");
        block2.addTransaction(userA.transferMoney(userB.getPublicKey(), 600));
        miner.mine(block2, chain);
        System.out.println("\nuserA's balance is: " + userA.calculateBalance());
        System.out.println("userB's balance is: " + userB.calculateBalance());

        Block block3 = new Block(block2.getId() + 1, block2.getHash());
        System.out.println("\nuserB is attempting to send funds (110) to userA...");
        block3.addTransaction(userB.transferMoney(userA.getPublicKey(), 110));
        System.out.println("\nuserA's balance is: " + userA.calculateBalance());
        System.out.println("userB's balance is: " + userB.calculateBalance());
        miner.mine(block3, chain);

        System.out.println("Miner's reward: " + miner.getReward());

    }
}
