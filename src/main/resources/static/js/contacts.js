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
            <td>
              <svg @click="updateContact = true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6 cursor-pointer">
                <path stroke-linecap="round" stroke-linejoin="round"
                  d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10"
                />
              </svg>
            </td>
            <td class="px-4 py-4" x-data="{ deleteContact: false }">
              <svg @click="deleteContact =!deleteContact" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6 cursor-pointer">
                <path stroke-linecap="round" stroke-linejoin="round"
                  d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0"
                />
              </svg>
              <div x-show="deleteContact" class="fixed inset-0 z-50 overflow-y-auto" aria-labelledby="modal-title" role="dialog" aria-modal="true" x-cloak>
                <div class="flex items-end justify-center min-h-screen text-black px-4 text-center md:items-center sm:block sm:p-0">
                  <div x-cloak @click="deleteContact = false" x-show="deleteContact" x-transition:enter="transition ease-out duration-300 transform" x-transition:enter-start="opacity-0" x-transition:enter-end="opacity-100" x-transition:leave="transition ease-in duration-200 transform" x-transition:leave-start="opacity-100" x-transition:leave-end="opacity-0" class="fixed inset-0 transition-opacity bg-gray-500 bg-opacity-40" aria-hidden="true"></div>
                  <div x-cloak x-show="deleteContact" x-transition:enter="transition ease-out duration-300 transform" x-transition:enter-start="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95" x-transition:enter-end="opacity-100 translate-y-0 sm:scale-100" x-transition:leave="transition ease-in duration-200 transform" x-transition:leave-start="opacity-100 translate-y-0 sm:scale-100" x-transition:leave-end="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95" class="inline-block w-full max-w-xl p-8 my-20 overflow-hidden text-left transition-all transform bg-white rounded-lg shadow-xl 2xl:max-w-2xl">
                    <div class="flex items-center justify-between">
                      <h2 class="text-xl font-semibold">Are you sure you want to delete this contact?</h2>
                      <a href="javascript:void(0)" @click="deleteContact = false" class="text-gray-600 focus:outline-none hover:text-gray-700 mb-4">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                          <path stroke-linecap="round" stroke-linejoin="round"
                            d="M6 18L18 6M6 6l12 12"
                          />
                        </svg>
                      </a>
                    </div>
                    <div class="space-y-4">
                      <p>Contact Name: <span>${
                        contact.firstName + " " + contact.lastName
                      }</span>
                      </p>
                      <p>
                        Please note that deleting this contact will
                        permanently remove it from the system, along with
                        all associated information.
                      </p>
                      <p>This action cannot be undone.</p>
                    </div>
                    <div class="justify-around items-center mt-4 space-y-4">
                      <form action="/deleteContact/${contact.id}" method="post">
                        <input type="submit" value="Delete" style="background-color: #d11a2a"/>
                      </form>
                      <button @click="deleteContact = false" class="button bg-gray-100 hover:bg-gray-200 text-black">
                        Cancel
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </td>
          `;

          const editForm = document.createElement("form");
          editForm.action = "/updateContact";
          editForm.method = "post";

          editForm.innerHTML = `
            <td class="flex m-2">
              <input type="hidden" name="id" value="${contact.id}" />
              <input type="text" name="firstName" value="${contact.firstName}" id="firstName" class="h-10 mr-2" />
              <input type="text" name="lastName" value="${contact.lastName}" id="lastName" class="h-10" />
            </td>
            <td>
              <input type="email" name="email" value="${contact.email}" id="email" class="h-10" autocomplete="email" />
            </td>
            <td>
              <input type="text" name="phoneNumber" value="${contact.phoneNumber}" id="phoneNumber" class="h-10" />
            </td>
            <td>
              <input type="text" name="organization" value="${contact.organization}" id="organization" class="h-10" autocomplete="organization" />
            </td>
            <td>
              <input type="text" name="jobTitle" value="${contact.jobTitle}" id="jobTitle" class="h-10" />
            </td>
            <td>
              <input type="text" name="notes" value="${contact.notes}" id="notes" class="h-10" />
            </td>
            <td x-show="updateContact" x-cloak>
              <button type="submit">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M4.5 12.75l6 6 9-13.5" />
                </svg>
              </button>
            </td>
            <td x-show="updateContact" x-cloak>
              <svg @click="updateContact = false" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6 cursor-pointer">
                <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12" />
              </svg>
            </td>
          `;

          row.appendChild(editForm);

          tbody.appendChild(row);
        });
      }
    })
    .catch((error) => console.error("Error:", error));
}
