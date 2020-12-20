package vn.plusplus.spring.springbootdemo.controller;

import org.springframework.web.bind.annotation.*;
import vn.plusplus.spring.springbootdemo.controller.request.PostRequest;
import vn.plusplus.spring.springbootdemo.controller.response.GetResponse;
import vn.plusplus.spring.springbootdemo.controller.response.HomepageResponse;
import vn.plusplus.spring.springbootdemo.controller.response.Product;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/prefix")
public class RestAPIController {
    //Get API
    @RequestMapping(value = "/request-mapping", method = RequestMethod.GET)
    public Object getExample(){
        GetResponse response = new GetResponse();
        return response;
    }

    @GetMapping(value = "/request-mapping-2")
    public Object getExample2(){
        GetResponse response = new GetResponse();
        response.setName("AAA");
        response.setEmail("AAA@gmail.com");
        return response;
    }

    //Post API
    @RequestMapping(value = "/request-mapping", method = RequestMethod.POST)
    public Object postExample(@RequestBody PostRequest loginRequest){
        System.out.println("Received request with data: " + loginRequest.toString());
        return loginRequest.getUsername();
    }
    @PostMapping(value = "/request-mapping-2")
    public Object postExample2(@RequestBody PostRequest loginRequest){
        System.out.println("Received request with data in post API 2: " + loginRequest.toString());
        return "user: " + loginRequest.getUsername() + ", pass: " + loginRequest.getPassword();
    }

    //PUT API
    @RequestMapping(value = "/request-mapping", method = RequestMethod.PUT)
    public Object putExample(@RequestBody PostRequest updateRequest){
        System.out.println("PUT request with data: " + updateRequest.toString());
        return updateRequest.getUsername();
    }

    @PutMapping(value = "request-mapping-2")
    public Object putExample2(@RequestBody PostRequest updateRequest){
        System.out.println("PUT 2 request with data: " + updateRequest.toString());
        return updateRequest.getUsername();
    }

    //DELETE API
    @RequestMapping(value = "/request-mapping", method = RequestMethod.DELETE)
    public Object deleteExample(@RequestBody PostRequest deleteRequest){
        System.out.println("DELETE request with data: " + deleteRequest.toString());
        return "OK";
    }
    @DeleteMapping(value = "request-mapping-2")
    public Object deleteExample2(@RequestBody PostRequest deleteRequest){
        System.out.println("DELETE 2 request with data: " + deleteRequest.toString());
        return "OK 2";
    }

    @GetMapping(value = "/homepage")
    public HomepageResponse getHomePage(){
        HomepageResponse response = new HomepageResponse();
        List<String> cate = new ArrayList<>();
        cate.add("nam");
        cate.add("nữ");
        cate.add("gosto");
        response.setCategories(cate);

        List<Product> products = new ArrayList<>();
        products.add(new Product("Bitis hunter", "799999", "https://bitis.avatar.01"));
        response.setProducts(products);
        return response;
    }

    //Path variable
    @GetMapping(value = "/product-detail/{productID}/abc/{language}")
    public String getDetailProduct(@PathVariable(name = "productID") String prdId,
                                   @PathVariable(name = "language") String language){
        System.out.println("Getting detail for product ID: " + prdId);
        return "Detail " + prdId;
    }

    //Request Params
    @GetMapping(value = "/product-detail-2")
    public String getDetailProduct2(@RequestParam(name = "productId") String prId,
                                    @RequestParam(name = "language") String language){
        System.out.println("Getting detail for product Id in params: " + prId + ", language: " + language);
        return "Detail " + prId;
    }
}
