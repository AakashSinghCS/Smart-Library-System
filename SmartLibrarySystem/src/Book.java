import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
    private final SimpleIntegerProperty bookID;
    private final SimpleStringProperty title;
    private final SimpleStringProperty author;
    private final SimpleStringProperty genre;
    private final SimpleIntegerProperty availableCopies;

    public Book(int bookID, String title, String author, String genre, int availableCopies) {
        this.bookID = new SimpleIntegerProperty(bookID);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.genre = new SimpleStringProperty(genre);
        this.availableCopies = new SimpleIntegerProperty(availableCopies);
    }
    public SimpleIntegerProperty bookIDProperty() {
        return bookID;
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public SimpleStringProperty genreProperty() {
        return genre;
    }

    public SimpleIntegerProperty availableCopiesProperty() {
        return availableCopies;
    }
    public int getBookID() {
        return bookID.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getAuthor() {
        return author.get();
    }

    public String getGenre() {
        return genre.get();
    }

    public int getAvailableCopies() {
        return availableCopies.get();
    }
}
