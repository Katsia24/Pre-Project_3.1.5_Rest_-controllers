let formEdit = document.forms["formEdit"];
editUser();

async function editModal(id) {
    const modal = new bootstrap.Modal(document.querySelector('#editModal'));
    await openAndFillInTheModal(formEdit, modal, id);
}

function editUser() {
    formEdit.addEventListener("submit", ev => {
        ev.preventDefault();
        let roles = [];
        for (let i = 0; i < formEdit.roles.options.length; i++) {
            if (formEdit.roles.options[i].selected) {
                roles.push(formEdit.roles.options[i].text.replace('ROLE_', ''));
            }
        }
        fetch("admin/api/authUser" + formEdit.id.value, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: formEdit.id.value,
                username: formEdit.username.value,
                email: formEdit.email.value,
                password: formEdit.password.value,
                yearOfBirth: formEdit.yearOfBirth.value,
                roles: roles
            })
        }).then(() => {
            $('#closeEdit').click();
            getTableUser()
        });
    });
}