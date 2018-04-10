package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.CardBank;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlSerializableCardBank;
import seedu.address.testutil.CardBankBuilder;
import seedu.address.testutil.TagBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validCardBank.xml");
    private static final File MISSING_TAG_FIELD_FILE = new File(TEST_DATA_FOLDER + "missingTagField.xml");
    private static final File INVALID_TAG_FIELD_FILE = new File(TEST_DATA_FOLDER + "invalidTagField.xml");
    private static final File VALID_TAG_FILE = new File(TEST_DATA_FOLDER + "validTag.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempCardBank.xml"));


    private static final String INVALID_NAME = "Phys!cs";
    private static final String VALID_ID = "ad5abcb8-801f-42b5-85ec-4d7d72afe9b3";
    private static final String VALID_NAME = "Physics";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, CardBank.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, CardBank.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, CardBank.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        CardBank dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableCardBank.class).toModelType();
        assertEquals(7, dataFromFile.getTagList().size());
    }

    @Test
    public void xmlAdaptedTagFromFile_fileWithMissingTagField_validResult() throws Exception {
        XmlAdaptedTag actualTag = XmlUtil.getDataFromFile(
                MISSING_TAG_FIELD_FILE, XmlAdaptedTagWithRootElement.class);
        XmlAdaptedTag expectedTag = new XmlAdaptedTag(
                VALID_ID, null);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    public void xmlAdaptedTagFromFile_fileWithInvalidTagField_validResult() throws Exception {
        XmlAdaptedTag actualTag = XmlUtil.getDataFromFile(
                INVALID_TAG_FIELD_FILE, XmlAdaptedTagWithRootElement.class);
        XmlAdaptedTag expectedTag = new XmlAdaptedTag(
                VALID_ID, INVALID_NAME);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    public void xmlAdaptedTagFromFile_fileWithValidTag_validResult() throws Exception {
        XmlAdaptedTag actualTag = XmlUtil.getDataFromFile(
                VALID_TAG_FILE, XmlAdaptedTagWithRootElement.class);
        XmlAdaptedTag expectedTag = new XmlAdaptedTag(
                VALID_ID, VALID_NAME);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new CardBank());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new CardBank());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableCardBank dataToWrite = new XmlSerializableCardBank(new CardBank());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableCardBank dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableCardBank.class);
        assertEquals(dataToWrite, dataFromFile);

        CardBankBuilder builder = new CardBankBuilder(new CardBank());
        dataToWrite = new XmlSerializableCardBank(
                builder.withTag(new TagBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableCardBank.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedTag}
     * objects.
     */
    @XmlRootElement(name = "tag")
    private static class XmlAdaptedTagWithRootElement extends XmlAdaptedTag {
    }
}
