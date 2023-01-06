package com.example.form;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;

/**
 * 編集するitemの情報を格納するform.
 * 
 * @author shibatamasayuki
 *
 */
public class EditItemForm {
	private Integer id;
	@NotBlank
	private String name;
	private Integer condition;
	// 小カテゴリーのidをブラウザから取得し、category変数に代入.
	private Integer category;
	private String brand;
	@Range(min = (long) 1.0)
	private double price;
	private Integer shipping;
	@NotBlank(message = "descriptionの入力は必須です")
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getShipping() {
		return shipping;
	}

	public void setShipping(Integer shipping) {
		this.shipping = shipping;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		return "EditItemForm [id=" + id + ", name=" + name + ", condition=" + condition + ", category=" + category
				+ ", brand=" + brand + ", price=" + price + ", shipping=" + shipping + ", description=" + description
				+ "]";
	}

}
