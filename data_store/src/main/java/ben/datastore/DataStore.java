package ben.datastore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import ben.risk.irs.client.ClientNames;
import ben.mom.client.IMomClient;
import ben.mom.client.IMessageProcessor;
import ben.risk.irs.record.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Data Store.
 */
public class DataStore {

    /**
     * The Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(DataStore.class.getSimpleName());

    /**
     * The MOM Client.
     */
    @NotNull
    private final IMomClient momClient;

    /**
     * The actual store of records.
     */
    private final Map<Class<?>, Map<Integer, IRecord>> dataStore = new HashMap<>();

    /**
     * The types of update record messages.
     * <p>
     *     Keyed by record type.
     * </p>
     */
    private final Map<Class<? extends IRecord>, Class<?>> updateRecordTypes = new HashMap<>();

    /**
     * The types of delete record messages.
     * <p>
     *     Keyed by record type.
     * </p>
     */
    private final Map<Class<? extends IRecord>, Class<?>> deleteRecordTypes = new HashMap<>();

    /**
     * Constructor.
     * @param momClient the MOM Client
     */
    public DataStore(@NotNull IMomClient momClient) {
        this.momClient = momClient;
        momClient.subscribe(WriteRecord.class, new WriteRecordMessageProcessor());
        momClient.subscribe(DeleteRecord.class, new DeleteRecordMessageProcessor());
        momClient.subscribe(RequestAllRecords.class, new RequestAllRecordsMessageProcessor());
        momClient.subscribe(DeleteAllRecords.class, new DeleteAllRecordsMessageProcessor());
    }

    /**
     * Add a record type.
     * @param recordType the record type
     * @param updateRecordType the type of update record message
     * @param deleteRecordType the type of delete record message
     */
    public void addRecordType(@NotNull Class<? extends IRecord> recordType, @NotNull Class<? extends Serializable> updateRecordType, @NotNull Class<? extends Serializable> deleteRecordType) throws Exception {
        assert !updateRecordTypes.containsKey(recordType) : "Record type already added";
        assert !deleteRecordTypes.containsKey(recordType) : "Record type already added";
        updateRecordType.getDeclaredConstructor(recordType);
        deleteRecordType.getDeclaredConstructor(int.class);
        LOGGER.info("Adding record type " + recordType.getSimpleName());
        updateRecordTypes.put(recordType, updateRecordType);
        deleteRecordTypes.put(recordType, deleteRecordType);
    }

    /**
     * Create an update message for a record.
     * @param record the record
     * @return the update message
     * @throws Exception the message could not be created
     */
    @NotNull
    private Serializable createUpdateMessage(@NotNull IRecord record) throws Exception {
        Class<? extends IRecord> recordType = record.getClass();
        Class<?> updateMessageType = updateRecordTypes.get(recordType);
        return (Serializable) updateMessageType.getDeclaredConstructor(recordType).newInstance(record);
    }

    /**
     * Create a delete message for a record.
     * @param record the record
     * @return the delete message
     * @throws Exception the message could not be created
     */
    @NotNull
    private Serializable createDeleteMessage(@NotNull IRecord record) throws Exception {
        Class<? extends IRecord> recordType = record.getClass();
        Class<?> deleteMessageType = deleteRecordTypes.get(recordType);
        return (Serializable) deleteMessageType.getDeclaredConstructor(int.class).newInstance(record.getRecordId());
    }

    /**
     * Write Record Message Processor.
     */
    private class WriteRecordMessageProcessor implements IMessageProcessor<WriteRecord> {

        @Override
        public void processMessage(@NotNull WriteRecord writeRecord) {
            IRecord record = writeRecord.getRecord();
            Class<?> type = record.getClass();
            int recordId = record.getRecordId();
            LOGGER.info("Writing record " + recordId + " of type " + type.getSimpleName());

            Map<Integer, IRecord> recordsForThisType = dataStore.get(type);
            if (recordsForThisType == null) {
                LOGGER.info("Creating record set for " + type.getSimpleName());
                recordsForThisType = new HashMap<>();
                dataStore.put(type, recordsForThisType);
            }

            IRecord oldRecord = recordsForThisType.get(recordId);

            // The old record and the new record should not be the same instance
            assert oldRecord != record : "Same instance sent across the wire";

            // Only update the record if it is not in the store, or it is different
            if (oldRecord == null || !oldRecord.equals(record)) {
                recordsForThisType.put(record.getRecordId(), record);
                try {
                    momClient.sendMessage(ClientNames.ALL, createUpdateMessage(record));
                }
                catch (Exception e) {
                    LOGGER.error("Could not create update message", e);
                }
            }
            else {
                LOGGER.warn("No changes to record detected");
            }
        }
    }

    /**
     * Delete Record Message Processor.
     */
    private class DeleteRecordMessageProcessor implements IMessageProcessor<DeleteRecord> {

        @Override
        public void processMessage(@NotNull DeleteRecord deleteRecord) {
            Class<?> type = deleteRecord.getType();
            int recordId = deleteRecord.getRecordId();
            LOGGER.info("Deleting record " + recordId + " of " + type.getSimpleName());

            Map<Integer, IRecord> recordsForThisType = dataStore.get(type);
            assert recordsForThisType != null : "There are no records of this type";

            IRecord record = recordsForThisType.remove(recordId);
            assert record != null : "The record does not exist";

            try {
                momClient.sendMessage(ClientNames.ALL, createDeleteMessage(record));
            }
            catch (Exception e) {
                LOGGER.error("Could not create delete message", e);
            }
        }
    }

    /**
     * Request All Records Message Processor.
     */
    private class RequestAllRecordsMessageProcessor implements IMessageProcessor<RequestAllRecords> {

        @Override
        public void processMessage(@NotNull RequestAllRecords requestAllRecords) {
            Class<?> type = requestAllRecords.getType();
            LOGGER.info("Requesting all records of type " + type.getSimpleName());

            Map<Integer, IRecord> recordsForThisType = dataStore.get(type);
            if (recordsForThisType != null) {
                for (IRecord record : recordsForThisType.values()) {
                    try {
                        momClient.sendMessage(ClientNames.ALL, createUpdateMessage(record));
                    }
                    catch (Exception e) {
                        LOGGER.error("Could not create update message", e);
                    }
                }
            }
        }
    }

    /**
     * Delete All Records Message Processor.
     */
    private class DeleteAllRecordsMessageProcessor implements IMessageProcessor<DeleteAllRecords> {

        @Override
        public void processMessage(@NotNull DeleteAllRecords deleteAllRecords) {
            Class<?> type = deleteAllRecords.getType();
            LOGGER.info("Deleting all records of type " + type.getSimpleName());

            Map<Integer, IRecord> recordsForThisType = dataStore.remove(type);
            if (recordsForThisType != null) {
                for (IRecord record : recordsForThisType.values()) {
                    try {
                        LOGGER.info("Deleting record " + record.getRecordId() + " of " + type.getSimpleName());
                        momClient.sendMessage(ClientNames.ALL, createDeleteMessage(record));
                    }
                    catch (Exception e) {
                        LOGGER.error("Could not create delete message", e);
                    }
                }
            }
        }
    }
}
