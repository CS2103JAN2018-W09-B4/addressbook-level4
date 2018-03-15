package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_BACK_CARD_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BACK_CARD_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONT_CARD_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONT_CARD_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.DuplicateCardException;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalCards {

    public static final Card CARD_3 = new CardBuilder().withFront("Who is the current POTUS?").withBack("Donald Trump")
            .build();
    public static final Card CARD_4 = new CardBuilder().withFront("Who was the previous POTUS?")
            .withBack("Barack Obama").build();
    public static final Card CARD_5 = new CardBuilder().withFront("Who is Singapore's current Prime Minister?")
            .withBack("Lee Hsien Loong").build();
    public static final Card CARD_6 = new CardBuilder().withFront("Who was Singapore's first Prime Minister?")
            .withBack("Lee Kuan Yew").build();
    public static final Card CARD_7 = new CardBuilder().withFront("What does NUS stand for?")
            .withBack("National University of Singapore").build();
    public static final Card CARD_8 = new CardBuilder().withFront("What does NTU stand for?")
            .withBack("Nanyang Technological University").build();
    public static final Card CARD_9 = new CardBuilder().withFront("What does SMU stand for?")
            .withBack("Singapore Management University").build();

    // Manually added
    public static final Card CARD_10 = new CardBuilder().withFront("What does SUTD stand for?")
            .withBack("Singapore University of Technology and Design").build();
    public static final Card CARD_11 = new CardBuilder().withFront("What does SUSS stand for?")
            .withBack("Singapore University of Social Sciences").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Card CARD_1 = new CardBuilder().withFront(VALID_FRONT_CARD_1).withBack(VALID_BACK_CARD_1)
            .build();
    public static final Card CARD_2 = new CardBuilder().withFront(VALID_FRONT_CARD_2).withBack(VALID_BACK_CARD_2)
            .build();

    private TypicalCards() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBookWithCards() {
        AddressBook ab = new AddressBook();
        for (Card card : getTypicalCards()) {
            try {
                ab.addCard(card);
            } catch (DuplicateCardException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Card> getTypicalCards() {
        return new ArrayList<>(Arrays.asList(CARD_3, CARD_4, CARD_5, CARD_6, CARD_7, CARD_8, CARD_9, CARD_10, CARD_11));
    }
}
