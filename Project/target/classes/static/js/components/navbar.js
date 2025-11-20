/*

    Navbar for our initial page
    Written by Guacamoleboy

*/

const navbarHTML = `
<!-- Navbar Overlay -->
<nav class="guac-navbar-overlay guac-d-flex guac-justify-center guac-align-center guac-pt-2">
    <div class="guac-text-center">
        <a href="https://www.johannesfog.dk" class="guac-animate guac-slide-down">
            <img src="/images/logo/fog.svg" alt="FOG Logo" class="fog-logo-nav-2 guac-pt-1">
        </a>
        <h2 class="guac-animate guac-fade-in guac-mt-1">
            Holder i længden
        </h2>
    </div>
</nav>
`;

// ____________________________________________________________________

export function loadNavbar(containerId = "navbar-component") {
    const container = document.getElementById(containerId);

    if (!container) {
        console.error(`Navbar container #${containerId} not found`);
        return;
    }

    container.innerHTML = navbarHTML;
}

loadNavbar();