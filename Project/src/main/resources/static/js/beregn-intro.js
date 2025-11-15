document.addEventListener("DOMContentLoaded", () => {
    localStorage.setItem("carport_model", "Carport");
    localStorage.setItem("carport_wood", "Trykimprægneret");
    localStorage.setItem("carport_roof", "Plast");
    localStorage.setItem("carport_contact", "12 34 56 78");
    
    const form = document.querySelector(".beregn-form");
    const materialeValgt = document.getElementById("materiale");
    
    form.addEventListener("submit", (e) => {
        e.preventDefault();
        
        const materiale = materialeValgt.value;
        if (materiale === "træ") {
            localStorage.setItem("carport_wood", "Trykimprægneret");
        } else if (materiale === "stål") {
            localStorage.setItem("carport_roof", "Plast");
        }
        
        window.location.href = "/beregn/app";
    });
});

