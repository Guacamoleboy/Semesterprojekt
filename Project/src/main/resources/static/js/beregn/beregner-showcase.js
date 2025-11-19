/*

    Our Beregner App Visually + Dynamic Load
    /beregn/app

    - Guac

*/

let currentStep = 0;
const lengthOptions = [240, 270, 300, 330, 360, 390, 420, 450, 480, 510, 540, 570, 600, 630, 660, 690, 720, 750, 780];
const widthOptions  = [240, 270, 300, 330, 360, 390, 420, 450, 480, 510, 540, 570, 600];
const heightOptions = [200, 210, 220, 230, 240, 250, 260, 270, 280, 290, 300];
const toolShedWidth = [210, 240, 270, 300, 330, 360, 390, 420, 450, 480, 510, 540, 570, 600, 630, 660, 690, 720];
const toolShedLength = [150, 180, 210, 240, 270, 300, 330, 360, 390, 420, 450, 480, 510, 540, 570, 600, 630, 660];
const toolShedHeight = [200, 210, 220, 230, 240, 250, 260, 270, 280, 290, 300];
const toolShedIntro = ["Ja", "Nej"];

const beregnet = [
    {
        img: "/images/page-content/beregn/beregn-1.png",
        label: "Vælg længde",
        options: lengthOptions
    },
    {
        img: "/images/page-content/beregn/beregn-2.png",
        label: "Vælg bredde",
        options: widthOptions
    },
    {
        img: "/images/page-content/beregn/beregn-3.png",
        label: "Vælg højde",
        options: heightOptions
    },
    {
        img: "/images/page-content/beregn/shed-1.png",
        label: "Redskabsskur",
        options: toolShedIntro
    },
    {
        img: "/images/page-content/beregn/shed-2.png",
        label: "Vælg Længde",
        options: toolShedLength
    },
    {
        img: "/images/page-content/beregn/shed-3.png",
        label: "Vælg Bredde",
        options: toolShedWidth
    },
    {
        img: "/images/page-content/beregn/shed-4.png",
        label: "Vælg Højde",
        options: toolShedHeight
    }
];

// _________________________________________________________

document.addEventListener("DOMContentLoaded", () => {

    // DOMContent Attributes + Query Selectors
    const wrapper = document.querySelector(".beregn-input-wrapper");
    const img = document.querySelector(".section-beregn .guac-row img");
    const select = wrapper.querySelector(".beregn-select");
    const nextBtn = wrapper.querySelector(".c-form__button");
    const toggle = wrapper.querySelector(".c-form__toggle");
    const checkbox = wrapper.querySelector(".c-checkbox");

    // Intro + Setup
    toggle.dataset.title = "Start";
    checkbox.checked = false;

    // _________________________________________________________

    function loadStep(i) {
        const step = beregnet[i];

        // Change picture per step
        img.src = step.img;

        // Clears select options per step
        select.innerHTML = "";

        // Placeholder Settings
        const defaultOption = document.createElement("option");
        defaultOption.textContent = step.label;
        defaultOption.value = "";
        defaultOption.disabled = true;
        defaultOption.selected = true;
        defaultOption.hidden = true;
        select.appendChild(defaultOption);

        // Adds our options from our Arrays
        step.options.forEach(v => {
            const o = document.createElement("option");
            o.value = v;
            o.textContent = (typeof v === "number") ? v + " cm" : v;
            select.appendChild(o);
        });

        // Checks if we are on the last step
        nextBtn.textContent = (i === beregnet.length - 1) ? "Beregn" : "Næste";

        // Adds +i after each step to our counter
        currentStep = i;
    }

    // _________________________________________________________

    checkbox.addEventListener("change", () => {
        if (checkbox.checked) {
            loadStep(0);
        }
    });

    // _________________________________________________________

    nextBtn.addEventListener("click", () => {

        const step = beregnet[currentStep];

        // Validation for no input
        if (!select.value || select.value === "") {
            showNotification("Du skal vælge en værdi", "fog");
            return;
        }

        // If "nej" -> /beregn/modtag
        if (step.label.toLowerCase().includes("redskabsskur") && select.value === "Nej") {
            window.location.href = "/beregn/modtag";
            return;
        }

        // Loads our step unless we are on the last one
        if (currentStep < beregnet.length - 1) {
            loadStep(currentStep + 1);
        } else {
            window.location.href = "/beregn/modtag";
        }

    });

});