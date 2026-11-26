import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class Book {
    private int bookId;
    private String bookName;
    private String author;
    private String category;
    private int availableCopies;

    public Book(int bookId, String bookName, String author, String category, int availableCopies) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.category = category;
        setAvailableCopies(availableCopies);
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        if (availableCopies >= 0) {
            this.availableCopies = availableCopies;
        } else {
            System.out.println("Invalid copy count. Available copies cannot be negative.");
        }
    }

    public void displayBookDetails() {
        System.out.printf("ID: %d | Title: %s | Author: %s | Category: %s | Available Copies: %d%n",
                bookId, bookName, author, category, availableCopies);
    }
}

class LibraryMember {
    private int memberId;
    private String memberName;
    private String contactNumber;

    public LibraryMember(int memberId, String memberName, String contactNumber) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.contactNumber = contactNumber;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void displayMemberDetails() {
        System.out.print("Member ID: " + memberId + " | Name: " + memberName + " | Contact: " + contactNumber);
    }
}

class Student extends LibraryMember {
    private String department;
    private int year;

    public Student(int memberId, String memberName, String contactNumber, String department, int year) {
        super(memberId, memberName, contactNumber);
        this.department = department;
        this.year = year;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public void displayMemberDetails() {
        super.displayMemberDetails();
        System.out.println(" | Role: Student | Department: " + department + " | Year: " + year);
    }
}

class Faculty extends LibraryMember {
    private String department;
    private String designation;

    public Faculty(int memberId, String memberName, String contactNumber, String department, String designation) {
        super(memberId, memberName, contactNumber);
        this.department = department;
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public void displayMemberDetails() {
        super.displayMemberDetails();
        System.out.println(" | Role: Faculty | Department: " + department + " | Designation: " + designation);
    }
}

class IssueRecord {
    private int issueId;
    private int memberId;
    private int bookId;
    private String bookName;
    private boolean active;

    public IssueRecord(int issueId, int memberId, int bookId, String bookName) {
        this.issueId = issueId;
        this.memberId = memberId;
        this.bookId = bookId;
        this.bookName = bookName;
        this.active = true;
    }

    public int getIssueId() {
        return issueId;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void displayRecord() {
        String status = active ? "Active (Issued)" : "Returned";
        System.out.printf("Issue ID: %d | Member ID: %d | Book ID: %d | Book: %s | Status: %s%n",
                issueId, memberId, bookId, bookName, status);
    }
}

abstract class LibraryOperations {
    public abstract void addBook(Book book);
    public abstract void displayBooks();
    public abstract void issueBook(int bookId, int memberId);
    public abstract void returnBook(int bookId, int memberId);
}

class Library extends LibraryOperations {
    private List<Book> bookList;
    private List<LibraryMember> memberList;
    private Map<Integer, Book> bookMap;
    private Map<Integer, IssueRecord> issueRecords;
    private Set<Integer> issuedBookIds;

    private int nextMemberId = 1001;
    private int nextIssueId = 1;

    public Library() {
        bookList = new ArrayList<>();
        memberList = new ArrayList<>();
        bookMap = new HashMap<>();
        issueRecords = new LinkedHashMap<>();
        issuedBookIds = new HashSet<>();
    }

    @Override
    public void addBook(Book book) {
        if (bookMap.containsKey(book.getBookId())) {
            System.out.println("Error: A book with ID " + book.getBookId() + " already exists!");
            return;
        }
        if (book.getAvailableCopies() < 0) {
            System.out.println("Error: Available copies cannot be negative.");
            return;
        }
        bookList.add(book);
        bookMap.put(book.getBookId(), book);
        System.out.println("\nBook Added Successfully!");
    }

    @Override
    public void displayBooks() {
        if (bookList.isEmpty()) {
            System.out.println("No books registered in the library.");
            return;
        }

        System.out.println("\n--- All Registered Books ---");
        List<Book> zeroCopyBooks = new ArrayList<>();
        for (Book book : bookList) {
            book.displayBookDetails();
            if (book.getAvailableCopies() == 0) {
                zeroCopyBooks.add(book);
            }
        }

        System.out.println("Total Number of Books Registered: " + bookList.size());
        System.out.println("\n--- Books with Zero Available Copies ---");
        if (zeroCopyBooks.isEmpty()) {
            System.out.println("None (all books have at least 1 available copy).");
        } else {
            for (Book b : zeroCopyBooks) {
                b.displayBookDetails();
            }
        }
    }

    public void searchBook(int bookId) {
        Book book = bookMap.get(bookId);
        if (book != null) {
            System.out.println("\n--- Book Found ---");
            book.displayBookDetails();
        } else {
            System.out.println("Book with ID " + bookId + " does not exist.");
        }
    }

