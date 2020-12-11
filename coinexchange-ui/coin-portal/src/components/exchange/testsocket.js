var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#userinfo").html("");
}

function connect() {
    // var socket = new SockJS('/tick/ws');
    var socket = new SockJS('http://54.238.242.99/tick/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        var usessionId= "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        var symbol= $('#symbol').val();
        var period= $('#period').val();
/*       stompClient.send("/user/subscribe", {usessionId: usessionId}, JSON.stringify({
            'symbol': symbol,
            'period': period
        }));*/
        stompClient.subscribe('/topic/'+symbol+'.'+period, function (res) {

        });
/*        */
    });
    socket.onclose = function() {
        console.log('connection closed');
    };
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/topic/subscribe", {}, JSON.stringify({'payload': $("#name").val()}));
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});
