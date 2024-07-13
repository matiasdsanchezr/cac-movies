let messageElement = document.querySelector("#message");
const formElement = document.querySelector("#form");

const displayError = (message) => {
  messageElement.style.color = "#f55";
  messageElement.textContent = message;
};

const disableFormInputs = () => {
  const inputs = formElement.querySelectorAll("input,select,button");
  inputs.forEach((element) => {
    element.setAttribute("disabled", "");
  });
};

const enableFormInputs = () => {
  const inputs = formElement.querySelectorAll("input,select,button");
  inputs.forEach((element) => {
    element.removeAttribute("disabled");
  });
};

const resetMessage = () => {
  messageElement.textContent = "";
};

const onSubmit = async (e) => {
  e.preventDefault();
  const formData = new FormData(e.target);

  const firstName = formData.get("firstName");
  const lastName = formData.get("lastName");
  const email = formData.get("email");
  const password = formData.get("password");
  const repassword = formData.get("repassword");
  const birthdate = new Date(formData.get("birthdate")).getTime();
  const country = formData.get("country");

  if (password != repassword)
    return displayError("Las contraseñas no coinciden");

  const user = { firstName, lastName, email, password, birthdate, country };
  disableFormInputs();

  try {
    const response = await fetch("http://localhost:8090/apimovies/signup", {
      method: "POST",
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      },
      body: JSON.stringify(user),
    });
    if (response.ok) {
      messageElement.style.color = "#3f3";
      messageElement.textContent = "Usuario guardado en la base de datos";
    } else if (response.status == 409) {
      displayError("Email ya registrado");
      enableFormInputs();
    } else {
      displayError(
        `Error al crear el nuevo usuario - Error ${response.status}`
      );
      enableFormInputs();
    }
  } catch (e) {
    console.error(e);
  }
};

formElement.addEventListener("submit", onSubmit);
