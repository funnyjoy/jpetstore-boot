package com.jpetstore.jpetstore.app.cart;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.jpetstore.jpetstore.domain.model.Cart;
import com.jpetstore.jpetstore.domain.service.catalog.CatalogService;

@Controller
@RequestMapping("cart")
public class CartController {
	@Inject
	protected CartHelper cartHelper;

	@Inject
	protected CatalogService catalogService;

	@Inject
	protected Cart cart;

	@ModelAttribute
	public CartForm setUpForm() {
		return new CartForm();
	}

	@ModelAttribute
	public Cart getCart() {
		return cart;
	}

	@RequestMapping("viewCart")
	public String viewCart() {
		return "cart/Cart";
	}

	@RequestMapping("addItemToCart")
	public RedirectView addItemToCart(@RequestParam("workingItemId") String workingItemId, HttpServletRequest request) {
		cartHelper.addItemToCart(workingItemId, cart);

		RedirectView redirect = new RedirectView("/cart/viewCart");
		redirect.setHosts(new String[] { request.getHeader("X-FORWARDED-HOST") });

		return redirect;
//		return "redirect:/cart/viewCart";
	}

	@RequestMapping("updateCartQuantities")
	public String updateCartQuantities(CartForm cartForm, Model model) {
		cartHelper.updateCartQuantities(cartForm, cart);
		return "redirect:/cart/viewCart";
	}

	@RequestMapping("removeItemFromCart")
	public String removeItemFromCart(@RequestParam("cartItem") String cartItem) {
		cart.removeItemById(cartItem);
		return "redirect:/cart/viewCart";
	}

	@RequestMapping("checkOut")
	public String checkOut() {
		return "cart/Checkout";
	}

}
