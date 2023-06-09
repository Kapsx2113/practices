package com.sigua.practices.services;

import com.sigua.practices.model.Image;
import com.sigua.practices.model.Product;
import com.sigua.practices.model.User;
import com.sigua.practices.repositories.ProductRepository;
import com.sigua.practices.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<Product> getProductList(String title) {
        if (title == null || title.equals(""))
        return productRepository.findAll();
        return productRepository.findByTitle(title);
    }
    public void saveProduct(Principal principal, Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3 ){
        product.setUser(getUserByPrincipal (principal));
        Image image1;
        Image image2;
        Image image3;

     if(file1.getSize() !=0 ){
         image1 = toImageEntity(file1);
        image1.setPreviewImage(true);
        product.addImageToProduct(image1);
     } else {
        File imageFile = new File("src/main/resources/imageNF.png");
        MultipartFile multipartFile = convertToMultipartFile (imageFile);
        image1 = toImageEntity(multipartFile);
        image1.setPreviewImage(true);
        product.addImageToProduct(image1);
     }
        if(file2.getSize() !=0 ){
            image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
        }
        if(file3.getSize() !=0 ){
            image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
        }
        Product productFromDb = productRepository.save(product);
        productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());
        productRepository.save(product);
    }

    public User getUserByPrincipal(Principal principal) {
            if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    //Convert image 2 Multipartfile
    private MultipartFile convertToMultipartFile(File imageFile) {
        MockMultipartFile mockMultipartFile;
       try (FileInputStream input = new FileInputStream(imageFile)){
            mockMultipartFile = new MockMultipartFile("my_file", imageFile.getName(),"image/jpeg", input );
       } catch (IOException e){
           throw new RuntimeException(e);
       }
       return mockMultipartFile;
    }

    private Image toImageEntity(MultipartFile file) {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFilName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        try {
            image.setBytes(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
