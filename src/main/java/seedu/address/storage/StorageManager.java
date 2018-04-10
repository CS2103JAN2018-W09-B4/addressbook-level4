package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.CardBankChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCardBank;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of CardBank data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private CardBankStorage cardBankStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(CardBankStorage cardBankStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.cardBankStorage = cardBankStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public String getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ CardBank methods ==============================

    @Override
    public String getCardBankFilePath() {
        return cardBankStorage.getCardBankFilePath();
    }

    @Override
    public Optional<ReadOnlyCardBank> readCardBank() throws DataConversionException, IOException {
        return readCardBank(cardBankStorage.getCardBankFilePath());
    }

    @Override
    public Optional<ReadOnlyCardBank> readCardBank(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return cardBankStorage.readCardBank(filePath);
    }

    @Override
    public void saveCardBank(ReadOnlyCardBank cardBank) throws IOException {
        saveCardBank(cardBank, cardBankStorage.getCardBankFilePath());
    }

    @Override
    public void saveCardBank(ReadOnlyCardBank cardBank, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        cardBankStorage.saveCardBank(cardBank, filePath);
    }


    @Override
    @Subscribe
    public void handleCardBankChangedEvent(CardBankChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveCardBank(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
