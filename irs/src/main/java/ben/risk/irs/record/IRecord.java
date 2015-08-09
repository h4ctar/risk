package ben.risk.irs.record;

import net.jcip.annotations.Immutable;

import java.io.Serializable;

@Immutable
public interface IRecord extends Serializable {

    int getRecordId();
}
