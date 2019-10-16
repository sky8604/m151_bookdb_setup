package ch.bzz.book.model;

/**
 * a user for authentication and authorization
 * <p>
 * M151: BookDB
 *
 * @author Marcel Suter
 * @version 1.0
 * @since 2019-10-13
 */
public class User {
    private String userName;
    private String eMail;
    private String userRole;

    /**
     * Gets the userName
     *
     * @return value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the userName
     *
     * @param userName the value to set
     */

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the eMail
     *
     * @return value of eMail
     */
    public String geteMail() {
        return eMail;
    }

    /**
     * Sets the eMail
     *
     * @param eMail the value to set
     */

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    /**
     * Gets the userRole
     *
     * @return value of userRole
     */
    public String getUserRole() {
        if (this.userRole == null) {
            setUserRole("guest");
        }
        return userRole;
    }

    /**
     * Sets the userRole
     *
     * @param userRole the value to set
     */

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
