const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/greetings', (greeting) => {
        showMessage(JSON.parse(greeting.body).content);
        
    });
    sendMessage("-1");
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};


function connect() {
    stompClient.activate();
}


function sendID() {
    const idUser = new URLSearchParams(location.search).get('id');
    sendMessage(idUser);
}

function sendMessage(message) {
    stompClient.publish({
        destination: "/app/hello",
        body: JSON.stringify({ 'content': message })
    });
}

function showMessage(message) {
    console.log(message);
    if(message.startsWith("Digital Cadastrada") || message.startsWith("Digital Encontrada")){

        const idUser = message.substr(message.indexOf("ID: ") + 4, message.length - message.indexOf("ID: ") + 4);
        console.log(idUser);
        var user = findUser(idUser);
        user.then(function(result) {
            console.log(result);
            if(result.id == idUser){
                showHome(result);
            }
        })
        
    }
    
    if(message.startsWith("Erro:")){
        $("#message").css("color", "red");
        $("#btn").prop('disabled', false);
        $("#btn").html("Tentar Novamente");
    }
    else{
        $("#message").css("color", "black");
        $("#btn").prop('disabled', true);
    }
    if(message.startsWith('Please type in the ID')){
        sendID();
    }
    else{
        
        $("#message").text(message);
    }
    
}

function registerFingerprint() {
    sendMessage("1");
}

function login() {
    sendMessage("2");
}

function showHome(user) {
    $("body").html("<div class='d-flex justify-content-center align-items-center text-center'><h3>Ola " + user.name + " seu nivel de acesso é " + user.nivelAcesso + "</h3></div>");
    
}
connect();







