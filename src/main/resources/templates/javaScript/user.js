alert("WORK!!!")
// let url = "http://localhost:8080/api/user";
// let response = await fetch(url);
//
// if (response.ok) {
//     let json = await response.json();
//     alert("OK: " + json[0].email);
// } else {
//     alert("Error HTTP: " + response.status);
// }

document.addEventListener('DOMContentLoaded', async function () {
    await showUserEmailOnNavbar()
    await fillTableAboutUser();
});

async function dataAboutCurrentUser() {
    const response = await fetch("/user")
    return await response.json();
}
async function fillTableAboutUser(){
    const currentUserTable1 = document.getElementById("currentUserTable");
    const currentUser = await dataAboutCurrentUser();

    let currentUserTableHTML = "";
    currentUserTableHTML +=
        `<tr>
            <td>${currentUser.id}</td>
            <td>${currentUser.username}</td>
            <td>${currentUser.email}</td>
            <td>${currentUser.yearOfBirth}</td>
            <td>${currentUser.password}</td>
            <td>${currentUser.roles.map(role => role.name).join(' ')}</td>
        </tr>`
    currentUserTable1.innerHTML = currentUserTableHTML;
}

async function showUserEmailOnNavbar() {
    const currentUserEmailNavbar = document.getElementById("currentUserEmailNavbar")
    const currentUser = await dataAboutCurrentUser();
    currentUserEmailNavbar.innerHTML =
        `<strong>${currentUser.email}</strong>
                 with roles:
                 ${currentUser.roles.map(role => role.name).join(' ')}`;
}