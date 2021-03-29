package blockchain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Blockchain {

    private List<Block> blockchain = new ArrayList<>();

    public void addBlock(Block block) {
        blockchain.add(block);
    }

    public List<Block> getBlockchain() {
        return new ArrayList<>(blockchain);
    }

    public int size() {
        return blockchain.size();
    }

    @Override
    public String toString() {
        return blockchain.stream()
                .map(Block::toString)
                .collect(Collectors.joining("\n"));
    }
}
