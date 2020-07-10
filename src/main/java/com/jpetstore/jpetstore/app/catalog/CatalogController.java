package com.jpetstore.jpetstore.app.catalog;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jpetstore.jpetstore.domain.service.catalog.CatalogService;

@Controller
@RequestMapping("catalog")
public class CatalogController {
	@Inject
	protected CatalogService catalogService;

	@RequestMapping
	public String main() {
		return "catalog/Main";
	}

	@RequestMapping("viewCategory")
	public String viewCategory(@RequestParam("categoryId") String categoryId, Model model) {
		return "catalog/Category";
	}

	@RequestMapping("viewProduct")
	public String viewProduct(@RequestParam("productId") String productId, Model model) {
		return "catalog/Product";
	}

	@RequestMapping("viewItem")
	public String viewItem(@RequestParam("itemId") String itemId, Model model) {
		return "catalog/Item";
	}

	@RequestMapping(params = "keyword")
	public String searchProducts(@Validated ProductSearchForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "common/Error";
		}
		return "catalog/SearchProducts";
	}
}
