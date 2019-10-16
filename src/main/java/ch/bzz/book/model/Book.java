package ch.bzz.book.model;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * a book in the shelf
 * <p>
 * M151: BookDB
 *
 * @author Marcel Suter
 * @version 1.0
 * @since 2019-10-13
 */
public class Book {

    private String bookUUID;
    private String title;
    private String author;
    private String publisher;
    private BigDecimal price;
    private String isbn;

    /**
     * default constructor
     */
    public Book() {
        setBookUUID(UUID.randomUUID().toString());
        setTitle(null);
    }

    /**
     * @return the bookUUID
     */
    public String getBookUUID() {
        return bookUUID;
    }

    /**
     * @param bookUUID the bookUUID to be set
     */
    public void setBookUUID(String bookUUID) {
        this.bookUUID = bookUUID;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * @param publisher the publisher to set
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @param isbn the isbn to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}