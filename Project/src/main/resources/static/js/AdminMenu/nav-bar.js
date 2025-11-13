document.addEventListener("DOMContentLoaded", () => {
    const sections = document.querySelectorAll(".content-section");
    const buttons = document.querySelectorAll(".nav-bar button");


    buttons.forEach(button => {
        button.addEventListener("click", () => {
            const id = button.textContent.trim();
            sections.forEach(section => section.style.display = "none");
            document.getElementById(id).style.display = "block";
        });
    });
});
