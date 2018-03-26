package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;

import guitests.guihandles.CardCardHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TagCardHandle;
import guitests.guihandles.TagListPanelHandle;
import seedu.address.model.card.Card;
import seedu.address.model.tag.Tag;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(TagCardHandle expectedCard, TagCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedTag}.
     */
    public static void assertCardDisplaysTag(Tag expectedTag, TagCardHandle actualCard) {
        assertEquals(expectedTag.getName().fullName, actualCard.getName());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedCard}
     * @param expectedCard
     * @param actualCard
     */
    public static void assertCardDisplaysCard(Card expectedCard, CardCardHandle actualCard) {
        assertEquals(expectedCard.getBack(), actualCard.getBack());
        assertEquals(expectedCard.getFront(), actualCard.getFront());
    }
    /**
     * Asserts that the list in {@code tagListPanelHandle} displays the details of {@code tags} correctly and
     * in the correct order.
     */
    public static void assertListMatching(TagListPanelHandle tagListPanelHandle, Tag... tags) {
        for (int i = 0; i < tags.length; i++) {
            assertCardDisplaysTag(tags[i], tagListPanelHandle.getTagCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code tagListPanelHandle} displays the details of {@code tags} correctly and
     * in the correct order.
     */
    public static void assertListMatching(TagListPanelHandle tagListPanelHandle, List<Tag> tags) {
        assertListMatching(tagListPanelHandle, tags.toArray(new Tag[0]));
    }

    /**
     * Asserts the size of the list in {@code tagListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(TagListPanelHandle tagListPanelHandle, int size) {
        int numberOfPeople = tagListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
