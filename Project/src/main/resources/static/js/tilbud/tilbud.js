/*

    Tilbud page .js main content

*/

document.addEventListener("click", async (e) => {

    const target = e.target.closest("#sendOfferBtn");
    const orderId = target.dataset.orderId;
    const finalPriceEl = document.getElementById("tilbud-final-price");
    let finalPrice = parseFloat(finalPriceEl.textContent);

    if (!orderId || orderId === "${orderId}") {
        showNotification("Intet gyldigt ID fundet", "fog");
        return;
    }

    try {
        const res = await fetch("/sendOffer", {
            method: "POST",
            body: new URLSearchParams({
                orderId: orderId,
                totalPrice: finalPrice
            })
        });

        if (res.ok) {
            window.location.href = "/menu?success=offerCreatedAdmin";
        } else {
            showNotification("Kunne ikke afsende tilbud", "fog");
        }
    } catch (err) {
        console.error(err);
        showNotification("Fejl " + err, "fog");
    }

});