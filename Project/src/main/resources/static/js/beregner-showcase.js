/*

    Dynamic load of content for our "Beregn.html" page.
    Written by Guacamoleboy

    Last updated by: Guacamoleboy
    Date: 13/11-2025

*/

let currentStep = 0;
const beregnSteps = [
    {
        img: "/images/page-content/beregn/beregn-1.png",
        placeholder: "Indtast længde i cm"
    },
    {
        img: "/images/page-content/beregn/beregn-2.png",
        placeholder: "Indtast bredde i cm"
    },
    {
        img: "/images/page-content/beregn/beregn-3.png",
        placeholder: "Indtast højde i cm"
    }
];

// ____________________________________________________________________

document.addEventListener("DOMContentLoaded", () => {

    const wrapper = document.querySelector(".beregn-input-wrapper");
    const img = document.querySelector(".section-beregn .guac-row img");
    const input = wrapper.querySelector(".c-form__input");
    const nextBtn = wrapper.querySelector(".c-form__button");
    const toggle = wrapper.querySelector(".c-form__toggle");
    const checkbox = wrapper.querySelector(".c-checkbox");

    toggle.dataset.title = "Start";
    checkbox.checked = false;

    function loadStep(i) {
        img.src = beregnSteps[i].img;
        input.placeholder = beregnSteps[i].placeholder;
        input.value = ""; // Clears placeholder text per step

        if (i === beregnSteps.length - 1) {
            nextBtn.textContent = "Beregn";
        } else {
            nextBtn.textContent = "Næste";
        }

        currentStep = i;
    }

    // ________________________________________________________________

    checkbox.addEventListener("change", () => {
        if (checkbox.checked) {
            loadStep(0);
        }
    });

    // ________________________________________________________________

    nextBtn.addEventListener("click", () => {

        if (!/^\d{1,10}$/.test(input.value.trim())) {
            showNotification("Indtast venligst kun tal", "fog");
            return;
        }

        if (currentStep < beregnSteps.length - 1) {
            loadStep(currentStep + 1);
        } else {
            window.location.href = "/beregn/modtag";
        }

    });

});