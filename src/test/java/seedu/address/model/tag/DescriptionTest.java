package seedu.address.model.tag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null description
        Assert.assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid descriptions
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid descriptions
        assertTrue(Description.isValidDescription("This is valid description"));
        assertTrue(Description.isValidDescription("-")); // one character
        assertTrue(Description.isValidDescription("This is also a long valid description")); // long description
    }
}
