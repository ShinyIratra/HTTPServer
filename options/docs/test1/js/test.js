function coucou()
{
    alert("Coucou");
}

function loadUsers()
{
    fetch('../json/test.json').then(response => response.json()).then(data =>
    {
        const usersDiv = document.getElementById('users');
        data.forEach(element => {
            const userDiv = document.createElement('div');
            userDiv.className = 'user';
            userDiv.innerHTML = `<strong> ID : </strong> ${user.id} <br>
                                <strong> Nom : </strong> ${user.name} <br>
                                <strong> Email : </strong> ${user.email} <br>`;
            usersDiv.appendChild(userDiv);
        });
    })
    .catch(error => {
        console.error('Erreur lors du chargement des utilisateurs:', error);
    });

    document.addEventListener('DOMContentLoaded', loadUsers);
}