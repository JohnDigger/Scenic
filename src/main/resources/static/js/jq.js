$(document).ready(function () {
    $("#password").focus(function () {
        setTimeout(function () {
            $(".display_hand_left").css("display", "block");
            $(".display_hand_right").css("display", "block");
        }, 300)

        $(".hand_left").css("display", "none");
        $(".hand_right").css("display", "none");
        $(".txt").html("");
        $(".handing_left").css("display", "block");
        $(".handing_right").css("display", "block");
    });

    $("#password").blur(function () {
        $(".display_hand_left").css("display", "none");
        $(".display_hand_right").css("display", "none");
        $(".hand_left").css("display", "block");
        $(".hand_right").css("display", "block");
        $(".handing_left").css("display", "none");
        $(".handing_right").css("display", "none");
    });

 });

$(function () {
    $("#out").height($(document).height());
    $("#out").width($(window).width());
});