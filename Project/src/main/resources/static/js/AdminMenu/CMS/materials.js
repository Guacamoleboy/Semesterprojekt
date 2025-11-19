window.addEventListener("DOMContentLoaded", async () => {
    const CMSsearchbtn = document.getElementById("CMSsearchbtn");
    const cmsProductsContainer = document.getElementById("CMSproductResults");
    const prevBtn = document.getElementById("prevCMSProduct");
    const nextBtn = document.getElementById("nextCMSProduct");

    const addNewProductForm = document.getElementById("addNewProduct");
    const addNewMaterialbtn = document.getElementById("createNewProduct");

    const PageSize = 5;
    let cmsProductsData = [];
    let productPage = 0;
    let searched = false;
    let categories = [];

    // _________________________________________________

    async function loadCategories() {

        const res = await fetch("/getCategories", {
            method: "POST"
        });

        if (res.ok) {

            categories = await res.json();

        } else {

            console.log("Fejl ved indlæsning af kategorier!");

        }
    }

    await loadCategories();

    // _________________________________________________

    CMSsearchbtn.addEventListener("click", async function () {

        if (!searched) {
            searched = true;
            CMSsearchbtn.textContent = "Skjul";
            cmsProductsContainer.style.display = "flex";
            const form = new FormData(document.getElementById("CMSSearchProductForm"));

            const res = await fetch("/searchMaterials", {
                method: "POST",
                body: form
            });

            if (res.ok) {
                cmsProductsData = await res.json();
                productPage = 0;
                renderProducts();
            } else {
                console.log("Der skete en fejl under søgning a materialer!");
            }

        } else {
            searched = false;
            CMSsearchbtn.textContent = "Søg";
            cmsProductsContainer.style.display = "none";
            cmsProductsContainer.innerHTML = '';
            togglePageShift(false);
            cmsProductsData = [];
            productPage = 0;
        }

    });

    // _________________________________________________

    addNewMaterialbtn.addEventListener("click", async function () {

        const section = addNewMaterialbtn.closest(".content-section");
        const form = new FormData(addNewProductForm);

        const res = await fetch("/addMaterial", {
            method: "POST",
            body: form
        });

        if (res.ok) {

            const inputs = section.querySelectorAll("input");
            inputs.forEach(input =>{
                input.value = ""
            });

        } else {
            console.log("Der er sket en fejl ved oprettelse af materiale");
        }
    });

    // _________________________________________________

    function renderProducts() {

        cmsProductsContainer.style.display = "flex";
        cmsProductsContainer.innerHTML = "";

        if (cmsProductsData.length === 0) {

            cmsProductsContainer.innerHTML = "<p>Ingen produkter fundet.</p>";
            togglePageShift(false);
            return;

        }

        const start = productPage * PageSize;
        const pageItems = cmsProductsData.slice(start, start + PageSize);

        pageItems.forEach(p => {

            const div = document.createElement("div");
            div.classList.add("product-item");

            div.innerHTML = `
                <div class="product-view" id="product-view-${p.id}">
                    <p>ID: ${p.id} | Navn: ${p.name}</p>
                    <p>Beskrivelse: ${p.description}</p>
                    <p>Enhed: ${p.unit}</p>
                    <p>Dimensioner: ${p.length ?? 'N/A'} x ${p.width ?? 'N/A'} x ${p.height ?? 'N/A'} | Pris: ${p.price} DKK</p>
                    <p>Kategori: ${p.category_name}</p>
                    <button class="guac-btn action-btn edit-btn" data-id="${p.id}">Rediger</button>
                    <button class="guac-btn action-btn delete-btn" data-id="${p.id}">Slet</button>
                </div>

                <div class="product-edit" id="product-edit-${p.id}" style="display:none;">
                    <p>ID: ${p.id}</p>
                    <p>Navn: <span contenteditable="true" id="edit-name-${p.id}" class="editable">${p.name}</span></p>
                    <p>Beskrivelse: <span contenteditable="true" id="edit-description-${p.id}" class="editable">${p.description}</span></p>
                    <p>Unit: <span contenteditable="true" id="edit-unit-${p.id}" class="editable">${p.unit}</span></p>

                    <p>Dimensioner: 
                        L: <span contenteditable="true" id="edit-length-${p.id}" class="editable">${p.length ?? ''}</span> | 
                        W: <span contenteditable="true" id="edit-width-${p.id}" class="editable">${p.width ?? ''}</span> | 
                        H: <span contenteditable="true" id="edit-height-${p.id}" class="editable">${p.height ?? ''}</span> | 
                        Pris: <span contenteditable="true" id="edit-price-${p.id}" class="editable">${p.price}</span>
                    </p>

                    <p>Kategori:
                        <select id="edit-category-${p.id}">
                            ${categories.map(c => `<option value="${c.id}" ${c.id === p.category_id ? 'selected' : ''}>${c.name}</option>`).join('')}
                        </select>
                    </p>

                    <button class="guac-btn action-btn save-edit-btn" data-id="${p.id}">Gem</button>
                    <button class="guac-btn action-btn cancel-edit-btn" data-id="${p.id}">Annuller</button>
                </div>
            `;

            cmsProductsContainer.appendChild(div);

        });

        setupEditButtons();
        setupDeleteButtons();
        togglePageShift(true);

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

                const name = document.getElementById(`edit-name-${id}`).textContent.trim();
                const description = document.getElementById(`edit-description-${id}`).textContent.trim();
                const unit = document.getElementById(`edit-unit-${id}`).textContent.trim();
                const lengthText = document.getElementById(`edit-length-${id}`).textContent.trim();
                const widthText = document.getElementById(`edit-width-${id}`).textContent.trim();
                const heightText = document.getElementById(`edit-height-${id}`).textContent.trim();
                const priceText = document.getElementById(`edit-price-${id}`).textContent.trim();

                const categoryId = parseInt(document.getElementById(`edit-category-${id}`).value);
                const length = lengthText ? parseInt(lengthText) : null;
                const width = widthText ? parseInt(widthText) : null;
                const height = heightText ? parseInt(heightText) : null;
                const price = parseFloat(priceText) || 0;

                const formData = new FormData();
                formData.append("id", id);
                formData.append("category_id", categoryId);
                formData.append("name", name);
                formData.append("description", description);
                formData.append("unit", unit);
                if (length !== null) formData.append("length", length);
                if (width !== null) formData.append("width", width);
                if (height !== null) formData.append("height", height);
                formData.append("price", price);

                const res = await fetch("/updateMaterial", {
                    method: "POST",
                    body: formData
                });

                if (res.ok) {

                    const i = cmsProductsData.findIndex(p => p.id == id);
                    if (i !== -1) {
                        cmsProductsData[i].name = name;
                        cmsProductsData[i].description = description;
                        cmsProductsData[i].unit = unit;
                        cmsProductsData[i].length = length;
                        cmsProductsData[i].width = width;
                        cmsProductsData[i].height = height;
                        cmsProductsData[i].price = price;
                        cmsProductsData[i].category_id = categoryId;
                    }
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

                const res = await fetch("/deleteMaterial", {
                    method: "POST",
                    body: formData
                });

                if (res.ok) {

                    cmsProductsData = cmsProductsData.filter(p => p.id != id);
                    renderProducts();

                } else {

                    console.log("Noget gik galt. Prøv igen.");

                }

            });

        });

    }

    // _________________________________________________

    function togglePageShift(show) {

        if (!show || cmsProductsData.length <= PageSize) {

            prevBtn.style.display = "none";
            nextBtn.style.display = "none";

        } else {

            prevBtn.style.display = productPage > 0 ? "inline-block" : "none";
            nextBtn.style.display = (productPage + 1) * PageSize < cmsProductsData.length ? "inline-block" : "none";

        }

        prevBtn.onclick = () => {

            if (productPage > 0) {

                productPage--;
                renderProducts();

            }

        };

        nextBtn.onclick = () => {

            if ((productPage + 1) * PageSize < cmsProductsData.length) {

                productPage++;
                renderProducts();

            }

        };

    }

});
