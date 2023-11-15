function searchTenants() {
  const query = document.getElementById("searchTenants").value;

  fetch(`/searchTenants?query=${query}`)
    .then((response) => response.json())
    .then((data) => {
      const tenantsContainer = document.querySelector("#tenantsContainer");
      tenantsContainer.innerHTML = "";

      if (data.length === 0) {
        tenantsContainer.innerHTML =
          '<p class="text-center py-4">No results found</p>';
      } else {
        data.forEach((tenant) => {
          const card = document.createElement("div");
          card.className = "border border-gray-200 rounded-lg shadow-lg p-4";

          card.innerHTML = `
                <div class="space-y-4">
                  <div class="flex justify-between">
                    <h3 class="text-xl">${tenant.firstName} ${
            tenant.lastName
          }</h3>
                    <div class="flex gap-4" x-data="{ deleteTenant: false, updateTenant: false }">
                    <svg @click="updateTenant =! updateTenant" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6 cursor-pointer">
                      <path stroke-linecap="round" stroke-linejoin="round"
                        d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10"
                      />
                    </svg>
                    <div x-show="updateTenant" class="fixed inset-0 z-50 overflow-y-auto" aria-labelledby="modal-title" role="dialog" aria-modal="true" x-cloak>
                      <div class="flex items-end justify-center min-h-screen px-4 text-center md:items-center sm:block sm:p-0">
                        <div x-cloak @click="updateTenant = false" x-show="updateTenant" x-transition:enter="transition ease-out duration-300 transform" x-transition:enter-start="opacity-0" x-transition:enter-end="opacity-100" x-transition:leave="transition ease-in duration-200 transform" x-transition:leave-start="opacity-100" x-transition:leave-end="opacity-0" class="fixed inset-0 transition-opacity bg-gray-500 bg-opacity-40" aria-hidden="true"></div>
                        <div x-cloak x-show="updateTenant" x-transition:enter="transition ease-out duration-300 transform" x-transition:enter-start="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95" x-transition:enter-end="opacity-100 translate-y-0 sm:scale-100" x-transition:leave="transition ease-in duration-200 transform" x-transition:leave-start="opacity-100 translate-y-0 sm:scale-100" x-transition:leave-end="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95" class="inline-block w-full max-w-xl p-8 my-20 overflow-hidden text-left transition-all transform bg-white rounded-lg shadow-xl 2xl:max-w-2xl">
                          <div class="flex items-center justify-between">
                            <h2 class="text-xl font-semibold">
                              Update the tenant
                            </h2>
                            <a
                              href="javascript:void(0)"
                              @click="updateTenant = false"
                              class="text-gray-600 focus:outline-none hover:text-gray-700 mb-4"
                            >
                              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                <path stroke-linecap="round" stroke-linejoin="round"
                                  d="M6 18L18 6M6 6l12 12"
                                />
                              </svg>
                            </a>
                          </div>

                          <p class="mt-2 text-sm text-gray-500">
                            Update information about the tenant or add missing
                            information.
                          </p>

                          <form action="/updateTenant" method="post" class="mt-5 space-y-4">
                            <input
                              type="hidden"
                              name="id"
                              value=${tenant.id}
                            />
                            <div class="relative">
                              <input
                                type="text"
                                id="firstName"
                                name="firstName"
                                class="peer"
                                placeholder=""
                                value=${tenant.firstName}
                              />
                              <label
                                for="firstName"
                                class="peer-focus:px-2 peer-focus:text-blue-700 peer-focus:dark:text-blue-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2 peer-placeholder-shown:top-1/2 peer-focus:top-2 peer-focus:scale-75 peer-focus:-translate-y-4"
                                >First name</label
                              >
                            </div>

                            <div class="relative">
                              <input
                                type="text"
                                id="lastName"
                                name="lastName"
                                class="peer"
                                placeholder=""
                                value=${tenant.lastName}
                              />
                              <label
                                for="lastName"
                                class="peer-focus:px-2 peer-focus:text-blue-700 peer-focus:dark:text-blue-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2 peer-placeholder-shown:top-1/2 peer-focus:top-2 peer-focus:scale-75 peer-focus:-translate-y-4"
                                >Last name</label
                              >
                            </div>

                            <div class="relative">
                              <input
                                type="text"
                                id="city"
                                name="city"
                                class="peer"
                                placeholder=""
                                value=${tenant.city}
                              />
                              <label
                                for="city"
                                class="peer-focus:px-2 peer-focus:text-blue-700 peer-focus:dark:text-blue-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2 peer-placeholder-shown:top-1/2 peer-focus:top-2 peer-focus:scale-75 peer-focus:-translate-y-4"
                                >City</label
                              >
                            </div>

                            <div class="relative">
                              <input
                                type="text"
                                id="address"
                                name="address"
                                class="peer"
                                placeholder=""
                                autocomplete="street-address"
                                value=${tenant.address}
                                
                              />
                              <label
                                for="address"
                                class="peer-focus:px-2 peer-focus:text-blue-700 peer-focus:dark:text-blue-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2 peer-placeholder-shown:top-1/2 peer-focus:top-2 peer-focus:scale-75 peer-focus:-translate-y-4"
                                >Address</label
                              >
                            </div>

                            <div class="relative">
                              <input
                                type="text"
                                id="phoneNumber"
                                name="phoneNumber"
                                class="peer"
                                placeholder=""
                                value=${tenant.phoneNumber}
                              />
                              <label
                                for="phoneNumber"
                                class="peer-focus:px-2 peer-focus:text-blue-700 peer-focus:dark:text-blue-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2 peer-placeholder-shown:top-1/2 peer-focus:top-2 peer-focus:scale-75 peer-focus:-translate-y-4"
                                >Phone Number</label
                              >
                            </div>

                            <div class="relative">
                              <input
                                type="email"
                                id="email"
                                name="email"
                                class="peer"
                                placeholder=""
                                autocomplete="email"
                                value=${tenant.email}
                              />
                              <label
                                for="email"
                                class="peer-focus:px-2 peer-focus:text-blue-700 peer-focus:dark:text-blue-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2 peer-placeholder-shown:top-1/2 peer-focus:top-2 peer-focus:scale-75 peer-focus:-translate-y-4"
                                >E-mail address</label
                              >
                            </div>

                            <div class="relative">
                              <input
                                type="date"
                                id="leaseStartDate"
                                name="leaseStartDate"
                                class="peer"
                                placeholder=""
                                value=${tenant.leaseStartDate}
                              />
                              <label
                                for="leaseStartDate"
                                class="peer-focus:px-2 peer-focus:text-blue-700 peer-focus:dark:text-blue-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2 peer-placeholder-shown:top-1/2 peer-focus:top-2 peer-focus:scale-75 peer-focus:-translate-y-4"
                                >Lease Start Date</label
                              >
                            </div>

                            <div class="relative">
                              <input
                                type="date"
                                id="leaseEndDate"
                                name="leaseEndDate"
                                class="peer"
                                placeholder=""
                                value=${tenant.leaseEndDate}
                              />
                              <label
                                for="leaseEndDate"
                                class="peer-focus:px-2 peer-focus:text-blue-700 peer-focus:dark:text-blue-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2 peer-placeholder-shown:top-1/2 peer-focus:top-2 peer-focus:scale-75 peer-focus:-translate-y-4"
                                >Lease End Date</label
                              >
                            </div>

                            <div class="relative">
                              <input
                                type="text"
                                id="rentAmount"
                                name="rentAmount"
                                class="peer"
                                placeholder=""
                                value=${tenant.rentAmount}
                              />
                              <label
                                for="rentAmount"
                                class="peer-focus:px-2 peer-focus:text-blue-700 peer-focus:dark:text-blue-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2 peer-placeholder-shown:top-1/2 peer-focus:top-2 peer-focus:scale-75 peer-focus:-translate-y-4"
                                >Rent Amount</label
                              >
                            </div>

                            <div class="relative">
                              <input
                                type="text"
                                id="securityDepositAmount"
                                name="securityDepositAmount"
                                class="peer"
                                placeholder=""
                                value=${tenant.securityDepositAmount}
                              />
                              <label
                                for="securityDepositAmount"
                                class="peer-focus:px-2 peer-focus:text-blue-700 peer-focus:dark:text-blue-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2 peer-placeholder-shown:top-1/2 peer-focus:top-2 peer-focus:scale-75 peer-focus:-translate-y-4"
                                >Security Deposit Amount</label
                              >
                            </div>

                            <div class="flex justify-end mt-8">
                              <input type="submit" value="Submit" />
                            </div>
                          </form>
                        </div>
                      </div>
                    </div>
                    <svg
                      @click="deleteTenant =!deleteTenant"
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
                        d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0"
                      />
                    </svg>
                    <div
                      x-show="deleteTenant"
                      class="fixed inset-0 z-50 overflow-y-auto"
                      aria-labelledby="modal-title"
                      role="dialog"
                      aria-modal="true"
                      x-cloak
                    >
                      <div
                        class="flex items-end justify-center min-h-screen text-black px-4 text-center md:items-center sm:block sm:p-0"
                      >
                        <div
                          x-cloak
                          @click="deleteTenant = false"
                          x-show="deleteTenant"
                          x-transition:enter="transition ease-out duration-300 transform"
                          x-transition:enter-start="opacity-0"
                          x-transition:enter-end="opacity-100"
                          x-transition:leave="transition ease-in duration-200 transform"
                          x-transition:leave-start="opacity-100"
                          x-transition:leave-end="opacity-0"
                          class="fixed inset-0 transition-opacity bg-gray-500 bg-opacity-40"
                          aria-hidden="true"
                        ></div>

                        <div
                          x-cloak
                          x-show="deleteTenant"
                          x-transition:enter="transition ease-out duration-300 transform"
                          x-transition:enter-start="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
                          x-transition:enter-end="opacity-100 translate-y-0 sm:scale-100"
                          x-transition:leave="transition ease-in duration-200 transform"
                          x-transition:leave-start="opacity-100 translate-y-0 sm:scale-100"
                          x-transition:leave-end="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
                          class="inline-block w-full max-w-xl p-8 my-20 overflow-hidden text-left transition-all transform bg-white rounded-lg shadow-xl 2xl:max-w-2xl"
                        >
                          <div class="flex items-center justify-between">
                            <h2 class="text-xl font-semibold">
                              Are you sure you want to delete this tenant?
                            </h2>
                            <a
                              href="javascript:void(0)"
                              @click="deleteTenant = false"
                              class="text-gray-600 focus:outline-none hover:text-gray-700 mb-4"
                            >
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
                                  d="M6 18L18 6M6 6l12 12"
                                />
                              </svg>
                            </a>
                          </div>

                          <div class="space-y-4">
                            <p>
                              Tenant Name: <span>${
                                tenant.firstName + " " + tenant.lastName
                              }</span>
                            </p>
                            <p>
                              Please note that deleting this tenant will
                              permanently remove it from the system, along with
                              all associated information.
                            </p>
                            <p>This action cannot be undone.</p>
                          </div>
                          <div
                            class="justify-around items-center mt-4 space-y-4"
                          >
                            <form action="/deleteTenant/${
                              tenant.id
                            }" method="post"
                            >
                              <input
                                type="submit"
                                value="Delete"
                                style="background-color: #d11a2a"
                              />
                            </form>
                            <button
                              @click="deleteTenant = false"
                              class="button bg-gray-100 hover:bg-gray-200 text-black"
                            >
                              Cancel
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  </div>
                  <div class="flex items-center">
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
                        d="M6.75 3v2.25M17.25 3v2.25M3 18.75V7.5a2.25 2.25 0 012.25-2.25h13.5A2.25 2.25 0 0121 7.5v11.25m-18 0A2.25 2.25 0 005.25 21h13.5A2.25 2.25 0 0021 18.75m-18 0v-7.5A2.25 2.25 0 015.25 9h13.5A2.25 2.25 0 0121 11.25v7.5m-9-6h.008v.008H12v-.008zM12 15h.008v.008H12V15zm0 2.25h.008v.008H12v-.008zM9.75 15h.008v.008H9.75V15zm0 2.25h.008v.008H9.75v-.008zM7.5 15h.008v.008H7.5V15zm0 2.25h.008v.008H7.5v-.008zm6.75-4.5h.008v.008h-.008v-.008zm0 2.25h.008v.008h-.008V15zm0 2.25h.008v.008h-.008v-.008zm2.25-4.5h.008v.008H16.5v-.008zm0 2.25h.008v.008H16.5V15z"
                    />
                    </svg>
                    <span>${formatDate(tenant.leaseStartDate)} - ${formatDate(
            tenant.leaseEndDate
          )}</span>
                  </div>
                  
                    <p>Rent: €${tenant.rentAmount.toFixed(2)}</p>
                    <p>Security Deposit: €${tenant.securityDepositAmount.toFixed(
                      2
                    )}</p>
                  
                </div>
              `;

          tenantsContainer.appendChild(card);
        });
      }
    })
    .catch((error) => console.error("Error:", error));
}

function formatDate(dateString) {
  const options = { year: "numeric", month: "2-digit", day: "2-digit" };
  return new Date(dateString).toLocaleDateString("en-US", options);
}
