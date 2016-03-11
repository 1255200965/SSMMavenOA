package cn.ssm.oa.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "itcast_topic")
public class Topic {
    @Id
    private Long id;

    private String title;

    private String faceIcon;

    private Date postTime;

    private String ipAddr;

    private Long authorId;

    private Integer type;

    private Integer replyCount;

    private Date lastUpdateTime;

    private Long forumId;

    private Long lastReplyId;

    private String content;

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
     * @return type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return replyCount
     */
    public Integer getReplyCount() {
        return replyCount;
    }

    /**
     * @param replyCount
     */
    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    /**
     * @return lastUpdateTime
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * @param lastUpdateTime
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * @return forumId
     */
    public Long getForumId() {
        return forumId;
    }

    /**
     * @param forumId
     */
    public void setForumId(Long forumId) {
        this.forumId = forumId;
    }

    /**
     * @return lastReplyId
     */
    public Long getLastReplyId() {
        return lastReplyId;
    }

    /**
     * @param lastReplyId
     */
    public void setLastReplyId(Long lastReplyId) {
        this.lastReplyId = lastReplyId;
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