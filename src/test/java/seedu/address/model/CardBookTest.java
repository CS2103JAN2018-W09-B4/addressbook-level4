package seedu.address.model;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
