function login() {
    'use strict';
    (async () => {
        let user = {
            username: username.value,
            password: password.value
        };

        let response = await fetch('/authenticate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(user)
        });

        let result = await response.json();
        if (response.status === 200){
            localStorage.setItem("sessionId", result.token);
            location.href = '/friends.html';
        }})()
}

function logout() {
    localStorage.clear();
}

function isLogin() {
    let ses = localStorage.getItem("sessionId")
    if (ses == null) {
        location.href = "/login.html"; // перенаправляет браузер на другой URL
    }

}

async function showMessages() {
    let response = await fetch('/message', {
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': `Bearer ${localStorage.getItem("sessionId")}`
            }
        }
    ).then(response => response.json());
    for (let p in response) {
        console.log(response[p]);
        let div = document.createElement('div');
        div.className = "alert";
        div.innerHTML = "<strong>" + response[p].sender.username + " </strong>" + response[p].text;
        document.body.append(div);
    }
}

async function showFriends() {
    let response = await fetch('/user', {
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': `Bearer ${localStorage.getItem("sessionId")}`
            }
        }
    ).then(response => response.json());
    for (let user in response) {
        // console.log(response[user]);
        let div = document.createElement('div');
        div.id = `user${response[user].id}`;
        div.className = "alert";
        div.innerHTML = "<strong>" + response[user].username + " </strong><button onclick='showPC(" + response[user].id + ")'>Messages</button>";
        document.body.append(div);
    }
}

async function showPC(userId) {
    let response = await fetch(`/message/pc/${userId}`, {
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': `Bearer ${localStorage.getItem("sessionId")}`
            }
        }
    ).then(response => response.json());

    if (document.getElementById(`message${userId}`) !== null) {
        document.getElementById(`message${userId}`).remove();
    }

    let divUser = document.getElementById(`user${userId}`);
    let divUserMessages = document.createElement('div');
    divUserMessages.id = `message${userId}`;
    divUserMessages.className = "message";
    divUser.appendChild(divUserMessages);

    for (let p in response.messages) {
        let divUserMessage = document.createElement('div');
        divUserMessage.innerHTML = "<strong>" + response.messages[p].sender.username + " </strong>" + response.messages[p].text;
        divUserMessages.appendChild(divUserMessage);
    }

    let divMessagePanel = document.createElement('div');
    divMessagePanel.id = `messPanel${userId}`;
    divMessagePanel.innerHTML =
        `<input type="text" id="messageText${userId}" name="MessageText" size="50"> <br>` +
        `<input type="button" onclick="sendMessage(${userId}, ${`messageText${userId}.value`})" value="Send">`;
    divUserMessages.append(divMessagePanel);
}

async function sendMessage(userId, text) {
    let message = {
        recipient: userId,
        text: text
    };

    let response = await fetch('/message', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': `Bearer ${localStorage.getItem("sessionId")}`
        },
        body: JSON.stringify(message)
    });

    let result = await response.json();

}
