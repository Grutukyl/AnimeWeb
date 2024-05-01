function salvarFavorito(id, titulo, cover){
    let url = "http://localhost:8080/favoritos";
    const media = {
        idMedia: id,
        title: titulo,
        coverImage: cover,
    };
    console.log(id);
    console.log(document.cookie);
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(media),
    }).then(response => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('Erro ao salvar favorito');
        }
    ).then(data => {
        console.log(data);
    });
}

function atualizarTextoFavorito(checkbox) {
    const label = checkbox.parentNode;
    const span = label.querySelector('span');
    span.textContent = checkbox.checked ? 'Desfavoritar' : 'Favoritar';
}

function submitForm() {
    document.getElementById("buscaForm").submit();
}

function buscarOn(event){
    var element = document.getElementById("buscaFormButton");
    element.disabled = false;
    element.style.display = "block";
}