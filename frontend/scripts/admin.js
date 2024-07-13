const onClickDelete = async (e, movieId) => {
  e.preventDefault();
  try {
    const response = await fetch("http://localhost:8090/apimovies/peliculas", {
      method: "DELETE",
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      },
      body: JSON.stringify({ movieId }),
    });
    if (response.ok) alert("Pelicula eliminada de la base de datos");
    else
      alert(
        `La pelicula no pudo eliminarse de la base de datos - Codigo de error ${response.status}`
      );

    location.reload();
  } catch (e) {
    console.error(e);
  }
};

/** Cuando se complete el fetching de peliculas desde la api rellenar la tabla de peliculas */
const onMoviesFetched = (response) => {
  const movies = response;
  const moviesTable = document.querySelector(".admin-table-container");

  movies.forEach((movie) => {
    const movieCard = document.createElement("div");
    movieCard.classList.add("admin-movie-card");

    const title = document.createElement("h3");
    title.textContent = movie.title;
    movieCard.append(title);

    const poster = document.createElement("img");
    poster.src = `data:image/png;charset=utf-8;base64, ${movie.poster}`;
    movieCard.append(poster);

    const genre = document.createElement("p");
    genre.textContent = `${movie.genre} - ${movie.duration}min`;
    movieCard.append(genre);

    const description = document.createElement("p");
    description.textContent = movie.description;
    movieCard.append(description);

    const form = document.createElement("button");
    form.name = "delete-button";
    form.value = movie.id;
    form.textContent = "Eliminar";
    form.classList.add("btn");
    form.style.backgroundColor = "#f55";

    const deleteForm = document.createElement("form");
    deleteForm.setAttribute("method", "post");
    deleteForm.onsubmit = (e) => onClickDelete(e, movie.movieId);
    deleteForm.append(form);
    movieCard.append(deleteForm);

    moviesTable.append(movieCard);
  });
};

/** Convertir un archivo a base64 */
async function imageToBase64(inputFile) {
  var reader = new FileReader();

  return new Promise((resolve, reject) => {
    reader.onerror = () => {
      reader.abort();
      reject(new DOMException("Error al cargar la image de portada."));
    };

    reader.onload = () => {
      const dataURL = reader.result;
      const base64 = dataURL.slice(dataURL.indexOf(",") + 1);
      resolve(base64);
    };

    reader.readAsDataURL(inputFile);
  });
}

/** Hacer un fetch de peliculas */
const fetchMovies = () => {
  fetch(`http://localhost:8090/apimovies/peliculas`)
    .then((response) => response.json())
    .then((response) => {
      onMoviesFetched(response);
    })
    .catch((err) => console.error(err));
};

addEventListener("load", onload);
onload = () => {
  fetchMovies();
};

const addMovieFormOnSubmit = async (e) => {
  e.preventDefault();
  const formData = new FormData(e.target);

  const title = formData.get("title");
  const description = formData.get("description");
  const genre = formData.get("genre");
  const duration = formData.get("duration");
  const year = formData.get("year");
  const poster = await imageToBase64(formData.get("poster"));
  const movie = {
    title,
    description,
    genre,
    duration,
    year,
    poster,
  };

  try {
    const response = await fetch("http://localhost:8090/apimovies/peliculas", {
      method: "POST",
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      },
      body: JSON.stringify(movie),
    });
    console.log(response);
    if (response.ok) location.reload();
    else
      alert(
        `La pelicula no pudo registrarse en la base de datos - Codigo de error ${response.status}`
      );
  } catch (e) {
    console.error(e);
  }
};

const addMovieForm = document.querySelector("#movie-form");
addMovieForm.addEventListener("submit", addMovieFormOnSubmit);

const deleteForm = document.querySelector("#delete-form");
deleteForm.addEventListener("submit", deleteFormOnSubmit);
