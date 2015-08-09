package ben.risk.irs;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Map;

/**
 * Message Factory Interface.
 */
public interface IMessageFactory<T extends Serializable> {

    @NotNull
    String getMessageName();

    @NotNull
    Class<T> getMessageClass();

    @NotNull
    T createMessage(@NotNull Map<String, String> values);
}
