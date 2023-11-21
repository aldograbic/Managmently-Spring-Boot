const createAccountBtn = document.getElementById("createAccountBtn");

document
  .getElementById("createAccountBtn")
  .addEventListener("click", function (event) {
    validateUserType();
    validatePasswordMatch();

    // Check if either validation failed and prevent form submission if needed
    if (createAccountBtn.disabled) {
      event.preventDefault();
    }
  });

function selectUserType(roleId) {
  const innerDivs = document.querySelectorAll(".grid");
  innerDivs.forEach((div) => {
    div.style.border = "";
  });

  document.getElementById("roleId").value = roleId;

  const userTypeDivs = document.querySelectorAll(".flex > div");
  userTypeDivs.forEach((div) => {
    div.classList.remove("selected");
  });

  const selectedDiv = document.querySelector(
    `[onclick="selectUserType(${roleId})"]`
  );
  selectedDiv.classList.add("selected");

  validateUserType();
}

function validateUserType() {
  const roleId = document.getElementById("roleId").value;
  const userTypeErrorSpan = document.getElementById("userTypeError");

  if (!roleId) {
    const userTypeDiv = document.getElementById("userTypeDiv");

    if (userTypeDiv) {
      const innerDivs = userTypeDiv.querySelectorAll(".grid");

      innerDivs.forEach((div) => {
        div.classList.add("border-red-500");
      });
      userTypeErrorSpan.innerHTML = "Select user type.";
      createAccountBtn.disabled = true;
    }
  } else {
    const innerDivs = document.querySelectorAll(".grid");
    innerDivs.forEach((div) => {
      div.classList.remove("border-red-500");
    });
    userTypeErrorSpan.innerHTML = "";
    createAccountBtn.disabled = false;
  }

  createAccountBtn.classList.toggle("opacity-50", createAccountBtn.disabled);
}

function validatePasswordMatch() {
  const password = document.getElementById("password").value;
  const confirmPassword = document.getElementById("confirmPassword").value;
  const confirmPasswordInput = document.getElementById("confirmPassword");
  const passwordErrorSpan = document.getElementById("passwordError");

  const passwordsMatch = password === confirmPassword;

  if (!passwordsMatch) {
    confirmPasswordInput.style.border = "1px solid #ef4444";
    passwordErrorSpan.innerHTML = "Passwords must match.";
  } else {
    confirmPasswordInput.style.border = "";
    passwordErrorSpan.innerHTML = "";
  }

  createAccountBtn.disabled = !roleId || !passwordsMatch;

  createAccountBtn.classList.toggle("opacity-50", createAccountBtn.disabled);
}

document
  .getElementById("confirmPassword")
  .addEventListener("input", validatePasswordMatch);

function validatePassword() {
  const password = document.getElementById("password").value;
  const lengthCriteria = document.getElementById("lengthCriteria");
  const numberCriteria = document.getElementById("numberCriteria");
  const specialCharCriteria = document.getElementById("specialCharCriteria");
  const createAccountBtn = document.getElementById("createAccountBtn");

  passwordCriteriaMet = {
    length: password.length >= 6,
    hasNumber: /\d/.test(password),
    hasSpecialChar: /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/.test(password),
  };

  const lightGreenClass = "text-green-500";
  const grayClass = "text-gray-500";

  lengthCriteria.classList.toggle(lightGreenClass, passwordCriteriaMet.length);
  numberCriteria.classList.toggle(
    lightGreenClass,
    passwordCriteriaMet.hasNumber
  );
  specialCharCriteria.classList.toggle(
    lightGreenClass,
    passwordCriteriaMet.hasSpecialChar
  );

  lengthCriteria.classList.toggle(grayClass, !passwordCriteriaMet.length);
  numberCriteria.classList.toggle(grayClass, !passwordCriteriaMet.hasNumber);
  specialCharCriteria.classList.toggle(
    grayClass,
    !passwordCriteriaMet.hasSpecialChar
  );

  validateUserType();

  // Check if any of the password criteria is not met
  const anyCriteriaNotMet = Object.values(passwordCriteriaMet).some(
    (criteria) => !criteria
  );

  createAccountBtn.disabled = !roleId || anyCriteriaNotMet;

  createAccountBtn.classList.toggle("opacity-50", createAccountBtn.disabled);
}
