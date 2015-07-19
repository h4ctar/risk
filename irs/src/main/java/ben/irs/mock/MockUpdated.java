package ben.irs.mock;

import java.io.Serializable;

/**
 * Mock Updated.
 */
public class MockUpdated implements Serializable {

    private final MockRecord record;

    public MockUpdated(MockRecord record) {
        this.record = record;
    }

    public MockRecord getRecord() {
        return record;
    }
}
