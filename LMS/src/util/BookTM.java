package util;

public class BookTM implements Cloneable {

    private String bookid;
    private String title;
    private String author;
    private double price;
    private String status;


    public BookTM() {
    }

    public BookTM clone(){

        return new BookTM(this.bookid,this.title,this.author,this.price,this.status);
    }


    public BookTM(String bookid, String title, String author, double price, String status) {
        this.bookid = bookid;
        this.title = title;
        this.author = author;
        this.price = price;
        this.status = status;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BookTM{" +
                "bookid='" + bookid + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
