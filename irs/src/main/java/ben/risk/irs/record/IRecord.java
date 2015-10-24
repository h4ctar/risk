package ben.risk.irs.record;

import net.jcip.annotations.Immutable;

import java.io.Serializable;

/**
 * Record Interface.
 * <p>
 *     All records that are stored in the data store must implement this interface.
 * </p>
 */
@Immutable
public interface IRecord extends Serializable {

    /**
     * Get the ID of the record.
     * @return the ID of the record
     */
    int getRecordId();
}
