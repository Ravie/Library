package shared;

import java.util.List;

public class BookPagination {

    private List<Book> books;
    private int size;
    private int currentPage;

    public BookPagination() {
    }

    public BookPagination(List<Book> books, int size) {
        this.books = books;
        this.size = size;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
