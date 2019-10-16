package ch.bzz.book.service;

import ch.bzz.book.data.UserDao;
import ch.bzz.book.model.User;
import ch.bzz.book.util.TokenHandler;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * service controller for authentication
 * <p>
 * M151: BookDB
 *
 * @author Marcel Suter
 * @version 1.0
 * @since 2019-10-13
 */
@Path("user")
public class UserService {

    /**
     * authenticate the user with username/password
     *
     * @param username the username
     * @param password the password
     * @return empty String
     */
    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password
    ) {

        UserDao userDao = new UserDao();
        Map<String, String> filter = new HashMap<>();
        filter.put("username", username);
        filter.put("password", password);
        User user = userDao.getEntity(filter);

        int httpStatus;
        String tokenData;
        if (user.getUserRole().equals("guest")) {
            httpStatus = 401;
            tokenData = null;
        } else {
            httpStatus = 200;
            tokenData = user.getUserName() + ";" + user.getUserRole();
        }

        NewCookie tokenCookie = TokenHandler.buildCookie(tokenData);
        return Response
                .status(httpStatus)
                .entity("")
                .cookie(tokenCookie)
                .build();
    }

    /**
     * logoff the user and destroy the session
     *
     * @return Response
     */
    @GET
    @Path("logoff")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logoff(
    ) {
        int httpStatus = 200;
        NewCookie tokenCookie = TokenHandler.buildCookie(null);
        return Response
                .status(httpStatus)
                .entity("")
                .cookie(tokenCookie)
                .build();
    }

    /**
     * returns the username and role of the authenticated user
     *
     * @param token cookie with the jwtoken
     * @return map as json
     */
    @GET
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response authUser(
            @CookieParam("jwtoken") String token
    ) {
        int httpStatus = 403;

        Map<String, String> userData = new HashMap<>();

        if (token != null) {
            Map<String, String> claimMap = TokenHandler.readClaims(token);
            String tokenData = claimMap.getOrDefault("subject", null);
            if (tokenData != null) {
                String[] data = tokenData.split(";");
                userData.put("username", data[0]);
                userData.put("userRole", data[1]);
            }
        }

        return Response
                .status(httpStatus)
                .entity(userData)
                .build();
    }

}
