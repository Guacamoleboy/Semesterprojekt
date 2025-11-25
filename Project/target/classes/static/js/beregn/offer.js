/*

    Dynamic button on "/beregn/modtag"
    Activated when everything is filled out.

*/

const inputs = document.querySelectorAll(".modtag-card-form input");
const buttonWrapper = document.querySelector(".modtag-btn-wrapper");
const button = document.getElementById("modtagBtn");

// _____________________________________________________

function checkInputs() {

    // Initial
    let allFilled = true;
    const emailInput = document.querySelector('input[name="email"]');
    const telInput = document.querySelector('input[name="telefon"]');

    // For-each over input
    inputs.forEach(input => {
        if (input.value.trim() === "") allFilled = false;
    });

    // Valiation
    const emailValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailInput.value);
    const telValue = telInput.value.replace(/\s/g, "");
    const telValid = /^\d{8}$/.test(telValue);

    // Validation 2.0
    if (!emailValid || !telValid) allFilled = false;

    // Visual
    if (allFilled) {
        button.classList.remove("guac-locked");
        buttonWrapper.classList.remove("guac-tooltip");
        buttonWrapper.removeAttribute("data-tooltip");
    } else {
        button.classList.add("guac-locked");
        buttonWrapper.classList.add("guac-tooltip");
        buttonWrapper.setAttribute("data-tooltip", "Indtast kundeoplysninger");
    }

}

// _________________________________________________________________

    inputs.forEach(input => {
        input.addEventListener("input", checkInputs);
    });

// _________________________________________________________________

checkInputs();