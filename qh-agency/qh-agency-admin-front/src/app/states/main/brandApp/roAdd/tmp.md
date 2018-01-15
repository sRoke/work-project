


```js

// load

var loadList = [   
    {
        title: "商品管理",
        children: [
            {
                title : "商品管理",
                authorities : [
                    {
                        authority : "ITEM_PROP_R" ,
                        title : "新增",
                        
                        
                    },
                    {
                        
                        authority : "ITEM_PROP_R" ,
                        title : "新增",
                        
                    },
                    {
                        authority : "ITEM_PROP_R" ,
                        title : "新增"
                    }
                    
                ]
            }
        ]
        
    }
];


// info
var infoList = [
   "ITEM_PROP_R",  "ITEM_PROP_W", "ITEM_PROP_D"
]
// post /update
{
    userId:""
   authorities: [  "{ITEM_PROP_W:false}", "ITEM_PROP_D"]
}

// post /update ()
userId=xxx&authorities=A&authorities=B&authorities=C



$scope.xxx = {
    loadList : loadList
}




function getUserAuthorities(loadList){
    var authorities = []
    
    loadList.forEach
    
    
}






```