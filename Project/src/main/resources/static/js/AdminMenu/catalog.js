
const showCatalogbtn = document.getElementById("ShowAllProductsBtn");
const priceListContent = document.getElementById('showCatalogResult');
const prevAllProducts = document.getElementById("prevCatalog");
const nextAllProducts = document.getElementById("nextCatalog");


const searchProductbtn = document.getElementById("ShowProductsBtn");
const searchForm = document.getElementById("searchProductForm");
const searchResults = document.getElementById('productResults');
const prevSearch = document.getElementById("prevProduct");
const nextSearch = document.getElementById("nextProduct");

// _______________________________________________

const PageSize = 5;

// _______________________________________________

let allProducts = [];
let searchProducts = [];
let allCurrentPage = 0;
let searchCurrentPage = 0;
let productsShown = false;
let searched = false;

// _______________________________________________

function renderAllProducts() {
    priceListContent.innerHTML = '';
    const start = allCurrentPage * PageSize;
    const end = start + PageSize;
    const pageProducts = allProducts.slice(start, end);

    pageProducts.forEach(p => {
        const div = document.createElement('div');
        div.innerHTML = `
            <div class="product-item">
                <p>ID: ${p.id}</p>
                <p>Navn: ${p.name}</p>
                <p>Beskrivelse: ${p.description}</p>
                <p>Enhed: ${p.unit}</p>
                <p>Mål: ${p.length ?? 'N/A'} x ${p.width ?? 'N/A'} x ${p.height ?? 'N/A'}</p>
                <p>Pris: ${p.price} DKK</p>
                <p>Kategori: ${p.category_name}</p>
            </div>`
        ;

        priceListContent.appendChild(div);
    });

    prevAllProducts.style.display = allCurrentPage > 0 ? 'inline-block' : 'none';
    nextAllProducts.style.display = (allCurrentPage + 1) * PageSize < allProducts.length ? 'inline-block' : 'none';
}

// _______________________________________________

showCatalogbtn.addEventListener("click", async function () {
    if (!productsShown) {
        productsShown = true;
        showCatalogbtn.textContent = "Skjul katalog";
        priceListContent.style.display = "inline-flex";
        try {
            const res = await fetch('/getMaterials', {method: 'POST'});
            allProducts = await res.json();
            allCurrentPage = 0;
            renderAllProducts();
        } catch (err) {
            console.error(err);
            priceListContent.innerHTML = 'Fejl ved hentning af produkter';
        }
    } else {

        productsShown = false;
        showCatalogbtn.textContent = "Vis katalog";
        priceListContent.style.display = "none";
        prevAllProducts.style.display = "none";
        nextAllProducts.style.display = "none";

    }

});

// _______________________________________________

prevAllProducts.addEventListener('click', () => {
    if (allCurrentPage > 0) {
        allCurrentPage--;
        renderAllProducts();
    }
});

// _______________________________________________

nextAllProducts.addEventListener('click', () => {
    if ((allCurrentPage + 1) * PageSize < allProducts.length) {
        allCurrentPage++;
        renderAllProducts();
    }
});

// _______________________________________________

function renderSearchResults() {

    searchResults.innerHTML = '';
    const start = searchCurrentPage * PageSize;
    const end = start + PageSize;
    const pageProducts = searchProducts.slice(start, end);

    pageProducts.forEach(p => {
        const div = document.createElement('div');
        div.innerHTML = `
            <div class="product-item">
                <p>ID: ${p.id}</p>
                <p>Navn: ${p.name}</p>
                <p>Beskrivelse: ${p.description}</p>
                <p>Enhed: ${p.unit}</p>
                <p>Mål: ${p.length ?? 'N/A'} x ${p.width ?? 'N/A'} x ${p.height ?? 'N/A'}</p>
                <p>Pris: ${p.price} DKK</p>
                <p>Kategori: ${p.category_name}</p>
            </div>
        `;

        searchResults.appendChild(div);
    });


    const hasResults = searchProducts.length > 0;

    if (hasResults) {
        prevSearch.style.display = searchCurrentPage > 0 ? 'inline-block' : 'none';
        nextSearch.style.display = (searchCurrentPage + 1) * PageSize < searchProducts.length ? 'inline-block' : 'none';
    } else {
        searchResults.innerHTML = "<p>Ingen produkter fundet.</p>";
    }
}

// _______________________________________________

searchProductbtn.addEventListener("click", async function (e) {
    if (!searched) {
        searched = true
        searchProductbtn.textContent= "Skjul";
        searchResults.style.display ="inline-flex"
        const form = new FormData(searchForm);

        try {
            const res = await fetch('/searchMaterials', {
                method: 'POST',
                body: form
            });

            searchProducts = await res.json();
            searchCurrentPage = 0;
            renderSearchResults();
        } catch (err) {
            console.error(err);
        }
    } else {
        searched = false;
        searchProductbtn.textContent= "Søg";
        searchResults.innerHTML = '';
        searchResults.style.display ="none"
        prevSearch.style.display="none"
        nextSearch.style.display="none"
        searchProducts = [];
        searchCurrentPage = 0;
    }
});

// _______________________________________________

prevSearch.addEventListener('click', () => {
    if (searchCurrentPage > 0) {
        searchCurrentPage--;
        renderSearchResults();
    }
});

// _______________________________________________

nextSearch.addEventListener('click', () => {
    if ((searchCurrentPage + 1) * PageSize < searchProducts.length) {
        searchCurrentPage++;
        renderSearchResults();
    }
});
