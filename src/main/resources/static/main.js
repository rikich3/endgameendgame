    let currentBatch = 0;
    let selectedIndex = null;

    document.addEventListener('DOMContentLoaded', function() {
        loadPlaylist();
        getPopularidad(); // Vista por defecto
    });

    //Sección categoria---------------------------------------

    function loadCategory(endpoint) {
        fetch(endpoint)
        .then(response => response.json())
        .then(data => {
            const musicBoxList = document.getElementById('musicBoxList');
            musicBoxList.innerHTML = '';
            data.forEach(music => {
                console.log(data)
                let musica = music.cancion;
                const musicBox = document.createElement('div');
                musicBox.className = 'musicBoxVista';
                musicBox.id = `v${musica.id}`;
                musicBox.innerHTML = `
                    <img src="${music.imgURL}" alt="Img-musica" class="music-image">
                    <div class="music-info">
                        <p>${musica.trackName}</p>
                        <p>${musica.artistName}</p>
                    </div>
                    <button onclick="addToPlaylist(${musica.id})">Add</button>
                `;
                musicBoxList.appendChild(musicBox);
            });

        })
        .catch(error => console.error('Error:', error));
    }


    // Carga los siguientes elementos
    function loadNextBatch(endpoint) {
        currentBatch++;
        //endpoint(popularidad, duracion, fecha)
        loadCategory(`${endpoint}/${currentBatch}`);
    }

    // Carga los elementos anteriores
    function loadPreviousBatch(endpoint) {
        if (currentBatch > 0) {
            currentBatch--;
            loadCategory(`${endpoint}/${currentBatch}`);
        }
    }

    //Tipo de vistas 
    function getPopularidad() {
        loadCategory('/vistas/popularidad/0');
    }

    function getDuracion() {
        loadCategory('/vistas/duracion/0');
    }

    function getFecha() {
        loadCategory('/vistas/fecha/0');
    }

    //Sección Playlist -------------------------------------------------------------------------------

    function loadPlaylist() {
        fetch('/playlist/list')
        .then(response => response.json())
        .then(data => {
            const playlistContainer = document.getElementById('playlist');
            playlistContainer.innerHTML = '<h2>My List</h2>';

            console.log(data);
            data.forEach(music => {
                let musica = music.cancion;
                const songElement = document.createElement('div');
                songElement.className = 'draggable playlist-song';
                songElement.id = `p${musica.id}`;
                songElement.draggable = true; 
                songElement.innerHTML = `
                    <button class="move-button">⇅</button>
                    <img src="${music.imgURL}" alt="Img-musica" class="song-image">
                    <div class="song-info">
                        <p>${musica.trackName}      </p>
                        <p>${musica.artistName}</p>
                    </div>
                    <button class="delete-button" id="${musica.id}" onclick="remove(event)">X</button>
                `;
                playlistContainer.appendChild(songElement);
            });
            initializeDragAndDrop() //Se iniciali<a la funcion de arrastrar con las listas agregada
        })
        .catch(error => console.error('Error:', error));
    }


    // FUNCIONES
    function addToPlaylist(musicId) {
        fetch(`/playlist/add/${musicId}`, {
            method: 'POST',
        })
        .then(response => {
            if (response.ok) {
                loadPlaylist();
                console.log('Música añadida correctamente');
            } else {
                console.error('Error al añadir música');
            }
        })
        .catch(error => console.error('Error:', error));
    }

    function remove(event) {
        const songId = event.target.id;
        fetch(`/playlist/remove/${songId}`, {
            method: 'DELETE',
        })
        .then(response => {
            if (response.ok) {
                loadPlaylist();
                console.log('Música eliminada correctamente');
            } else {
                console.error('Error al eliminar música');
            }
        })
        .catch(error => console.error('Error:', error));
    }

    function move(origenId, targetId) {
        fetch(`/playlist/move/${origenId.slice(1)}/${targetId.slice(1)}`, {
            method: 'DELETE',       
        })
        .then(response => {
            if (response.ok) {
                loadPlaylist();
                console.log('Música movida correctamente');
            } else {
                console.error('Error al mover música');
            }
        })
        .catch(error => console.error('Error:', error));
    }
    
    //Funcion para arrastrar los elementos 

    function initializeDragAndDrop() {
        const draggables = document.querySelectorAll('.draggable');
        const container = document.querySelector('.my-list');

        if (!container === 0) {
            console.error('contenedor no encontrado.');
            return;
        }
        if (draggables.length === 0) {
            console.error('Draggable element not found.');
            return;
        }

        let draggedElement = null;

        draggables.forEach(draggable => {
            draggable.addEventListener('dragstart', () => {
                draggedElement = draggable;
                draggable.classList.add('dragging');
            });

            draggable.addEventListener('dragend', () => {
                draggable.classList.remove('dragging');
                draggedElement = null;
            });
        });

        container.addEventListener('dragover', e => {
            e.preventDefault();
            const afterElement = getDragAfterElement(container, e.clientY);
            const dragging = document.querySelector('.dragging');
            if (afterElement == null) {
                container.appendChild(dragging);
            } else {
                container.insertBefore(dragging, afterElement);
            }
        });

        // container.addEventListener('drop', e => {
        //     e.preventDefault();
        //     const afterElement = getDragAfterElement(container, e.clientY);
        //     const dragging = document.querySelector('.dragging');
        //     if (afterElement == null) {
        //         container.appendChild(dragging);
        //     } else {
        //         container.insertBefore(dragging, afterElement);
        //     }
        container.addEventListener('drop', e => {
            e.preventDefault();
            const afterElement = getDragAfterElement(container, e.clientY);
            const dragging = document.querySelector('.dragging');
            if (afterElement == null) {
                afterElement.id = -1;
                container.appendChild(dragging);
            } else {
                container.insertBefore(dragging, afterElement);
            }

            // Invoke the mover() method
            //const targetId = afterElement  != null? afterElement.id : -1;
            move(dragging.id, afterElement.id);
        });
    }

    function getDragAfterElement(container, y) {
        const draggableElements = [...container.querySelectorAll('.draggable:not(.dragging)')];

        return draggableElements.reduce((closest, child) => {
            const box = child.getBoundingClientRect();
            const offset = y - box.top - box.height / 2;
            if (offset < 0 && offset > closest.offset) {
                return { offset: offset, element: child };
            } else {
                return closest;
            }
        }, { offset: Number.NEGATIVE_INFINITY }).element;
    }
