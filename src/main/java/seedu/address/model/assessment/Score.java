package seedu.address.model.assessment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Score {

    public static final String MESSAGE_CONSTRAINTS = "Score should be of the format actual-score/total-score "
            + "and adhere to the following constraints:\n"
            + "1. The actual-score should have a value greater than or equal to 0.\n"
            + "2. The total-score should have a value greater than 0.\n"
            + "3. The actual-score should be less than or equal to the total-score.";

    public static final int PASSING_THRESHOLD = 50;

    public final int actualScore;
    public final int totalScore;

    /**
     * Constructs a {@code Score}.
     *
     * @param actualScore A valid actual score.
     * @param totalScore A valid total score.
     */
    public Score(int actualScore, int totalScore) {
        requireNonNull(actualScore);
        requireNonNull(totalScore);
        checkArgument(isValidScore(actualScore, totalScore), MESSAGE_CONSTRAINTS);
        this.actualScore = actualScore;
        this.totalScore = totalScore;
    }

    public boolean isPassing() {
        return actualScore / totalScore * 100 >= PASSING_THRESHOLD;
    }

    /**
     * Returns true if the given score and total score are valid
     */
    public static boolean isValidScore(int testActualScore, int testTotalScore) {
        return testActualScore >= 0 && testTotalScore > 0 && testActualScore <= testTotalScore;
    }

    @Override
    public String toString() {
        return actualScore + "/" + totalScore;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Score // instanceof handles nulls
                && actualScore == ((Score) other).actualScore // state check
                && actualScore == ((Score) other).totalScore);
    }

    @Override
    public int hashCode() {
        return actualScore * 31 + totalScore;
    }
}
