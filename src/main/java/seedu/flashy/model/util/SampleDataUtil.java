package seedu.flashy.model.util;

import java.util.Arrays;

import seedu.flashy.model.CardBank;
import seedu.flashy.model.ReadOnlyCardBank;
import seedu.flashy.model.card.Card;
import seedu.flashy.model.card.FillBlanksCard;
import seedu.flashy.model.card.McqCard;
import seedu.flashy.model.card.exceptions.DuplicateCardException;
import seedu.flashy.model.cardtag.DuplicateEdgeException;
import seedu.flashy.model.tag.Name;
import seedu.flashy.model.tag.Tag;

/**
 * Contains utility methods for populating {@code CardBank} with sample data.
 */
public class SampleDataUtil {

    private static final Tag BIOLOGY_TAG = new Tag(new Name("Biology"));
    private static final Tag CHEMISTRY_TAG = new Tag(new Name("Chemistry"));
    private static final Tag MATHEMATICS_TAG = new Tag(new Name("Mathematics"));
    private static final Tag PHYSICS_TAG = new Tag(new Name("Physics"));
    private static final Tag ENGLISH_TAG = new Tag(new Name("English"));

    private static final Card BIOLOGY_CARD = new Card("What is a neuron?",
            "a specialized cell transmitting nerve impulses; a nerve cell");
    private static final Card CHEMISTRY_MCQ_CARD = new McqCard("What is the bonding between non-metals",
            "2", Arrays.asList("Covalent bonding", "Ionic bonding"));
    private static final Card MATHEMATICS_CARD = new Card("What is 1 + 1?", "2");
    private static final FillBlanksCard PHYSICS_FILLBLANKS_CARD = new FillBlanksCard(
            "When an electron goes from _ energy to a _ energy level, it emits a photon", "higher, lower");
    private static final McqCard ENGLISH_CARD = new McqCard("What are action words?",
            "2", Arrays.asList("Noun", "Verb", "Pronoun"));

    public static Tag[] getSampleTags() {
        return new Tag[] { BIOLOGY_TAG, CHEMISTRY_TAG, MATHEMATICS_TAG, PHYSICS_TAG, ENGLISH_TAG };
    }

    public static Card[] getSampleCards() {
        return new Card[] { BIOLOGY_CARD, CHEMISTRY_MCQ_CARD, MATHEMATICS_CARD, PHYSICS_FILLBLANKS_CARD, ENGLISH_CARD };
    }

    public static ReadOnlyCardBank getSampleCardBank() {
        CardBank sampleAb = new CardBank();
        for (Tag sampleTag : getSampleTags()) {
            sampleAb.addTag(sampleTag);
        }
        for (Card sampleCard: getSampleCards()) {
            try {
                sampleAb.addCard(sampleCard);
            } catch (DuplicateCardException dce) {
                throw new AssertionError("not possible");
            }
        }
        try {
            sampleAb.addEdge(PHYSICS_FILLBLANKS_CARD, PHYSICS_TAG);
            sampleAb.addEdge(BIOLOGY_CARD, BIOLOGY_TAG);
            sampleAb.addEdge(CHEMISTRY_MCQ_CARD, CHEMISTRY_TAG);
            sampleAb.addEdge(MATHEMATICS_CARD, MATHEMATICS_TAG);
            sampleAb.addEdge(ENGLISH_CARD, ENGLISH_TAG);
        } catch (DuplicateEdgeException e) {
            throw new AssertionError("not possible");
        }
        return sampleAb;
    }

}
