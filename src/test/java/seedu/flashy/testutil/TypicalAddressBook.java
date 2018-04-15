package seedu.flashy.testutil;

import static seedu.flashy.testutil.TypicalCards.BIOLOGY_CARD;
import static seedu.flashy.testutil.TypicalCards.CHEMISTRY_CARD;
import static seedu.flashy.testutil.TypicalCards.ECONOMICS_CARD;
import static seedu.flashy.testutil.TypicalCards.HISTORY_CARD;
import static seedu.flashy.testutil.TypicalCards.MATHEMATICS_CARD;
import static seedu.flashy.testutil.TypicalCards.PHYSICS_CARD;
import static seedu.flashy.testutil.TypicalCards.PHYSICS_CARD_2;
import static seedu.flashy.testutil.TypicalCards.getTypicalCards;
import static seedu.flashy.testutil.TypicalFillBlanksCards.ECONOMICS_FILLBLANKS_CARD;
import static seedu.flashy.testutil.TypicalFillBlanksCards.HISTORY_FILLBLANKS_CARD;
import static seedu.flashy.testutil.TypicalFillBlanksCards.MATHEMATICS_FILLBLANKS_CARD;
import static seedu.flashy.testutil.TypicalFillBlanksCards.PHYSICS_FILLBLANKS_CARD;
import static seedu.flashy.testutil.TypicalFillBlanksCards.getTypicalFillBlanksCards;
import static seedu.flashy.testutil.TypicalMcqCards.ENGLISH_MCQ_CARD;
import static seedu.flashy.testutil.TypicalMcqCards.HISTORY_MCQ_CARD;
import static seedu.flashy.testutil.TypicalMcqCards.MATHEMATICS_MCQ_CARD;
import static seedu.flashy.testutil.TypicalMcqCards.PHYSICS_MCQ_CARD;
import static seedu.flashy.testutil.TypicalMcqCards.getTypicalMcqCards;
import static seedu.flashy.testutil.TypicalTags.BIOLOGY_TAG;
import static seedu.flashy.testutil.TypicalTags.CHEMISTRY_TAG;
import static seedu.flashy.testutil.TypicalTags.ECONOMICS_TAG;
import static seedu.flashy.testutil.TypicalTags.ENGLISH_TAG;
import static seedu.flashy.testutil.TypicalTags.HISTORY_TAG;
import static seedu.flashy.testutil.TypicalTags.MATHEMATICS_TAG;
import static seedu.flashy.testutil.TypicalTags.PHYSICS_TAG;

import seedu.flashy.model.AddressBook;
import seedu.flashy.model.card.Card;
import seedu.flashy.model.card.FillBlanksCard;
import seedu.flashy.model.card.McqCard;
import seedu.flashy.model.card.exceptions.DuplicateCardException;
import seedu.flashy.model.cardtag.DuplicateEdgeException;
import seedu.flashy.model.tag.Tag;

/**
 * Class that creates a typical flashy book.
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
            addressBook.addEdge(BIOLOGY_CARD, BIOLOGY_TAG);
            addressBook.addEdge(CHEMISTRY_CARD, CHEMISTRY_TAG);
            addressBook.addEdge(ECONOMICS_CARD, ECONOMICS_TAG);
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
