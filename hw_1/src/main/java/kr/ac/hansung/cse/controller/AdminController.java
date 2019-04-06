package kr.ac.hansung.cse.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ProductService productService;
	
	@RequestMapping // request admin
	public String adminPage() {
		return "admin"; //definition name. tiles.xml
	}
	
	@RequestMapping("/productInventory")
	public String getProducts(Model model){//controller -> model ->
		/*
		 * controller가 database를 조회해서 products정보를 가져온다
		 * 정보를 모델에 저장하고 뷰쪽으로 return. view는 model에서 데이터를 집어내어 화면을 꾸밈
		 * 
		 */
		List<Product> products = productService.getProducts();
		model.addAttribute("products", products);
		
		return "productInventory";
	}
	
	//get타입만 처리
	@RequestMapping(value = "/productInventory/addProduct", method=RequestMethod.GET)
	public String addProduct(Model model) {
		//String form tag library 사용.
		//객체의 내용을 바탕으로 form부분을 채울 수 있음.
		Product product = new Product();
		product.setCategory("컴퓨터"); // 첫 세팅하는법.
		
		model.addAttribute("product", product); // addProduct.jsp의 modelAttribute와 같아야함
		
		return "addProduct";
	}
	
	//post타입 처리
	@RequestMapping(value = "/productInventory/addProduct", method=RequestMethod.POST)
	public String addProductPost(@Valid Product product, BindingResult result) { // 사용자가 입력한 내용이 객체에 담기게 된다.
		/*
		 * 사용자의 입력 내용이 post로 http body부분에 담겨 넘어온다.
		 * 객체에 자동으로 바인딩 시켜야 한다.
		 */
		
		if(result.hasErrors()) {
			System.out.println("From data has some errors");
			List<ObjectError> errors = result.getAllErrors();
			
			for(ObjectError error:errors) {
				System.out.println(error.getDefaultMessage());
			}
			return "addProduct"; // error날경우 다시 입력페이지로
		}
		
		if(!productService.addProduct(product))
			System.out.println("Adding product cannot be done");
		
		return "redirect:/admin/productInventory";
	}
	
	//{id}가 PathVariable
	@RequestMapping(value = "/productInventory/deleteProduct/{id}", method=RequestMethod.GET)
	public String deleteProduct(@PathVariable int id) {
		
		if( !productService.deleteProduct(id) ) {
			System.out.println("Deleting product cannot be done");
		}
		
		return "redirect:/admin/productInventory"; //다시 DB에서 읽어오도록	
	}
	
	@RequestMapping(value = "/productInventory/updateProduct/{id}", method=RequestMethod.GET)
	public String updateProduct(@PathVariable int id, Model model) {
		//기존의 product를 확인
		//addProduct에서 기존의 내용을 보여주어야함
		//id에 해당되는 Product조회
		Product product = productService.getProductById(id); // product 가져옴
		
		model.addAttribute("product", product);
		
		return "updateProduct";	
	}
	
	@RequestMapping(value = "/productInventory/updateProduct", method=RequestMethod.POST)
	public String updateProductPost(@Valid Product product, BindingResult result) { 

		if(result.hasErrors()) {
			System.out.println("From data has some errors");
			List<ObjectError> errors = result.getAllErrors();
			
			for(ObjectError error:errors) {
				System.out.println(error.getDefaultMessage());
			}
			return "updateProduct"; // error날경우 다시 입력페이지로
		}
		
		//System.out.println(product);
		if(!productService.updateProduct(product))
			System.out.println("Updating product cannot be done");
		
		return "redirect:/admin/productInventory";
	}
	
}
