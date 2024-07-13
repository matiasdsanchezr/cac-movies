let divEl = null;

const onSubmit = async (e) => {
  e.preventDefault();
  const formData = new FormData(e.target);

  const email = formData.get("email");
  const password = formData.get("password");
  const user = {
    email,
    password,
  };

  if (divEl) {
    divEl.remove();
    divEl = null;
  }

  try {
    const response = await fetch("http://localhost:8090/apimovies/signin", {
      method: "POST",
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      },
      body: JSON.stringify(user),
    });
    if (response.ok) {
      const user = await response.json();
      divEl = document.createElement("div");
      divEl.style.padding = "1rem 0";
      divEl.style.display = "grid";
      divEl.style.gap = "1rem";
      divEl.style.textAlign = "center";

      const titleEl = document.createElement("h2");
      titleEl.innerHTML = "Has iniciado sesión";
      titleEl.style.color = "#3f3";
      divEl.append(titleEl);

      const nameEl = document.createElement("p");
      const nameSpan = document.createElement("span");
      nameSpan.style.textTransform = "capitalize";
      nameSpan.textContent = `${user.firstName}`;
      nameEl.innerHTML = `Nombre(s): `;
      nameEl.append(nameSpan);
      divEl.append(nameEl);

      const lastNameEl = document.createElement("p");
      const lastNameSpan = document.createElement("span");
      lastNameSpan.style.textTransform = "capitalize";
      lastNameSpan.textContent = user.lastName;
      lastNameEl.innerHTML = `Apellido(s): `;
      lastNameEl.append(lastNameSpan);
      divEl.append(lastNameEl);

      const birthdateEl = document.createElement("p");
      birthdateEl.innerHTML = `Fecha de nacimiento: ${new Date(
        user.birthdate
      ).toLocaleDateString("es-AR", {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "numeric",
      })}`;
      divEl.append(birthdateEl);

      const countryEl = document.createElement("p");
      countryEl.innerHTML = `Pais de residencia: ${user.country}`;
      divEl.append(countryEl);

      e.target.append(divEl);
    } else {
      divEl = document.createElement("div");
      divEl.style.padding = "1rem 0";
      divEl.style.display = "grid";
      divEl.style.gap = "1rem";
      divEl.style.textAlign = "center";
      divEl.style.color = "#f55";

      const titleEl = document.createElement("h2");
      titleEl.innerHTML = "Credenciales invalidas";
      divEl.append(titleEl);

      e.target.append(divEl);
    }
  } catch (e) {
    console.error(e);
  }
};

document.querySelector("#form").addEventListener("submit", onSubmit);
