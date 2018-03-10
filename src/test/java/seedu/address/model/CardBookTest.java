package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class CardBookTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final CardBook cardBook = new CardBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), cardBook.getCardList());
    }

    @Test
    public void getCardList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        cardBook.getCardList().remove(0);
    }
}
