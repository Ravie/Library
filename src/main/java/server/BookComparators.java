package server;

import shared.Book;

import java.util.Comparator;

public class BookComparators {
    public static Comparator<Book> compareById = Comparator.comparingInt(Book::getId);
    public static Comparator<Book> compareByAuthor = Comparator.comparing(Book::getAuthor);
    public static Comparator<Book> compareByTitle = Comparator.comparing(Book::getTitle);
    public static Comparator<Book> compareByPageNum = Comparator.comparingInt(Book::getPageNum);
    public static Comparator<Book> compareByPublishingYear = Comparator.comparingInt(Book::getPublishingYear);
    public static Comparator<Book> compareByDateAdded = (o1, o2) -> {
        if ((o1.getAddedYear() - o2.getAddedYear()) != 0) {
            return (int) (o1.getAddedYear() - o2.getAddedYear());
        } else {
            if ((o1.getAddedMonth() - o2.getAddedMonth()) != 0) {
                return (int) (o1.getAddedMonth() - o2.getAddedMonth());
            } else {
                return (int) (o1.getAddedDay() - o2.getAddedDay());
            }
        }
    };


}
