package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedTag.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalTags.BENSON;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Address;
import seedu.address.model.tag.Email;
import seedu.address.model.tag.Name;
import seedu.address.model.tag.Phone;
import seedu.address.testutil.Assert;

public class XmlAdaptedTagTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();

    @Test
    public void toModelType_validTagDetails_returnsTag() throws Exception {
        XmlAdaptedTag tag = new XmlAdaptedTag(BENSON);
        assertEquals(BENSON, tag.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedTag tag =
                new XmlAdaptedTag(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedTag tag = new XmlAdaptedTag(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedTag tag =
                new XmlAdaptedTag(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedTag tag = new XmlAdaptedTag(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedTag tag =
                new XmlAdaptedTag(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedTag tag = new XmlAdaptedTag(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedTag tag =
                new XmlAdaptedTag(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedTag tag = new XmlAdaptedTag(VALID_NAME, VALID_PHONE, VALID_EMAIL, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }
}
