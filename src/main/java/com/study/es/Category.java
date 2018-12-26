package com.study.es;

/**
 * 产品分类
 * @author Thinkpad
 *
 */
public class Category {

	private String productPlanId;
    private String id;
    private String name;
    private String parentId;
	public String getProductPlanId() {
		return productPlanId;
	}
	public void setProductPlanId(String productPlanId) {
		this.productPlanId = productPlanId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public Category() {}
	public Category(String productPlanId, String id, String name, String parentId) {
		super();
		this.productPlanId = productPlanId;
		this.id = id;
		this.name = name;
		this.parentId = parentId;
	}
    
}
