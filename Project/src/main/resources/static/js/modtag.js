document.addEventListener("DOMContentLoaded", () => {
    const model = localStorage.getItem("carport_model") || "Carprot";
    const length = localStorage.getItem("carport_length") || "600";
    const width = localStorage.getItem("carport_width") || "320";
    const wood = localStorage.getItem("carport_wood") || "Trykimpregneret";
    const roof = localStorage.getItem("carport_roof") || "Plastik";
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

    const submitBtn = document.querySelector(".modtag-submit-btn");
    const form = document.querySelector(".modtag-form");
    
    if (submitBtn && form) {
        submitBtn.addEventListener("click", (e) => {
            e.preventDefault();
            
            const inputs = form.querySelectorAll("input");
            const formData = {
                fornavn: inputs[0]?.value || "",
                efternavn: inputs[1]?.value || "",
                email: inputs[2]?.value || "",
                telefon: inputs[3]?.value || ""
            };
            
            const resumeData = {
                model: resume[0]?.querySelector(".modtag-resume-value")?.textContent || "",
                mål: resume[1]?.querySelector(".modtag-resume-value")?.textContent || "",
                træ: resume[2]?.querySelector(".modtag-resume-value")?.textContent || "",
                tag: resume[3]?.querySelector(".modtag-resume-value")?.textContent || "",
                kontakt: resume[4]?.querySelector(".modtag-resume-value")?.textContent || ""
            };
            
            const allData = {
                kontaktoplysninger: formData,
                resumé: resumeData,
                carportData: {
                    length: length,
                    width: width,
                    height: localStorage.getItem("carport_height") || "",
                    model: model,
                    wood: wood,
                    roof: roof
                }
            };
            
            console.log("kontakt:", formData);
            console.log("resume:", resumeData);
            console.log("carport resume:", allData.carportData);
            console.log("alt data:", allData);
        });
    }
});

