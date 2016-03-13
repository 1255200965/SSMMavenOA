package cn.ssm.oa.po;

import java.util.List;

import javax.persistence.*;

@Table(name = "itcast_department")
public class Department {
    @Id
    private Long id;

    private String name;

    private String description;

    @Column(name="parentId") // 默认的查询是驼峰法转下划线即：parentId => parent_id导致sql错误
    private Long parentId;
    
    @Transient // 该注解表示本属性不是数据库表中的字段
    private Department parent;

    @Transient
    private List<Department> children;

    public List<Department> getChildren() {
		return children;
	}

	public void setChildren(List<Department> children) {
		this.children = children;
	}

    public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	/**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * @return parentId
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}