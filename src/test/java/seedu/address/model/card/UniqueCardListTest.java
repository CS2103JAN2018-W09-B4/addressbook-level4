package seedu.address.model.card;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UniqueCardListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueCardList uniqueCardList = new UniqueCardList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueCardList.asObservableList().remove(0);
    }

}