package com.song.pzforestserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 微博信息表
 * @TableName weibos_status
 */
@TableName(value ="weibos_status")
@Data
public class Status implements Serializable {
    /**
     * 微博ID
     */
    @TableId
    private String id;

    /**
     * 微博MID
     */
    private String mid;

    /**
     * 字符串型的微博ID
     */
    private String idstr;

    /**
     * 微博信息内容
     */
    private String text;

    /**
     * 微博来源
     */
    private String source;

    /**
     * 是否已收藏
     */
    private Integer favorited;

    /**
     * 是否被截断
     */
    private Integer truncated;

    /**
     * 回复ID
     */
    private String inReplyToStatusId;

    /**
     * 回复人UID
     */
    private String inReplyToUserId;

    /**
     * 回复人昵称
     */
    private String inReplyToScreenName;

    /**
     * 缩略图片地址
     */
    private String thumbnailPic;

    /**
     * 中等尺寸图片地址
     */
    private String bmiddlePic;

    /**
     * 原始图片地址
     */
    private String originalPic;

    /**
     * 地理信息字段
     */
    private String geo;

    /**
     * 微博作者的用户ID
     */
    private String userId;

    /**
     * 被转发的原微博信息字段
     */
    private String retweetedStatus;

    /**
     * 转发数
     */
    private Integer repostsCount;

    /**
     * 评论数
     */
    private Integer commentsCount;

    /**
     * 表态数
     */
    private Integer attitudesCount;

    /**
     * 暂未支持
     */
    private Integer mlevel;

    /**
     * 微博的可见性及指定可见分组信息
     */
    private String visible;

    /**
     * 微博配图ID
     */
    private String picIds;

    /**
     * 微博流内的推广微博ID
     */
    private String ad;

    /**
     * 创建时间
     */
    private Date createdTime;

    @TableField(exist = false)
    private Image image;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Status other = (Status) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMid() == null ? other.getMid() == null : this.getMid().equals(other.getMid()))
            && (this.getIdstr() == null ? other.getIdstr() == null : this.getIdstr().equals(other.getIdstr()))
            && (this.getText() == null ? other.getText() == null : this.getText().equals(other.getText()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getFavorited() == null ? other.getFavorited() == null : this.getFavorited().equals(other.getFavorited()))
            && (this.getTruncated() == null ? other.getTruncated() == null : this.getTruncated().equals(other.getTruncated()))
            && (this.getInReplyToStatusId() == null ? other.getInReplyToStatusId() == null : this.getInReplyToStatusId().equals(other.getInReplyToStatusId()))
            && (this.getInReplyToUserId() == null ? other.getInReplyToUserId() == null : this.getInReplyToUserId().equals(other.getInReplyToUserId()))
            && (this.getInReplyToScreenName() == null ? other.getInReplyToScreenName() == null : this.getInReplyToScreenName().equals(other.getInReplyToScreenName()))
            && (this.getThumbnailPic() == null ? other.getThumbnailPic() == null : this.getThumbnailPic().equals(other.getThumbnailPic()))
            && (this.getBmiddlePic() == null ? other.getBmiddlePic() == null : this.getBmiddlePic().equals(other.getBmiddlePic()))
            && (this.getOriginalPic() == null ? other.getOriginalPic() == null : this.getOriginalPic().equals(other.getOriginalPic()))
            && (this.getGeo() == null ? other.getGeo() == null : this.getGeo().equals(other.getGeo()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getRetweetedStatus() == null ? other.getRetweetedStatus() == null : this.getRetweetedStatus().equals(other.getRetweetedStatus()))
            && (this.getRepostsCount() == null ? other.getRepostsCount() == null : this.getRepostsCount().equals(other.getRepostsCount()))
            && (this.getCommentsCount() == null ? other.getCommentsCount() == null : this.getCommentsCount().equals(other.getCommentsCount()))
            && (this.getAttitudesCount() == null ? other.getAttitudesCount() == null : this.getAttitudesCount().equals(other.getAttitudesCount()))
            && (this.getMlevel() == null ? other.getMlevel() == null : this.getMlevel().equals(other.getMlevel()))
            && (this.getVisible() == null ? other.getVisible() == null : this.getVisible().equals(other.getVisible()))
            && (this.getPicIds() == null ? other.getPicIds() == null : this.getPicIds().equals(other.getPicIds()))
            && (this.getAd() == null ? other.getAd() == null : this.getAd().equals(other.getAd()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMid() == null) ? 0 : getMid().hashCode());
        result = prime * result + ((getIdstr() == null) ? 0 : getIdstr().hashCode());
        result = prime * result + ((getText() == null) ? 0 : getText().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getFavorited() == null) ? 0 : getFavorited().hashCode());
        result = prime * result + ((getTruncated() == null) ? 0 : getTruncated().hashCode());
        result = prime * result + ((getInReplyToStatusId() == null) ? 0 : getInReplyToStatusId().hashCode());
        result = prime * result + ((getInReplyToUserId() == null) ? 0 : getInReplyToUserId().hashCode());
        result = prime * result + ((getInReplyToScreenName() == null) ? 0 : getInReplyToScreenName().hashCode());
        result = prime * result + ((getThumbnailPic() == null) ? 0 : getThumbnailPic().hashCode());
        result = prime * result + ((getBmiddlePic() == null) ? 0 : getBmiddlePic().hashCode());
        result = prime * result + ((getOriginalPic() == null) ? 0 : getOriginalPic().hashCode());
        result = prime * result + ((getGeo() == null) ? 0 : getGeo().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getRetweetedStatus() == null) ? 0 : getRetweetedStatus().hashCode());
        result = prime * result + ((getRepostsCount() == null) ? 0 : getRepostsCount().hashCode());
        result = prime * result + ((getCommentsCount() == null) ? 0 : getCommentsCount().hashCode());
        result = prime * result + ((getAttitudesCount() == null) ? 0 : getAttitudesCount().hashCode());
        result = prime * result + ((getMlevel() == null) ? 0 : getMlevel().hashCode());
        result = prime * result + ((getVisible() == null) ? 0 : getVisible().hashCode());
        result = prime * result + ((getPicIds() == null) ? 0 : getPicIds().hashCode());
        result = prime * result + ((getAd() == null) ? 0 : getAd().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mid=").append(mid);
        sb.append(", idstr=").append(idstr);
        sb.append(", text=").append(text);
        sb.append(", source=").append(source);
        sb.append(", favorited=").append(favorited);
        sb.append(", truncated=").append(truncated);
        sb.append(", inReplyToStatusId=").append(inReplyToStatusId);
        sb.append(", inReplyToUserId=").append(inReplyToUserId);
        sb.append(", inReplyToScreenName=").append(inReplyToScreenName);
        sb.append(", thumbnailPic=").append(thumbnailPic);
        sb.append(", bmiddlePic=").append(bmiddlePic);
        sb.append(", originalPic=").append(originalPic);
        sb.append(", geo=").append(geo);
        sb.append(", userId=").append(userId);
        sb.append(", retweetedStatus=").append(retweetedStatus);
        sb.append(", repostsCount=").append(repostsCount);
        sb.append(", commentsCount=").append(commentsCount);
        sb.append(", attitudesCount=").append(attitudesCount);
        sb.append(", mlevel=").append(mlevel);
        sb.append(", visible=").append(visible);
        sb.append(", picIds=").append(picIds);
        sb.append(", ad=").append(ad);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}