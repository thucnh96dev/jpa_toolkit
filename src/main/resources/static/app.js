var stompClient = null;
function connect() {
    var socket = new SockJS('/notification', null, {
        'protocols_whitelist': ['websocket']
    });
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (data) {
           console.log(JSON.stringify(data));
        });
    });
}

