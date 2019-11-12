package ch.bzz.book.service;


import ch.bzz.book.data.BookDao;
import ch.bzz.book.model.Book;
import ch.bzz.book.util.TokenHandler;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * service controller for books
 * <p>
 * M151: BookDB
 *
 * @author Marcel Suter
 * @version 1.0
 * @since 2019-10-13
 */
@Path("bookshelf")
public class BookService {
    /**
     * produces the number of books
     *
     * @param token encrypted authorization token
     * @return Response
     */
    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)

    public Response countBooks(
            @CookieParam("jwtoken") String token
    ) {

        int httpStatus = 200;
        Integer bookCount = new BookDao().count();

        return Response
                .status(httpStatus)
                .entity("{\"bookCount\":" + bookCount + "}")
                .build();
    }

    /**
     * produces a list of all books
     *
     * @param token encrypted authorization token
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public Response listBooks(
            @CookieParam("jwtoken") String token
    ) {

        int httpStatus = 403;
        List<Book> bookList = new ArrayList<>();
        if (token != null) {
            Map<String, String> claimMap = TokenHandler.readClaims(token);
            String tokenData = claimMap.getOrDefault("subject", null);
            String[] data = tokenData.split(";");
            if (data[0] != null && !data[0].equals("guest")) {
                httpStatus = 200;
            }
        }

        return Response
                .status(httpStatus)
                .entity(bookList)
                .build();
    }

    /**
     * reads a single book identified by the bookId
     *
     * @param bookUUID the bookUUID in the URL
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

    public Response readBook(
            @QueryParam("uuid") String bookUUID,
            @CookieParam("jwtoken") String token
    ) {
        Book book = new Book();
        int httpStatus = 403;
        if (token != null) {
            Map<String, String> claimMap = TokenHandler.readClaims(token);
            String tokenData = claimMap.getOrDefault("subject", null);
            String[] data = tokenData.split(";");
            if (data[0] != null && !data[0].equals("guest")) {
                httpStatus = 200;
            }
        }
        return Response
                .status(httpStatus)
                .entity(book)
                .build();
    }

    @POST
    @Path("save")
    @Produces(MediaType.TEXT_PLAIN)
    public Response saveBook(
            @Context Book book,
            @CookieParam("jwtoken") String token
    ) {
        int httpStatus = 403;
        if (token != null) {
            Map<String, String> claimMap = TokenHandler.readClaims(token);
            String tokenData = claimMap.getOrDefault("subject", null);
            String[] data = tokenData.split(";");
            if (data[0] != null && !data[0].equals("guest")) {
                httpStatus = 200;
            }
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteBook(
            @QueryParam("uuid") String bookUUID,
            @CookieParam("jwtoken") String token
    ) {
        int httpStatus = 403;
        if (token != null) {
            Map<String, String> claimMap = TokenHandler.readClaims(token);
            String tokenData = claimMap.getOrDefault("subject", null);
            String[] data = tokenData.split(";");
            if (data[0] != null && !data[0].equals("guest")) {
                httpStatus = 200;
            }
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}
