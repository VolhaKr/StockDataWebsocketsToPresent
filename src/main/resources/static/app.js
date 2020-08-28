var stompClient = null;
var myVar;
var array = [];

//var tabledata = [
// 	{id:1, name:"Oli Bob", age:"12", col:"red", dob:""},
// 	{id:2, name:"Mary May", age:"1", col:"blue", dob:"14/05/1982"},
// 	{id:3, name:"Christine Lobowski", age:"42", col:"green", dob:"22/05/1982"},
// 	{id:4, name:"Brendon Philips", age:"125", col:"orange", dob:"01/08/1980"},
// 	{id:5, name:"Margret Marmajuke", age:"16", col:"yellow", dob:"31/01/1999"},
// ];
function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/stock-data-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/products/subscribedproducts', function (products) {
            showProducts(JSON.parse(products.body).product_id);
        });
        stompClient.subscribe('/stockinfo/displayedprices', function (greeting2) {
                    showPrices(JSON.parse(greeting2.body).content);
                });
              //  stompClient.subscribe('/topic3/greetings3', function (greeting3) {
                //                    showGreeting3(JSON.parse(greeting3.body).content_yes);
                  //              });

    }
   // $("#example-table").tabulator();

    );

}
//function create_table(){}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    myVar = setInterval(myTimer, 4000);

    function myTimer() {

      stompClient.send("/app/products_to_display", {}, JSON.stringify({'productPair': $("#name").val()}));
    }
  //  stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

//function create_table(){
////create Tabulator on DOM element with id "example-table"
//var table = new Tabulator("#example-table", {
// 	height:205, // set height of table (in CSS or here), this enables the Virtual DOM and improves render speed dramatically (can be any valid css height value)
// 	data:tabledata, //assign data to table
// 	layout:"fitColumns", //fit columns to width of table (optional)
// 	columns:[ //Define Table Columns
//	 	{title:"Name", field:"name", width:150},
//	 	{title:"Age", field:"age", align:"left", formatter:"progress"},
//	 	{title:"Favourite Color", field:"col"},
//	 	{title:"Date Of Birth", field:"dob", sorter:"date", align:"center"},
// 	],
// 	rowClick:function(e, row){ //trigger an alert message when the row is clicked
// 		alert("Row " + row.getData().id + " Clicked!!!!");
// 	},
//});
//}

function subscribe() {
    $("input:checkbox[name=type]:checked").each(function() {
                            array.push($(this).val());
                        });
                        console.log (array.length);

   // stompClient.send("/app/hello3", {}, JSON.stringify({'products_sent': "1"}));
     stompClient.send("/app/products_to_subscribe", {}, JSON.stringify({'product_id': array}));

    }
function stopTimer() {
    clearInterval(myVar);
    }
function showPrices(message) {
    $("#prices").append("<tr><td>" + message + "</td></tr>");
}
function showProducts(products) {
    $("#products").append("<tr><td>" + products + "</td></tr>");
}




$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();

    });

    $( "#connect" ).click(function() { connect();
   });
//$( "#create_table" ).click(function() {
//    create_table();});
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#stop_timer" ).click(function() { stopTimer(); });
    $( "#subscribe" ).click(function() { subscribe(); });
 /*   $('button').on('click', function() {
                var array = [];
                $("input:checkbox[name=type]:checked").each(function() {
                    array.push($(this).val());
                });
                $('#GFG_DOWN').text(array);
            });*/



   /*   $('subscribe').on('click', function() {
      console.log( "I want array");
                 var array = [];
                 $("input:checkbox[name=type]:checked").each(function() {
                     array.push($(this).val());
                 });
                 console.log(array );
                 $('#subscription_products_ids').append("<tr><td>" + text(array[0]) + "</td></tr>");
             });
*/
});