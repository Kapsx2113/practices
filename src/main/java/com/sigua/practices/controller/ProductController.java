package com.sigua.practices.controller;

import com.sigua.practices.model.Product;
import com.sigua.practices.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;


    @GetMapping("/")
    public String returnIndex(@RequestParam(name = "title", required = false) String title,
                              Principal principal, Model model) {
        model.addAttribute("productList", productService.getProductList(title));
       model.addAttribute("user",productService.getUserByPrincipal(principal));
        return "index";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id,Principal principal, Model model){
        Product product = productService.getProductById(id);
       model.addAttribute("product", product);
       model.addAttribute("images", product.getImages());
       model.addAttribute("user", productService.getUserByPrincipal(principal));
        return "product-info";
    }

    @PostMapping("/product/delete/{id}")
    public String removeProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return "redirect:/";
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestParam(name = "file1") MultipartFile file1,
                                @RequestParam(name = "file2") MultipartFile file2,
                                @RequestParam(name = "file3") MultipartFile file3,
                                Product product, Principal principal){
        productService.saveProduct(principal, product, file1, file2, file3);
        return "redirect:/";
    }
}
