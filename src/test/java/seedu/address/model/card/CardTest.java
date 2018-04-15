package seedu.address.model.card;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_BACK_CS2103T_CARD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONT_CS2103T_CARD;

import java.util.UUID;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CardTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Card(null,
                VALID_FRONT_CS2103T_CARD, VALID_BACK_CS2103T_CARD));
        Assert.assertThrows(NullPointerException.class, () -> new Card(UUID.randomUUID(),
                null, VALID_BACK_CS2103T_CARD));
        Assert.assertThrows(NullPointerException.class, () -> new Card(UUID.randomUUID(),
                VALID_FRONT_CS2103T_CARD, null));
        Assert.assertThrows(NullPointerException.class, () -> new Card(null, VALID_BACK_CS2103T_CARD));
        Assert.assertThrows(NullPointerException.class, () -> new Card(VALID_FRONT_CS2103T_CARD, null));
    }

    @Test
    public void constructor_invalidParam_throwsIllegalArgumentException() {
        String invalidParam = " ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Card(UUID.randomUUID(),
                invalidParam, VALID_BACK_CS2103T_CARD));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Card(UUID.randomUUID(),
                VALID_FRONT_CS2103T_CARD, invalidParam));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Card(invalidParam, VALID_BACK_CS2103T_CARD));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Card(VALID_FRONT_CS2103T_CARD, invalidParam));
    }

    @Test
    public void isValidCard() {
        Assert.assertThrows(NullPointerException.class, () -> Card.isValidCard(null));

        assertFalse(Card.isValidCard(""));
        assertFalse(Card.isValidCard(" "));

        assertTrue(Card.isValidCard("Hello"));
        assertTrue(Card.isValidCard("Hello World"));
    }
}
