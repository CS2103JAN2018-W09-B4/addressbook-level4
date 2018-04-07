package seedu.address.testutil;

import static seedu.address.testutil.TypicalCards.HISTORY_CARD;
import static seedu.address.testutil.TypicalCards.MATHEMATICS_CARD;
import static seedu.address.testutil.TypicalCards.PHYSICS_CARD;
import static seedu.address.testutil.TypicalCards.PHYSICS_CARD_2;
import static seedu.address.testutil.TypicalCards.getTypicalCards;
import static seedu.address.testutil.TypicalFillBlanksCards.ECONOMICS_FILLBLANKS_CARD;
import static seedu.address.testutil.TypicalFillBlanksCards.HISTORY_FILLBLANKS_CARD;
import static seedu.address.testutil.TypicalFillBlanksCards.MATHEMATICS_FILLBLANKS_CARD;
import static seedu.address.testutil.TypicalFillBlanksCards.PHYSICS_FILLBLANKS_CARD;
import static seedu.address.testutil.TypicalFillBlanksCards.getTypicalFillBlanksCards;
import static seedu.address.testutil.TypicalMcqCards.ENGLISH_MCQ_CARD;
import static seedu.address.testutil.TypicalMcqCards.HISTORY_MCQ_CARD;
import static seedu.address.testutil.TypicalMcqCards.MATHEMATICS_MCQ_CARD;
import static seedu.address.testutil.TypicalMcqCards.PHYSICS_MCQ_CARD;
import static seedu.address.testutil.TypicalMcqCards.getTypicalMcqCards;
import static seedu.address.testutil.TypicalTags.ECONOMICS_TAG;
import static seedu.address.testutil.TypicalTags.ENGLISH_TAG;
import static seedu.address.testutil.TypicalTags.HISTORY_TAG;
import static seedu.address.testutil.TypicalTags.MATHEMATICS_TAG;
import static seedu.address.testutil.TypicalTags.PHYSICS_TAG;

import seedu.address.model.AddressBook;
import seedu.address.model.card.Card;
import seedu.address.model.card.FillBlanksCard;
import seedu.address.model.card.McqCard;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.model.cardtag.DuplicateEdgeException;
import seedu.address.model.tag.Tag;

/**
 * Class that creates a typical address book.
 */
public class TypicalAddressBook {

    /**
     * Returns an {@code AddressBook} with all stub data.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook addressBook = new AddressBook();
        for (Tag tag : TypicalTags.getTypicalTags()) {
            addressBook.addTag(tag);
        }

        for (Card card : getTypicalCards()) {
            try {
                addressBook.addCard(card);
            } catch (DuplicateCardException e) {
                throw new AssertionError("not possible");
            }
        }

        try {
            addressBook.addEdge(PHYSICS_CARD, PHYSICS_TAG);
            addressBook.addEdge(PHYSICS_CARD_2, PHYSICS_TAG);
            addressBook.addEdge(MATHEMATICS_CARD, MATHEMATICS_TAG);
            addressBook.addEdge(HISTORY_CARD, HISTORY_TAG);
        } catch (DuplicateEdgeException e) {
            throw new AssertionError("not possible");
        }

        return addressBook;
    }

    /**
     * Returns an {@code AddressBook} with all stub data (MCQ cards).
     */
    public static AddressBook getTypicalAddressBookMcqCards() {
        AddressBook addressBook = new AddressBook();
        for (Tag tag : TypicalTags.getTypicalTags()) {
            addressBook.addTag(tag);
        }

        for (McqCard card : getTypicalMcqCards()) {
            try {
                addressBook.addCard(card);
            } catch (DuplicateCardException e) {
                throw new AssertionError("not possible");
            }
        }

        try {
            addressBook.addEdge(PHYSICS_MCQ_CARD, PHYSICS_TAG);
            addressBook.addEdge(MATHEMATICS_MCQ_CARD, MATHEMATICS_TAG);
            addressBook.addEdge(HISTORY_MCQ_CARD, HISTORY_TAG);
            addressBook.addEdge(ENGLISH_MCQ_CARD, ENGLISH_TAG);
        } catch (DuplicateEdgeException e) {
            throw new AssertionError("not possible");
        }

        return addressBook;
    }

    /**
     * Returns an {@code AddressBook} with all stub data (MCQ cards).
     */
    public static AddressBook getTypicalAddressBookFillBlanksCards() {
        AddressBook addressBook = new AddressBook();
        for (Tag tag : TypicalTags.getTypicalTags()) {
            addressBook.addTag(tag);
        }

        for (FillBlanksCard card : getTypicalFillBlanksCards()) {
            try {
                addressBook.addCard(card);
            } catch (DuplicateCardException e) {
                throw new AssertionError("not possible");
            }
        }

        try {
            addressBook.addEdge(PHYSICS_FILLBLANKS_CARD, PHYSICS_TAG);
            addressBook.addEdge(MATHEMATICS_FILLBLANKS_CARD, MATHEMATICS_TAG);
            addressBook.addEdge(HISTORY_FILLBLANKS_CARD, HISTORY_TAG);
            addressBook.addEdge(ECONOMICS_FILLBLANKS_CARD, ECONOMICS_TAG);
        } catch (DuplicateEdgeException e) {
            throw new AssertionError("not possible");
        }

        return addressBook;
    }

    public static AddressBook getAddressBookFromCardArray(Card[] cardArray) {
        AddressBook addressBook = new AddressBook();
        for (Card card : cardArray) {
            try {
                addressBook.addCard(card);
            } catch (DuplicateCardException dce) {
                throw new AssertionError("not possible");
            }
        }
        return addressBook;
    }
}
