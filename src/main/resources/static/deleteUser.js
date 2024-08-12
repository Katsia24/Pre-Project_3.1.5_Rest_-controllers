'use strict';

let deleteForm = document.forms["formDelete"]

async function deleteModal(id) {
    const modal = new bootstrap.Modal(document.querySelector('#deleteModal'));
    await openAndFillInTheModal(deleteForm, modal, id);
    switch (deleteForm.roles.value) {
        case '1':
            deleteForm.roles.value = 'ADMIN';
            break;
        case '2':
            deleteForm.roles.value = 'USER';
            break;
    }
    deleteUser()
}

function deleteUser() {
    deleteForm.addEventListener("submit", ev => {
        ev.preventDefault();
        fetch("admin/api/deleteUser" + deleteForm.id.value, {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(() => {
            $('#closeDelete').click();
            getTableUser();
        });
    });
}