/*

    Dynamic navbar for sub-pages
    use data-backlink to trigger a different site.

    - Guac

*/

export function loadNavbar() {

    const container = document.getElementById("navbar2-component");
    const backLink = container.dataset.backlink || "/";
    const navbarHTML = `
    <!-- Navbar Row -->
    <nav class="guac-navbar-overlay navbar-row">
        <!-- Left -->
        <div class="navbar-left">
            <a href="https://www.johannesfog.dk" class="navbar-logo guac-animate guac-slide-left">
                <img src="/images/logo/fog.svg" alt="FOG Logo" class="fog-logo-nav">
            </a>
            <h2 class="navbar-text guac-animate guac-fade-in">Holder i længden</h2>
        </div>

        <!-- Right -->
        <div class="navbar-right">
            <a href="#" class="navbar-right-link guac-animate guac-slide-right">
                <i class="fa fa-phone guac-mr-2"></i> 20 21 44 11
            </a>
            <a href="https://www.johannesfog.dk/om-fog/forretninger" class="navbar-right-link guac-animate guac-slide-right">
                <i class="fa fa-map-marker-alt"></i> Find butik
            </a>
            <a href="${backLink}" class="navbar-right-link guac-animate guac-slide-right">
                Tilbage
            </a>
        </div>
    </nav>
    `;

    container.innerHTML = navbarHTML;

}

// Loads our nav element with the correct link via "data-backlink"
loadNavbar();