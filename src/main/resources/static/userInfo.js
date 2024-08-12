'use strict';


function getCurrentUser() {
    fetch("user/api")
        .then(res => res.json())
        .then(user => {
            const roles = user.roles.map(role => role.name).join(',')
            $('#emailCurrentUser').append(`<span>${user.email}</span>`)
            $('#roleCurrentUser').append(`<span>${roles.replace('ROLE_', '') + ' '}</span>`)
            const u = `$(
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>${user.yearOfBirth}</td>
                        <td>${roles.replace('ROLE_', '') + ' '}</td>
                    </tr>)`;
            $('#currentUser').append(u)
        })
}

getCurrentUser()