    public void registerStudent(String name, String contact, String department, int year) {
        int id = generateUniqueMemberId();
        LibraryMember student = new Student(id, name, contact, department, year);
        memberList.add(student);
        System.out.println("\nStudent Registered Successfully! Assigned Member ID: " + id);
    }

    public void registerFaculty(String name, String contact, String department, String designation) {
        int id = generateUniqueMemberId();
        LibraryMember faculty = new Faculty(id, name, contact, department, designation);
        memberList.add(faculty);
        System.out.println("\nFaculty Registered Successfully! Assigned Member ID: " + id);
    }

    private int generateUniqueMemberId() {
        while (findMemberById(nextMemberId) != null) {
            nextMemberId++;
        }
        return nextMemberId++;
    }

    public LibraryMember findMemberById(int memberId) {
        for (LibraryMember member : memberList) {
            if (member.getMemberId() == memberId) {
                return member;
            }
        }
        return null;
    }

    public void displayAllMembers() {
        if (memberList.isEmpty()) {
            System.out.println("No library members registered.");
            return;
        }
        System.out.println("\n--- Registered Library Members ---");
        for (LibraryMember member : memberList) {
            member.displayMemberDetails();
        }
    }

    public void searchMember(int memberId) {
        LibraryMember member = findMemberById(memberId);
        if (member != null) {
            System.out.println("\n--- Member Found ---");
            member.displayMemberDetails();
        } else {
            System.out.println("Member with ID " + memberId + " not found.");
        }
    }

    @Override
    public void issueBook(int bookId, int memberId) {
        LibraryMember member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Error: Member ID " + memberId + " does not exist.");
            return;
        }

        Book book = bookMap.get(bookId);
        if (book == null) {
            System.out.println("Error: Book ID " + bookId + " does not exist.");
            return;
        }

        if (book.getAvailableCopies() <= 0) {
            System.out.println("Error: No copies available for \"" + book.getBookName() + "\".");
            return;
        }

        for (IssueRecord record : issueRecords.values()) {
            if (record.isActive() && record.getMemberId() == memberId && record.getBookId() == bookId) {
                System.out.println("Error: Member already has an active issue for this book.");
                return;
            }
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        int issueId = nextIssueId++;
        IssueRecord record = new IssueRecord(issueId, memberId, bookId, book.getBookName());
        issueRecords.put(issueId, record);

        issuedBookIds.add(bookId);

        System.out.println("\nBook Issued Successfully!\n");
        System.out.println("Member: " + member.getMemberName());
        System.out.println("Book: " + book.getBookName());
        System.out.println("Available Copies: " + book.getAvailableCopies());
    }

    @Override
    public void returnBook(int bookId, int memberId) {
        IssueRecord activeRecord = null;
        for (IssueRecord record : issueRecords.values()) {
            if (record.isActive() && record.getMemberId() == memberId && record.getBookId() == bookId) {
                activeRecord = record;
                break;
            }
        }

        if (activeRecord == null) {
            System.out.println("Error: No active issue record found for Member ID " + memberId + " and Book ID " + bookId + ".");
            return;
        }

        Book book = bookMap.get(bookId);
        if (book != null) {
            book.setAvailableCopies(book.getAvailableCopies() + 1);
        }

        activeRecord.setActive(false);

        boolean hasOtherActiveIssues = false;
        for (IssueRecord record : issueRecords.values()) {
            if (record.isActive() && record.getBookId() == bookId) {
                hasOtherActiveIssues = true;
                break;
            }
        }
        if (!hasOtherActiveIssues) {
            issuedBookIds.remove(bookId);
        }

        System.out.println("\nBook Returned Successfully!");
        if (book != null) {
            System.out.println("Book: " + book.getBookName() + " | Updated Available Copies: " + book.getAvailableCopies());
        }
    }

