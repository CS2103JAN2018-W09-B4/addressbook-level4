# jethrokuan
###### /java/seedu/address/logic/parser/ListCommandParser.java
``` java
/**
 * Parses input arguments for the list command.
 */
public class ListCommandParser implements Parser<Command> {

    public static final String PREFIX_NO_TAGS_ONLY = "-t";
    public static final String MESSAGE_PARSE_FAILURE = "Invalid arguments passed.\n\n" + ListCommand.MESSAGE_USAGE;
    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand.
     * @throws ParseException if the args is invalid
     */
    @Override
    public Command parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.equals("")) {
            return new ListCommand(false);
        } else if (trimmedArgs.equals(PREFIX_NO_TAGS_ONLY)) {
            return new ListCommand(true);
        } else {
            throw new ParseException(MESSAGE_PARSE_FAILURE);
        }
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java

    /**
     * Parses a {@code String tag} into a {@code Tag}
     * Leading and trailing whitespaces will be trimmed
     */
    public static Optional<Set<Tag>> parseTags(List<String> tagNames) throws IllegalValueException {
        if (tagNames.isEmpty()) {
            return Optional.empty();
        }

        Set<Tag> tags = new HashSet<>();
        for (String tagName : tagNames) {
            if (!Name.isValidName(tagName)) {
                throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
            }
            tags.add(new Tag(new Name(tagName.trim())));
        }

        return Optional.of(tags);
    }
```
###### /java/seedu/address/logic/commands/ListCommand.java
``` java
/**
 * Lists all cards in the card bank.
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Cleared all filters.";

    public static final String MESSAGE_SUCCESS_NO_TAGS_ONLY = "Showing cards with no tags.";

    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": lists all cards and tags.\n"
            + COMMAND_WORD + " " + ListCommandParser.PREFIX_NO_TAGS_ONLY + ": lists only cards without tags.";

    private final boolean untaggedOnly;

    public ListCommand(boolean untaggedOnly) {
        this.untaggedOnly = untaggedOnly;
    }
    @Override
    public CommandResult execute() {
        String message;
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        if (untaggedOnly) {
            message = MESSAGE_SUCCESS_NO_TAGS_ONLY;
            model.showUntaggedCards();
        } else {
            message = MESSAGE_SUCCESS;
            model.showAllCards();
        }

        EventsCenter.getInstance().post(new EmptyCardBackEvent());
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListCommand)
                && untaggedOnly == ((ListCommand) other).untaggedOnly;
    }
}
```
###### /java/seedu/address/storage/XmlAdaptedCardMapEntry.java
``` java
public class XmlAdaptedCardMapEntry {
    @XmlElement(required = true)
    private String cardId;

    @XmlElement(required = true)
    private List<String> tags;

    public XmlAdaptedCardMapEntry() {
        tags = new ArrayList<>();
    }

    /**
     * Constructs an instance of XMmlAdaptedCardMapEntry from a Map Entry.
     * @param entry Map Entry for cardMap
     */
    public XmlAdaptedCardMapEntry(Map.Entry<String, Set<String>> entry) {
        this();
        cardId = entry.getKey();
        for (String tagId: entry.getValue()) {
            tags.add(tagId);
        }
    }

    public String getCardId() {
        return cardId;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCardTag)) {
            return false;
        }

        XmlAdaptedCardMapEntry otherCardMap = (XmlAdaptedCardMapEntry) other;

        return Objects.equals(otherCardMap.cardId, cardId)
                && Objects.equals(otherCardMap.tags, tags);
    }
}
```
###### /java/seedu/address/storage/XmlAdaptedTagMapEntry.java
``` java
public class XmlAdaptedTagMapEntry {
    @XmlElement(required = true)
    private String tagId;

    @XmlElement(required = true)
    private List<String> cards;

    public XmlAdaptedTagMapEntry() {
        cards = new ArrayList<>();
    }

    /**
     * Constructs an instance of XmlAdaptedTagMapEntry from a Map Entry in CardTag's tagMap
     * @param entry Map Entry of tagMap.
     */
    public XmlAdaptedTagMapEntry(Map.Entry<String, Set<String>> entry) {
        this();
        tagId = entry.getKey();
        for (String cardId : entry.getValue()) {
            cards.add(cardId);
        }
    }

    public String getTagId() {
        return tagId;
    }

    public List<String> getCards() {
        return cards;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCardTag)) {
            return false;
        }

        XmlAdaptedTagMapEntry otherCardMap = (XmlAdaptedTagMapEntry) other;

        return Objects.equals(otherCardMap.tagId, tagId)
                && Objects.equals(otherCardMap.cards, cards);
    }
}
```
###### /java/seedu/address/storage/XmlAdaptedCardTag.java
``` java
/**
 * JAXB-friendly version of an edge in CardTag
 */
@XmlRootElement(name = "cardtag")
public class XmlAdaptedCardTag {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "%s field is missing!";

    @XmlElement(required = true)
    private List<XmlAdaptedCardMapEntry> cardEntry;

    @XmlElement(required = true)
    private List<XmlAdaptedTagMapEntry> tagEntry;

    public XmlAdaptedCardTag() {
        cardEntry = new ArrayList<>();
        tagEntry = new ArrayList<>();
    }

    /**
     * Constructs a new XmlAdaptedCardTag from given edge details.
     *
     * @param cardEntry List of Card -> [Tag] entries
     * @param tagEntry  List of Tag -> [Card] entries
     */
    public XmlAdaptedCardTag(List<XmlAdaptedCardMapEntry> cardEntry, List<XmlAdaptedTagMapEntry> tagEntry) {
        this.cardEntry = cardEntry;
        this.tagEntry = tagEntry;
    }

    public XmlAdaptedCardTag(CardTag cardTag) {
        this();
        cardEntry.addAll(cardTag.getCardMap().entrySet().stream()
                .map(XmlAdaptedCardMapEntry::new).collect(Collectors.toList()));
        tagEntry.addAll(cardTag.getTagMap().entrySet().stream()
                .map(XmlAdaptedTagMapEntry::new).collect(Collectors.toList()));
    }

    public List<XmlAdaptedCardMapEntry> getCardEntry() {
        return cardEntry;
    }

    public List<XmlAdaptedTagMapEntry> getTagEntry() {
        return tagEntry;
    }

    /**
     * Converts this addressbook into the model's {@code CardTag} object.
     * @return corresponding CardTag object
     * @throws IllegalValueException if there are invalid values within the cardTag entries.
     */
    public CardTag toModelType() throws IllegalValueException {
        CardTag cardTag = new CardTag();
        HashMap<String, Set<String>> cardMap = new HashMap<>();
        for (XmlAdaptedCardMapEntry entry : cardEntry) {
            String cardId = entry.getCardId();
            List<String> tags = entry.getTags();
            if (cardId == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "card ID"));
            }
            if (tags == null || tags.size() == 0) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "tags"));
            }

            cardMap.put(cardId, Sets.newHashSet(tags));
        }

        HashMap<String, Set<String>> tagMap = new HashMap<>();
        for (XmlAdaptedTagMapEntry entry : tagEntry) {
            String tagId = entry.getTagId();
            List<String> cards = entry.getCards();
            if (tagId == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "tag ID"));
            }
            if (cards == null || cards.size() == 0) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "cards"));
            }

            tagMap.put(tagId, Sets.newHashSet(cards));
        }

        cardTag.setCardMap(cardMap);
        cardTag.setTagMap(tagMap);

        return cardTag;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCardTag)) {
            return false;
        }

        XmlAdaptedCardTag otherEdge = (XmlAdaptedCardTag) other;

        return Objects.equals(cardEntry, otherEdge.cardEntry)
                && Objects.equals(tagEntry, otherEdge.tagEntry);
    }
}
```
###### /java/seedu/address/model/cardtag/CardTag.java
``` java
/**
 *
 * This class captures the relations between cards and tags.
 */
public class CardTag {
    public static final String MESSAGE_CARD_HAS_TAG = "Card already has tag '%s'";
    public static final String MESSAGE_CARD_NO_TAG = "Card has no tag '%s'";
    public static final String LOG_EDGE_ADDED = "Edge added between Card %s and Tag %s";
    private static final String LOG_EDGE_REMOVED = "Edge removed between Card %s and Tag %s";

    private static final Logger logger = LogsCenter.getLogger(CardTag.class);
    private HashMap<String, Set<String>> cardMap; // cardMap["cardId"] = Set<tagId>
    private HashMap<String, Set<String>> tagMap; // tagMap["tagId"] = Set<cardId>

    public CardTag() {
        this.cardMap = new HashMap<>();
        this.tagMap = new HashMap<>();
    }

    public CardTag(CardTag cardTag) {
        this.cardMap = new HashMap<>();
        this.tagMap = new HashMap<>();

        for (Map.Entry<String, Set<String>> entry: cardTag.cardMap.entrySet()) {
            String key = entry.getKey();
            Set<String> values = entry.getValue();

            this.cardMap.put(key, new HashSet<>());
            for (String id: values) {
                this.cardMap.get(key).add(id);
            }
        }

        for (Map.Entry<String, Set<String>> entry: cardTag.tagMap.entrySet()) {
            String key = entry.getKey();
            Set<String> values = entry.getValue();

            this.tagMap.put(key, new HashSet<>());
            for (String id: values) {
                this.tagMap.get(key).add(id);
            }
        }
    }

    public HashMap<String, Set<String>> getCardMap() {
        return cardMap;
    }

    public HashMap<String, Set<String>> getTagMap() {
        return tagMap;
    }

    public void setCardMap(HashMap<String, Set<String>> cardMap) {
        this.cardMap = cardMap;
    }

    public void setTagMap(HashMap<String, Set<String>> tagMap) {
        this.tagMap = tagMap;
    }

    /**
     * Checks if the Card and Tag given are connected by an edge.
     * @param cardId UUID of card
     * @param tagId UUID of tag
     * @return true if cord and tag are connected, false otherwise
     */
    public boolean isConnected(String cardId, String tagId) {
        Set<String> tags = cardMap.get(cardId);
        Set<String> cards = tagMap.get(tagId);

        return (tags != null && tags.contains(tagId))
                || (cards != null && cards.contains(cardId)); // should always short-circuit here
    }

    public boolean isConnected(Card card, Tag tag) {
        return isConnected(card.getId().toString(), tag.getId().toString());
    }


    public List<Card> getCards(String tagId, ObservableList<Card> cardList) {
        Set<String> cards = tagMap.get(tagId);
        if (cards != null) {
            return cardList.filtered(card -> cards.contains(card.getId().toString()));
        } else {
            return FXCollections.observableArrayList();
        }
    }

    public List<Card> getCards(Tag tag, ObservableList<Card> cardList) {
        return getCards(tag.getId().toString(), cardList);
    }

    public List<Tag> getTags(String cardId, ObservableList<Tag> tagList) {
        Set<String> tags = cardMap.get(cardId);
        if (tags != null) {
            return tagList.filtered(tag -> tags.contains(tag.getId().toString()));
        } else {
            return Collections.emptyList();
        }
    }

    public List<Tag> getTags(Card card, ObservableList<Tag> tagList) {
        return getTags(card.getId().toString(), tagList);
    }

    /**
     * Adds an edge between card and tag.
     * @param card Card
     * @param tag Tag
     * @throws DuplicateEdgeException when the edge between card and tag already exists
     */
    public void addEdge(Card card, Tag tag) throws DuplicateEdgeException {
        String cardId = card.getId().toString();
        String tagId = tag.getId().toString();

        if (isConnected(cardId, tagId)) {
            throw new DuplicateEdgeException(tag);
        }

        Set<String> tags = cardMap.get(cardId);
        if (tags == null) {
            cardMap.put(cardId, Stream.of(tagId).collect(Collectors.toSet()));
        } else {
            tags.add(tagId);
        }

        Set<String> cards = tagMap.get(tagId);
        if (cards == null) {
            tagMap.put(tagId, Stream.of(cardId).collect(Collectors.toSet()));
        } else {
            cards.add(cardId);
        }

        logger.info(String.format(LOG_EDGE_ADDED, card.toString(), tag.toString()));
    }

    /**
     * Removes the undirected edge between card and tag.
     * @param card Card
     * @param tag Tag
     * @throws EdgeNotFoundException if there is no edge to remove.
     */
    public void removeEdge(Card card, Tag tag) throws EdgeNotFoundException {
        String cardId = card.getId().toString();
        String tagId = tag.getId().toString();

        if (!isConnected(cardId, tagId)) {
            throw new EdgeNotFoundException(tag);
        }

        Set<String> tags = cardMap.get(cardId);
        Set<String> cards = tagMap.get(tagId);
        if (tags != null) {
            tags.remove(tagId);
            if (tags.isEmpty()) {
                cardMap.remove(cardId);
            }
        }

        if (cards != null) {
            cards.remove(cardId);
            if (cards.isEmpty()) {
                tagMap.remove(tagId);
            }
        }

        logger.info(String.format(LOG_EDGE_REMOVED, card.toString(), tag.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CardTag)) {
            return false;
        }

        CardTag otherCardTag = (CardTag) other;

        return Objects.equals(otherCardTag.cardMap, cardMap)
                && Objects.equals(otherCardTag.tagMap, tagMap);
    }

    /**
     * returns whether a tag has any cards associated to it.
     * @param tag
     * @return
     */
    public boolean hasCards(Tag tag) {
        String tagId = tag.getId().toString();
        return tagMap.containsKey(tagId);
    }
}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void deleteTag(Tag target) throws TagNotFoundException {
        CardTag cardTag = this.addressBook.getCardTag();
        List<Card> cards = cardTag.getCards(target, this.addressBook.getCardList());
        for (Card card : cards) {
            try {
                cardTag.removeEdge(card, target);
            } catch (EdgeNotFoundException e) {
                throw new IllegalStateException("Not possible to reach here.");
            }
        }

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
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void addCard(Card card) throws DuplicateCardException {
        addressBook.addCard(card);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteCard(Card card) throws CardNotFoundException {
        CardTag cardTag = this.getAddressBook().getCardTag();
        List<Tag> tags = cardTag.getTags(card, this.getAddressBook().getTagList());

        // We need to clone tags because removing tags while iterating over it results in strange behaviour.
        List<Tag> tempTags = new ArrayList<>();
        for (Tag tag : tags) {
            tempTags.add(tag);
        }

        int size = tempTags.size();
        for (Tag tag : tempTags) {
            try {
                cardTag.removeEdge(card, tag);
                if (!cardTag.hasCards(tag)) {
                    addressBook.removeTag(tag);
                }
            } catch (EdgeNotFoundException e) {
                throw new IllegalStateException("Not possible to reach here.");
            } catch (TagNotFoundException e) {
                throw new IllegalStateException("Not possible to reach here.");
            }
        }
        addressBook.deleteCard(card);
        indicateAddressBookChanged();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
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
        for (Tag tag : tags) {
            int index = this.addressBook.getTagList().indexOf(tag);
            if (index == -1) {
                throw new TagNotFoundException(tag);
            }
            Tag existingTag = this.addressBook.getTagList().get(index);
            cardTag.removeEdge(card, existingTag);
        }
        indicateAddressBookChanged(); // Force UI update.
    }

    // NOTE: tag passed might not have the correct ids, so it is important to fetch them first.
    @Override
    public void addTags(Card card, Set<Tag> tags) throws DuplicateEdgeException {
        CardTag cardTag = this.getAddressBook().getCardTag();
        for (Tag tag : tags) {
            Tag newOrExistingTag = addTag(tag).getTag();
            cardTag.addEdge(card, newOrExistingTag);
        }
        indicateAddressBookChanged(); // Force UI update.
    }

    @Override
    public void showUntaggedCards() {
        Predicate<Card> predCardsNoTags = card -> getTags(card).isEmpty();
        filteredCards.setAll(this.getAddressBook().getCardList().filtered(predCardsNoTags));
    }
```
