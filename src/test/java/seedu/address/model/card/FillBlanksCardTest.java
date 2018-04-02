package seedu.address.model.card;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILLBLANK_BACK_SET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILLBLANK_FRONT_SET;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@author shawnclq
public class FillBlanksCardTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new FillBlanksCard(null,
                VALID_FILLBLANK_FRONT_SET, VALID_FILLBLANK_BACK_SET));
        Assert.assertThrows(NullPointerException.class, () -> new FillBlanksCard(UUID.randomUUID(),
                null, VALID_FILLBLANK_BACK_SET));
        Assert.assertThrows(NullPointerException.class, () -> new FillBlanksCard(UUID.randomUUID(),
                VALID_FILLBLANK_FRONT_SET, null));
        Assert.assertThrows(NullPointerException.class, () ->
                new FillBlanksCard(null, VALID_FILLBLANK_BACK_SET));
        Assert.assertThrows(NullPointerException.class, () ->
                new FillBlanksCard(VALID_FILLBLANK_FRONT_SET, null));
    }

    @Test
    public void constructor_invalidParam_throwsIllegalArgumentException() {
        String invalidParam = " ";
        List<String> invalidFrontSet = Arrays.asList(new String[] {invalidParam, "Hello", "World"});
        List<String> invalidBackSet = Arrays.asList(new String[] {invalidParam, "Hello"});
        Assert.assertThrows(IllegalArgumentException.class, () -> new FillBlanksCard(UUID.randomUUID(),
                invalidFrontSet, VALID_FILLBLANK_BACK_SET));
        Assert.assertThrows(IllegalArgumentException.class, () -> new FillBlanksCard(UUID.randomUUID(),
                VALID_FILLBLANK_FRONT_SET, invalidBackSet));
        Assert.assertThrows(IllegalArgumentException.class, () -> new FillBlanksCard(invalidFrontSet,
                VALID_FILLBLANK_BACK_SET));
        Assert.assertThrows(IllegalArgumentException.class, () -> new FillBlanksCard(VALID_FILLBLANK_FRONT_SET,
                invalidBackSet));
    }

    @Test
    public void isValidMcqCard() {
        Assert.assertThrows(NullPointerException.class, () -> FillBlanksCard.isValidFillBlanksCard(
                null, VALID_FILLBLANK_BACK_SET));
        Assert.assertThrows(NullPointerException.class, () -> FillBlanksCard.isValidFillBlanksCard(
                VALID_FILLBLANK_FRONT_SET, null));
        List<String> invalidBack = Arrays.asList(new String[]{"Too", "Many", "Options"});
        assertFalse(FillBlanksCard.isValidFillBlanksCard(VALID_FILLBLANK_FRONT_SET, invalidBack));

        assertTrue(FillBlanksCard.isValidFillBlanksCard(VALID_FILLBLANK_FRONT_SET, VALID_FILLBLANK_BACK_SET));
    }

}
