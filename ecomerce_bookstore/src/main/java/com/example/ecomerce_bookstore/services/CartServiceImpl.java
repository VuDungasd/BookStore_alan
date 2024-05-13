//package com.example.ecomerce_bookstore.services;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import com.example.ecomerce_bookstore.entities.Book;
//import com.example.ecomerce_bookstore.entities.Cart;
//import com.example.ecomerce_bookstore.entities.CartItem;
//import com.example.ecomerce_bookstore.exceptions.APIException;
//import com.example.ecomerce_bookstore.exceptions.ResourceNotFoundException;
//import com.example.ecomerce_bookstore.payloads.BookDTO;
//import com.example.ecomerce_bookstore.payloads.CartDTO;
//import com.example.ecomerce_bookstore.repositories.BookRepo;
//import com.example.ecomerce_bookstore.repositories.CartItemRepo;
//import com.example.ecomerce_bookstore.repositories.CartRepo;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//import jakarta.transaction.Transactional;
//
//@Transactional
//@Service
//public class CartServiceImpl implements CartService {
//
//    @Autowired
//    private CartRepo cartRepo;
//
//    @Autowired
//    private BookRepo bookRepo;
//
//    @Autowired
//    private CartItemRepo cartItemRepo;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Override
//    public CartDTO addProductToCart(Long cartId, Long bookId, Integer quantity) {
//
//        Cart cart = cartRepo.findById(cartId)
//                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));
//
//        Book book = bookRepo.findById(bookId)
//                .orElseThrow(() -> new ResourceNotFoundException("Book", "bookId", bookId));
//
//        CartItem cartItem = cartItemRepo.findCartItemByProductIdAndCartId(cartId, bookId);
//
//        if (cartItem != null) {
//            throw new APIException("Product " + book.getBookName() + " already exists in the cart");
//        }
//
//        if (book.getQuantity() == 0) {
//            throw new APIException(book.getBookName() + " is not available");
//        }
//
//        if (book.getQuantity() < quantity) {
//            throw new APIException("Please, make an order of the " + book.getBookName()
//                    + " less than or equal to the quantity " + book.getQuantity() + ".");
//        }
//
//        CartItem newCartItem = new CartItem();
//
//        newCartItem.setBook(book);
//        newCartItem.setCart(cart);
//        newCartItem.setQuantity(quantity);
//        newCartItem.setDiscount(book.getDiscount());
//        newCartItem.setProductPrice(book.getSpecialPrice());
//
//        cartItemRepo.save(newCartItem);
//
//        book.setQuantity(book.getQuantity() - quantity);
//
//        cart.setTotalPrice(cart.getTotalPrice() + (book.getSpecialPrice() * quantity));
//
//        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
//
//        List<BookDTO> bookDTOS = cart.getCartItems().stream()
//                .map(p -> modelMapper.map(p.getBook(), BookDTO.class)).collect(Collectors.toList());
//
//        cartDTO.setBooks(bookDTOS);
//
//        return cartDTO;
//
//    }
//
//    @Override
//    public List<CartDTO> getAllCarts() {
//        List<Cart> carts = cartRepo.findAll();
//
//        if (carts.size() == 0) {
//            throw new APIException("No cart exists");
//        }
//
//        List<CartDTO> cartDTOs = carts.stream().map(cart -> {
//            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
//
//            List<BookDTO> books = cart.getCartItems().stream()
//                    .map(p -> modelMapper.map(p.getBook(), BookDTO.class)).collect(Collectors.toList());
//
//            cartDTO.setBooks(books);
//
//            return cartDTO;
//
//        }).collect(Collectors.toList());
//
//        return cartDTOs;
//    }
//
//    @Override
//    public CartDTO getCart(String emailId, Long cartId) {
//        Cart cart = cartRepo.findCartByEmailAndCartId(emailId, cartId);
//
//        if (cart == null) {
//            throw new ResourceNotFoundException("Cart", "cartId", cartId);
//        }
//
//        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
//
//        List<BookDTO> books = cart.getCartItems().stream()
//                .map(p -> modelMapper.map(p.getBook(), BookDTO.class)).collect(Collectors.toList());
//
//        cartDTO.setBooks(books);
//
//        return cartDTO;
//    }
//
//    @Override
//    public void updateProductInCarts(Long cartId, Long bookId) {
//        Cart cart = cartRepo.findById(cartId)
//                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));
//
//        Book book = bookRepo.findById(bookId)
//                .orElseThrow(() -> new ResourceNotFoundException("Book", "bookId", bookId));
//
//        CartItem cartItem = cartItemRepo.findCartItemByBookIdAndCartId(cartId, bookId);
//
//        if (cartItem == null) {
//            throw new APIException("Book " + book.getBookName() + " not available in the cart!!!");
//        }
//
//        double cartPrice = cart.getTotalPrice() - (cartItem.getBookPrice() * cartItem.getQuantity());
//
//        cartItem.setBookPrice(book.getSpecialPrice());
//
//        cart.setTotalPrice(cartPrice + (cartItem.getBookPrice() * cartItem.getQuantity()));
//
//        cartItem = cartItemRepo.save(cartItem);
//    }
//
//    @Override
//    public CartDTO updateBookQuantityInCart(Long cartId, Long bookId, Integer quantity) {
//        Cart cart = cartRepo.findById(cartId)
//                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));
//
//        Book book = bookRepo.findById(bookId)
//                .orElseThrow(() -> new ResourceNotFoundException("Book", "bookId", bookId));
//
//        if (book.getQuantity() == 0) {
//            throw new APIException(book.getBookName() + " is not available");
//        }
//
//        if (book.getQuantity() < quantity) {
//            throw new APIException("Please, make an order of the " + book.getBookName()
//                    + " less than or equal to the quantity " + book.getQuantity() + ".");
//        }
//
//        CartItem cartItem = cartItemRepo.findCartItemByBookIdAndCartId(cartId, bookId);
//
//        if (cartItem == null) {
//            throw new APIException("Book " + book.getBookName() + " not available in the cart!!!");
//        }
//
//        double cartPrice = cart.getTotalPrice() - (cartItem.getBookPrice() * cartItem.getQuantity());
//
//        book.setQuantity(book.getQuantity() + cartItem.getQuantity() - quantity);
//
//        cartItem.setBookPrice(book.getSpecialPrice());
//        cartItem.setQuantity(quantity);
//        cartItem.setDiscount(book.getDiscount());
//
//        cart.setTotalPrice(cartPrice + (cartItem.getBookPrice() * quantity));
//
//        cartItem = cartItemRepo.save(cartItem);
//
//        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
//
//        List<BookDTO> bookDTOs = cart.getCartItems().stream()
//                .map(p -> modelMapper.map(p.getBook(), BookDTO.class)).collect(Collectors.toList());
//
//        cartDTO.setBooks(bookDTOs);
//
//        return cartDTO;
//
//    }
//
//    @Override
//    public String deleteBookFromCart(Long cartId, Long bookId) {
//        Cart cart = cartRepo.findById(cartId)
//                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));
//
//        CartItem cartItem = cartItemRepo.findCartItemByBookIdAndCartId(cartId, bookId);
//
//        if (cartItem == null) {
//            throw new ResourceNotFoundException("Book", "bookId", bookId);
//        }
//
//        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getBookPrice() * cartItem.getQuantity()));
//
//        Book book = cartItem.getBook();
//        book.setQuantity(book.getQuantity() + cartItem.getQuantity());
//
//        cartItemRepo.deleteCartItemByBookIdAndCartId(cartId, bookId);
//
//        return "Book " + cartItem.getBook().getBookName() + " removed from the cart !!!";
//    }
//
//}
