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
