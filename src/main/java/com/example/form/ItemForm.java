package com.example.form;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;

//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotEmpty;

/**
 * 追加するアイテムの情報を格納するフォーム.
 * 
 * @author shibatamasayuki
 *
 */
public class ItemForm {
	@NotBlank(message = "名前の入力は必須です")
	private String name;
	@Range(min=1, message = "priceの入力は必須です")
	private String price;
	private Integer category;
	
	@NotBlank(message="brandの入力は必須です")
	private String brand;
	
	private Integer condition;
	@NotBlank(message="descriptionの入力は必須です")
	private String description;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ItemForm [name=" + name + ", price=" + price + ", category=" + category + ", brand=" + brand
				+ ", condition=" + condition + ", description=" + description + "]";
	}

}
