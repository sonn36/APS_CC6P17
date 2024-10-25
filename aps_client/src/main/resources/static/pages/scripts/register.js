const form = document.querySelector("#registerForm");

form.addEventListener('submit', event => {
    event.preventDefault();
    registerUser();
})

async function registerUser() {

    const username_txt = document.getElementById('username').value;
    const email_txt = document.getElementById('email').value;
    const password_txt = document.getElementById('password').value;

    try {
        let response = await fetch('http://localhost:8081/users', {
            method: 'POST',
            body: JSON.stringify({
                name: username_txt,
                email: email_txt,
                nivelAcesso: 1,
                password: password_txt
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(response.status);
        }

        let result = await response.json();
        window.location = "registerFinger.html?id=" + result.id;
    } catch (error) {
        console.error(error.message);
    }


}

async function findUser(id) {
    try {
        let response = await fetch('http://localhost:8081/users/' + id)

        if (!response.ok) {
            throw new Error(response.status);
        }

        let result = await response.json();
        return result;
    } catch (error) {
        console.error(error.message);
    }
}

