async function getOneUser(id) {
    let response = await fetch("admin/api/user?id="+ id);
    return await response.json();
}

async function openAndFillInTheModal(form, modal, id) {
    modal.show();
    let user = await getOneUser(id);
    form.id.value = user.id;
    form.username.value = user.username;
    form.email.value = user.email;
    form.yearOfBirth.value = user.yearOfBirth;
    form.roles.value = user.roles;
    // form.roles.value = user.roles.join(',');
}