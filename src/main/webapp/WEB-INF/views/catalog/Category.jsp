<%@ include file="../common/IncludeTop.jsp"%>

<div id="BackLink">
    <a href="${pageContext.request.contextPath}/catalog">Return to
        Main Menu</a>
</div>

<div id="Catalog">
    <h2 id="categoryName"></h2>
    <table id="productList">
        <tr>
            <th>Product ID</th>
            <th>Name</th>
        </tr>
    </table>
</div>

<script>
var categoryId = '<%= request.getParameter("categoryId") %>';

$.ajax({
    url: "/categories/" + categoryId,
    type: "GET",
    cache: false,
    dataType: "json",
    success: function(data){
        $('#categoryName').text(data.name);
    },
    error: function (request, status, error){        
        var msg = "ERROR : " + request.status + "<br>"
        msg +=  + "내용 : " + request.responseText + "<br>" + error;
        console.log(msg);              
    }
});


$.ajax({
    url: "/categories/" + categoryId + "/products",
    type: "GET",
    cache: false,
    dataType: "json",
    success: function(data){
        $.each(data, function() {
            $("#productList").append("<tr><td><a href='/catalog/viewProduct?productId=" + this.productId + "'>" + this.productId + "</a></td><td>" + this.name + "</td></tr>")
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


