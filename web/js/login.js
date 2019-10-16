/**
 * view-controller for login.html
 *
 * M151: BookDB
 *
 * @author  Marcel Suter
 * @since   2019-10-13
 * @version 1.0
 */

/**
 * register listeners
 */
$(document).ready(function () {
    showMessage("empty", " ");

    /**
     * listener for submitting the form sends the login data to the web service
     */
    $("#loginForm").submit(sendLogin);

    /**
     * listener for button [Abmelden]
     */
    $("#logoff").click(sendLogoff);


});

/**
 * sends the login-request
 * @param form the form with the username/password
 */
function sendLogin(form) {
    form.preventDefault();
    $
        .ajax({
            url: "./resource/user/login",
            dataType: "text",
            type: "POST",
            data: $("#loginForm").serialize()
        })
        .done(function () {
            window.location.href = "./bookshelf.html";
        })
        .fail(function (xhr) {
            if (xhr.status == 401) {
                showMessage("warning", "Benutzername/Passwort unbekannt");
            } else {
                showMessage("error", "Es ist ein Fehler aufgetreten");
            }
        })
}

/**
 * sends the logoff-request
 */
function sendLogoff() {
    $
        .ajax({
            url: "./resource/user/logoff",
            dataType: "text",
            type: "GET"
        })
        .done(function () {
            window.location.href = "./login.html";
        })
        .fail(function () {
            showMessage("error", "Es ist ein Fehler aufgetreten");
        })
}

