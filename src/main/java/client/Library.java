package client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import shared.Book;
import shared.BookPagination;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.Date;

public class Library implements EntryPoint, DialogBoxAction {
    private BookEditor dialogBox;
    private VerticalPanel mainPanel = new VerticalPanel();
    private HorizontalPanel addPanel = new HorizontalPanel();
    private HorizontalPanel pagesPanel = new HorizontalPanel();
    private HorizontalPanel pages = new HorizontalPanel();
    private FlexTable booksFlexTable = new FlexTable();
    private Label labelShow = new Label("Show:");
    private Button add = new Button("Add a book");
    private Button show3 = new Button("3");
    private Button show10 = new Button("10");
    private Button showAll = new Button("All");
    private BookService service = GWT.create(BookService.class);
    private String sorting = "id";
    private int selectedPage = 1;
    private int booksNum = -1;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {


        String root = Defaults.getServiceRoot();
        root = root.replace("Library/", "");
        Defaults.setServiceRoot(root);

        labelShow.removeStyleName("gwt-Button");
        labelShow.addStyleName("paginationPanel");
        pagesPanel.add(labelShow);
        show3.removeStyleName("gwt-Button");
        show3.addStyleName("paginationPanel");
        pagesPanel.add(show3);
        show10.removeStyleName("gwt-Button");
        show10.addStyleName("paginationPanel");
        pagesPanel.add(show10);
        showAll.removeStyleName("gwt-Button");
        showAll.addStyleName("paginationPanel");
        pagesPanel.add(showAll);
        addPanel.add(add);
        addPanel.addStyleName("addPanel");
        pagesPanel.add(addPanel);
        mainPanel.add(pagesPanel);

        createTable();
        mainPanel.add(booksFlexTable);

        mainPanel.add(pages);

        show3.addClickHandler(clickEvent -> {
            booksNum = 3;
            service.getAllBooks(selectedPage, booksNum, sorting, new PagingMethodCallBack());
        });
        show10.addClickHandler(clickEvent -> {
            booksNum = 10;
            service.getAllBooks(selectedPage, booksNum, sorting, new PagingMethodCallBack());
        });
        showAll.addClickHandler(clickEvent -> {
            booksNum = -1;
            service.getAllBooks(selectedPage, booksNum, sorting, new PagingMethodCallBack());
        });

        RootPanel.get("library").add(mainPanel);

        add.addClickHandler(clickEvent -> openBookEditor(new Book()));

    }


