document.addEventListener("DOMContentLoaded", () => {

    // Attributes
    const step1Form = document.querySelector("#step-1-bi .beregn-form");
    const step2Form = document.querySelector("#step-2-bi .beregn-form");
    const step1Container = document.getElementById("step-1-bi");
    const step2Container = document.getElementById("step-2-bi");
    const materialeValgt = document.getElementById("materiale");
    const backBtn = document.getElementById("intro-back-roof");

    //  _______________________________________________________

    step1Form.addEventListener("submit", (e) => {
        e.preventDefault();
        const taghældning = document.getElementById("Taghældning").value;
        localStorage.setItem("carport_roof", taghældning);
        step1Container.style.display = "none";
        step2Container.style.display = "flex";
    });

    //  _______________________________________________________

    step2Form.addEventListener("submit", (e) => {
        e.preventDefault();
        const materiale = materialeValgt.value;
        const materialText = materiale === "tree" ? "Træ" : "Plast";
        localStorage.setItem("carport_material", materialText);

        window.location.href = "/beregn/app";
    });


    //  _______________________________________________________

    backBtn.addEventListener("click", (e) => {
        e.preventDefault();
        step2Container.style.display = "none";
        step1Container.style.display = "flex";
    });

});