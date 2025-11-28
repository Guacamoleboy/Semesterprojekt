function hideAll() {

    document.querySelectorAll('.profile-actual').forEach(section => {

        section.style.display = "none"

    });

}

function show(id) {

    const element = document.getElementById(id);

    if (element){

        element.style.display = "block";

    }
}

document.querySelectorAll('.dropdown-toggle').forEach(element => {
    element.addEventListener('click', () => {
        const next = element.nextElementSibling;
        if(next) {

            document.querySelectorAll('.dropdown-content').forEach(dc => {
                if(dc !== next) dc.classList.remove("show");
            });

            next.classList.toggle("show");

            const id = element.dataset.section;
            if (id) {
                hideAll();
                show(id);
            }
        }
    });
});

document.querySelectorAll('.dropdown-content a').forEach(item => {

    item.addEventListener('click', e => {

        e.preventDefault();

        const dropdown = item.closest('.dropdown');
        let id = null;

        if (dropdown) {

            const toggle = dropdown.querySelector('.dropdown-toggle');
            if (toggle && toggle.dataset.section) {

                id = toggle.dataset.section;

            }

        }

        if (id) {

            hideAll();
            show(id);

        }

        if (dropdown) {

            const content = dropdown.querySelector('.dropdown-content');

            if (content) {

                content.classList.remove("show");

            }

        }

        const text = item.textContent.trim();
        let section = null;

        document.querySelectorAll('.content-section').forEach(sec => {

            const heading = sec.querySelector("h2");
            if (heading && heading.textContent.includes(text)) {

                section = sec;

            }

        });

        if (section) {

            section.scrollIntoView({ behavior: "smooth" });

        }

    });

});
