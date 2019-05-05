package server;

import shared.Book;
import shared.BookPagination;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BookModel {

    private int size;

    public BookPagination getAllBooks(int page, int booksNum, String sorting) {
        List<Book> books = new ArrayList<>();
        try {
            new FileWriter("books.txt", true);
            File file = new File("books.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String book = bufferedReader.readLine();
            try {
                Integer.valueOf(book);
            } catch (NumberFormatException ex) {
                System.out.println("creating Library");
                createLib();
            }
            System.out.println("reading Library");
            books = readFromFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("returning Library");
        return sortPage(books, page, booksNum, sorting);
    }

    public BookPagination addBook(Book book, int booksNum, String sorting) {
        List<Book> books = readFromFile();
        BookPagination bp = new BookPagination();
        size++;
        book.setId(size);
        books.add(book);
        writeToFile(books);
        int curPage = 1;
        if (booksNum != -1)
            curPage = searchPage(book, booksNum, sorting);
        bp = sortPage(books, curPage, booksNum, sorting);
        bp.setCurrentPage(curPage);
        return bp;
    }

    public BookPagination deleteBook(int id, int page, int booksNum, String sorting) {
        List<Book> books = readFromFile();
        for (Book book : books)
            if (book.getId() == id) {
                books.remove(book);
                break;
            }
        writeToFile(books);
        return sortPage(books, page, booksNum, sorting);
    }

    public BookPagination editBook(Book newBook, int page, int booksNum, String sorting) {
        List<Book> books = readFromFile();
        for (Book book : books)
            if (book.getId() == newBook.getId()) {
                books.set(books.indexOf(book),newBook);
                break;
            }
        writeToFile(books);
        return sortPage(books, page, booksNum, sorting);
    }

    private Integer searchPage(Book book, int booksNum, String sorting) {
        List<Book> library = getAllBooks(1, -1, sorting).getBooks();
        int page = 0;
        for (int curPage = 1; curPage * booksNum <= library.size(); curPage++) {
            List<Book>  booksOnPage = library.subList((curPage - 1) * booksNum, curPage * booksNum);
            for(Book bookOnPage : booksOnPage)
                if(bookOnPage.getId()==book.getId())
                    page = curPage;
        }
        return page;
    }

    public BookPagination sortPage(List<Book> books, int page, int booksNum, String sorting) {
        List<Book> booksOnPage;
        BookPagination bookPagination = new BookPagination();
        booksOnPage = BookSort.makeSort(books, sorting);
        bookPagination.setSize(booksOnPage.size());
        System.out.println("------------------->>>>" + page);
        if (page == 1) {
            switch (booksNum) {
                case 3:
                    if (booksOnPage.size() > 3)
                        booksOnPage = booksOnPage.subList(0, 3);
                    break;
                case 10:
                    if (booksOnPage.size() > 10)
                        booksOnPage = booksOnPage.subList(0, 10);
                    break;
            }
        } else {
            if (booksNum * page > booksOnPage.size()) {
                booksOnPage = booksOnPage.subList((page - 1) * booksNum, booksOnPage.size());
            } else {
                booksOnPage = booksOnPage.subList((page - 1) * booksNum, page * booksNum);
            }
        }
        bookPagination.setBooks(booksOnPage);
        return bookPagination;
    }

    private void createLib() {
        File file = new File("books.txt");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, false);
            size = 5;
            fileWriter.write(String.valueOf(size));
            fileWriter.write('\n');
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String book = new String("1###J. R. R. Tolkien###The Fellowship of the Ring###423###1954###2000###11###01");
        try {
            fileWriter.write(book);
            fileWriter.write('\n');
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        book = new String("2##J. R. R. Tolkien###The Two Towers###352###1954###2000###11###19");
        try {
            fileWriter.write(book);
            fileWriter.write('\n');
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        book = new String("3###J. R. R. Tolkien###The Return of the King###416###1955###2000###12###26");
        try {
            fileWriter.write(book);
            fileWriter.write('\n');
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        book = new String("4###Ray Bradbury###Fahrenheit 451###158###1953###2000###10###19");
        try {
            fileWriter.write(book);
            fileWriter.write('\n');
            fileWriter.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        book = new String("5###Agatha Christie###And Then There Were None###272###1939###2000###10###19");
        try {
            fileWriter.write(book);
            fileWriter.write('\n');
            fileWriter.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void writeToFile(List<Book> books) {
        try {
            File file = new File("books.txt");
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(String.valueOf(size));
            fileWriter.write('\n');
            for (Book book : books) {
                String bookLine = new String(book.getId() + "###" +
                        book.getAuthor() + "###" +
                        book.getTitle() + "###" +
                        book.getPageNum() + "###" +
                        book.getPublishingYear() + "###" +
                        book.getAddedYear() + "###" +
                        book.getAddedMonth() + "###" +
                        book.getAddedDay());
                fileWriter.write(bookLine);
                fileWriter.write('\n');
            }
            fileWriter.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<Book> readFromFile() {
        List<Book> books = new ArrayList<>();
        try {
            File file = new File("books.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String bookLine = bufferedReader.readLine();
            size = Integer.valueOf(bookLine);
            System.out.println("Books in library: " + size);
            bookLine = bufferedReader.readLine();
            while (bookLine != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(bookLine, "###");
                books.add(new Book(Integer.valueOf(stringTokenizer.nextToken()),
                        stringTokenizer.nextToken(), stringTokenizer.nextToken(),
                        Integer.valueOf(stringTokenizer.nextToken()),
                        Integer.valueOf(stringTokenizer.nextToken()),
                        Integer.valueOf(stringTokenizer.nextToken()),
                        Integer.valueOf(stringTokenizer.nextToken()),
                        Integer.valueOf(stringTokenizer.nextToken())));
                bookLine = bufferedReader.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return books;
    }
}
