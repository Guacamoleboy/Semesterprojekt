/*

    Should probably change step=1-3 to status=STATUS
    For now load it with step=3 for example.

    - Guac

*/

document.addEventListener("DOMContentLoaded", function () {

    const urlParams = new URLSearchParams(window.location.search);
    const step = parseInt(urlParams.get("step")) || 1;
    const title = document.getElementById("status-title");
    const text = document.getElementById("status-text");

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

    const content = {
        1: {
            title: "Mail afsendt til os",
            text: `Din mail er blevet afsendt og afventer at blive kigget på af en medarbejder.
                   Vi forsøger så vidt som muligt at svare tilbage inden 24 timer.
                   <br><br>Følg status her på siden.`
        },
        2: {
            title: "Vi beregner dit tilbud",
            text: `Vi er i gang med at beregne et tilbud til dig.
                   En medarbejder er tildelt sagen og arbejder på en løsning.
                   <br><br>Du hører fra os så snart dit tilbud er klar.`
        },
        3: {
            title: "Du har modtaget et tilbud",
            text: `Vi har afsendt et tilbud til dig.
                   Du har nu mulighed for at godkende vores tilbud. Prisen er fast og ændres ikke selv om priserne på materialer stiger fra vores side af.
                   <br><br>Vi skal have knapper her til Godkendelse / Afslag`
        }
    };

    // __________________________________________________________

    title.innerHTML = content[step].title;
    text.innerHTML = content[step].text;

});