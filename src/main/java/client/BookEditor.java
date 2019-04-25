package client;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;
import shared.Book;

import java.util.Date;

public class BookEditor extends DialogBox {

    private DialogBoxAction action = null;

    private int id;
    private String author;
    private String title;
    private int pageNum;
    private int publishingYear;

    private Button ok, cancel;
    private TextBox authorTextBox;
    private TextBox titleTextBox;
    private TextBox pageNumTextBox;
    private TextBox publishingYearTextBox;
    private PopupPanel hint = new PopupPanel();


    public BookEditor(Book book) {
        // Set the dialog box's caption.
        setText("Adding a book to the library");
        setModal(true);
        // Enable animation.
        setAnimationEnabled(true);
        id = book.getId();
        hint.setWidget(new Label("Incorrect input"));
        Label authorLabel = new Label("Author Name:");
        authorTextBox = new TextBox();
        if(id != -1)
            authorTextBox.setText(book.getAuthor());
        authorTextBox.addKeyUpHandler(new MyKeyUpHandler());
        authorTextBox.addMouseOverHandler(new MyMouseOverHandler());
        authorTextBox.addMouseOutHandler(new MyMouseOutHandler());
        //authorTextBox.addStyleName("invalid");
        Label titleLabel = new Label("Title:");
        titleTextBox = new TextBox();
        if(id != -1)
            titleTextBox.setText(book.getTitle());
        titleTextBox.addKeyUpHandler(new MyKeyUpHandler());
        titleTextBox.addMouseOverHandler(new MyMouseOverHandler());
        titleTextBox.addMouseOutHandler(new MyMouseOutHandler());
        //titleTextBox.addStyleName("invalid");
        Label pagesCountLabel = new Label("Pages Count:");
        pageNumTextBox = new TextBox();
        if(id != -1)
            pageNumTextBox.setText(String.valueOf(book.getPageNum()));
        pageNumTextBox.addKeyUpHandler(new MyKeyUpHandler());
        pageNumTextBox.addMouseOverHandler(new MyMouseOverHandler());
        pageNumTextBox.addMouseOutHandler(new MyMouseOutHandler());
        //pageNumTextBox.addStyleName("invalid");
        Label publishedYearLabel = new Label("Published Year:");
        publishingYearTextBox = new TextBox();
        if(id != -1)
            publishingYearTextBox.setText(String.valueOf(book.getPublishingYear()));
        publishingYearTextBox.addKeyUpHandler(new MyKeyUpHandler());
        publishingYearTextBox.addMouseOverHandler(new MyMouseOverHandler());
        publishingYearTextBox.addMouseOutHandler(new MyMouseOutHandler());
        //publishingYearTextBox.addStyleName("invalid");
        // DialogBox is a SimplePanel, so you have to set its widget
        // property to whatever you want its contents to be.
        ok = new Button("OK");
        ok.setEnabled(false);
        ok.addClickHandler(event -> {
            author = authorTextBox.getText();
            title = titleTextBox.getText();
            pageNum = Integer.valueOf(pageNumTextBox.getText());
            publishingYear = Integer.valueOf(publishingYearTextBox.getText());

            BookEditor.this.hide();
            if (action != null) {
                action.applyChanges(id);
            }
        });
        cancel = new Button("Cancel");
        cancel.setEnabled(true);
        cancel.addClickHandler(event -> BookEditor.this.hide());

        HorizontalPanel panel = new HorizontalPanel();
        panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.setSpacing(3);
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.setSpacing(10);
        verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        VerticalPanel authorPanel = new VerticalPanel();
        authorPanel.add(authorLabel);
        authorPanel.add(authorTextBox);
        panel.add(authorPanel);

        VerticalPanel titlePanel = new VerticalPanel();
        titlePanel.add(titleLabel);
        titlePanel.add(titleTextBox);
        panel.add(titlePanel);

        VerticalPanel pagesPanel = new VerticalPanel();
        pagesPanel.add(pagesCountLabel);
        pagesPanel.add(pageNumTextBox);
        panel.add(pagesPanel);

        VerticalPanel yearPanel = new VerticalPanel();
        yearPanel.add(publishedYearLabel);
        yearPanel.add(publishingYearTextBox);
        panel.add(yearPanel);

        verticalPanel.add(panel);

        HorizontalPanel panelButtons = new HorizontalPanel();
        panelButtons.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        panelButtons.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panelButtons.setSpacing(3);
        panelButtons.add(ok);
        panelButtons.add(cancel);
        verticalPanel.add(panelButtons);

        setWidget(verticalPanel);
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPublishingYear() {
        return publishingYear;
    }

    public void setAction(DialogBoxAction action) {
        this.action = action;
    }

    private boolean checkNum(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean checkString(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetterOrDigit(str.charAt(i)) &&
                    str.charAt(i) != '`' &&
                    str.charAt(i) != ' ' &&
                    str.charAt(i) != ',' &&
                    str.charAt(i) != '.' &&
                    str.charAt(i) != '-' &&
                    str.charAt(i) != '?' &&
                    str.charAt(i) != '!' &&
                    str.charAt(i) != '&' &&
                    str.charAt(i) != '"' &&
                    str.charAt(i) != ':' &&
                    str.charAt(i) != ';') {
                return false;
            }
        }
        return true;
    }

