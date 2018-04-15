package seedu.flashy;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.flashy.commons.core.Config;
import seedu.flashy.commons.core.GuiSettings;
import seedu.flashy.commons.exceptions.DataConversionException;
import seedu.flashy.commons.util.FileUtil;
import seedu.flashy.commons.util.XmlUtil;
import seedu.flashy.model.CardBank;
import seedu.flashy.model.Model;
import seedu.flashy.model.ModelManager;
import seedu.flashy.model.ReadOnlyCardBank;
import seedu.flashy.model.UserPrefs;
import seedu.flashy.storage.UserPrefsStorage;
import seedu.flashy.storage.XmlSerializableCardBank;
import seedu.flashy.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final String SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.xml");
    public static final String APP_TITLE = "Test App";

    protected static final String DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected static final String ADDRESS_BOOK_NAME = "Test";
    protected Supplier<ReadOnlyCardBank> initialDataSupplier = () -> null;
    protected String saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyCardBank> initialDataSupplier, String saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableCardBank(this.initialDataSupplier.get()),
                    this.saveFileLocation);
        }
    }

    @Override
    protected Config initConfig(String configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setAppTitle(APP_TITLE);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setCardBankFilePath(saveFileLocation);
        userPrefs.setCardBankName(ADDRESS_BOOK_NAME);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the flashy book data stored inside the storage file.
     */
    public CardBank readStorageCardBank() {
        try {
            return new CardBank(storage.readCardBank().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the CardBank format.");
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.");
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public String getStorageSaveLocation() {
        return storage.getCardBankFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getCardBank()), new UserPrefs());
        ModelHelper.setFilteredList(copy, model.getFilteredTagList());
        return copy;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates an XML file at the {@code filePath} with the {@code data}.
     */
    private <T> void createDataFileWithData(T data, String filePath) {
        try {
            File saveFileForTesting = new File(filePath);
            FileUtil.createIfMissing(saveFileForTesting);
            XmlUtil.saveDataToFile(saveFileForTesting, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
