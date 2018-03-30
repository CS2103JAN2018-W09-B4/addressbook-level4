package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.CardListPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.TagListPanelSelectionChangedEvent;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.CardNotFoundException;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.model.cardtag.CardTag;
import seedu.address.model.cardtag.DuplicateEdgeException;
import seedu.address.model.cardtag.EdgeNotFoundException;
import seedu.address.model.tag.AddTagResult;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Tag> filteredTags;
    private final ObservableList<Card> filteredCards;
    private Card selectedCard;
    private Tag selectedTag;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredTags = new FilteredList<>(this.addressBook.getTagList());

        // To prevent direct referencing, which would cause setAll() to affect addressBook's list
        filteredCards = FXCollections.observableArrayList();
        filteredCards.setAll(this.addressBook.getCardList());

        selectedTag = null;
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        updateFilteredCardList();
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deleteTag(Tag target) throws TagNotFoundException {
        addressBook.removeTag(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized AddTagResult addTag(Tag tag) {
        AddTagResult tagResult = addressBook.addTag(tag);
        updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        indicateAddressBookChanged();
        return tagResult;
    }

    @Override
    public void updateTag(Tag target, Tag editedTag)
            throws DuplicateTagException, TagNotFoundException {
        requireAllNonNull(target, editedTag);

        addressBook.updateTag(target, editedTag);
        indicateAddressBookChanged();
    }

    //=========== Filtered Tag List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Tag} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return FXCollections.unmodifiableObservableList(filteredTags);
    }

    @Override
    public void updateFilteredTagList(Predicate<Tag> predicate) {
        requireNonNull(predicate);
        filteredTags.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredTags.equals(other.filteredTags);
    }

    @Override
    public synchronized void addCard(Card card) throws DuplicateCardException {
        addressBook.addCard(card);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteCard(Card card) throws CardNotFoundException {
        addressBook.deleteCard(card);
        updateFilteredCardList();
        indicateAddressBookChanged();
    }

    @Override
    public void updateCard(Card target, Card editedCard)
            throws DuplicateCardException, CardNotFoundException {
        requireAllNonNull(target, editedCard);

        addressBook.updateCard(target, editedCard);
        indicateAddressBookChanged();
    }

    //@@author yong-jie
    @Override
    public void showAllCards() {
        filteredCards.setAll(this.addressBook.getCardList());
    }

    /**
     * Synchronises the card list with that of the card bank.
     */
    public void updateFilteredCardList() {
        if (selectedTag == null) {
            showAllCards();
            return;
        }
        filterCardsByTag(selectedTag);
    }

    @Override
    public void filterCardsByTag(Tag tag) {
        filteredCards.setAll(addressBook
                .getCardTag()
                .getCards(tag, addressBook.getCardList()));
    }

    //@@author
    @Override
    public ObservableList<Card> getFilteredCardList() {
        return FXCollections.unmodifiableObservableList(filteredCards);
    }

    //@@author pukipuki
    @Override
    public void showDueCards() {
        filteredCards.setAll(this.addressBook.getTodayReviewList());
    }
    //@@author

    //@@author jethrokuan
    @Override
    public void addEdge(Card card, Tag tag) throws DuplicateEdgeException {
        this.getAddressBook().getCardTag().addEdge(card, tag);
        updateFilteredCardList();
    }

    @Override
    public void removeEdge(Card card, Tag tag) throws EdgeNotFoundException {
        this.getAddressBook().getCardTag().removeEdge(card, tag);
        updateFilteredCardList();
    }

    @Override
    public List<Tag> getTags(Card card) {
        return this.getAddressBook()
                .getCardTag()
                .getTags(card, this.getAddressBook().getTagList());
    }

    // NOTE: tag passed might not have the correct ids, so it is important to fetch them first.
    @Override
    public void removeTags(Card card, Set<Tag> tags) throws EdgeNotFoundException, TagNotFoundException {
        CardTag cardTag = this.getAddressBook().getCardTag();
        for (Tag tag: tags) {
            int index = this.addressBook.getTagList().indexOf(tag);
            if (index == -1) {
                throw new TagNotFoundException(tag);
            }
            Tag existingTag = this.addressBook.getTagList().get(index);
            cardTag.removeEdge(card, existingTag);
        }
    }

    // NOTE: tag passed might not have the correct ids, so it is important to fetch them first.
    @Override
    public void addTags(Card card, Set<Tag> tags) throws DuplicateEdgeException {
        CardTag cardTag = this.getAddressBook().getCardTag();
        for (Tag tag: tags) {
            Tag newOrExistingTag = addTag(tag).getTag();
            cardTag.addEdge(card, newOrExistingTag);
        }
    }
    //@@author

    //@@author yong-jie
    @Subscribe
    private void handleTagListPanelSelectionEvent(TagListPanelSelectionChangedEvent event) {
        selectedTag = event.getNewSelection().tag;
        filterCardsByTag(selectedTag);
    }

    //@@author pukipuki
    @Subscribe
    private void handleCardListPanelSelectionEvent(CardListPanelSelectionChangedEvent event) {
        this.selectedCard = event.getNewSelection().card;
    }
    //@@author
}
