package seedu.address.model.card;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILLBLANKS_BACK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILLBLANKS_FRONT;

import java.util.UUID;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@author shawnclq
public class FillBlanksCardTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new FillBlanksCard(null,
                VALID_FILLBLANKS_FRONT, VALID_FILLBLANKS_BACK));
        Assert.assertThrows(NullPointerException.class, () -> new FillBlanksCard(UUID.randomUUID(),
                null, VALID_FILLBLANKS_BACK));
        Assert.assertThrows(NullPointerException.class, () -> new FillBlanksCard(UUID.randomUUID(),
                VALID_FILLBLANKS_FRONT, null));
        Assert.assertThrows(NullPointerException.class, () ->
                new FillBlanksCard(null, VALID_FILLBLANKS_BACK));
        Assert.assertThrows(NullPointerException.class, () ->
                new FillBlanksCard(VALID_FILLBLANKS_FRONT, null));
    }

    @Test
    public void constructor_invalidParam_throwsIllegalArgumentException() {
        String invalidParam = " ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new FillBlanksCard(UUID.randomUUID(),
                invalidParam, VALID_FILLBLANKS_BACK));
        Assert.assertThrows(IllegalArgumentException.class, () -> new FillBlanksCard(UUID.randomUUID(),
                VALID_FILLBLANKS_FRONT, invalidParam));
        Assert.assertThrows(IllegalArgumentException.class, () -> new FillBlanksCard(invalidParam,
                VALID_FILLBLANKS_BACK));
        Assert.assertThrows(IllegalArgumentException.class, () -> new FillBlanksCard(VALID_FILLBLANKS_FRONT,
                invalidParam));
    }

    @Test
    public void isValidMcqCard() {
        Assert.assertThrows(NullPointerException.class, () -> FillBlanksCard.isValidFillBlanksCard(
                null, VALID_FILLBLANKS_BACK));
        Assert.assertThrows(NullPointerException.class, () -> FillBlanksCard.isValidFillBlanksCard(
                VALID_FILLBLANKS_FRONT, null));
        String invalidBack = "Too, many, options";
        assertFalse(FillBlanksCard.isValidFillBlanksCard(VALID_FILLBLANKS_FRONT, invalidBack));

        assertTrue(FillBlanksCard.isValidFillBlanksCard(VALID_FILLBLANKS_FRONT, VALID_FILLBLANKS_BACK));
    }

}
