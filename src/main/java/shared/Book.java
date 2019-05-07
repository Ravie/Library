package shared;

import java.util.Objects;

public class Book {
    private int id;
    private String author;
    private String title;
    private int pageNum;
    private int publishingYear;
    private int addedYear;
    private int addedMonth;
    private int addedDay;

    public Book() {
        this.id = -1;
    }

    public Book(String author, String title, int pageNum, int publishingYear, int addedYear, int addedMonth, int addedDay) {
        this.author = author;
        this.title = title;
        this.pageNum = pageNum;
        this.publishingYear = publishingYear;
        this.addedYear = addedYear;
        this.addedMonth = addedMonth;
        this.addedDay = addedDay;
    }

    public Book(int id, String author, String title, int pageNum, int publishingYear, int addedYear, int addedMonth, int addedDay) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.pageNum = pageNum;
        this.publishingYear = publishingYear;
        this.addedYear = addedYear;
        this.addedMonth = addedMonth;
        this.addedDay = addedDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(int publishingYear) {
        this.publishingYear = publishingYear;
    }

    public int getAddedYear() {
        return addedYear;
    }

    public void setAddedYear(int addedYear) {
        this.addedYear = addedYear;
    }

    public int getAddedMonth() {
        return addedMonth;
    }

    public void setAddedMonth(int addedMonth) {
        this.addedMonth = addedMonth;
    }

    public int getAddedDay() {
        return addedDay;
    }

    public void setAddedDay(int addedDay) {
        this.addedDay = addedDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getPageNum() == book.getPageNum() &&
                getPublishingYear() == book.getPublishingYear() &&
                Objects.equals(getAuthor(), book.getAuthor()) &&
                Objects.equals(getTitle(), book.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthor(), getTitle(), getPageNum(), getPublishingYear());
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", pageNum=" + pageNum +
                ", publishingYear=" + publishingYear +
                ", addedYear=" + addedYear +
                ", addedMonth=" + addedMonth +
                ", addedDay=" + addedDay +
                '}';
    }
}
