package isi.shoppingCart.usecases.services;

import isi.shoppingCart.entities.Cart;
import isi.shoppingCart.entities.CartItem;
import isi.shoppingCart.entities.Product;
import isi.shoppingCart.usecases.ports.CartRepository;

import java.util.List;

public class ConfirmarCompraUseCase {

    private CartRepository cartRepository;

    public ConfirmarCompraUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public String execute() {
        Cart cart = cartRepository.getCart();
        List<CartItem> items = cart.getItems();

        if (items.isEmpty()) {
            return "El carrito está vacío. Agregue productos antes de confirmar la compra.";
        }

        descontarStockDeProductos(items);

        cart.clear();
        cartRepository.save(cart);

        return "";
    }

    private void descontarStockDeProductos(List<CartItem> items) {
        int i;
        int j;

        for (i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            Product product = item.getProduct();

            for (j = 0; j < item.getQuantity(); j++) {
                product.decreaseAvailableQuantity();
            }
        }
    }
}
