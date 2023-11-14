function selectUserType(roleId) {
  document.getElementById("roleId").value = roleId;
  const userTypeDivs = document.querySelectorAll(".flex > div");
  userTypeDivs.forEach((div) => {
    div.classList.remove("selected");
  });

  const selectedDiv = document.querySelector(
    `[onclick="selectUserType(${roleId})"]`
  );
  selectedDiv.classList.add("selected");
}
