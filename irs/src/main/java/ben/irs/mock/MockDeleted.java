package ben.irs.mock;

import java.io.Serializable;

/**
 * Mock Deleted.
 */
public class MockDeleted implements Serializable {

    /**
     * The ID of the record that was deleted.
     */
    private final int id;

    /**
     * Constructor.
     * @param id the ID of the record that was deleted
     */
    public MockDeleted(int id) {
        this.id = id;
    }

    /**
     * Get the ID of the record that was deleted.
     * @return the ID of the record that was deleted
     */
    public int getId() {
        return id;
    }
}
