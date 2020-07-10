<%@ include file="../common/IncludeTop.jsp"%>

<div id="BackLink">
</div>

<div id="Catalog">

    <table>
        <tr>
            <td id="description"></td>
        </tr>
        <tr>
            <td><b id="itemId"></b></td>
        </tr>
        <tr>
            <td><b><font size="4" id="attributes"></font></b></td>
        </tr>
        <tr>
            <td id="productName"></td>
        </tr>
        <tr>
            <td id="quantity"></td>
        </tr>
        <tr>
            <td id="listPrice"></td>
        </tr>

        <tr>
            <td id="addToCartLink"></td>
        </tr>
    </table>

</div>

<script>
var itemId = '<%= request.getParameter("itemId") %>';

$.ajax({
    url: "/items/" + itemId,
    type: "GET",
    cache: false,
    dataType: "json",
    success: function(data){
        var item = data;
        var product = data.product;
        $('#BackLink').append("<a href='/catalog/viewProduct?productId=" + product.productId + "'>Return to " + product.productId + "</a>");
        $('#description').append(product.description);
        $('#itemId').text(item.itemId);
        $('#attributes').text(item.attribute1 + " " + item.attribute2 + " " + item.attribute3 + " " + item.attribute4 + " " + item.attribute5 + " " + product.name);
        $('#productName').text(product.name);
        if (item.quantity <= 0)
            $('#quantity').text("Back ordered.");
        else $('#quantity').text(item.quantity + " in stock.");
        $('#listPrice').text(item.listPrice);
        $('#addToCartLink').append("<a href='/cart/addItemToCart?workingItemId=" + item.itemId + "'>Add to Cart</a>")
    },
    error: function (request, status, error){        
        var msg = "ERROR : " + request.status + "<br>"
        msg +=  + "내용 : " + request.responseText + "<br>" + error;
        console.log(msg);              
    }
});
</script>

<%@ include file="../common/IncludeBottom.jsp"%>



