package seedu.address.model.card;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Flashcard.
 * Guarantees: Question and Answer must not be null.
 */
public class Card {
    private final String question;
    private final String answer;

    public Card(String question, String answer) {
        requireAllNonNull(question, answer);
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
