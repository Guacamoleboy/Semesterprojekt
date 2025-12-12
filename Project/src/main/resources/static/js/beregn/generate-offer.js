/*

    Change from ChatGPT -> Actual solution.
    Just got it to work so we can edit and mess with it after

    - Guac

*/

document.getElementById("modtagBtn")?.addEventListener("click", async () => {

    const modtagBtn = document.getElementById("modtagBtn");
    modtagBtn.classList.add("guac-locked");
    modtagBtn.innerText = "Opretter tilbud...";
    console.log("Button pressed /beregn/modtag #DEBUG");

    /* Change these values to actual values */
    const values = document.querySelectorAll(".modtag-resume-value");
    console.log("Values on /beregn/modtag #DEBUG", values);

    const [mål, taghældning, tagMateriale, redskabsskur, redskabsskurMål] =
    Array.from(values).map(v => v.textContent.trim());
    console.log("Array values on /beregn/modtag #DEBUG", {mål, taghældning, tagMateriale, redskabsskur, redskabsskurMål});

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

    console.log("Carport values on /beregn/modtag #DEBUG", {width, length, height, angle, hasToolShed, toolShedWidth, toolShedLength});

    // User input
    const firstname = document.querySelector("input[name='fornavn']").value.trim();
    const lastname = document.querySelector("input[name='efternavn']").value.trim();
    const email = document.querySelector("input[name='email']").value.trim();
    const phone = document.querySelector("input[name='telefon']").value.trim();
    console.log("Customer values on /beregn/modtag #DEBUG", {firstname, lastname, email, phone});

    // generate-offer
    try {
        console.log("Sending request to /generate-offer  on /beregn/modtag #DEBUG");
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

        console.log("Response on /beregn/modtag #DEBUG", response.status, response.statusText);
        const data = await response.json();
        console.log("JSON data on /beregn/modtag #DEBUG", data);

        // Error handle
        if (!data.success) {
            showNotification("Fejl: " + data.error, "error");
            modtagBtn.classList.remove("guac-locked");
            modtagBtn.innerText = "Modtag fast tilbud";
            console.error("generate-offer failed on /beregn/modtag #DEBUG:", data.error);
            return;
        }

        // Send mail to Customer + Fog
        console.log("Sending on /beregn/modtag #DEBUG");
        await fetch("/beregn/modtag", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: new URLSearchParams({
                fornavn: firstname,
                efternavn: lastname,
                email: email,
                telefon: phone
            })
        });

        // Redirect + access + status
        console.log("/status/{orderId}/authorize on /beregn/modtag #DEBUG");
        await fetch(`/status/${data.orderId}/authorize`, { method: "POST" });

        window.location.href = `/status/${data.orderId}?status=pending&success=offerCreated`;
    } catch (err) {
        // Error notification
        console.error("Exception on /beregn/modtag #DEBUG", err);
        showNotification("Fejl: " + err, "error");
        modtagBtn.classList.remove("guac-locked");
        modtagBtn.innerText = "Modtag fast tilbud";
    }
});