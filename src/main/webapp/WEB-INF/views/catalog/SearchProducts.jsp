<%@ include file="../common/IncludeTop.jsp"%>

<div id="BackLink">
    <a href="/catalog">Return to Main Menu</a>
</div>

<div id="Catalog">

    <table id="productList">
        <tr>
            <th>&nbsp;</th>
            <th>Product ID</th>
            <th>Name</th>
        </tr>
    </table>

</div>


<script>
var keywords = '<%= request.getParameter("keyword") %>';

$.ajax({
    url: "/products?keywords=" + keywords,
    type: "GET",
    cache: false,
    dataType: "json",
    success: function(data){
        $.each(data, function() {

        	var productTable = "";
        	productTable += "<tr>";
        	productTable += "    <td><a href='/catalog/viewProduct?productId=" + this.productId + "'>" + this.description + "</a></td>";
        	productTable += "    <td><b> <a href='/catalog/viewProduct?productId=" + this.productId + "'><font color='BLACK'>" + this.productId + " </font></a>";
        	productTable += "    </b></td>";
        	productTable += "    <td>" + this.name + "</td>";
        	productTable += "</tr>";
        	
            $("#productList").append(productTable);
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




