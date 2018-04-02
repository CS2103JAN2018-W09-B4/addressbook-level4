package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.cardtag.CardTag;
import seedu.address.testutil.TypicalCardTag;

//@@author jethrokuan
public class XmlAdaptedCardTagTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlAdaptedCardTagTest/");
    private static final File TYPICAL_FILE = new File(TEST_DATA_FOLDER + "typicalCardTag.xml");
    private static final File INVALID_CARD_ID_FILE = new File(TEST_DATA_FOLDER + "invalidCardTag_missingCardId.xml");
    private static final File INVALID_CARDS_FILE = new File(TEST_DATA_FOLDER + "invalidCardTag_missingCards.xml");
    private static final File INVALID_TAG_ID_FILE = new File(TEST_DATA_FOLDER + "invalidCardTag_missingTagId.xml");
    private static final File INVALID_TAGS_FILE = new File(TEST_DATA_FOLDER + "invalidCardTag_missingTags.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalFile_success() throws Exception {
        XmlAdaptedCardTag dataFromFile = XmlUtil.getDataFromFile(TYPICAL_FILE,
                XmlAdaptedCardTag.class);
        CardTag cardTagFromFile = dataFromFile.toModelType();
        CardTag typicalCardTag = TypicalCardTag.getTypicalCardTag();
        assertEquals(cardTagFromFile, typicalCardTag);
    }

    @Test
    public void toModelType_missingCardId_throwsIllegalValueException() throws Exception {
        XmlAdaptedCardTag dataFromFile = XmlUtil.getDataFromFile(INVALID_CARD_ID_FILE,
                XmlAdaptedCardTag.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_missingCards_throwsIllegalValueException() throws Exception {
        XmlAdaptedCardTag dataFromFile = XmlUtil.getDataFromFile(INVALID_CARDS_FILE,
                XmlAdaptedCardTag.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_missingTagId_throwsIllegalValueException() throws Exception {
        XmlAdaptedCardTag dataFromFile = XmlUtil.getDataFromFile(INVALID_TAG_ID_FILE,
                XmlAdaptedCardTag.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_missingTags_throwsIllegalValueException() throws Exception {
        XmlAdaptedCardTag dataFromFile = XmlUtil.getDataFromFile(INVALID_TAGS_FILE,
                XmlAdaptedCardTag.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
//@@author
