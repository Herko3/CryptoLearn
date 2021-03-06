package merkletree;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        List<String> transactions = new ArrayList<>();
        transactions.add("aa");
        transactions.add("bb");
        transactions.add("cc");
        transactions.add("dd");
        transactions.add("ee");
        transactions.add("11");
        transactions.add("22");
        transactions.add("33");
        transactions.add("44");
        transactions.add("55");

        MerkleTree merkleTree = new MerkleTree(transactions);

        System.out.println(merkleTree.getMerkleRoot().get(0));
    }
}
