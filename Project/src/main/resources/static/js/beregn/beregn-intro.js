document.addEventListener("DOMContentLoaded", () => {

    // localStorage values (why? @Ebou)
    localStorage.setItem("carport_model", "Carport");
    localStorage.setItem("carport_wood", "Trykimprægneret");
    localStorage.setItem("carport_roof", "Plast");
    localStorage.setItem("carport_contact", "12 34 56 78");

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
        step1Container.style.display = "none";
        step2Container.style.display = "flex";
    });

    //  _______________________________________________________

    step2Form.addEventListener("submit", (e) => {
        e.preventDefault();
        const materiale = materialeValgt.value;
        if (materiale === "træ") {
            localStorage.setItem("carport_wood", "Trykimprægneret");
        } else if (materiale === "stål") {
            localStorage.setItem("carport_roof", "Plast");
        }
        window.location.href = "/beregn/app";
    });

    //  _______________________________________________________

    backBtn.addEventListener("click", (e) => {
        e.preventDefault();
        step2Container.style.display = "none";
        step1Container.style.display = "flex";
    });

});