package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysTag;

import org.junit.Test;

import guitests.guihandles.TagCardHandle;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TagBuilder;

public class TagCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Tag tag = new TagBuilder().build();
        TagCard tagCard = new TagCard(tag, 2);
        uiPartRule.setUiPart(tagCard);
        assertCardDisplay(tagCard, tag, 2);
    }

    @Test
    public void equals() {
        Tag tag = new TagBuilder().build();
        TagCard tagCard = new TagCard(tag, 0);

        // same tag, same index -> returns true
        TagCard copy = new TagCard(tag, 0);
        assertTrue(tagCard.equals(copy));

        // same object -> returns true
        assertTrue(tagCard.equals(tagCard));

        // null -> returns false
        assertFalse(tagCard.equals(null));

        // different types -> returns false
        assertFalse(tagCard.equals(0));

        // different tag, same index -> returns false
        Tag differentTag = new TagBuilder().withName("differentName").build();
        assertFalse(tagCard.equals(new TagCard(differentTag, 0)));

        // same tag, different index -> returns false
        assertFalse(tagCard.equals(new TagCard(tag, 1)));
    }

    /**
     * Asserts that {@code tagCard} displays the details of {@code expectedTag} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(TagCard tagCard, Tag expectedTag, int expectedId) {
        guiRobot.pauseForHuman();

        TagCardHandle tagCardHandle = new TagCardHandle(tagCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", tagCardHandle.getId());

        // verify tag details are displayed correctly
        assertCardDisplaysTag(expectedTag, tagCardHandle);
    }
}
