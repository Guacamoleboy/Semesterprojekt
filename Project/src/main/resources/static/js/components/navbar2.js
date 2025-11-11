/*

    Navbar for subpage "beregner"
    Written by Guacamoleboy

*/

const navbarHTML = `
<!-- Navbar Row -->
<nav class="guac-navbar-overlay navbar-row">
    
    <!-- Left -->
    <div class="navbar-left">
        <!-- Logo -->
        <a href="https://www.johannesfog.dk" class="navbar-logo guac-animate guac-slide-left">
            <img src="/images/logo/fog.svg" alt="FOG Logo" class="fog-logo-nav">
        </a>
        <!-- Tekst -->
        <h2 class="navbar-text guac-animate guac-fade-in">
            Holder i længden
        </h2>
    </div>

    <!-- Right -->
    <div class="navbar-right">
        <a href="#" class="navbar-right-link guac-animate guac-slide-right">
            <i class="fa fa-phone guac-mr-2"></i> 20 21 44 11
        </a>
        <a href="https://www.johannesfog.dk/om-fog/forretninger" class="navbar-right-link guac-animate guac-slide-right">
            <i class="fa fa-map-marker-alt"></i> Find butik
        </a>
        <a href="/" class="navbar-right-link guac-animate guac-slide-right">
            <i class="fa fa-map-marker-alt"></i> Tilbage
        </a>
    </div>

</nav>
`;

// ____________________________________________________________________

export function loadNavbar(containerId = "navbar2-component") {
    const container = document.getElementById(containerId);

    if (!container) {
        console.error(`Navbar container #${containerId} not found`);
        return;
    }

    container.innerHTML = navbarHTML;
}

loadNavbar();