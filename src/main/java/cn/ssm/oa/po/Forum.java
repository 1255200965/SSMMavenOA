package cn.ssm.oa.po;

import javax.persistence.*;

@Table(name = "itcast_forum")
public class Forum {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Integer position;

    @Column(name="topicCount")
    private Integer topicCount;

    @Column(name="articleCount")
    private Integer articleCount;

    @Column(name="lastTopicId")
    private Long lastTopicId;

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
     * @return position
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * @param position
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * @return topicCount
     */
    public Integer getTopicCount() {
        return topicCount;
    }

    /**
     * @param topicCount
     */
    public void setTopicCount(Integer topicCount) {
        this.topicCount = topicCount;
    }

    /**
     * @return articleCount
     */
    public Integer getArticleCount() {
        return articleCount;
    }

    /**
     * @param articleCount
     */
    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    /**
     * @return lastTopicId
     */
    public Long getLastTopicId() {
        return lastTopicId;
    }

    /**
     * @param lastTopicId
     */
    public void setLastTopicId(Long lastTopicId) {
        this.lastTopicId = lastTopicId;
    }
}