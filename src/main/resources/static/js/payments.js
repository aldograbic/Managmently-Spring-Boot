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
              payment.tenant.tenant.firstName +
              " " +
              payment.tenant.tenant.lastName
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
            <form action="/updatePaymentStatus/${payment.id}" method="post">
            <td class="px-6" x-show="statusSelect" x-cloak>
              <select id="status" name="status" class="w-fit h-10 grid place-content-center p-0 pl-2">
                <option
                  th:selected="${payment.status == "Pending"}"
                  value="Pending"
                >Pending</option>
                <option
                  th:selected="${payment.status == "Complete"}"
                  value="Complete"
                >Complete</option>
                <option
                  th:selected="${payment.status == "Overdue"}"
                  value="Overdue"
                >Overdue</option>
              </select>
            </td>

            <td x-show="!editPayment" x-cloak>
              <svg
                @click="statusSelect = true; editPayment = true"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                class="w-6 h-6 cursor-pointer"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10"
                />
              </svg>
            </td>

            <td x-show="editPayment" x-cloak>
              <svg
                @click="editPayment = false; statusSelect = false"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                class="w-6 h-6 cursor-pointer"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M6 18L18 6M6 6l12 12"
                />
              </svg>

              <button type="submit">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke-width="1.5"
                  stroke="currentColor"
                  class="w-6 h-6"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    d="M4.5 12.75l6 6 9-13.5"
                  />
                </svg>
              </button>
            </td>
          </form>
          `;

          tbody.appendChild(row);
        });
      }
    })
    .catch((error) => console.error("Error:", error));
}

function exportToPDF() {
  const { jsPDF } = window.jspdf;
  var doc = new jsPDF();
  doc.text("Payments Table", 20, 10);

  var rows = document
    .getElementById("paymentTable")
    .getElementsByTagName("tbody")[0]
    .getElementsByTagName("tr");

  var totalAmount = 0;

  for (var i = 0; i < rows.length; i++) {
    var cols = rows[i].getElementsByTagName("td");
    var rowData = [];

    for (var j = 0; j < cols.length; j++) {
      var cellData = cols[j].textContent;

      if (j === 3) {
        totalAmount += parseFloat(cellData.replace(/[^\d.]/g, "")) || 0;
      }

      rowData.push(cellData);
    }

    doc.text(20, 20 + i * 10, rowData.join(" | "));
  }

  doc.text(
    "Total Payment Amount: €" + totalAmount.toFixed(2),
    20,
    20 + rows.length * 10
  );

  doc.save("payments.pdf");
}
