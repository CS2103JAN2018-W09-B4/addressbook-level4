package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedTag.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalTags.BIOLOGY_TAG;

import java.util.UUID;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Description;
import seedu.address.model.tag.Name;
import seedu.address.testutil.Assert;

public class XmlAdaptedTagTest {
    private static final String INVALID_ID = "";
    private static final String INVALID_NAME = "M@th";
    private static final String INVALID_DESCRIPTION = " ";

    private static final String VALID_ID = UUID.randomUUID().toString();
    private static final String VALID_NAME = BIOLOGY_TAG.getName().toString();
    private static final String VALID_DESCRIPTION = BIOLOGY_TAG.getDescription().toString();

    @Test
    public void toModelType_validTagDetails_returnsTag() throws Exception {
        XmlAdaptedTag tag = new XmlAdaptedTag(BIOLOGY_TAG);
        assertEquals(BIOLOGY_TAG, tag.toModelType());
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValuetException() {
        XmlAdaptedTag tag =
                new XmlAdaptedTag(INVALID_ID, VALID_NAME, VALID_DESCRIPTION);
        Assert.assertThrows(IllegalArgumentException.class, tag::toModelType);
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        XmlAdaptedTag tag =
                new XmlAdaptedTag(null, VALID_NAME, VALID_DESCRIPTION);
        Assert.assertThrows(IllegalValueException.class, tag::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedTag tag =
                new XmlAdaptedTag(VALID_ID, INVALID_NAME, VALID_DESCRIPTION);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedTag tag = new XmlAdaptedTag(VALID_ID, null, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }



    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedTag tag =
                new XmlAdaptedTag(VALID_ID, VALID_NAME, INVALID_DESCRIPTION);
        String expectedMessage = Description.MESSAGE_DESCRIPTION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedTag tag = new XmlAdaptedTag(VALID_ID, VALID_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }
}
