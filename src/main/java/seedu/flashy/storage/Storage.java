package seedu.flashy.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.flashy.commons.events.model.CardBankChangedEvent;
import seedu.flashy.commons.events.storage.DataSavingExceptionEvent;
import seedu.flashy.commons.exceptions.DataConversionException;
import seedu.flashy.model.ReadOnlyCardBank;
import seedu.flashy.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends CardBankStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getCardBankFilePath();

    @Override
    Optional<ReadOnlyCardBank> readCardBank() throws DataConversionException, IOException;


    @Override
    void saveCardBank(ReadOnlyCardBank cardBank) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleCardBankChangedEvent(CardBankChangedEvent abce);
}
