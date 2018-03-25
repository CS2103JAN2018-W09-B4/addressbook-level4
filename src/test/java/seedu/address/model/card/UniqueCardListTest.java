package seedu.address.model.card;

import org.junit.Test;

public class UniqueCardListTest {
    @Test
    public void asObservableList_modifyList_noError() {
        UniqueCardList uniqueCardList = new UniqueCardList();
        uniqueCardList.asObservableList().add(new Card("hi", "bye"));
    }
}
