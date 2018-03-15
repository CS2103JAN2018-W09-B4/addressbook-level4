package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.tag.Tag;

/**
 * A utility class containing a list of {@code Tag} objects to be used in tests.
 */
public class TypicalTags {

    public static final Tag ALICE = new TagBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .build();
    public static final Tag BENSON = new TagBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .build();
    public static final Tag CARL = new TagBuilder().withName("Carl Kurz")
            .withAddress("wall street").build();
    public static final Tag DANIEL = new TagBuilder().withName("Daniel Meier")
            .withAddress("10th street").build();
    public static final Tag ELLE = new TagBuilder().withName("Elle Meyer")
            .withAddress("michegan ave").build();
    public static final Tag FIONA = new TagBuilder().withName("Fiona Kunz")
            .withAddress("little tokyo").build();
    public static final Tag GEORGE = new TagBuilder().withName("George Best")
            .withAddress("4th street").build();

    // Manually added
    public static final Tag HOON = new TagBuilder().withName("Hoon Meier")
            .withAddress("little india").build();
    public static final Tag IDA = new TagBuilder().withName("Ida Mueller")
            .withAddress("chicago ave").build();

    // Manually added - Tag's details found in {@code CommandTestUtil}
    public static final Tag AMY = new TagBuilder().withName(VALID_NAME_AMY)
            .withAddress(VALID_ADDRESS_AMY).build();
    public static final Tag BOB = new TagBuilder().withName(VALID_NAME_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTags() {} // prevents instantiation

    public static List<Tag> getTypicalTags() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
