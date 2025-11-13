const priceListContent = document.getElementById('priceListContent');
const searchResults = document.getElementById('searchResults');
const prevAllProducts = document.getElementById("prevAllProducts");
const nextAllProducts = document.getElementById("nextAllProducts");
const prevSearch = document.getElementById("prevSearch");
const nextSearch = document.getElementById("nextSearch");

// _______________________________________________

const PageSize = 5;

// _______________________________________________

let allProducts = [];
let searchProducts = [];
let allCurrentPage = 0;
let searchCurrentPage = 0;

// _______________________________________________

function renderAllProducts() {
    priceListContent.innerHTML = '';
    const start = allCurrentPage * PageSize;
    const end = start + PageSize;
    const pageProducts = allProducts.slice(start, end);

    pageProducts.forEach(p => {
        const div = document.createElement('div');
        div.classList.add('product-item');
        div.textContent = `${p.id} - ${p.title} - ${p.size} - ${p.unitPrice} DKK`;
        priceListContent.appendChild(div);
    });

    prevAllProducts.style.display = allCurrentPage > 0 ? 'inline-block' : 'none';
    nextAllProducts.style.display = (allCurrentPage + 1) * PageSize < allProducts.length ? 'inline-block' : 'none';
}

// _______________________________________________

function renderSearchResults() {
    searchResults.innerHTML = '';
    const start = searchCurrentPage * PageSize;
    const end = start + PageSize;
    const pageProducts = searchProducts.slice(start, end);

    pageProducts.forEach(p => {
        const div = document.createElement('div');
        div.classList.add('product-item');
        div.textContent = `${p.id} - ${p.title} - ${p.size} - ${p.unitPrice} DKK`;
        searchResults.appendChild(div);
    });


    const searchNav = document.getElementById('searchNavigation');
    const hasResults = searchProducts.length > 0;

    searchNav.style.display = hasResults ? 'block' : 'none';
    if (hasResults) {
        prevSearch.style.display = searchCurrentPage > 0 ? 'inline-block' : 'none';
        nextSearch.style.display = (searchCurrentPage + 1) * PageSize < searchProducts.length ? 'inline-block' : 'none';
    }
}

// _______________________________________________

async function loadAllProducts() {
    try {
        const res = await fetch('/getProducts', { method: 'POST' });
        allProducts = await res.json();
        allCurrentPage = 0;
        renderAllProducts();
    } catch (err) {
        console.error(err);
        priceListContent.innerHTML = 'Fejl ved hentning af produkter';
    }
}

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

document.getElementById('searchForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const searchParams = {
        id: formData.get('varenr') ? parseInt(formData.get('varenr')) : null,
        title: formData.get('title') || '',
        size: formData.get('mål') || ''
    };

    try {
        const res = await fetch('/searchProducts', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(searchParams)
        });
        searchProducts = await res.json();
        searchCurrentPage = 0;
        renderSearchResults();
    } catch (err) {
        console.error(err);
        searchResults.innerHTML = 'Fejl ved søgning af produkter';
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

// _______________________________________________

document.getElementById('hideSearchResults').addEventListener('click', () => {
    searchResults.innerHTML = '';
    searchProducts = [];
    searchCurrentPage = 0;
    document.getElementById('searchNavigation').style.display = 'none';
});

// _______________________________________________

window.addEventListener('DOMContentLoaded', loadAllProducts);