function searchContacts() {
  const query = document.getElementById("searchContacts").value;

  fetch(`/searchContacts?query=${query}`)
    .then((response) => response.json())
    .then((data) => {
      const tbody = document.querySelector("#contactTable tbody");
      tbody.innerHTML = "";

      if (data.length === 0) {
        const noResultsRow = document.createElement("tr");
        noResultsRow.innerHTML =
          '<td colspan="6" class="text-center py-4">No results found</td>';
        tbody.appendChild(noResultsRow);
      } else {
        data.forEach((contact) => {
          const row = document.createElement("tr");

          row.innerHTML = `
              <td class="px-6 py-4">${
                contact.firstName + " " + contact.lastName
              }</td>
              <td class="px-6 py-4">${contact.email}</td>
              <td class="px-6 py-4">${contact.phoneNumber}</td>
              <td class="px-6 py-4">${contact.organization}</td>
              <td class="px-6 py-4">${contact.jobTitle}</td>
              <td class="px-6 py-4">${contact.notes}</td>
            `;

          tbody.appendChild(row);
        });
      }
    })
    .catch((error) => console.error("Error:", error));
}
