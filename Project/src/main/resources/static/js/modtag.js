document.addEventListener("DOMContentLoaded", () => {
    const urlParams = new URLSearchParams(window.location.search);
    const model = localStorage.getItem("carport_model") || "Carport";
    const length = urlParams.get("length") || localStorage.getItem("carport_length") || "600";
    const width = urlParams.get("width") || localStorage.getItem("carport_width") || "320";
    const wood = localStorage.getItem("carport_wood") || "Trykimprægneret";
    const roof = urlParams.get("roof") || localStorage.getItem("carport_roof") || "Plast";
    const contact = localStorage.getItem("carport_contact") || "12 34 56 78";
    
    const resume = document.querySelectorAll(".modtag-resume-item");
    if (resume.length >= 5) {
        resume[0].querySelector(".modtag-resume-value").textContent = model;
        resume[1].querySelector(".modtag-resume-value").textContent = `${width} x ${length} cm`;
        resume[2].querySelector(".modtag-resume-value").textContent = wood;
        resume[3].querySelector(".modtag-resume-value").textContent = roof;
        resume[4].querySelector(".modtag-resume-value").textContent = contact;
    }
    
    const telefon = document.querySelector('input[type="tel"]');
    if (telefon) {
        telefon.value = contact;
    }

});

