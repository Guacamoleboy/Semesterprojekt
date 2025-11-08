/*

    Dynamic load of content for our "Beregn.html" page.
    Written by Guacamoleboy

    Last updated by: Guacamoleboy
    Date: 08/11-2025

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

function loadStep(stepIndex) {
    const wrapper = document.querySelector(".beregn-input-wrapper");
    const backBtn = wrapper.querySelector(".beregn-btn-back");
    const input = wrapper.querySelector(".beregn-input");
    const img = document.querySelector(".section-beregn .guac-row img");

    if (!beregnSteps[stepIndex]) return;

    img.src = beregnSteps[stepIndex].img;
    input.placeholder = beregnSteps[stepIndex].placeholder;

    backBtn.style.display = stepIndex === 0 ? "none" : "inline-flex";

    currentStep = stepIndex;

}

// ____________________________________________________________________

document.addEventListener("DOMContentLoaded", () => {

    const wrapper = document.querySelector(".beregn-input-wrapper");
    const backBtn = wrapper.querySelector(".beregn-btn-back");
    const nextBtn = wrapper.querySelector(".beregn-btn-next");
    const input = wrapper.querySelector(".beregn-input");
    const img = document.querySelector(".section-beregn .guac-row img");

    function loadStep(stepIndex) {

        if (!beregnSteps[stepIndex]) return;

        img.src = beregnSteps[stepIndex].img;
        input.placeholder = beregnSteps[stepIndex].placeholder;

        backBtn.style.display = stepIndex === 0 ? "none" : "inline-flex";

        if (stepIndex === beregnSteps.length - 1) {
            nextBtn.textContent = "Beregn";
            nextBtn.classList.add("godkend");
        } else {
            nextBtn.textContent = "Næste";
            nextBtn.classList.remove("godkend");
        }

        currentStep = stepIndex;
    }

    backBtn.addEventListener("click", () => {
        if (currentStep > 0) loadStep(currentStep - 1);
    });

    nextBtn.addEventListener("click", () => {
        if (currentStep < beregnSteps.length - 1) loadStep(currentStep + 1);
    });

    loadStep(0);

});