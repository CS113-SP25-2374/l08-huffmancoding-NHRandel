import java.util.*;

public class HuffmanCoding implements HuffmanInterface {
    private Map<Character, String> charToCode;
    private Map<String, Character> codeToChar;
    int count = 0;

    private class Node implements Comparable<Node> {
        char character;
        int freq;
        Node left, right;

        Node(char character, int freq) {
            this.character = character;
            this.freq = freq;
        }

        Node(Node left, Node right) {
            this.character = '\0';
            this.freq = left.freq + right.freq;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            count++;
            return left == null && right == null;
        }

        public int compareTo(Node other) {
            count++;
            return this.freq - other.freq;
        }
    }

    public String encode(String message) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char ch : message.toCharArray()) {
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
            count++;
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            pq.offer(new Node(entry.getKey(), entry.getValue()));
            count++;
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            pq.offer(new Node(left, right));
            count++;
        }

        Node root = pq.poll();
        charToCode = new HashMap<>();
        codeToChar = new HashMap<>();
        buildCodeMap(root, "");

        StringBuilder encoded = new StringBuilder();
        for (char ch : message.toCharArray()) {
            encoded.append(charToCode.get(ch));
        }

        return encoded.toString();
    }

    public String decode(String encodedMessage) {
        StringBuilder decoded = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();

        for (char bit : encodedMessage.toCharArray()) {
            currentCode.append(bit);
            if (codeToChar.containsKey(currentCode.toString())) {
                decoded.append(codeToChar.get(currentCode.toString()));
                currentCode.setLength(0);
                count++;
            }
        }

        return decoded.toString();
    }

    private void buildCodeMap(Node node, String code) {
        if (node.isLeaf()) {
            charToCode.put(node.character, code);
            codeToChar.put(code, node.character);
            count++;
            return;
        }
        buildCodeMap(node.left, code + "0");
        buildCodeMap(node.right, code + "1");
    }

    public String getCount(){ // returns count
        return String.valueOf(count);
    }
}
