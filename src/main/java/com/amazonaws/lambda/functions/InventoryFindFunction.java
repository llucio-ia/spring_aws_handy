package com.amazonaws.lambda.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class InventoryFindFunction extends InventoryS3Client
		implements RequestHandler<HttpQueryStringRequest, HttpProductResponse> {
	@Override
	public HttpProductResponse handleRequest(HttpQueryStringRequest request, Context context) {
		context.getLogger().log("Input: " + request);

		String idAsString = (String) request.getQueryStringParameters().get("id");

		if (idAsString.equalsIgnoreCase("all")) {
			Product[] products = getAllProducts();
			HttpProductResponse response = new HttpProductResponse(products);
			return response;
		}

		Integer productId = Integer.parseInt(idAsString);

		Product product = getProductById(productId);

		return new HttpProductResponse(product);
	}

	private Product getProductById(int prodId) {

		Product[] products = getAllProducts();

		for (Product prod : products) {
			if (prod.getId() == prodId) {
				return prod;
			}
		}
		return null;
	}
}
