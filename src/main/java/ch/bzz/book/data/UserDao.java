package ch.bzz.book.data;

import ch.bzz.book.model.User;
import ch.bzz.book.service.Config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * data handler for users
 * <p>
 * M151: BookDB
 *
 * @author Marcel Suter
 * @version 1.0
 * @since 2019-10-13
 */
public class UserDao {

    /**
     * search a user with the username/password
     *
     * @return the user object
     */
    public static User getEntity(Map<String, String> filter) {
        BufferedReader bufferedReader;
        FileReader fileReader;
        try {
            fileReader = new FileReader(Config.getProperty("userFile"));
            bufferedReader = new BufferedReader(fileReader);

        } catch (FileNotFoundException fileEx) {
            fileEx.printStackTrace();
            throw new RuntimeException();
        }

        User user = new User();
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";");

                if (
                        filter.get("username").equals(data[0]) &&
                                filter.get("password").equals(data[3])
                ) {
                    user.setUserName(data[0]);
                    user.seteMail(data[1]);
                    user.setUserRole(data[2]);
                }

            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
                throw new RuntimeException();
            }
        }
        return user;
    }
}
