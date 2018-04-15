package seedu.flashy.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.flashy.commons.exceptions.IllegalValueException;
import seedu.flashy.commons.util.FileUtil;
import seedu.flashy.commons.util.XmlUtil;
import seedu.flashy.model.CardBank;
import seedu.flashy.testutil.TypicalCardBank;

public class XmlSerializableCardBankTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlSerializableCardBankTest/");
    private static final File TYPICAL_FILE = new File(TEST_DATA_FOLDER + "typicalCardBank.xml");
    private static final File INVALID_TAG_FILE = new File(TEST_DATA_FOLDER + "invalidTagCardBank.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalFile_success() throws Exception {
        XmlSerializableCardBank dataFromFile = XmlUtil.getDataFromFile(TYPICAL_FILE,
                XmlSerializableCardBank.class);
        CardBank cardBankFromFile = dataFromFile.toModelType();
        CardBank typicalCardBank = TypicalCardBank.getTypicalCardBank();
        assertEquals(cardBankFromFile, typicalCardBank);
    }

    @Test
    public void toModelType_invalidFile_throwsIllegalValueException() throws Exception {
        XmlSerializableCardBank dataFromFile = XmlUtil.getDataFromFile(INVALID_TAG_FILE,
                XmlSerializableCardBank.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
