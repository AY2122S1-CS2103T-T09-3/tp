package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.group.Description;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.student.Student;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Student ALICE = new StudentBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withGroup("CS2103T", "hi").withPhone("94351253").build();
    public static final Student BENSON = new StudentBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com")
            .withGroup("CS2103T", "hi").withPhone("98765432").build();
    public static final Student CARL = new StudentBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withGroup("CS2103T", "hi").withAddress("wall street").build();
    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withGroup("CS2103T", "hi").withAddress("10th street").build();
    public static final Student ELLE = new StudentBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withGroup("CS2103T", "hi").withAddress("michegan ave").build();
    public static final Student FIONA = new StudentBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withGroup("CS2103T", "hi").withAddress("little tokyo").build();
    public static final Student GEORGE = new StudentBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withGroup("CS2103T", "hi").withAddress("4th street").build();

    // Manually added
    public static final Student HOON = new StudentBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withGroup("CS2103T", "hi").withAddress("little india").build();
    public static final Student IDA = new StudentBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withGroup("CS2103T", "hi").withAddress("chicago ave").build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withGroup(VALID_GROUP_NAME_AMY, VALID_DESC_AMY).build();
    public static final Student BOB = new StudentBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withGroup(VALID_GROUP_NAME_BOB, VALID_DESC_BOB).build();

    //TODO GroupBuilder in the future?
    public static final Group GROUPCS2103T = new Group(new GroupName("CS2103T"), new Description("hi"));

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalStudents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical students.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Student student : getTypicalStudents()) {
            ab.addStudent(student);
        }

        for (Group group : getTypicalGroups()) {
            ab.addGroup(group);
        }

        return ab;
    }

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Group> getTypicalGroups() {
        return new ArrayList<>(List.of(GROUPCS2103T));
    }
}