    public void displayIssuedBooks(boolean onlyActive) {
        if (issueRecords.isEmpty()) {
            System.out.println("No issue records found.");
            return;
        }

        System.out.println(onlyActive ? "\n--- Active Issue Records ---" : "\n--- All Issue Records ---");
        boolean found = false;
        for (IssueRecord record : issueRecords.values()) {
            if (!onlyActive || record.isActive()) {
                record.displayRecord();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No active issue records found.");
        }
    }

    public void generateLibraryReport() {
        int activeIssueCount = 0;
        for (IssueRecord record : issueRecords.values()) {
            if (record.isActive()) {
                activeIssueCount++;
            }
        }

        int totalAvailableCopies = 0;
        List<Book> unavailableBooks = new ArrayList<>();
        for (Book book : bookList) {
            totalAvailableCopies += book.getAvailableCopies();
            if (book.getAvailableCopies() == 0) {
                unavailableBooks.add(book);
            }
        }

        System.out.println("\n========================================");
        System.out.println("           LIBRARY STATISTICS           ");
        System.out.println("========================================");
        System.out.println("Total Books Registered      : " + bookList.size());
        System.out.println("Total Registered Members    : " + memberList.size());
        System.out.println("Total Active Issue Records  : " + activeIssueCount);
        System.out.println("Unique Books Currently Out  : " + issuedBookIds.size());
        System.out.println("Total Available Book Copies : " + totalAvailableCopies);
        System.out.println("\n--- Currently Unavailable Books ---");
        if (unavailableBooks.isEmpty()) {
            System.out.println("All registered books are currently available.");
        } else {
            for (Book b : unavailableBooks) {
                b.displayBookDetails();
            }
        }
    }
}
class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            printMenu();
            choice = readInt(scanner, "Enter Your Choice: ");

            switch (choice) {
                case 1:
                    int bookId = readInt(scanner, "Enter Book ID: ");
                    System.out.print("Enter Book Name: ");
                    String bookName = scanner.nextLine().trim();
                    System.out.print("Enter Author Name: ");
                    String author = scanner.nextLine().trim();
                    System.out.print("Enter Category: ");
                    String category = scanner.nextLine().trim();
                    int copies = readInt(scanner, "Enter Available Copies: ");

                    Book newBook = new Book(bookId, bookName, author, category, copies);
                    library.addBook(newBook);
                    break;

                case 2:
                    library.displayBooks();
                    break;

                case 3:
                    int searchBookId = readInt(scanner, "Enter Book ID to search: ");
                    library.searchBook(searchBookId);
                    break;

                case 4:
                    System.out.print("Enter Student Name: ");
                    String studentName = scanner.nextLine().trim();
                    System.out.print("Enter Contact Number: ");
                    String studentContact = scanner.nextLine().trim();
                    System.out.print("Enter Department: ");
                    String studentDept = scanner.nextLine().trim();
                    int year = readInt(scanner, "Enter Year (e.g., 1, 2, 3, 4): ");

                    library.registerStudent(studentName, studentContact, studentDept, year);
                    break;

                case 5:
                    System.out.print("Enter Faculty Name: ");
                    String facultyName = scanner.nextLine().trim();
                    System.out.print("Enter Contact Number: ");
                    String facultyContact = scanner.nextLine().trim();
                    System.out.print("Enter Department: ");
                    String facultyDept = scanner.nextLine().trim();
                    System.out.print("Enter Designation: ");
                    String designation = scanner.nextLine().trim();

                    library.registerFaculty(facultyName, facultyContact, facultyDept, designation);
                    break;

                case 6:
                    library.displayAllMembers();
                    break;

                case 7:
                    int searchMemberId = readInt(scanner, "Enter Member ID to search: ");
                    library.searchMember(searchMemberId);
                    break;

                case 8:
                    int issueMemberId = readInt(scanner, "Enter Member ID: ");
                    int issueBookId = readInt(scanner, "Enter Book ID: ");
                    library.issueBook(issueBookId, issueMemberId);
                    break;

                case 9:
                    int returnMemberId = readInt(scanner, "Enter Member ID: ");
                    int returnBookId = readInt(scanner, "Enter Book ID: ");
                    library.returnBook(returnBookId, returnMemberId);
                    break;

                case 10:
                    System.out.println("1. View All Issue Records");
                    System.out.println("2. View Only Active Issue Records");
                    int viewOption = readInt(scanner, "Select option (1 or 2): ");
                    library.displayIssuedBooks(viewOption == 2);
                    break;

                case 11:
                    library.generateLibraryReport();
                    break;

                case 12:
                    System.out.println("Exiting Library Management System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice! Please select a number between 1 and 12.");
            }
        } while (choice != 12);

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n========================================");
        System.out.println("          LIBRARY MANAGEMENT SYSTEM");
        System.out.println("========================================\n");
        System.out.println("1. Add Book");
        System.out.println("2. Display All Books");
        System.out.println("3. Search Book");
        System.out.println("4. Register Student");
        System.out.println("5. Register Faculty");
        System.out.println("6. Display All Members");
        System.out.println("7. Search Member");
        System.out.println("8. Issue Book");
        System.out.println("9. Return Book");
        System.out.println("10. Display Issued Books");
        System.out.println("11. Generate Library Report");
        System.out.println("12. Exit\n");
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }
}