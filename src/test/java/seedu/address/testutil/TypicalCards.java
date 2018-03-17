package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_BACK_CARD_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BACK_CARD_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONT_CARD_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONT_CARD_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.card.Card;

/**
 * A utility class containing a list of {@code Tag} objects to be used in tests.
 */
public class TypicalCards {

    public static final Card MATHEMATICS_CARD = new CardBuilder().withFront("What is 1 + 1?")
            .withBack("2").build();
    public static final Card CHEMISTRY_CARD = new CardBuilder().withFront("Name two types of bonding between atoms")
            .withBack("Covalent and Ionic bonding").build();
    public static final Card COMSCI_CARD = new CardBuilder().withFront("What is Java?")
            .withBack("A programming language").build();
    public static final Card GEOGRAPHY_CARD = new CardBuilder().withFront("Which continent is Singapore in?")
            .withBack("Asia").build();
    public static final Card HISTORY_CARD = new CardBuilder().withFront("When is Singapore's National Day?")
            .withBack("9th August 1965").build();
    public static final Card ECONOMICS_CARD = new CardBuilder().withFront("What does GDP stand for?")
            .withBack("Gross Domestic Product").build();
    public static final Card LITERATURE_CARD = new CardBuilder().withFront("Who wrote To Kill A Mockingbird?")
            .withBack("Harper Lee").build();

    // Manually added
    public static final Card PHYSICS_CARD = new CardBuilder().withFront("Why do things fall?")
            .withBack("Gravity").build();
    public static final Card ENGLISH_CARD = new CardBuilder().withFront("What are action words?")
            .withBack("Verbs").build();

    // Manually added - Card's details found in {@code CommandTestUtil}
    public static final Card CS2103T_CARD = new CardBuilder().withFront(VALID_FRONT_CARD_1).withBack(VALID_BACK_CARD_1)
            .build();
    public static final Card CS2101_CARD = new CardBuilder().withFront(VALID_FRONT_CARD_2).withBack(VALID_BACK_CARD_2)
            .build();

    private TypicalCards() {} // prevents instantiation

    public static List<Card> getTypicalCards() {
        return new ArrayList<>(Arrays.asList(MATHEMATICS_CARD, CHEMISTRY_CARD, COMSCI_CARD, GEOGRAPHY_CARD,
                HISTORY_CARD, ECONOMICS_CARD, LITERATURE_CARD, PHYSICS_CARD, ENGLISH_CARD));
    }
}
