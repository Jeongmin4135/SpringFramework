<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="container-wrapper">
	<div class="container">
		<h1>Product Detail</h1>
		<p class="lead">Here is the detail information of the product!</p>

		<sf:form
			action="${pageContext.request.contextPath}/admin/productInventory/updateProduct"
			method="post" modelAttribute="product">
			<div class="row">
				<div class="col-md-6">
					<c:set var="imageFilename"
						value="/resources/images/${product.name}.jpg" />
					<img src="<c:url value="${imageFilename}" />" alt="image"
						style="width: 80%" />
					<div class="col-md-6">
						<h3>${product.name}</h3>
						<p>${product.description}</p>
						<p>Manufacturer : ${product.manufacturer}</p>
						<p>Category : ${product.category}</p>
						<h4>${product.price}원</h4>
					</div>
				</div>
		</sf:form>
	</div>
</div>