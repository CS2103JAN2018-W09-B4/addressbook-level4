package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TAG;
import static seedu.address.testutil.TypicalTags.getTypicalTags;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysTag;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.TagCardHandle;
import guitests.guihandles.TagListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToTagRequestEvent;
import seedu.address.model.tag.Tag;

public class TagListPanelTest extends GuiUnitTest {
    private static final ObservableList<Tag> TYPICAL_TAGS =
            FXCollections.observableList(getTypicalTags());

    private static final JumpToTagRequestEvent JUMP_TO_SECOND_EVENT = new JumpToTagRequestEvent(INDEX_SECOND_TAG);

    private TagListPanelHandle tagListPanelHandle;

    @Before
    public void setUp() {
        TagListPanel tagListPanel = new TagListPanel(TYPICAL_TAGS);
        uiPartRule.setUiPart(tagListPanel);

        tagListPanelHandle = new TagListPanelHandle(getChildNode(tagListPanel.getRoot(),
                TagListPanelHandle.TAG_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_TAGS.size(); i++) {
            tagListPanelHandle.navigateToCard(TYPICAL_TAGS.get(i));
            Tag expectedTag = TYPICAL_TAGS.get(i);
            TagCardHandle actualCard = tagListPanelHandle.getTagCardHandle(i);

            assertCardDisplaysTag(expectedTag, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        TagCardHandle expectedCard = tagListPanelHandle.getTagCardHandle(INDEX_SECOND_TAG.getZeroBased());
        TagCardHandle selectedCard = tagListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
