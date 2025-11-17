document.addEventListener("DOMContentLoaded", () => {

    document.querySelectorAll('.dropdown-toggle').forEach(toggle => {
        toggle.addEventListener('click', () => {

            const targetId = toggle.dataset.section;

            if (!targetId) return;

            document.querySelectorAll('.profile-actual').forEach(sec => {
                sec.style.display = "none";
            });

            const target = document.getElementById(targetId);
            if (target) target.style.display = "block";
        });
    });


    document.querySelectorAll('.dropdown-content a').forEach(item => {
        item.addEventListener("click", (e) => {
            e.preventDefault();

            const text = item.textContent.trim();

            const section = Array.from(document.querySelectorAll('.content-section'))
                .find(sec => sec.querySelector("h2")?.textContent.trim().includes(text));

            if (section) {
                section.scrollIntoView({ behavior: "smooth" });
            }
        });
    });

});
