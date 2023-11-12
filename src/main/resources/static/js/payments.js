function formatAmount(amount) {
  return "€" + parseFloat(amount).toFixed(2);
}

function searchPayments() {
  const query = document.getElementById("searchPayments").value;

  fetch(`/searchPayments?query=${query}`)
    .then((response) => response.json())
    .then((data) => {
      const tbody = document.querySelector("#paymentTable tbody");
      tbody.innerHTML = "";

      if (data.length === 0) {
        const noResultsRow = document.createElement("tr");
        noResultsRow.innerHTML =
          '<td colspan="5" class="text-center py-4">No results found</td>';
        tbody.appendChild(noResultsRow);
      } else {
        data.forEach((payment) => {
          const row = document.createElement("tr");

          row.innerHTML = `
            <td class="px-6 py-4">${payment.id}</td>
            <td class="px-6 py-4">${
              payment.tenant.firstName + " " + payment.tenant.lastName
            }</td>
            <td class="px-6 py-4">${payment.paymentDate}</td>
            <td class="px-6 py-4">${formatAmount(payment.paymentAmount)}</td>
            <td class="px-6 py-4">
              <span x-show="!statusSelect" class="border p-2 rounded-lg ${
                payment.status === "Pending"
                  ? "bg-gray-100"
                  : payment.status === "Overdue"
                  ? "bg-red-100"
                  : "bg-green-100"
              }">${payment.status}</span>
            </td>
          `;

          tbody.appendChild(row);
        });
      }
    })
    .catch((error) => console.error("Error:", error));
}
