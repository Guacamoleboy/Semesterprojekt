/*

    Top Viewport Render of our Carport from any given dimension
    Gruppe D

*/

/* Dette er vores værdier vi skal bruge pr. carport mål, de skal indhentes pr. carportCalculator */
const rafterAmount = 15;
const polesAmount = 6;
const length = 600;
const width = 780;
/* Dette er vores værdier vi skal bruge pr. carport mål, de skal indhentes pr. carportCalculator */

const margin = 50;
const raftersMargin = 55;
const overhang = 35;
const woodWidth = 4.5;
const trapezOverhang = 5;
const pole = 10;
const poleSpacing = 310;

const raftersCalc = Array.from({ length: rafterAmount }, (_, i) => {
    const x = i === rafterAmount - 1 ? width - woodWidth : i * raftersMargin;

    return `<rect x="${x}" y="0" width="${woodWidth}" height="${length}" />`;
}).join("\n");

const polesCalc = Array.from({ length: polesAmount }, (_, i) => {
    const x = i * poleSpacing;

    return `
        <rect x="${x}" y=${32} width="${pole}" height="${pole}" />
        <rect x="${x}" y=${562} width="${pole}" height="${pole}" />
    `;
}).join("\n");


const topSvg = `
    <!-- Total SVG Render -->
    <!-- Top View Render Start-->
    <svg class="svg-render" height="100%" viewBox="0 0 855 690"
         preserveAspectRatio="xMinYMin">
    
        <defs>
            <marker id="beginArrow" markerWidth="12" markerHeight="12" refX="0" refY="6" orient="auto">
                <path d="M0,6 L12,0 L12,12 L0,6" />
            </marker>
            <marker id="endArrow" markerWidth="12" markerHeight="12" refX="12" refY="6" orient="auto">
                <path d="M0,0 L12,6 L0,12 L0,0 " />
            </marker>
        </defs>
    
        <!-- Pile -->
        <line class="arrow-visuals" x1="40" y1="10" x2="40" y2="610" style="stroke:#000000;
            marker-start: url(#beginArrow);
            marker-end: url(#endArrow);" />
    
        <line class="arrow-visuals" x1="75" y1="650" x2=${width} y2="650" style="stroke:#000000;
            marker-start: url(#beginArrow);
            marker-end: url(#endArrow);" />
    
        <!-- Text -->
        <text style="text-anchor: middle" transform="translate(30,300) rotate(-90)">600 cm</text>
        <text style="text-anchor: middle" x=${width/2} y=${length+70}>780 cm</text>
        
        <svg x="75" y="10" width="780" height="600" viewBox="0 0 ${width+margin} ${length+margin}" preserveAspectRatio="xMinYMin"> <!-- Display Area of the SVG Render -->

            <!-- Ramme -->
            <rect x="0" y="0" height=${width} width=${length} /> <!-- width( is length ) | height ( is width )-->
        
            <!-- Remme -->
            <rect x="0" y=${overhang} height=${woodWidth} width=${width} />
            <rect x="0" y=${length - overhang} height=${woodWidth} width=${width} />
        
            <!-- Spær -->
            ${raftersCalc}
            
            <!-- Last Rafter -->
            <rect x=${width - woodWidth} y="0" height=${length} width=${woodWidth} />
        
            <!-- Kryds -->
            <line class="svg-cross" x1=${raftersMargin} y1=${overhang} x2=${length} y2=${length - overhang + woodWidth} />
            <line class="svg-cross" x1=${raftersMargin}  y1=${length - overhang + woodWidth} x2=${length} y2=${overhang} />
        
            <!-- Stolper -->
            ${polesCalc}
            <!-- Top View Render End-->
        </svg>
        
    </svg>
    <!-- Total SVG Render -->
    
`;

/* no "export" of the function since it's not a module unlike navbar */
function loadTopSvg(containerId = "top-svg-render") {

    const container = document.getElementById(containerId);

    if(!container) {
        console.error(`Component top-svg-render not found`);
        return;
    }

    container.innerHTML = topSvg;

}

loadTopSvg();