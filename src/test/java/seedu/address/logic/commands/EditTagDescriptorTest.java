package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_COMSCI;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_COMSCI;

import org.junit.Test;

import seedu.address.testutil.EditTagDescriptorBuilder;

public class EditTagDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditTagDescriptor descriptorWithSameValues = new EditCommand.EditTagDescriptor(DESC_ENGLISH);
        assertTrue(DESC_ENGLISH.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ENGLISH.equals(DESC_ENGLISH));

        // null -> returns false
        assertFalse(DESC_ENGLISH.equals(null));

        // different types -> returns false
        assertFalse(DESC_ENGLISH.equals(5));

        // different values -> returns false
        assertFalse(DESC_ENGLISH.equals(DESC_COMSCI));

        // different name -> returns false
        EditCommand.EditTagDescriptor editedEnglish =
                new EditTagDescriptorBuilder(DESC_ENGLISH).withName(VALID_NAME_COMSCI).build();
        assertFalse(DESC_ENGLISH.equals(editedEnglish));

    }
}
