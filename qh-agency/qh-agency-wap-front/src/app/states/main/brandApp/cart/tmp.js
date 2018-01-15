// var skus = [
//     {
//         skuId: "SKU_ID_1",
//         specs: [
//             {
//                 propId: "COLOR_ID_1",
//                 propValueId: "COLOR_RED_ID_1"
//             },
//             {
//                 propId: "WEIGHT_ID_1",
//                 propValueId: "WEIGHT_10KG_ID_1"
//             },
//         ]
//     }
// ];
//
// skus["${spec_1_value_id}.${spec_2_value_id}"] = sku[0]
//
//
// var specs = [
//     {
//         specId: "",
//         values: [
//             {
//                 valueId: ""
//             }
//
//         ],
//         selectedValueId: ""  // 代表用户选择
//     }
// ];
//
//
//
//
// //----------------
//
// function arrToMap(specs, skus) {
//
//
//     for (var j = 0; j < skus.length; j++) {
//
//         var key = ""
//         for (var i = 0; i < specs.length; i++) {
//             key += skus.values.find(function(item){
//                 item.propId =  specs[j].id
//             }).propValueId
//         }
//         skus[key] =  skus[i]
//     }
//
// };
//
//
//
//
// //
//
// function getCurSku(){
//
//     var key = ""
//     for (var i = 0; i < specs.length; i++) {
//         key += specs[i].selectedValueId
//     }
//     curSku = skus[key]
// }
//
//
//
//
// var a = [
//     {
//         id: "a",
//         value: "aaa"
//     },
//     {
//         id: "b",
//         value: "bbb"
//     },
// ];
//
// function arrToMap(arr, itemIdPropName) {
//
//     for (var i = 0; i < arr.length; i++) {
//         var item = arr[i];
//         arr[item[itemIdPropName]] = item;
//     }
//
// };
//
// arrToMap(a, "id")
//
// console.log(a["a"]);
