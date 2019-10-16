/**
 * view-controller for bookedit.html
 *
 * M151: BookDB
 *
 * @author  Marcel Suter
 * @since   2019-10-13
 * @version 1.0
 */

/**
 * load the book data
 */
$(document).ready(function () {
    showMessage("empty", " ");
    loadBook();

    /**
     * listener for submitting the form
     */
    $("#bookeditForm").submit(saveBook);

    /**
     * listener for button [abbrechen], redirects to bookshelf
     */
    $("#cancel").click(function () {
        window.location.href = "./bookshelf.html";
    });
});

/**
 *  loads the data of this book
 *
 */
function loadBook() {
    var bookUUID = $.urlParam('uuid');
    if (bookUUID !== null && bookUUID != -1) {
        $
            .ajax({
                url: "./resource/bookshelf/read?uuid=" + bookUUID,
                dataType: "json",
                type: "GET"
            })
            .done(showBook)
            .fail(function (xhr, status, errorThrown) {
                if (xhr.status == 404) {
                    showMessage("warning", "Kein Buch gefunden");
                } else {
                    window.location.href = "./bookshelf.html";
                }
            })
    }
}

/**
 * shows the data of this book
 * @param  book  the book data to be shown
 */
function showBook(book) {
    $("#message").empty();
    $("#title").val(book.title);
    $("#author").val(book.author);
    $("#publisher").val(book.publisher);
    $("#price").val(book.price);
    $("#isbn").val(book.isbn);
}

/**
 * sends the book data to the webservice
 * @param form the form being submitted
 */
function saveBook(form) {
    form.preventDefault();
    $
        .ajax({
            url: "./resource/bookshelf/save?uuid=" + $.urlParam('uuid'),
            dataType: "text",
            type: "POST",
            data: $("#bookeditForm").serialize(),
        })
        .done(function (jsonData) {
            showBook(jsonData);
            window.location.href = "./bookshelf.html";
        })
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 403) {
                location.href = "./login.html";
            } else if (xhr.status == 404) {
                showMessage("warning", "Dieses Buch existiert nicht");
            } else {
                showMessage("alert", "Fehler beim Speichern des Buchs");
            }
        })
}