    public class MyKeyUpHandler implements KeyUpHandler {

        @Override
        public void onKeyUp(KeyUpEvent keyUpEvent) {
            author = authorTextBox.getText();
            title = titleTextBox.getText();
            try {
                pageNum = Integer.valueOf(pageNumTextBox.getText());
            } catch (Exception ex) {
                ok.setEnabled(false);
                pageNumTextBox.removeStyleName("valid");
                pageNumTextBox.addStyleName("invalid");
            }
            try {
                publishingYear = Integer.valueOf(publishingYearTextBox.getText());
            } catch (Exception ex) {
                ok.setEnabled(false);
                publishingYearTextBox.removeStyleName("valid");
                publishingYearTextBox.addStyleName("invalid");
            }

            boolean isValid = true;
            if (!author.equals("") && checkString(author)) {
                authorTextBox.removeStyleName("invalid");
                authorTextBox.addStyleName("valid");
            } else {
                isValid = false;
                ok.setEnabled(false);
                authorTextBox.removeStyleName("valid");
                authorTextBox.addStyleName("invalid");
            }

            if (!title.equals("") && checkString(title)) {
                titleTextBox.removeStyleName("invalid");
                titleTextBox.addStyleName("valid");
            } else {
                isValid = false;
                ok.setEnabled(false);
                titleTextBox.removeStyleName("valid");
                titleTextBox.addStyleName("invalid");
            }

            if ((pageNum > -1) && checkNum(pageNumTextBox.getText())) {
                pageNumTextBox.removeStyleName("invalid");
                pageNumTextBox.addStyleName("valid");
            } else {
                isValid = false;
                ok.setEnabled(false);
                pageNumTextBox.removeStyleName("valid");
                pageNumTextBox.addStyleName("invalid");
            }

            if ((publishingYear <= new Date().getYear() + 1900) && checkNum(publishingYearTextBox.getText())) {
                publishingYearTextBox.removeStyleName("invalid");
                publishingYearTextBox.addStyleName("valid");
            } else {
                isValid = false;
                ok.setEnabled(false);
                publishingYearTextBox.removeStyleName("valid");
                publishingYearTextBox.addStyleName("invalid");
            }

            ok.setEnabled(isValid);
        }
    }

    public class MyMouseOverHandler implements MouseOverHandler {

        @Override
        public void onMouseOver(MouseOverEvent mouseOverEvent) {
            TextBox tb = (TextBox)mouseOverEvent.getSource();
            hint.setPopupPosition(tb.getAbsoluteLeft(), tb.getAbsoluteTop()+tb.getOffsetHeight());
            if(tb.getStyleName().contains("invalid")) {
                hint.setVisible(true);
            } else {
                hint.setVisible(false);
            }
            hint.show();
        }
    }

    public class MyMouseOutHandler implements MouseOutHandler {

        @Override
        public void onMouseOut(MouseOutEvent mouseOutEvent) {
            hint.hide();
        }
    }
}
