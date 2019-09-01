package util;

import javafx.scene.control.Button;

public class IBookCartTM {
    private String bookid;
    private String title;
    private String author;
     private Button delete;

    public IBookCartTM() {
    }

    public IBookCartTM(String bookid, String title, String author, Button delete) {
        this.setBookid(bookid);
        this.setTitle(title);
        this.setAuthor(author);
        this.setDelete(delete);
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "IBookCartTM{" +
                "bookid='" + getBookid() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", delete=" + getDelete() +
                '}';
    }
}
