const stompClient = new StompJs.Client({
    brokerURL: `ws://${window.location.host}/gs-guide-websocket`
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);

    // vehicles topic (new)
    stompClient.subscribe('/topic/vehicles', (message) => {
        const vehicles = JSON.parse(message.body);
        showVehicles(vehicles);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('WebSocket error', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker error: ' + frame.headers['message']);
    console.error('Details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);

    $("#getVehicles").prop("disabled", !connected);

    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }

    $("#vehicles").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

// 👇 this triggers server-side streaming
function getVehicles() {
    stompClient.publish({
        destination: "/app/vehicles/request",
        body: ""
    });
}

function showVehicles(vehicles) {
    $("#vehicles").html(""); // clear table

    vehicles.forEach(v => {
        $("#vehicles").append(`
            <tr>
                <td>${v.line ?? ''}</td>
                <td>${v.routeShortName ?? ''}</td>
                <td>${v.routeLongName ?? ''}</td>
                <td>${v.tripId ?? ''}</td>
                <td>${v.stopName ?? ''}</td>
                <td>${v.latitude ?? ''}</td>
                <td>${v.longitude ?? ''}</td>
            </tr>
        `);
    });
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());

    $("#connect").click(connect);
    $("#disconnect").click(disconnect);
    $("#getVehicles").click(getVehicles);
});