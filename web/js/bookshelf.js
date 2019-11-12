/**
 * view-controller for bookshelf.html
 *
 * M151: BookDB
 *
 * @author  Marcel Suter
 * @since   2019-10-15
 * @version 1.0
 */

/**
 * loads all the books
 */
$(document).ready(function () {
    showMessage("info", "Bücher werden geladen");
    loadBooks();
    countBooks();

    /**
     * listener for buttons within shelfForm
     */
    $("#shelfForm").on("click", "button", function () {
        if (confirm("Wollen Sie dieses Buch wirklich löschen?")) {
            deleteBook(this.value);
        }
    });
});

/**
 * loads the books from the webservice
 *
 */
function loadBooks() {
    $
        .ajax({
            url: "./resource/bookshelf/list",
            dataType: "json",
            type: "GET"
        })
        .done(showBooks)
        .fail(function (xhr) {
            if (xhr.status == 403) {
                location.href = "./login.html";
            } else {
                showMessage("error", "Fehler beim Lesen der Bücher");
            }
        })
}

/**
 * gets the bookcount from the webservice
 *
 */
function countBooks() {
    $
        .ajax({
            url: "./resource/bookshelf/count",
            dataType: "json",
            type: "GET"
        })
        .done(showCount)
        .fail(function (xhr) {
            if (xhr.status == 403) {
                location.href = "./login.html";
            } else {
                showMessage("error", "Fehler beim Lesen der Bücher");
            }
        })
}

/**
 * shows all books as a table
 *
 * @param bookData all books as an array
 */
function showBooks(bookData) {
    var tableData = "";
    $.each(bookData, function (index, book) {
        tableData += "<tr>";
        tableData += "<td>" + book.title + "</td>";
        tableData += "<td>" + book.author + "</td>";
        tableData += "<td>" + book.publisher + "</td>";
        tableData += "<td>" + book.price + "</td>";
        tableData += "<td>" + book.isbn + "</td>";
        tableData += "<td><a href='./bookedit.html?uuid=" + book.bookUUID + "'>Bearbeiten</a></td>";
        tableData += "<td><button type='button' id='delete_" + index + "' value='" + book.bookUUID + "'>Löschen</button></td>";
        tableData += "</tr>";
    });
    $("#bookshelf > tbody").html(tableData);
    showMessage("empty", " ");
}

/**
 * shows the book count
 * @param data
 */
function showCount(data) {
    $('#bookCount').text(data.bookCount);
}

/**
 * send delete request for a book
 * @param bookUUID
 */
function deleteBook(bookUUID) {
    $
        .ajax({
            url: "./resource/bookshelf/delete?uuid=" + bookUUID,
            dataType: "text",
            type: "DELETE",
        })
        .done(function () {
            loadBooks();
            showMessage("success","Buch gelöscht");

        })
        .fail(function () {
            if (xhr.status == 403) {
                location.href = "./login.html";
            } else {
                showMessage("warning", "Fehler beim Löschen des Buchs");
            }
        })
}