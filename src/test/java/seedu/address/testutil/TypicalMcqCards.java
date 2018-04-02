package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_BACK_CS2101_CARD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BACK_CS2103T_CARD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONT_CS2101_CARD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONT_CS2103T_CARD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.card.McqCard;

/**
 * A utility class containing a list of {@code McqCard} objects to be used in tests.
 */
public class TypicalMcqCards {

    public static final McqCard MATHEMATICS_CARD = (McqCard) new McqCardBuilder()
            .resetOptions()
            .addOption("1").addOption("2").addOption("3")
            .withId("3647849-d900-4f0e-8573-e3c9ab40864054")
            .withFront("What is 1 + 1?")
            .withBack("2").build();
    public static final McqCard CHEMISTRY_CARD = (McqCard) new McqCardBuilder()
            .resetOptions()
            .addOption("Covalent bonding").addOption("Ionic bonding")
            .withId("f581860a-d5db-4925-aeab-1f1e442457f3")
            .withFront("What is the bonding between non-metals")
            .withBack("1").build();
    public static final McqCard HISTORY_CARD = (McqCard) new McqCardBuilder()
            .resetOptions()
            .addOption("1944").addOption("1946").addOption("1945")
            .withId("3f92bd2c-affc-499c-a9a0-28b46fd61791")
            .withFront("When did World War II end?")
            .withBack("3").build();
    public static final McqCard GEOGRAPHY_CARD = (McqCard) new McqCardBuilder()
            .resetOptions()
            .addOption("Asia").addOption("Africa").addOption("South America").addOption("North America")
            .addOption("Europe").addOption("Australia").addOption("Antartica")
            .withId("8c19a1f2-cfa2-4060-b9e3-67bd1ef101e0")
            .withFront("Which continent is Singapore in?")
            .withBack("1").build();

    // Manually added
    public static final McqCard PHYSICS_CARD = (McqCard) new McqCardBuilder()
            .resetOptions()
            .addOption("Air").addOption("Gravity").addOption("Electricity")
            .withId("7d59e0a2-4e64-4540-abdf-ce0fa552edb7")
            .withFront("Why do things fall?")
            .withBack("2").build();
    public static final McqCard ENGLISH_CARD = (McqCard) new McqCardBuilder()
            .resetOptions()
            .addOption("Noun").addOption("Verb").addOption("Adverb").addOption("Adjectives")
            .withId("8f4716c7-f82e-462f-bad1-e59f98b8624e")
            .withFront("What are action words?")
            .withBack("2").build();

    // Manually added - McqCard's details found in {@code CommandTestUtil}
    public static final McqCard CS2103T_CARD = (McqCard) new McqCardBuilder()
            .withFront(VALID_FRONT_CS2103T_CARD)
            .withBack(VALID_BACK_CS2103T_CARD).build();
    public static final McqCard CS2101_CARD = (McqCard) new McqCardBuilder()
            .withFront(VALID_FRONT_CS2101_CARD)
            .withBack(VALID_BACK_CS2101_CARD).build();

    private TypicalMcqCards() {} // prevents instantiation

    public static List<McqCard> getTypicalMcqCards() {
        return new ArrayList<>(Arrays.asList(MATHEMATICS_CARD, CHEMISTRY_CARD, GEOGRAPHY_CARD,
                HISTORY_CARD, PHYSICS_CARD, ENGLISH_CARD));
    }
}
