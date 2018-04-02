package seedu.address.model.card;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_MCQ_BACK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MCQ_FRONT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MCQ_OPTION_SET;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author shawnclq
public class McqCardTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new McqCard(null,
                VALID_MCQ_FRONT, VALID_MCQ_BACK, VALID_MCQ_OPTION_SET));
        Assert.assertThrows(NullPointerException.class, () -> new McqCard(UUID.randomUUID(),
                null, VALID_MCQ_BACK, VALID_MCQ_OPTION_SET));
        Assert.assertThrows(NullPointerException.class, () -> new McqCard(UUID.randomUUID(),
                VALID_MCQ_FRONT, null, VALID_MCQ_OPTION_SET));
        Assert.assertThrows(NullPointerException.class, () -> new McqCard(UUID.randomUUID(),
                VALID_MCQ_FRONT, VALID_MCQ_BACK, null));
        Assert.assertThrows(NullPointerException.class, () -> new McqCard(null, VALID_MCQ_BACK,
                VALID_MCQ_OPTION_SET));
        Assert.assertThrows(NullPointerException.class, () -> new McqCard(VALID_MCQ_FRONT, null,
                VALID_MCQ_OPTION_SET));
        Assert.assertThrows(NullPointerException.class, () -> new McqCard(VALID_MCQ_FRONT, null,
                VALID_MCQ_OPTION_SET));
        Assert.assertThrows(NullPointerException.class, () -> new McqCard(VALID_MCQ_FRONT, VALID_MCQ_BACK,
                null));
    }

    @Test
    public void constructor_invalidParam_throwsIllegalArgumentException() {
        String invalidParam = " ";
        List<String> invalidOptionSet = Arrays.asList(new String[] {"", "Hello", "World"});
        Assert.assertThrows(IllegalArgumentException.class, () -> new McqCard(UUID.randomUUID(),
                invalidParam, VALID_MCQ_BACK, VALID_MCQ_OPTION_SET));
        Assert.assertThrows(IllegalArgumentException.class, () -> new McqCard(UUID.randomUUID(),
                VALID_MCQ_FRONT, invalidParam, VALID_MCQ_OPTION_SET));
        Assert.assertThrows(IllegalArgumentException.class, () -> new McqCard(UUID.randomUUID(),
                VALID_MCQ_FRONT, VALID_MCQ_BACK, invalidOptionSet));
        Assert.assertThrows(IllegalArgumentException.class, () -> new McqCard(invalidParam, VALID_MCQ_BACK,
                VALID_MCQ_OPTION_SET));
        Assert.assertThrows(IllegalArgumentException.class, () -> new McqCard(VALID_MCQ_FRONT, invalidParam,
                VALID_MCQ_OPTION_SET));
        Assert.assertThrows(IllegalArgumentException.class, () -> new McqCard(VALID_MCQ_FRONT, VALID_MCQ_BACK,
                invalidOptionSet));
    }

    @Test
    public void isValidMcqCard() {
        Assert.assertThrows(NullPointerException.class, () -> McqCard.isValidMcqCard(null, VALID_MCQ_OPTION_SET));
        Assert.assertThrows(NullPointerException.class, () -> McqCard.isValidMcqCard(VALID_MCQ_BACK, null));

        assertFalse(McqCard.isValidMcqCard("Hello", VALID_MCQ_OPTION_SET));
        assertFalse(McqCard.isValidMcqCard("0", VALID_MCQ_OPTION_SET));
        assertFalse(McqCard.isValidMcqCard("-1", VALID_MCQ_OPTION_SET));
        assertFalse(McqCard.isValidMcqCard("4", VALID_MCQ_OPTION_SET));

        assertTrue(McqCard.isValidMcqCard("1", VALID_MCQ_OPTION_SET));
        assertTrue(McqCard.isValidMcqCard("2", VALID_MCQ_OPTION_SET));
        assertTrue(McqCard.isValidMcqCard("3", VALID_MCQ_OPTION_SET));
    }
}
//@@author
