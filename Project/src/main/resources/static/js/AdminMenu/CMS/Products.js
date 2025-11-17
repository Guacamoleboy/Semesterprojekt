window.addEventListener("DOMContentLoaded", () => {
    const CMSsearchbtn = document.getElementById("CMSsearchbtn");
    const CMSproducts = document.getElementById("CMSproductResults");
    const prevBtn = document.getElementById("prevCMSProduct");
    const nextBtn = document.getElementById("nextCMSProduct");

    const addNewProductForm = document.getElementById("addNewProduct");
    const addNewProductbtn = document.getElementById("createNewProduct")

    // _________________________________________________

    const PageSize = 5;
    let CMSProducts = [];
    let productPage = 0;

    // _________________________________________________


    let searched = false;

    CMSsearchbtn.addEventListener("click", async function () {
        if(!searched) {
            searched = true
            CMSsearchbtn.textContent="Skjul"
            CMSproducts.style.display ="flex"
            const form = new FormData(document.getElementById("CMSSearchProductForm"));

            const res = await fetch("/searchProducts", {
                method: "POST",
                body: form
            });

            CMSProducts = await res.json();
            productPage = 0;
            renderProducts();
        } else {
            searched = false;
            CMSsearchbtn.textContent = "Søg";
            CMSproducts.style.display ="none"
            CMSproducts.innerHTML = '';
            togglePageShift(false);
            CMSProducts = [];
            productPage = 0;
        }

    });

    // _________________________________________________

    addNewProductbtn.addEventListener("click", async function () {

        const section = addNewProductbtn.closest(".content-section");
        const form = new FormData(addNewProductForm);

        const res = await fetch("/addProduct", {
            method: "POST",
            body: form
        });

        if (res.ok) {

            const inputs = section.querySelectorAll("input");
            inputs.forEach(input => {
                input.value = "";
            });

        } else {
            console.log("Der er sket en fejl!");
        }
    });

    // _________________________________________________

    function renderProducts() {

        CMSproducts.style.display = "flex";
        CMSproducts.innerHTML = "";

        if (CMSProducts.length === 0) {

            CMSproducts.innerHTML = "<p>Ingen produkter fundet.</p>";
            togglePageShift(false);
            return;

        }

        const start = productPage * PageSize;
        const pageItems = CMSProducts.slice(start, start + PageSize);

        pageItems.forEach(p => {

            const div = document.createElement("div");
            div.classList.add("product-item");

            div.innerHTML = `
                <div class="product-view" id="product-view-${p.id}">
                    <p>Varenummer: ${p.id} | Vare: ${p.title}</p>
                    <p>Mål: ${p.size} | Pris: ${p.unitPrice} DKK</p>
                    <button class="guac-btn action-btn edit-btn" data-id="${p.id}">Rediger</button>
                    <button class="guac-btn action-btn delete-btn" data-id="${p.id}">Slet</button>
                </div>
                <div class="product-edit" id="product-edit-${p.id}" style="display:none;">
                    <p>Varenummer: ${p.id} | 
                       Vare: <span contenteditable="true" id="edit-title-${p.id}" class="editable">${p.title}</span></p>
                    <p>Mål: <span contenteditable="true" id="edit-size-${p.id}" class="editable">${p.size}</span> | 
                       Pris: <span contenteditable="true" id="edit-price-${p.id}" class="editable">${p.unitPrice}</span> DKK</p>
                    <button class="guac-btn action-btn save-edit-btn" data-id="${p.id}">Gem</button>
                    <button class="guac-btn action-btn cancel-edit-btn" data-id="${p.id}">Annuller</button>
                </div>
            `;

            CMSproducts.appendChild(div);

        });

        setupEditButtons();
        setupDeleteButtons();
        togglePageShift(true);
        //addHideButton();

    }

    // _________________________________________________

    function setupEditButtons() {

        document.querySelectorAll(".edit-btn").forEach(btn => {

            btn.addEventListener("click", () => {

                const id = btn.dataset.id;
                document.getElementById(`product-view-${id}`).style.display = "none";
                document.getElementById(`product-edit-${id}`).style.display = "block";

            });

        });

        document.querySelectorAll(".cancel-edit-btn").forEach(btn => {

            btn.addEventListener("click", () => {

                const id = btn.dataset.id;
                document.getElementById(`product-view-${id}`).style.display = "block";
                document.getElementById(`product-edit-${id}`).style.display = "none";

            });

        });

        document.querySelectorAll(".save-edit-btn").forEach(btn => {

            btn.addEventListener("click", async () => {

                const id = btn.dataset.id;
                const title = document.getElementById(`edit-title-${id}`).textContent;
                const size = document.getElementById(`edit-size-${id}`).textContent;
                const unitPrice= document.getElementById(`edit-price-${id}`).textContent


                const formData = new FormData();
                formData.append("id", id);
                formData.append("title", title);
                formData.append("size", size);
                formData.append("unitPrice", unitPrice);

                const res = await fetch("/updateProduct", {
                    method: "POST",
                    body: formData
                });

                if (res.ok) {

                    const i = CMSProducts.findIndex(p => p.id == id);
                    if (i !== -1) CMSProducts[i] = { id, title, size, unitPrice };
                    renderProducts();

                } else {

                    console.log("Der skete en fejl!");

                }
            });
        });
    }

    // _________________________________________________

    function setupDeleteButtons() {

        document.querySelectorAll(".delete-btn").forEach(btn => {

            btn.addEventListener("click", async () => {

                const id = btn.dataset.id;

                const formData = new FormData();
                formData.append("id", id);

                const res = await fetch("/deleteProduct", {
                    method: "POST",
                    body: formData
                });

                if (res.ok) {

                    CMSProducts = CMSProducts.filter(p => p.id != id);
                    renderProducts();

                } else {

                    console.log("Noget gik galt. Prøv igen.");

                }

            });

        });

    }

    // _________________________________________________

    function togglePageShift(show) {

        if (!show || CMSProducts.length <= PageSize) {

            prevBtn.style.display = "none";
            nextBtn.style.display = "none";

        } else {

            prevBtn.style.display = productPage > 0 ? "inline-block" : "none";
            nextBtn.style.display = (productPage + 1) * PageSize < CMSProducts.length ? "inline-block" : "none";

        }

        prevBtn.addEventListener("click", () => {

            if (productPage > 0) {

                productPage--;
                renderProducts();

            }

        });

        nextBtn.addEventListener("click", () => {

            if ((productPage + 1) * PageSize < CMSProducts.length) {

                productPage++;
                renderProducts();

            }

        });

    }

});
