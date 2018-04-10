package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyCardBank;

/**
 * A class to access CardBank data stored as an xml file on the hard disk.
 */
public class XmlCardBankStorage implements CardBankStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlCardBankStorage.class);

    private String filePath;

    public XmlCardBankStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getCardBankFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCardBank> readCardBank() throws DataConversionException, IOException {
        return readCardBank(filePath);
    }

    /**
     * Similar to {@link #readCardBank()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCardBank> readCardBank(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        File cardBankFile = new File(filePath);

        if (!cardBankFile.exists()) {
            logger.info("CardBank file "  + cardBankFile + " not found");
            return Optional.empty();
        }

        XmlSerializableCardBank xmlCardBank = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
        try {
            return Optional.of(xmlCardBank.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + cardBankFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCardBank(ReadOnlyCardBank cardBank) throws IOException {
        saveCardBank(cardBank, filePath);
    }

    /**
     * Similar to {@link #saveCardBank(ReadOnlyCardBank)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveCardBank(ReadOnlyCardBank cardBank, String filePath) throws IOException {
        requireNonNull(cardBank);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableCardBank(cardBank));
    }

}
