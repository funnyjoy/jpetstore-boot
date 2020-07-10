<%@ include file="../common/IncludeTop.jsp"%>

<div id="BackLink">
</div>

<div id="Catalog">

    <h2 id="productName"></h2>

    <table id="itemList">
        <tr>
            <th>Item ID</th>
            <th>Product ID</th>
            <th>Description</th>
            <th>List Price</th>
            <th>&nbsp;</th>
        </tr>
    </table>

</div>

<script>
var productId = '<%= request.getParameter("productId") %>';

$.ajax({
    url: "/products/" + productId,
    type: "GET",
    cache: false,
    dataType: "json",
    success: function(data){
        $('#BackLink').append("<a href='/catalog/viewCategory?categoryId=" + data.categoryId + "'>Return to " + data.categoryId + "</a>");
        $('#productName').text(data.name);
    },
    error: function (request, status, error){        
        var msg = "ERROR : " + request.status + "<br>"
        msg +=  + "내용 : " + request.responseText + "<br>" + error;
        console.log(msg);              
    }
});

$.ajax({
    url: "/products/" + productId + "/items",
    type: "GET",
    cache: false,
    dataType: "json",
    success: function(data){
        $.each(data, function() {

            itemTable = "";
            itemTable += "<tr>";
            itemTable += "<td><a href='/catalog/viewItem?itemId=" + this.itemId + "'>" + this.itemId + "</a></td>";
            itemTable += "<td>" + this.product.productId + "</td>";
            itemTable += "<td>" + this.attribute1 + " " + this.attribute2 + " " + this.attribute3 + " " + this.attribute4 + " " + this.attribute5;
            itemTable += "    " + this.product.name + "</td>";
            itemTable += "<td>" + this.listPrice + "</td>";
            itemTable += "<td><a href='/cart/addItemToCart?workingItemId=" + this.itemId + "'>Add to Cart</a></td>";
            itemTable += "</tr>";

            $("#itemList").append(itemTable)
        });
    },
    error: function (request, status, error){        
        var msg = "ERROR : " + request.status + "<br>"
        msg +=  + "내용 : " + request.responseText + "<br>" + error;
        console.log(msg);              
    }
});
</script>

<%@ include file="../common/IncludeBottom.jsp"%>





