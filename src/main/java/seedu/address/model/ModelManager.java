package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupContainsKeywordsPredicate;
import seedu.address.model.group.GroupName;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.student.Student;
import seedu.address.model.student.TelegramHandle;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final CsBook csBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Student> filteredStudents;
    private final FilteredList<Group> filteredGroups;

    /**
     * Initializes a ModelManager with the given csBook and userPrefs.
     */
    public ModelManager(ReadOnlyCsBook csBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(csBook, userPrefs);

        logger.fine("Initializing with address book: " + csBook + " and user prefs " + userPrefs);

        this.csBook = new CsBook(csBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudents = new FilteredList<>(this.csBook.getStudentList());
        filteredGroups = new FilteredList<>(this.csBook.getGroupList());
    }

    public ModelManager() {
        this(new CsBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getCsBookFilePath() {
        return userPrefs.getCsBookFilePath();
    }

    @Override
    public void setCsBookFilePath(Path csBookFilePath) {
        requireNonNull(csBookFilePath);
        userPrefs.setCsBookFilePath(csBookFilePath);
    }

    //=========== CsBook ================================================================================

    @Override
    public void setCsBook(ReadOnlyCsBook csBook) {
        this.csBook.resetData(csBook);
    }

    @Override
    public ReadOnlyCsBook getCsBook() {
        return csBook;
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return csBook.hasStudent(student);
    }

    @Override
    public void changeStudentGroup(Student student, Group newGroup) {
        requireNonNull(student);
        Student foundStudent = getStudentByName(student.getName());
        Name name = foundStudent.getName();
        TelegramHandle telegramHandle = foundStudent.getTelegramHandle();
        Email email = foundStudent.getEmail();
        GroupName groupName = newGroup.getGroupName();
        Student updatedStudent = new Student(name, telegramHandle, email, groupName);
        csBook.setStudent(foundStudent, updatedStudent);
    }

    @Override
    public void deleteStudent(Student target) {
        // Retrieve existing group in model
        GroupName groupName = target.getGroupName();
        Group retrievedGroup = getGroupByGroupName(groupName);

        // Remove reference to student from the group that the student belonged to
        retrievedGroup.removeStudent(target);

        csBook.removeStudent(target);
    }

    @Override
    public void addStudent(Student student) {
        // Retrieve existing group in model
        GroupName groupName = student.getGroupName();
        Group retrievedGroup = getGroupByGroupName(groupName);

        // Add reference to student into the group
        retrievedGroup.addStudent(student);

        csBook.addStudent(student);
    }

    @Override
    public Student getStudentByName(Name studentName) {
        updateFilteredStudentList(new NameContainsKeywordsPredicate(List.of(studentName.toString())));

        // return null if the student is not found
        if (getFilteredStudentList().isEmpty()) {
            updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
            return null;
        }

        assert getFilteredStudentList().size() == 1 : "Students name should be unique";

        Student retrievedStudent = getFilteredStudentList().get(0);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);

        return retrievedStudent;
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);

        csBook.setStudent(target, editedStudent);
    }

    @Override
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return csBook.hasGroup(group);
    }

    @Override
    public boolean hasGroup(GroupName groupName) {
        requireAllNonNull(groupName);
        return getGroupByGroupName(groupName) != null;
    }

    @Override
    public void updateGroupStudent(Group group, Student student) {
        requireAllNonNull(group, student);
        // get students original group
        GroupName oldGroupName = student.getGroupName();
        Group oldGroup = getGroupByGroupName(oldGroupName);

        // remove reference to student in old group
        oldGroup.removeStudent(student);

        // get new group
        GroupName newGroupName = group.getGroupName();
        Group newGroup = getGroupByGroupName(newGroupName);

        //TODO: junwei might need to change to student name below

        // add reference to student in new group
        newGroup.addStudent(student);
    }

    @Override
    public void deleteGroup(Group target) {
        Set<Student> studentsToDelete = target.getStudents();

        // Delete all students associated with the group
        for (Student student : studentsToDelete) {
            csBook.removeStudent(student);
        }

        csBook.removeGroup(target);
    }

    @Override
    public void addGroup(Group group) {
        csBook.addGroup(group);
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
    }

    @Override
    public Group getGroupByGroupName(GroupName groupName) {
        updateFilteredGroupList(new GroupContainsKeywordsPredicate(List.of(groupName.toString())));

        // return null if the group is not found
        if (getFilteredGroupList().isEmpty()) {
            updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
            return null;
        }

        Group retrievedGroup = getFilteredGroupList().get(0);
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);

        return retrievedGroup;
    }

    @Override
    public boolean hasAssessment(Student student, Assessment assessment) {
        requireNonNull(student);
        requireNonNull(assessment);
        return csBook.hasAssessment(student, assessment);
    }

    @Override
    public void addAssessment(Student student, Assessment assessment) {
        csBook.addAssessment(student, assessment);
    }

    @Override
    public void deleteAssessment(Student student, Assessment assessment) {
        csBook.deleteAssessment(student, assessment);
    }

    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code versionedCsBook}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    //=========== Filtered Group List Accessors =============================================================

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return filteredGroups;
    }

    @Override
    public void updateFilteredGroupList(Predicate<Group> predicate) {
        requireNonNull(predicate);
        filteredGroups.setPredicate(predicate);
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
        return csBook.equals(other.csBook)
                && userPrefs.equals(other.userPrefs)
                && filteredStudents.equals(other.filteredStudents)
                && filteredGroups.equals(other.filteredGroups);
    }

}
