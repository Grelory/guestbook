const regex = /^([a-zA-Z0-9\s\!\?_,\.''-])+$/;

const entries = document.querySelector("div.content div.entries");
const form = document.querySelector("div.content form");

function validateMessage(message, name) {
    return regex.test(message) && regex.test(name);
}

form.addEventListener('submit', function(event) {
    event.preventDefault();

    if (validateMessage(this.message.value, this.name.value) === false) return alert("Not appropriate message!");

    const data = `message=${this.message.value}&name=${this.name.value}`
    saveMessageAndReload(data);
    this.message.value = this.name.value = "";
})

function saveMessageAndReload(data) {
    fetch("http://localhost:8000/message-receiver", {
        method: "POST",
        body: data
    })
        .then(function(response) {
            console.log(response.status);
        })
    loadAllMessages();
}

function loadAllMessages() {
    entries.innerHTML = "";
    fetch("http://localhost:8000/records")
        .then(function(response) {
            return response.json();
        })
        .then(function(messages) {
            messages.forEach(message => {
                entries.insertAdjacentHTML("beforeend", `
                    <div class="entity">
                        <p class="message"><strong>${message.message}</strong></p>
                        <p class="name">Name: <strong>${message.name}</strong></p>
                        <p class="date">Date: ${message.date}</p>
                    </div>
                `);
            })
        })

}

loadAllMessages();