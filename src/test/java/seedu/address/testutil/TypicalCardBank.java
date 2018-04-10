package seedu.address.testutil;

import static seedu.address.testutil.TypicalCards.BIOLOGY_CARD;
import static seedu.address.testutil.TypicalCards.CHEMISTRY_CARD;
import static seedu.address.testutil.TypicalCards.ECONOMICS_CARD;
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
import static seedu.address.testutil.TypicalTags.BIOLOGY_TAG;
import static seedu.address.testutil.TypicalTags.CHEMISTRY_TAG;
import static seedu.address.testutil.TypicalTags.ECONOMICS_TAG;
import static seedu.address.testutil.TypicalTags.ENGLISH_TAG;
import static seedu.address.testutil.TypicalTags.HISTORY_TAG;
import static seedu.address.testutil.TypicalTags.MATHEMATICS_TAG;
import static seedu.address.testutil.TypicalTags.PHYSICS_TAG;

import seedu.address.model.CardBank;
import seedu.address.model.card.Card;
import seedu.address.model.card.FillBlanksCard;
import seedu.address.model.card.McqCard;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.model.cardtag.DuplicateEdgeException;
import seedu.address.model.tag.Tag;

/**
 * Class that creates a typical address book.
 */
public class TypicalCardBank {

    /**
     * Returns an {@code CardBank} with all stub data.
     */
    public static CardBank getTypicalCardBank() {
        CardBank cardBank = new CardBank();
        for (Tag tag : TypicalTags.getTypicalTags()) {
            cardBank.addTag(tag);
        }

        for (Card card : getTypicalCards()) {
            try {
                cardBank.addCard(card);
            } catch (DuplicateCardException e) {
                throw new AssertionError("not possible");
            }
        }

        try {
            cardBank.addEdge(PHYSICS_CARD, PHYSICS_TAG);
            cardBank.addEdge(PHYSICS_CARD_2, PHYSICS_TAG);
            cardBank.addEdge(BIOLOGY_CARD, BIOLOGY_TAG);
            cardBank.addEdge(CHEMISTRY_CARD, CHEMISTRY_TAG);
            cardBank.addEdge(ECONOMICS_CARD, ECONOMICS_TAG);
            cardBank.addEdge(MATHEMATICS_CARD, MATHEMATICS_TAG);
            cardBank.addEdge(HISTORY_CARD, HISTORY_TAG);
        } catch (DuplicateEdgeException e) {
            throw new AssertionError("not possible");
        }

        return cardBank;
    }

    /**
     * Returns an {@code CardBank} with all stub data (MCQ cards).
     */
    public static CardBank getTypicalCardBankMcqCards() {
        CardBank cardBank = new CardBank();
        for (Tag tag : TypicalTags.getTypicalTags()) {
            cardBank.addTag(tag);
        }

        for (McqCard card : getTypicalMcqCards()) {
            try {
                cardBank.addCard(card);
            } catch (DuplicateCardException e) {
                throw new AssertionError("not possible");
            }
        }

        try {
            cardBank.addEdge(PHYSICS_MCQ_CARD, PHYSICS_TAG);
            cardBank.addEdge(MATHEMATICS_MCQ_CARD, MATHEMATICS_TAG);
            cardBank.addEdge(HISTORY_MCQ_CARD, HISTORY_TAG);
            cardBank.addEdge(ENGLISH_MCQ_CARD, ENGLISH_TAG);
        } catch (DuplicateEdgeException e) {
            throw new AssertionError("not possible");
        }

        return cardBank;
    }

    /**
     * Returns an {@code CardBank} with all stub data (MCQ cards).
     */
    public static CardBank getTypicalCardBankFillBlanksCards() {
        CardBank cardBank = new CardBank();
        for (Tag tag : TypicalTags.getTypicalTags()) {
            cardBank.addTag(tag);
        }

        for (FillBlanksCard card : getTypicalFillBlanksCards()) {
            try {
                cardBank.addCard(card);
            } catch (DuplicateCardException e) {
                throw new AssertionError("not possible");
            }
        }

        try {
            cardBank.addEdge(PHYSICS_FILLBLANKS_CARD, PHYSICS_TAG);
            cardBank.addEdge(MATHEMATICS_FILLBLANKS_CARD, MATHEMATICS_TAG);
            cardBank.addEdge(HISTORY_FILLBLANKS_CARD, HISTORY_TAG);
            cardBank.addEdge(ECONOMICS_FILLBLANKS_CARD, ECONOMICS_TAG);
        } catch (DuplicateEdgeException e) {
            throw new AssertionError("not possible");
        }

        return cardBank;
    }

    public static CardBank getCardBankFromCardArray(Card[] cardArray) {
        CardBank cardBank = new CardBank();
        for (Card card : cardArray) {
            try {
                cardBank.addCard(card);
            } catch (DuplicateCardException dce) {
                throw new AssertionError("not possible");
            }
        }
        return cardBank;
    }
}