    private void createTable() {

        service.getAllBooks(selectedPage, booksNum, sorting, new MethodCallback<BookPagination>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("load failed");
            }

            @Override
            public void onSuccess(Method method, BookPagination bookPagination) {
                drawTable(bookPagination);
            }
        });

    }

    private void drawTable(BookPagination bookPagination) {

        mainPanel.remove(booksFlexTable);
        booksFlexTable = new FlexTable();
        mainPanel.add(booksFlexTable);

        mainPanel.remove(pages);
        pages = new HorizontalPanel();
        if (booksNum != -1) {
            if (bookPagination.getSize() != 0) {
                int numberPages = bookPagination.getSize() / booksNum;
                if ((bookPagination.getSize() % booksNum) != 0)
                    numberPages++;

                for (int i = 1; i <= numberPages; i++) {
                    final int number = i;
                    Button page = new Button(String.valueOf(number));
                    page.removeStyleName("gwt-Button");
                    page.addStyleName("pageButton");
                    if (page.getText().equals(String.valueOf(selectedPage))) {
                        page.addStyleName("selectedButton");
                    }
                    page.addClickHandler(clickEvent ->
                            service.getAllBooks(number, booksNum, sorting, new MethodCallback<BookPagination>() {
                                @Override
                                public void onFailure(Method method, Throwable throwable) {
                                    Window.alert("change page fail");
                                }

                                @Override
                                public void onSuccess(Method method, BookPagination bookPagination1) {
                                    selectedPage = number;
                                    drawTable(bookPagination1);
                                }
                            })
                    );
                    pages.add(page);
                }
            }
            mainPanel.add(pages);
        }

        Button id = new Button("Id");
        id.removeStyleName("gwt-Button");
        id.addStyleName("column");
        if (sorting.equals("id")) id.addStyleName("sortByColumn");
        id.addClickHandler(clickEvent -> {
            sorting = "id";
            service.getAllBooks(selectedPage, booksNum, sorting, new SortMethodCallBack());
        });
        Button author = new Button("Author");
        author.removeStyleName("gwt-Button");
        author.addStyleName("column");
        if (sorting.equals("author")) author.addStyleName("sortByColumn");
        author.addClickHandler(clickEvent -> {
            sorting = "author";
            service.getAllBooks(selectedPage, booksNum, sorting, new SortMethodCallBack());
        });
        Button title = new Button("Title");
        title.removeStyleName("gwt-Button");
        title.addStyleName("column");
        if (sorting.equals("title")) title.addStyleName("sortByColumn");
        title.addClickHandler(clickEvent -> {
            sorting = "title";
            service.getAllBooks(selectedPage, booksNum, sorting, new SortMethodCallBack());
        });
        Button pages = new Button("Pages");
        pages.removeStyleName("gwt-Button");
        pages.addStyleName("column");
        if (sorting.equals("pageNum")) pages.addStyleName("sortByColumn");
        pages.addClickHandler(clickEvent -> {
            sorting = "pageNum";
            service.getAllBooks(selectedPage, booksNum, sorting, new SortMethodCallBack());
        });
        Button published = new Button("Published");
        published.removeStyleName("gwt-Button");
        published.addStyleName("column");
        if (sorting.equals("publishingYear")) published.addStyleName("sortByColumn");
        published.addClickHandler(clickEvent -> {
            sorting = "publishingYear";
            service.getAllBooks(selectedPage, booksNum, sorting, new SortMethodCallBack());
        });
        Button addDate = new Button("Date of change");
        addDate.removeStyleName("gwt-Button");
        addDate.addStyleName("column");
        if (sorting.equals("dateAdded")) addDate.addStyleName("sortByColumn");
        addDate.addClickHandler(clickEvent -> {
            sorting = "dateAdded";
            service.getAllBooks(selectedPage, booksNum, sorting, new SortMethodCallBack());
        });

        booksFlexTable.setWidget(0, 0, id);
        booksFlexTable.setWidget(0, 1, author);
        booksFlexTable.setWidget(0, 2, title);
        booksFlexTable.setWidget(0, 3, pages);
        booksFlexTable.setWidget(0, 4, published);
        booksFlexTable.setWidget(0, 5, addDate);

        booksFlexTable.getRowFormatter().addStyleName(0, "header");
        booksFlexTable.addStyleName("list");

        for (Book book : bookPagination.getBooks()) {
            addRow(book);
        }
    }

    private void addRow(Book book) {
        int row = booksFlexTable.getRowCount();
        booksFlexTable.setText(row, 0, String.valueOf(book.getId()));
        booksFlexTable.setText(row, 1, book.getAuthor());
        booksFlexTable.setText(row, 2, book.getTitle());
        booksFlexTable.setText(row, 3, String.valueOf(book.getPageNum()));
        booksFlexTable.setText(row, 4, String.valueOf(book.getPublishingYear()));
        booksFlexTable.setText(row, 5, book.getAddedYear() + "." + book.getAddedMonth() + "." + book.getAddedDay());

        Button edit = new Button("Edit");
        edit.addClickHandler(clickEvent -> openBookEditor(book));
        booksFlexTable.setWidget(row, 7, edit);

        Button delete = new Button("x");
        delete.addClickHandler(clickEvent -> service.deleteBook(selectedPage, booksNum, sorting, book.getId(), new MethodCallback<BookPagination>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("delete failed");
            }

            @Override
            public void onSuccess(Method method, BookPagination bookPagination) {
                drawTable(bookPagination);
            }
        }));
        booksFlexTable.setWidget(row, 8, delete);
    }


    class PagingMethodCallBack implements MethodCallback<BookPagination> {

        @Override
        public void onFailure(Method method, Throwable throwable) {
            Window.alert("paging failed");
        }

        @Override
        public void onSuccess(Method method, BookPagination bookPagination) {
            drawTable(bookPagination);
        }
    }

    class SortMethodCallBack implements MethodCallback<BookPagination> {

        @Override
        public void onFailure(Method method, Throwable throwable) {
            Window.alert("sort failed");
        }

        @Override
        public void onSuccess(Method method, BookPagination bookPagination) {
            drawTable(bookPagination);
        }
    }

    private void openBookEditor(Book book) {
        dialogBox = new BookEditor(book);
        dialogBox.setAction(this);
        dialogBox.center();
        dialogBox.show();
    }

    @Override
    public void applyChanges(int action) {
        if(action == -1) {
            service.addBook(booksNum, sorting, new Book(dialogBox.getAuthor(), dialogBox.getTitle(), dialogBox.getPageNum(), dialogBox.getPublishingYear(), new Date().getYear() + 1900, new Date().getMonth() + 1, new Date().getDate()), new MethodCallback<BookPagination>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    Window.alert("add failed");
                }

                @Override
                public void onSuccess(Method method, BookPagination bookPagination) {
                    selectedPage = bookPagination.getCurrentPage();
                    drawTable(bookPagination);
                }
            });
        } else {
            service.editBook(selectedPage, booksNum, sorting, new Book(dialogBox.getId(), dialogBox.getAuthor(), dialogBox.getTitle(), dialogBox.getPageNum(), dialogBox.getPublishingYear(), new Date().getYear() + 1900, new Date().getMonth() + 1, new Date().getDate()), new MethodCallback<BookPagination>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    Window.alert("edit failed");
                }

                @Override
                public void onSuccess(Method method, BookPagination bookPagination) {
                    drawTable(bookPagination);
                }
            });
        }
    }
}
