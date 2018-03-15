package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_COMSCI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_COMSCI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ENGLISH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.tag.Tag;

/**
 * A utility class containing a list of {@code Tag} objects to be used in tests.
 */
public class TypicalTags {

    public static final Tag PHYSICS = new TagBuilder().withName("Physics")
            .withDescription("physics physics")
            .build();
    public static final Tag BIOLOGY = new TagBuilder().withName("Biology Midterms")
            .withDescription("biology midterms")
            .build();
    public static final Tag CHEMISTRY = new TagBuilder().withName("Chemistry")
            .withDescription("chemistry chemistry").build();
    public static final Tag ECONOMICS = new TagBuilder().withName("Economics Midterms")
            .withDescription("economics economics").build();
    public static final Tag HISTORY = new TagBuilder().withName("History")
            .withDescription("history history").build();
    public static final Tag MALAY = new TagBuilder().withName("Malay")
            .withDescription("malay malay").build();
    public static final Tag TAMIL = new TagBuilder().withName("Tamil")
            .withDescription("tamil tamil").build();

    // Manually added
    public static final Tag RUSSIAN = new TagBuilder().withName("Russian Midterms")
            .withDescription("russian russian").build();
    public static final Tag BULGARIAN = new TagBuilder().withName("Bulgarian")
            .withDescription("bulgarian bulgarian").build();

    // Manually added - Tag's details found in {@code CommandTestUtil}
    public static final Tag ENGLISH = new TagBuilder().withName(VALID_NAME_ENGLISH)
            .withDescription(VALID_DESCRIPTION_ENGLISH).build();
    public static final Tag COMSCI = new TagBuilder().withName(VALID_NAME_COMSCI)
            .withDescription(VALID_DESCRIPTION_COMSCI)
            .build();

    public static final String KEYWORD_MATCHING_MIDTERMS = "Midterms"; // A keyword that matches MIDTERMS

    private TypicalTags() {} // prevents instantiation

    public static List<Tag> getTypicalTags() {
        return new ArrayList<>(Arrays.asList(PHYSICS, BIOLOGY, CHEMISTRY, ECONOMICS, HISTORY, MALAY, TAMIL));
    }
}
