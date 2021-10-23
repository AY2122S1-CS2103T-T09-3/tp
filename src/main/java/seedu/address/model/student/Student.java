package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.assessment.Assessment;
import seedu.address.model.group.GroupName;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    // Identity fields
    private final Name name;
    private final TelegramHandle telegramHandle;
    private final Email email;

    // Data fields
    private final GroupName groupName;
    private final Set<Assessment> assessments = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, TelegramHandle telegramHandle, Email email, GroupName groupName) {
        requireAllNonNull(name, telegramHandle, email, groupName);
        this.name = name;
        this.telegramHandle = telegramHandle;
        this.email = email;
        this.groupName = groupName;
    }

    public Name getName() {
        return name;
    }

    public TelegramHandle getTelegramHandle() {
        return telegramHandle;
    }

    public Email getEmail() {
        return email;
    }

    public GroupName getGroupName() {
        return groupName;
    }

    public boolean hasAssessment(Assessment assessment) {
        return assessments.contains(assessment);
    }

    public void addAssessment(Assessment assessment) {
        assessments.add(assessment);
    }

    /**
     * Returns true if both students have the same name.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.getName().equals(getName());
    }

    /**
     * Returns true if both students have the same identity and data fields.
     * This defines a stronger notion of equality between two students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return otherStudent.getName().equals(getName())
                && otherStudent.getTelegramHandle().equals(getTelegramHandle())
                && otherStudent.getEmail().equals(getEmail())
                && otherStudent.getGroupName().equals(getGroupName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, telegramHandle, email, groupName);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Telegram Handle: ")
                .append(getTelegramHandle())
                .append("; Email: ")
                .append(getEmail())
                .append("; Group: ")
                .append(getGroupName());

        return builder.toString();
    }

}
