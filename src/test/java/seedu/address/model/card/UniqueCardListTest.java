package seedu.address.model.card;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UniqueCardListTest {
    @Test
    public void asObservableList_modifyList_noError() {
        UniqueCardList uniqueCardList = new UniqueCardList();
        uniqueCardList.asObservableList().add(new Card("hi", "bye"));
    }
}
