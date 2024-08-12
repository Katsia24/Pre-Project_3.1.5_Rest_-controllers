'use strict';

function getCurrentUser() {
    fetch("user/api")
        .then(res => res.json())
        .then(user => {
            const roles = user.roles.map(role => role.name.replaceAll('ROLE_', '')).join(', ')
            $('#emailCurrentUser').append(`<span>${user.email}</span>`)
            $('#roleCurrentUser').append(`<span>${roles}</span>`)
            const u = `$(
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>${user.yearOfBirth}</td>                 
                        <td>${roles}</td> 
                    </tr>)`;
            $('#currentUser').append(u)
        })
}

getCurrentUser()