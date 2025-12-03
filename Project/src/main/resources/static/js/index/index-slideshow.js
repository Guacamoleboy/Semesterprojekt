/*

    Dynamic content load "index-slideshow" on "/"
    Written by: Guacamoleboy

*/

const intervalTime = 5000;
let currentStep = 0;
const slideSteps = [
    {
        img: "/images/carports/1.png",
        title: "DOBBELT CARPORT 6,00 X 4,80 M",
        description: "Bred byg selv-carport 6,00 x 4,80 m. med plads til 2 biler. Carporten er åben på alle siderne, men kan lukkes efter ønske.",
        specOne: "Åben Carport",
        specTwo: "Blåtonet PVC tag",
        specThree: "6,00 x 4,80 m",
        link: "https://www.johannesfog.dk/have-fritid/carporte/dobbelt-carporte/dobbelt-carport-6-00x4-80-m-car01du-00821"
    },
    {
        img: "/images/carports/2.png",
        title: "ENKELT CARPORT 6,00 X 5,40 M",
        description: "Enkelt byg selv-carport 3,00 x 4,80 m. med plads til 1 bil. Carporten er åben på alle siderne, men kan lukkes efter ønske.",
        specOne: "Åben Carport",
        specTwo: "Blåtonet PVC tag",
        specThree: "3,00 x 4,80 m",
        link: "https://www.johannesfog.dk/have-fritid/carporte/enkelt-carporte/enkelt-carport-6-00x5-40-m-car01dr-00822"
    },
    {
        img: "/images/carports/3.png",
        title: "CP01HR RØD ENKELT 3,9 X 7,8M",
        description: "Ekstra bred model!\n" +
            "Enkelt byg selv-carport 3,90X 7,80 m. med høj rejsning og plads til 1 bil samt skur bagerst i carporten.",
        specOne: "Åben Carport",
        specTwo: "Rød beton tagsten",
        specThree: "3,90 X 7,80 m",
        link: "https://www.johannesfog.dk/have-fritid/carporte/enkelt-carporte/carport-cp01hr-roed-01164"
    },
    {
        img: "/images/carports/4.png",
        title: "CP02HXL RØD DOBBELT 6,0 X 9,9M",
        description: "Bred byg selv-carport 6,00 x 9,90 m. med høj rejsning og plads til 2 biler. Stort redskabsrum bagerst i carporten.",
        specOne: "Kan lukkes efter ønske",
        specTwo: "Rød beton tagsten",
        specThree: "6,00 x 9,90 m",
        link: "https://www.johannesfog.dk/have-fritid/carporte/dobbelt-carporte/carport-cp02hxl-roed-01166"
    },
    {
        img: "/images/carports/5.png",
        title: "ENKELT CARPORT 3,00 X 6,20 M",
        description: "Enkelt byg selv-carport 3,00 x 6,20 m. med plads til 1 bil samt skur bagerst i carporten. Carporten er åben på siderne, men kan lukkes efter ønske.",
        specOne: "Åben Carport",
        specTwo: "blåtonet PVC",
        specThree: "3,00 x 6,20 m",
        link: "https://www.johannesfog.dk/have-fritid/carporte/enkelt-carporte/enkelt-carport-3-00x6-20-m-car01r-00820"
    },
    {
        img: "/images/carports/6.png",
        title: "ENKELT CARPORT 3,00 X 4,80 M",
        description: "Enkelt byg selv-carport 3,00 x 4,80 m. med plads til 1 bil. Carporten er åben på alle siderne, men kan lukkes efter ønske.",
        specOne: "Åben Carport",
        specTwo: "blåtonet PVC",
        specThree: "3,00 x 4,80 m",
        link: "https://www.johannesfog.dk/have-fritid/carporte/enkelt-carporte/enkelt-carport-3-00x4-80-m-car01-00816"
    },
    {
        img: "/images/carports/7.png",
        title: "CRXL1HR ENKELT 3,6 X 9,1M",
        description: "Enkelt byg selv-carport 3,60 x 9,10 m. med høj rejsning og plads til 1 bil, samt skur bagerst i carporten.",
        specOne: "Åben Carport",
        specTwo: "Sort beton tagsten",
        specThree: "3,60 x 9,10 m",
        link: "https://www.johannesfog.dk/have-fritid/carporte/enkelt-carporte/carport-crxl1hr-01161"
    },
    {
        img: "/images/carports/8.png",
        title: "CAR01H ENKELT 3,6 X 5,4M",
        description: "Enkelt byg selv-carport 3,60 x 5,40 m. med høj rejsning og plads til 1 bil. Carporten er åben på alle siderne, men kan lukkes efter ønske.",
        specOne: "Åben Carport",
        specTwo: "Sort beton tagsten",
        specThree: "3,60 x 5,40 m",
        link: "https://www.johannesfog.dk/have-fritid/carporte/enkelt-carporte/carport-crxl1hr-01161"
    }
];

// _________________________________________________________________________

function updateSlideshow() {

    // Initial
    const step = slideSteps[currentStep];
    const section = document.getElementById("showcase-index");

    // Dynamic load of content
    document.getElementById("i-s-title").textContent = step.title;
    document.getElementById("i-s-description").textContent = step.description;
    document.getElementById("i-s-specOne").textContent = step.specOne;
    document.getElementById("i-s-specTwo").textContent = step.specTwo;
    document.getElementById("i-s-specThree").textContent = step.specThree;
    document.getElementById("i-s-link").href = step.link;

    // Sets section background image
    section.style.backgroundImage = `
        linear-gradient(rgba(0, 0, 0, 0.1), rgba(0, 0, 0, 0.1)),
        url("${step.img}")
    `;

    // Calculates what step we are on and resets
    currentStep = (currentStep + 1) % slideSteps.length;
}

// Loads our content and updates the slideshow per interval
updateSlideshow();
setInterval(updateSlideshow, intervalTime);