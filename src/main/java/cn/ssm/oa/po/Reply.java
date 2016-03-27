package cn.ssm.oa.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "itcast_reply")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 新增回复记录需要主键返回(维护关联关系的时候需要使用)
    private Long id;

    private String title;

    @Column(name="faceIcon")
    private String faceIcon;

    @Column(name="postTime")
    private Date postTime;

    @Column(name="ipAddr")
    private String ipAddr;

    @Column(name="authorId")
    private Long authorId;

    @Column(name="topicId")
    private Long topicId;

    private String content;
    
    @Transient
    private User author;

    public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
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
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * @return faceIcon
     */
    public String getFaceIcon() {
        return faceIcon;
    }

    /**
     * @param faceIcon
     */
    public void setFaceIcon(String faceIcon) {
        this.faceIcon = faceIcon == null ? null : faceIcon.trim();
    }

    /**
     * @return postTime
     */
    public Date getPostTime() {
        return postTime;
    }

    /**
     * @param postTime
     */
    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    /**
     * @return ipAddr
     */
    public String getIpAddr() {
        return ipAddr;
    }

    /**
     * @param ipAddr
     */
    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr == null ? null : ipAddr.trim();
    }

    /**
     * @return authorId
     */
    public Long getAuthorId() {
        return authorId;
    }

    /**
     * @param authorId
     */
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    /**
     * @return topicId
     */
    public Long getTopicId() {
        return topicId;
    }

    /**
     * @param topicId
     */
    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}