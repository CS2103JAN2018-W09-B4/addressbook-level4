package seedu.flashy.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.flashy.testutil.TypicalCardBank.getTypicalCardBank;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.flashy.commons.events.model.CardBankChangedEvent;
import seedu.flashy.commons.events.storage.DataSavingExceptionEvent;
import seedu.flashy.model.CardBank;
import seedu.flashy.model.ReadOnlyCardBank;
import seedu.flashy.model.UserPrefs;
import seedu.flashy.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlCardBankStorage cardBankStorage = new XmlCardBankStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(cardBankStorage, userPrefsStorage);
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void cardBankReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlCardBankStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlCardBankStorageTest} class.
         */
        CardBank original = getTypicalCardBank();
        storageManager.saveCardBank(original);
        ReadOnlyCardBank retrieved = storageManager.readCardBank().get();
        assertEquals(original, new CardBank(retrieved));
    }

    @Test
    public void getCardBankFilePath() {
        assertNotNull(storageManager.getCardBankFilePath());
    }

    @Test
    public void handleCardBankChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlCardBankStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        storage.handleCardBankChangedEvent(new CardBankChangedEvent(new CardBank()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlCardBankStorageExceptionThrowingStub extends XmlCardBankStorage {

        public XmlCardBankStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveCardBank(ReadOnlyCardBank cardBank, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
