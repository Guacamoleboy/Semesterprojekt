window.addEventListener("DOMContentLoaded", async () => {
    const searchBtn = document.getElementById("searchRequestbtn");
    const resultsContainer = document.getElementById("searchRequestResults");
    const prevBtn = document.getElementById("prevRequest");
    const nextBtn = document.getElementById("nextRequest");

// _______________________________________________

    let PageSize = 5;
    let requestsData = [];
    let currentPage = 0;
    let searched = false;

// _______________________________________________

    function renderRequests() {

        resultsContainer.innerHTML = "";

        if (requestsData.length === 0) {
            resultsContainer.innerHTML = "<p>Ingen indsendte tilbud fundet.</p>";
            togglePageButtons(false);
            return;
        }

        const start = currentPage * PageSize;
        const pageItems = requestsData.slice(start, start + PageSize);

        pageItems.forEach(r => {
            const div = document.createElement("div");

            const roofType = r.roof;

            div.innerHTML = `
                <div class="request-item">
                    <p><strong>Ordre ID:</strong> ${r.id}</p>
                    <p><strong>Bruger:</strong> ${r.customer.firstName} ${r.customer.lastName}</p>
                    <p><strong>Email:</strong> ${r.customer.email} | <strong>Telefon:</strong> ${r.customer.phone}</p>
                    <p><strong>Mål:</strong> L:${r.length} W:${r.width} H:${r.height}</p>
                    <p><strong>Tagtype:</strong> ${roofType}</p>
                    <p><strong>Skur:</strong> ${r.hasToolShed ? "Ja" : "Nej"}</p>
                    <button class="guac-btn action-btn calculate-btn"
                        data-length="${r.length}"
                        data-width="${r.width}"
                        data-height="${r.height}"
                        data-rooftype="${roofType}"
                        data-hastoolshed="${r.hasToolShed ? "on" : "off"}">
                        Beregn
                    </button>
                </div>
            `;

            resultsContainer.appendChild(div);

        });

        setupCalculateButtons();
        togglePageButtons(true);

    }

// _______________________________________________

    function setupCalculateButtons() {

        document.querySelectorAll(".calculate-btn").forEach(btn => {

            btn.addEventListener("click", () => {

                const form = document.createElement("form");
                form.method = "POST";
                form.action = "/calculate";

                const orderIdInput = document.querySelector("input[name='order_id']");
                const orderId = orderIdInput ? orderIdInput.value : "";

                const fields = {
                    length: btn.dataset.length,
                    width: btn.dataset.width,
                    height: btn.dataset.height,
                    roofType: btn.dataset.rooftype,
                    hasToolShed: btn.dataset.hastoolshed === "on" ? "on" : "",
                    orderId: orderId
                };

                for (const name in fields) {
                    const input = document.createElement("input");
                    input.type = "hidden";
                    input.name = name;
                    input.value = fields[name];
                    form.appendChild(input);
                }

                document.body.appendChild(form);
                form.submit(); // sender POST til /calculate
            });


        });
    }

// _______________________________________________

    function togglePageButtons(show) {

        if (!show || requestsData.length <= PageSize) {

            prevBtn.style.display = "none";
            nextBtn.style.display = "none";

        } else {

            prevBtn.style.display = currentPage > 0 ? "inline-block" : "none";
            nextBtn.style.display = (currentPage + 1) * PageSize < requestsData.length ? "inline-block" : "none";

        }

        prevBtn.onclick = () => {

            if (currentPage > 0) {

                currentPage--;
                renderRequests();

            }

        };

        nextBtn.onclick = () => {

            if ((currentPage + 1) * PageSize < requestsData.length) {

                currentPage++;
                renderRequests();

            }

        };

    }

// _______________________________________________

    searchBtn.addEventListener("click", async () => {

        if(!searched) {

            searched = true;
            searchBtn.textContent = "Skjul"
            resultsContainer.style.display = "flex";
            const form = document.querySelector("#searchRequest form");
            const formData = new FormData(form);

            const res = await fetch("/searchRequest", {
                method: "POST",
                body: formData
            });

            if (!res.ok) {

                console.log("Fejl ved søgning af requests!");
                return;

            }

            requestsData = await res.json();
            currentPage = 0;
            renderRequests();

        } else {

            searched = false;
            searchBtn.textContent = "Søg"
            resultsContainer.style.display = "none";
            resultsContainer.innerHTML = "";
            togglePageButtons(false);
            requestsData = [];
            currentPage = 0;

        }

    });

    // _______________________________________________

});