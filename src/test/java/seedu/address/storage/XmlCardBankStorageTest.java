package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalCardBank.getTypicalCardBank;
import static seedu.address.testutil.TypicalTags.BULGARIAN_TAG;
import static seedu.address.testutil.TypicalTags.PHYSICS_TAG;
import static seedu.address.testutil.TypicalTags.RUSSIAN_TAG;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.CardBank;
import seedu.address.model.ReadOnlyCardBank;

public class XmlCardBankStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlCardBankStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readCardBank_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readCardBank(null);
    }

    private java.util.Optional<ReadOnlyCardBank> readCardBank(String filePath) throws Exception {
        return new XmlCardBankStorage(filePath).readCardBank(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCardBank("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readCardBank("NotXmlFormatCardBank.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readCardBank_invalidAndValidTagCardBank_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readCardBank("invalidAndValidTagCardBank.xml");
    }

    @Test
    public void readAndSaveCardBank_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempCardBank.xml";
        CardBank original = getTypicalCardBank();
        XmlCardBankStorage xmlCardBankStorage = new XmlCardBankStorage(filePath);

        //Save in new file and read back
        xmlCardBankStorage.saveCardBank(original, filePath);
        ReadOnlyCardBank readBack = xmlCardBankStorage.readCardBank(filePath).get();
        assertEquals(original, new CardBank(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTag(RUSSIAN_TAG);
        original.removeTag(PHYSICS_TAG);
        xmlCardBankStorage.saveCardBank(original, filePath);
        readBack = xmlCardBankStorage.readCardBank(filePath).get();
        assertEquals(original, new CardBank(readBack));

        //Save and read without specifying file path
        original.addTag(BULGARIAN_TAG);
        xmlCardBankStorage.saveCardBank(original); //file path not specified
        readBack = xmlCardBankStorage.readCardBank().get(); //file path not specified
        assertEquals(original, new CardBank(readBack));

    }

    @Test
    public void saveCardBank_nullCardBank_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveCardBank(null, "SomeFile.xml");
    }

    /**
     * Saves {@code cardBank} at the specified {@code filePath}.
     */
    private void saveCardBank(ReadOnlyCardBank cardBank, String filePath) {
        try {
            new XmlCardBankStorage(filePath).saveCardBank(cardBank, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCardBank_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveCardBank(new CardBank(), null);
    }


}
