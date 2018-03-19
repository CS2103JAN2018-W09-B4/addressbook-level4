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

    public static final Tag PHYSICS_TAG = new TagBuilder()
            .withId("67d1fc9c-4d3b-46ff-a663-bc76d80c148a")
            .withName("Physics")
            .withDescription("physics physics")
            .build();
    public static final Tag BIOLOGY_TAG = new TagBuilder()
            .withId("f7a33f28-8246-41ba-9347-0455124625c0")
            .withName("Biology Midterms")
            .withDescription("biology midterms")
            .build();
    public static final Tag CHEMISTRY_TAG = new TagBuilder()
            .withId("2c941c30-fde8-4572-ab41-9f29270a4245")
            .withName("Chemistry")
            .withDescription("chemistry chemistry").build();
    public static final Tag ECONOMICS_TAG = new TagBuilder()
            .withId("b7a91de5-32c9-476e-a72f-4c9fa963d4b4")
            .withName("Economics Midterms")
            .withDescription("economics economics").build();
    public static final Tag HISTORY_TAG = new TagBuilder()
            .withId("49af579d-3990-43e1-b11f-ed7ea0630434")
            .withName("History")
            .withDescription("history history").build();
    public static final Tag MALAY_TAG = new TagBuilder()
            .withId("437f5cba-8023-47d3-8269-7d23b7a7338b")
            .withName("Malay")
            .withDescription("malay malay").build();
    public static final Tag MATHEMATICS_TAG = new TagBuilder()
            .withId("e08641c4-fc6e-4426-bf3f-72fc63d67fe6")
            .withName("Mathematics")
            .withDescription("math math").build();

    // Manually added
    public static final Tag RUSSIAN = new TagBuilder()
            .withName("Russian Midterms")
            .withDescription("russian russian").build();
    public static final Tag BULGARIAN = new TagBuilder()
            .withName("Bulgarian")
            .withDescription("bulgarian bulgarian").build();

    // Manually added - Tag's details found in {@code CommandTestUtil}
    public static final Tag ENGLISH_TAG = new TagBuilder()
            .withId("38deca7c-b5cb-4b1a-8035-54e7f5908565")
            .withName(VALID_NAME_ENGLISH)
            .withDescription(VALID_DESCRIPTION_ENGLISH).build();
    public static final Tag COMSCI_TAG = new TagBuilder()
            .withId("32dc0d97-5ef5-4182-b2e9-3807688f9ee5")
            .withName(VALID_NAME_COMSCI)
            .withDescription(VALID_DESCRIPTION_COMSCI)
            .build();

    public static final String KEYWORD_MATCHING_MIDTERMS = "Midterms"; // A keyword that matches MIDTERMS

    private TypicalTags() {} // prevents instantiation

    public static List<Tag> getTypicalTags() {
        return new ArrayList<>(Arrays.asList(PHYSICS_TAG, BIOLOGY_TAG,
                CHEMISTRY_TAG, ECONOMICS_TAG, HISTORY_TAG, MALAY_TAG, MATHEMATICS_TAG));
    }
}
