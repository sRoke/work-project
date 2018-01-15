import h from "!html-loader?minimize=true!./index.root.html";
function reg($templateCache){
    $templateCache.put("/pages/index.root.html",h);
}
reg.$inject = ['$templateCache'];
export default reg;
