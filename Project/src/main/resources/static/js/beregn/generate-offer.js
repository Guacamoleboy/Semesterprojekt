/*

    Change from ChatGPT -> Actual solution.
    Just got it to work so we can edit and mess with it after

    - Guac

*/

document.getElementById("modtagBtn")?.addEventListener("click", async () => {

    const modtagBtn = document.getElementById("modtagBtn");
    modtagBtn.classList.add("guac-locked");
    modtagBtn.innerText = "Opretter tilbud...";

    /* Change these values to actual values */
    const values = document.querySelectorAll(".modtag-resume-value");
    const [mål, taghældning, tagMateriale, redskabsskur, redskabsskurMål] =
        Array.from(values).map(v => v.textContent.trim());
    const [width, length, height] = mål.split("x").map(v => parseFloat(v.trim()));
    const angle = taghældning === "Ja" ? 5 : 0;
    const hasToolShed = redskabsskur === "Ja";
    let toolShedWidth = 200;
    let toolShedLength = 300;
    if (redskabsskurMål) {
        const dims = redskabsskurMål.split("x").map(v => parseFloat(v.trim()));
        if (dims.length >= 2) {
            toolShedWidth = dims[0];
            toolShedLength = dims[1];
        }
    }
    /* Change these values to actual values */

    // User input
    const firstname = document.querySelector("input[name='fornavn']").value.trim();
    const lastname = document.querySelector("input[name='efternavn']").value.trim();
    const email = document.querySelector("input[name='email']").value.trim();
    const phone = document.querySelector("input[name='telefon']").value.trim();

    // generate-offer
    try {
        const response = await fetch("/generate-offer", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                // Customer Values
                firstname,
                lastname,
                email,
                phone,
                // Carport Order Values
                width,
                length,
                height,
                angle,
                roof: tagMateriale,
                hasToolShed,
                toolShedWidth,
                toolShedLength,
                hasTrapez: false
            })
        });

        const data = await response.json();

        // Error handle
        if (!data.success) {
            showNotification("Fejl: " + data.error, "error");
            modtagBtn.classList.remove("guac-locked");
            modtagBtn.innerText = "Modtag fast tilbud";
            return;
        }

        // Redirect + access + status
        await fetch(`/status/${data.orderId}/authorize`, { method: "POST" });
        window.location.href = `/status/${data.orderId}?status=pending&success=offerCreated`;
    } catch (err) {
        // Error notification
        showNotification("Fejl: " + err, "error");
        modtagBtn.classList.remove("guac-locked");
        modtagBtn.innerText = "Modtag fast tilbud";
    }
});