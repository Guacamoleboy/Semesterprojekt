document.addEventListener("DOMContentLoaded", function () {

    /* Content */
    const urlParams = new URLSearchParams(window.location.search);
    const step = parseInt(urlParams.get("status")) || 1;
    const title = document.getElementById("status-title");
    const text = document.getElementById("status-text");

    /* Buttons */
    const actionButtons = document.getElementById("status-action-buttons");
    const acceptBtn = document.getElementById("status-accept-btn");
    const declineBtn = document.getElementById("status-decline-btn");

    // __________________________________________________________

    for (let i = 1; i <= 3; i++) {
        const e = document.getElementById(`status-step-${i}`);
        e.classList.remove("status-line", "guac-locked");
        if (i === step) {
            e.classList.add("status-line");
        } else {
            e.classList.add("guac-locked");
        }
    }

    // __________________________________________________________

    /* Content + Button Validation */
    const content = [
        null,
        {
            title: "Mail afsendt til os",
            text: `Din mail er blevet afsendt og afventer at blive kigget på af en medarbejder.
                   Vi forsøger så vidt som muligt at svare tilbage inden 24 timer.
                   <br><br>Følg status her på siden.`,
            showButtons: false,
        },
        {
            title: "Vi beregner dit tilbud",
            text: `Vi er i gang med at beregne et tilbud til dig.
                   En medarbejder er tildelt sagen og arbejder på en løsning.
                   <br><br>Du hører fra os så snart dit tilbud er klar.`,
            showButtons: false,
        },
        {
            title: "Du har modtaget et tilbud",
            text: `Vi har afsendt et tilbud til dig.
                   Du har nu mulighed for at godkende vores tilbud. Prisen er fast og ændres ikke selv om priserne på materialer stiger fra vores side af.
                   <br><br>Dit tilbud består af PRIS`,
            showButtons: true,
        }
    ];

    title.innerHTML = content[step].title;
    text.innerHTML = content[step].text;

    // __________________________________________________________

    /* Step 3 Buttons */
    if (actionButtons) {
        if (content[step].showButtons) {
            actionButtons.style.display = "block";
        } else {
            actionButtons.style.display = "none";
        }
    }

    // __________________________________________________________

    /* EventListener | Accept */
    if (acceptBtn) {
        acceptBtn.addEventListener("click", function() {
            showNotification("Tilbud godkendt! Vi kontakter dig snarest.", "fog");
        });
    }

    /* EventListener | Deny */
    if (declineBtn) {
         declineBtn.addEventListener("click", function() {
             if (confirm("Er du sikker på at du vil afvise tilbuddet?")) {
                showNotification("Tilbud afvist. Vi beklager at vi ikke kunne hjælpe dig denne gang.", "green");
             }
         });
    }

});