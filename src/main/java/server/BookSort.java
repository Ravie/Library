package server;

import shared.Book;

import java.util.List;

public class BookSort {

    public static List<Book> makeSort(List<Book> books, String sorting) {
        switch (sorting) {
            case "id":
                books.sort(BookComparators.compareById);
                break;
            case "author":
                books.sort(BookComparators.compareByAuthor);
                break;
            case "title":
                books.sort(BookComparators.compareByTitle);
                break;
            case "pageNum":
                books.sort(BookComparators.compareByPageNum);
                break;
            case "publishingYear":
                books.sort(BookComparators.compareByPublishingYear);
                break;
            case "dateAdded":
                books.sort(BookComparators.compareByDateAdded);
                break;
        }
        return books;
    }
}
