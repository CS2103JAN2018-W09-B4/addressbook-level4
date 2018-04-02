package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalCards.MATHEMATICS_CARD;

import java.util.UUID;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.Assert;

//@@author shawnclq
public class XmlAdaptedCardTest {

    private static final String INVALID_FRONT = "";
    private static final String INVALID_BACK = "";
    private static final String INVALID_ID = "";

    private static final String VALID_FRONT = "what is 1+1?";
    private static final String VALID_BACK = "2";
    private static final String VALID_ID = UUID.randomUUID().toString();

    @Test
    public void toModelType_validCardDetails_returnsCard() throws Exception {
        XmlAdaptedCard card = new XmlAdaptedCard(MATHEMATICS_CARD);
        assertEquals(card.toModelType(), MATHEMATICS_CARD);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalArgumentException() {
        XmlAdaptedCard card = new XmlAdaptedCard(INVALID_ID, VALID_FRONT, VALID_BACK);
        Assert.assertThrows(IllegalArgumentException.class, card::toModelType);
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        XmlAdaptedCard card = new XmlAdaptedCard(null, VALID_FRONT, VALID_BACK);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }

    @Test
    public void toModelType_invalidFront_throwsIllegalValueException() {
        XmlAdaptedCard card = new XmlAdaptedCard(VALID_ID, INVALID_FRONT, VALID_BACK);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }

    @Test
    public void toModelType_nullFront_throwsIllegalValueException() {
        XmlAdaptedCard card = new XmlAdaptedCard(VALID_ID, null, VALID_BACK);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }

    @Test
    public void toModelType_invalidBack_throwsIllegalValueException() {
        XmlAdaptedCard card = new XmlAdaptedCard(VALID_ID, VALID_FRONT, INVALID_BACK);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }

    @Test
    public void toModelType_nullBack_throwsIllegalValueException() {
        XmlAdaptedCard card = new XmlAdaptedCard(VALID_ID, VALID_FRONT, null);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }
}

