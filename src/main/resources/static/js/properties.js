function formatAmount(amount) {
  return "€" + parseFloat(amount).toFixed(2);
}

function searchProperties() {
  const query = document.getElementById("searchProperties").value;

  fetch(`/searchProperties?query=${query}`)
    .then((response) => response.json())
    .then((data) => {
      const propertiesContainer = document.querySelector(
        "#propertiesContainer"
      );
      propertiesContainer.innerHTML = "";

      if (data.length === 0) {
        propertiesContainer.innerHTML =
          '<p class="text-center py-4">No results found</p>';
      } else {
        data.forEach((property) => {
          const card = document.createElement("div");
          card.className = "border border-gray-200 rounded-lg shadow-lg";

          card.innerHTML = `
              <div class="relative">
                <img src="img/property-placeholder.png" alt="Property image" class="w-full rounded-t-lg">
                <span class="absolute min-w-[4rem] text-center bottom-0 right-0 p-2 mb-2 mr-2 pointer-events-none bg-blue-100/75 rounded-full">
                  <span>${property.status}</span>
                </span>
              </div>
  
              <div class="p-4">
                <h2>${property.name}</h2>
                
              </div>
  
              <div class="flex items-center px-4">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 21h16.5M4.5 3h15M5.25 3v18m13.5-18v18M9 6.75h1.5m-1.5 3h1.5m-1.5 3h1.5m3-6H15m-1.5 3H15m-1.5 3H15M9 21v-3.375c0-.621.504-1.125 1.125-1.125h3.75c.621 0 1.125.504 1.125 1.125V21"
                />
                </svg>
                <p>${property.type}</p>
              </div>
  
              <div class="flex items-center px-4 py-4">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M15 10.5a3 3 0 11-6 0 3 3 0 016 0z" />
                  <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 10.5c0 7.142-7.5 11.25-7.5 11.25S4.5 17.642 4.5 10.5a7.5 7.5 0 1115 0z" />
                </svg>
                <p>${property.location}</p>
              </div>
            `;

          propertiesContainer.appendChild(card);
        });
      }
    })
    .catch((error) => console.error("Error:", error));
}
