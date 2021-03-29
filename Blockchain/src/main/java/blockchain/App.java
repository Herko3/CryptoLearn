package blockchain;

public class App {

    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        Miner miner = new Miner();

        Block block0 = new Block(0,Constants.GENESIS_PREV_HASH,"Transaction1");
        miner.mine(block0,blockchain);

        Block block1 = new Block(1,blockchain.getBlockchain().get(blockchain.size()-1).getHash(),"Transaction2");
        miner.mine(block1,blockchain);

        Block block2 = new Block(2,blockchain.getBlockchain().get(blockchain.size()-1).getHash(),"Transaction3");
        miner.mine(block2,blockchain);

        System.out.println("\n" + "BLOCKCHAIN: \n" + blockchain);
        System.out.println("Miner's reward: " + miner.getReward());

    }
}
