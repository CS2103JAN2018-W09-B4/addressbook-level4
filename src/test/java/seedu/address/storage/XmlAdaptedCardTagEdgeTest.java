package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class XmlAdaptedCardTagEdgeTest {
    private static final String CARD_ID_1 = "3648b549-d900-4f0e-8573-e3c9ab499054";
    private static final String CARD_ID_2 = "f581860a-d5db-4925-aeab-1f1e441407f3";

    private static final String TAG_ID_1 = "67d1fc9c-4d3b-46ff-a663-bc76d80c148a";
    private static final String TAG_ID_2 = "f7a33f28-8246-41ba-9347-0455124625c0";

    @Test
    public void equals_worksAsExpected() {
        assertEquals(new XmlAdaptedCardTagEdge(CARD_ID_1, TAG_ID_1),
                new XmlAdaptedCardTagEdge(CARD_ID_1, TAG_ID_1));

        assertNotEquals(new XmlAdaptedCardTagEdge(CARD_ID_2, TAG_ID_1),
                new XmlAdaptedCardTagEdge(CARD_ID_1, TAG_ID_1));

        assertNotEquals(new XmlAdaptedCardTagEdge(CARD_ID_2, TAG_ID_1),
                new XmlAdaptedCardTagEdge(CARD_ID_1, TAG_ID_2));
    }
}