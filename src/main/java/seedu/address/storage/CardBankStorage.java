package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCardBank;

/**
 * Represents a storage for {@link seedu.address.model.CardBank}.
 */
public interface CardBankStorage {

    /**
     * Returns the file path of the data file.
     */
    String getCardBankFilePath();

    /**
     * Returns CardBank data as a {@link ReadOnlyCardBank}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCardBank> readCardBank() throws DataConversionException, IOException;

    /**
     * @see #getCardBankFilePath()
     */
    Optional<ReadOnlyCardBank> readCardBank(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCardBank} to the storage.
     * @param cardBank cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCardBank(ReadOnlyCardBank cardBank) throws IOException;

    /**
     * @see #saveCardBank(ReadOnlyCardBank)
     */
    void saveCardBank(ReadOnlyCardBank cardBank, String filePath) throws IOException;

}
