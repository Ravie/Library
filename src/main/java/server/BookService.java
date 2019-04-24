package server;

import shared.Book;
import shared.BookPagination;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/books")
public class BookService {
    private BookModel bookModel = new BookModel();

    @GET
    @Path("/{page}/{booksNum}/{sorting}")
    @Produces(MediaType.APPLICATION_JSON)
    public BookPagination getAllBooks(@PathParam("page") int page, @PathParam("booksNum") int booksNum, @PathParam("sorting") String sorting) {
        return bookModel.getAllBooks(page, booksNum, sorting);
    }

    @POST
    @Path("/add/{page}/{booksNum}/{sorting}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public BookPagination addBook(@PathParam("page") int page, @PathParam("booksNum") int booksNum, @PathParam("sorting") String sorting, Book book) {
        return bookModel.addBook(book, page, booksNum, sorting);
    }

    @POST
    @Path("/edit/{page}/{booksNum}/{sorting}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public BookPagination editBook(@PathParam("page") int page, @PathParam("booksNum") int booksNum, @PathParam("sorting") String sorting, Book book){
        return bookModel.editBook(book, page, booksNum, sorting);
    }

    @DELETE
    @Path("/delete/{id}/{page}/{booksNum}/{sorting}")
    @Produces(MediaType.APPLICATION_JSON)
    public BookPagination deleteBook(@PathParam("id") int id, @PathParam("page") int page, @PathParam("booksNum") int booksNum, @PathParam("sorting") String sorting) {
        return bookModel.deleteBook(id, page, booksNum, sorting);
    }

}
