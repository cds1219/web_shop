package web.vo;

public class ProductVO {
	private String name, description;
	private int price;
	
	public ProductVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductVO(String name) {
		super();
		this.name = name;
	}
	public ProductVO(String name, String description, int price) {//set으로 바꾸기
		super();
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {//유효성검사 하기
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "ProductVO [name=" + name + ", description=" + description + ", price=" + price + "]";
	}
	
}
