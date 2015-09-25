package ben.risk.irs;

import java.util.HashMap;
import java.util.Map;

/**
 * Node.
 */
public class Node {

    private final Map<String, Node> nodes = new HashMap<>();

    private String value;

    public Node getNode(String nodeName) {
        return nodes.get(nodeName);
    }

    public void putNode(String nodeName, Node node) {
        assert !nodes.containsKey(nodeName);
        nodes.put(nodeName, node);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }

    public void print(int indent) {
        for (Map.Entry<String, Node> entry : getNodes().entrySet()) {
            for (int i = 0; i < indent * 2; i++) {
                System.out.print(" ");
            }
            if (entry.getValue().getValue() != null) {
                System.out.println(entry.getKey() + " = " + entry.getValue().getValue());
            }
            else {
                System.out.println(entry.getKey());
                entry.getValue().print(indent + 1);
            }
        }
    }
}
