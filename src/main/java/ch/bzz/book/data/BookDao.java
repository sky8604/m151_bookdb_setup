package ch.bzz.book.data;

import ch.bzz.book.service.Config;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * data access for book entity
 * <p>
 * M151: BookDB
 *
 * @author Marcel Suter
 * @version 1.0
 * @since 2019-10-13
 */
public class BookDao {

    /**
     * TODO
     *
     * @return
     */
    public Integer count() {

        Connection connection = null;                                         // import java.sql.Connection;
        try {
            InitialContext initialContext = new InitialContext();               // import javax.naming.InitialContext
            DataSource dataSource = (DataSource) initialContext.lookup(Config.getProperty("jdbcRessource"));  // import javax.sql.DataSource
            connection = dataSource.getConnection();

        } catch (NamingException nameEx) {                                    // import javax.naming.NamingException
            nameEx.printStackTrace();
            throw new RuntimeException();
        } catch (SQLException sqlEx) {                                        // import java.sql.SQLException
            sqlEx.printStackTrace();
            throw new RuntimeException();
        }

        PreparedStatement prepStmt = null;          // import java.sql.PreparedStatement
        ResultSet resultSet = null;                 // import java.sql.ResultSet

        int bookCount = 0;
        try {
            prepStmt = connection.prepareStatement("SELECT * FROM Book");
            resultSet = prepStmt.executeQuery();

            while (resultSet.next()) {
                bookCount++;
            }
        } catch (SQLException sqlEx) {              // import java.sql.SQLException

            sqlEx.printStackTrace();
            throw new RuntimeException();
        }  finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (prepStmt != null)
                    prepStmt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
                throw new RuntimeException();
            }
        }


        return bookCount;
    }
}
