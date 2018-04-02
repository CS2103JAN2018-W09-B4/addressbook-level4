package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalMcqCards.MATHEMATICS_CARD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.Assert;

//@@author shawnclq
public class XmlAdaptedMcqCardTest {

    private static final String INVALID_FRONT = "";
    private static final String INVALID_BACK = "";
    private static final String INVALID_ID = "";
    private static final List<String> INVALID_OPTIONS = new ArrayList<>();

    private static final String VALID_FRONT = "what is 1+1?";
    private static final String VALID_BACK = "2";
    private static final String VALID_ID = UUID.randomUUID().toString();
    private static String[] optionsArray = new String[]{"1", "2", "3", "4", "5"};
    private static final List<String> VALID_OPTIONS = Arrays.asList(optionsArray);

    @Test
    public void toModelType_validMcqCardDetails_returnsMcqCard() throws Exception {
        XmlAdaptedMcqCard card = new XmlAdaptedMcqCard(MATHEMATICS_CARD);
        assertEquals(card.toModelType(), MATHEMATICS_CARD);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalArgumentException() {
        XmlAdaptedMcqCard card = new XmlAdaptedMcqCard(INVALID_ID, VALID_FRONT, VALID_BACK, VALID_OPTIONS);
        Assert.assertThrows(IllegalArgumentException.class, card::toModelType);
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        XmlAdaptedMcqCard card = new XmlAdaptedMcqCard(null, VALID_FRONT, VALID_BACK, VALID_OPTIONS);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }

    @Test
    public void toModelType_invalidFront_throwsIllegalValueException() {
        XmlAdaptedMcqCard card = new XmlAdaptedMcqCard(VALID_ID, INVALID_FRONT, VALID_BACK, VALID_OPTIONS);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }

    @Test
    public void toModelType_nullFront_throwsIllegalValueException() {
        XmlAdaptedMcqCard card = new XmlAdaptedMcqCard(VALID_ID, null, VALID_BACK, VALID_OPTIONS);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }

    @Test
    public void toModelType_invalidBack_throwsIllegalValueException() {
        XmlAdaptedMcqCard card = new XmlAdaptedMcqCard(VALID_ID, VALID_FRONT, INVALID_BACK, VALID_OPTIONS);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }

    @Test
    public void toModelType_nullBack_throwsIllegalValueException() {
        XmlAdaptedMcqCard card = new XmlAdaptedMcqCard(VALID_ID, VALID_FRONT, null, VALID_OPTIONS);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }

    @Test
    public void toModelType_invalidOptions_throwsIllegalValueException() {
        XmlAdaptedMcqCard card = new XmlAdaptedMcqCard(VALID_ID, VALID_FRONT, VALID_BACK, INVALID_OPTIONS);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }

    @Test
    public void toModelType_nullOptions_throwsIllegalValueException() {
        XmlAdaptedMcqCard card = new XmlAdaptedMcqCard(VALID_ID, VALID_FRONT, VALID_BACK, null);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }
}

