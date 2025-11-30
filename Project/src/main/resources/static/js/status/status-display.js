document.addEventListener("DOMContentLoaded", function () {

    /* Content */
    const statusMap = {
        pending: 1,
        calculating: 2,
        offer: 3,
        accepted: 4,
        declined: 5
    };

    const urlParams = new URLSearchParams(window.location.search);
    const urlStatus = urlParams.get("status");
    const step = statusMap[urlStatus];
    let visualStep = step;
    const title = document.getElementById("status-title");
    const text = document.getElementById("status-text");

    /* Buttons */
    const actionButtons = document.getElementById("status-action-buttons");
    const acceptBtn = document.getElementById("status-accept-btn");
    const declineBtn = document.getElementById("status-decline-btn");

    // Gets ID from pathing
    const pathParts = window.location.pathname.split("/");
    const order_id = pathParts[2];

    // Validation
    if (!order_id) {
        showNotification("Kan ikke finde ordre-ID", "error");
    }

    // (4) && (5) fix
    if (step >= 4) {
        visualStep = 3;
    }

    // __________________________________________________________

    for (let i = 1; i <= 3; i++) {
        const e = document.getElementById(`status-step-${i}`);
        e.classList.remove("status-line", "guac-locked");
        if (i === visualStep) {
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
                   <br><br>20.000 kr`,
            showButtons: true,
        },
        {
            title: "Du har godkendt vores tilbud",
            text: `Vi har afsendt dit endelige tilbud til din mail<br><br><a href="/pdf/modtag/${order_id}.pdf" download>Download .pdf</a>`,
            showButtons: false,
        },
        {
            title: "Du har afvist tilbuddet",
            text: `Du er altid velkommen til at komme ned i en af vores forretninger og få snakket mere om et tilbud.`,
            showButtons: false,
        }
    ];

    title.innerHTML = content[step].title;
    text.innerHTML = content[step].text;

    // __________________________________________________________

    /* Gets totalPrice & Displays it */
    if (step === 3) {
        fetch(`/status/${order_id}/totalPrice`)
            .then(res => res.json())
            .then(data => {
                const price = data.totalPrice.toFixed(0);
                text.innerHTML = `
                Vi har afsendt et tilbud til dig.<br>
                Prisen er <strong>fast</strong> og ændres ikke selv om priserne på materialer stiger fra vores side.
                <br><br><span class="status-final-price">${price} kr</span>
            `;
            })
            .catch(err => {
                console.error(err);
            });
    } else {
        text.innerHTML = content[step].text;
    }

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
        acceptBtn.addEventListener("click", async function() {
            try {

                // Generate .pdf for order_id
                const pdfRes = await fetch(`/status/${order_id}/pdfgenerator`, { method: "POST" });
                const pdfData = await pdfRes.json();

                if (!pdfData.success) {
                    showNotification("Fejl ved generering af PDF: " + pdfData.error, "error");
                    return;
                }

                /* Status update */
                const statusRes = await fetch(`/status/${order_id}/update`, {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body: `status=accepted`
                });
                const statusData = await statusRes.json();

                if (!statusData.success) {
                    showNotification("Fejl ved opdatering af status: " + statusData.error, "error");
                    return;
                }

                /* Bug fix where bypass was allowed */
                const newStatus = "accepted";
                const newUrl = `/status/${order_id}?status=${newStatus}`;
                window.history.replaceState(null, "", newUrl);

                /* If all worked out */
                showNotification("Tilbud godkendt", "fog");
                actionButtons.style.display = "none";
                title.innerHTML = "Du har godkendt vores tilbud";
                text.innerHTML = `
                Vi har afsendt dit endelige tilbud til din mail.
                <br><br>
                <a href="/pdf/modtag/${order_id}.pdf" target="_blank">Åben dit tilbud (.pdf)</a>
            `;

            } catch (err) {
                showNotification("Fejl ved forespørgsel: " + err, "error");
            }
        });
    }

    // __________________________________________________________

    /* EventListener | Decline */
    if (declineBtn) {
        declineBtn.addEventListener("click", async function() {
            try {

                const statusRes = await fetch(`/status/${order_id}/update`, {
                    method: "POST",
                    body: `status=declined`
                });

                const statusData = await statusRes.json();

                if (!statusData.success) {
                    showNotification("Fejl ved opdatering af status: " + statusData.error, "error");
                    return;
                }

                // URL fix
                const newStatus = "declined";
                const newUrl = `/status/${order_id}?status=${newStatus}`;
                window.history.replaceState(null, "", newUrl);

                showNotification("Tilbud afvist", "fog");
                actionButtons.style.display = "none";
                title.innerHTML = "Du har afvist tilbuddet";
                text.innerHTML = `Du er altid velkommen til at komme ned i en af vores forretninger og få snakket mere om et tilbud.`;

            } catch (err) {
                showNotification("Fejl ved forespørgsel: " + err, "error");
            }
        });
    }

});