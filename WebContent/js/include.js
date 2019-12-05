$(function () {
    $.get("header.html",function (data) {
        $("#header").html(data);
    });
    $.get("footer.jsp",function (data) {
        $("#footer").html(data);
    });
});