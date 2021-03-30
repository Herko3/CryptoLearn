package crypto;

import cryptocurrency.TransactionOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Blockchain {

    private List<Block> blockchain = new ArrayList<>();
    private Map<String, TransactionOutput> UTXOs = new HashMap<>();

    public void addBlock(Block block) {
        blockchain.add(block);
    }

    public List<Block> getBlockchain() {
        return new ArrayList<>(blockchain);
    }

    public int size() {
        return blockchain.size();
    }

    public Map<String, TransactionOutput> getUTXOs() {
        return UTXOs;
    }

    @Override
    public String toString() {
        return blockchain.stream()
                .map(Block::toString)
                .collect(Collectors.joining("\n"));
    }
}
