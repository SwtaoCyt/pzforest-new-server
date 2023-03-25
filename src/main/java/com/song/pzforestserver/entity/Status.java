package com.song.pzforestserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 微博MID
     */
    @TableField(value = "mid")
    private Integer mid;

    /**
     * 字符串型的微博ID
     */
    @TableField(value = "idstr")
    private String idstr;

    /**
     * 微博信息内容
     */
    @TableField(value = "text")
    private String text;

    /**
     * 微博来源
     */
    @TableField(value = "source")
    private String source;

    /**
     * 是否已收藏
     */
    @TableField(value = "favorited")
    private Integer favorited;

    /**
     * 是否被截断
     */
    @TableField(value = "truncated")
    private Integer truncated;

    /**
     * 回复ID
     */
    @TableField(value = "in_reply_to_status_id")
    private String in_reply_to_status_id;

    /**
     * 回复人UID
     */
    @TableField(value = "in_reply_to_user_id")
    private String in_reply_to_user_id;

    /**
     * 回复人昵称
     */
    @TableField(value = "in_reply_to_screen_name")
    private String in_reply_to_screen_name;

    /**
     * 缩略图片地址
     */
    @TableField(value = "thumbnail_pic")
    private String thumbnail_pic;

    /**
     * 中等尺寸图片地址
     */
    @TableField(value = "bmiddle_pic")
    private String bmiddle_pic;

    /**
     * 原始图片地址
     */
    @TableField(value = "original_pic")
    private String original_pic;

    /**
     * 地理信息字段
     */
    @TableField(value = "geo")
    private String geo;

    /**
     * 微博作者的用户ID
     */
    @TableField(value = "user_id")
    private Integer user_id;

    /**
     * 被转发的原微博信息字段
     */
    @TableField(value = "retweeted_status")
    private String retweeted_status;

    /**
     * 转发数
     */
    @TableField(value = "reposts_count")
    private Integer reposts_count;

    /**
     * 评论数
     */
    @TableField(value = "comments_count")
    private Integer comments_count;

    /**
     * 表态数
     */
    @TableField(value = "attitudes_count")
    private Integer attitudes_count;

    /**
     * 暂未支持
     */
    @TableField(value = "mlevel")
    private Integer mlevel;

    /**
     * 微博的可见性及指定可见分组信息
     */
    @TableField(value = "visible")
    private String visible;

    /**
     * 微博配图ID
     */
    @TableField(value = "pic_ids")
    private String pic_ids;

    /**
     * 微博流内的推广微博ID
     */
    @TableField(value = "ad")
    private String ad;

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
            && (this.getIn_reply_to_status_id() == null ? other.getIn_reply_to_status_id() == null : this.getIn_reply_to_status_id().equals(other.getIn_reply_to_status_id()))
            && (this.getIn_reply_to_user_id() == null ? other.getIn_reply_to_user_id() == null : this.getIn_reply_to_user_id().equals(other.getIn_reply_to_user_id()))
            && (this.getIn_reply_to_screen_name() == null ? other.getIn_reply_to_screen_name() == null : this.getIn_reply_to_screen_name().equals(other.getIn_reply_to_screen_name()))
            && (this.getThumbnail_pic() == null ? other.getThumbnail_pic() == null : this.getThumbnail_pic().equals(other.getThumbnail_pic()))
            && (this.getBmiddle_pic() == null ? other.getBmiddle_pic() == null : this.getBmiddle_pic().equals(other.getBmiddle_pic()))
            && (this.getOriginal_pic() == null ? other.getOriginal_pic() == null : this.getOriginal_pic().equals(other.getOriginal_pic()))
            && (this.getGeo() == null ? other.getGeo() == null : this.getGeo().equals(other.getGeo()))
            && (this.getUser_id() == null ? other.getUser_id() == null : this.getUser_id().equals(other.getUser_id()))
            && (this.getRetweeted_status() == null ? other.getRetweeted_status() == null : this.getRetweeted_status().equals(other.getRetweeted_status()))
            && (this.getReposts_count() == null ? other.getReposts_count() == null : this.getReposts_count().equals(other.getReposts_count()))
            && (this.getComments_count() == null ? other.getComments_count() == null : this.getComments_count().equals(other.getComments_count()))
            && (this.getAttitudes_count() == null ? other.getAttitudes_count() == null : this.getAttitudes_count().equals(other.getAttitudes_count()))
            && (this.getMlevel() == null ? other.getMlevel() == null : this.getMlevel().equals(other.getMlevel()))
            && (this.getVisible() == null ? other.getVisible() == null : this.getVisible().equals(other.getVisible()))
            && (this.getPic_ids() == null ? other.getPic_ids() == null : this.getPic_ids().equals(other.getPic_ids()))
            && (this.getAd() == null ? other.getAd() == null : this.getAd().equals(other.getAd()));
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
        result = prime * result + ((getIn_reply_to_status_id() == null) ? 0 : getIn_reply_to_status_id().hashCode());
        result = prime * result + ((getIn_reply_to_user_id() == null) ? 0 : getIn_reply_to_user_id().hashCode());
        result = prime * result + ((getIn_reply_to_screen_name() == null) ? 0 : getIn_reply_to_screen_name().hashCode());
        result = prime * result + ((getThumbnail_pic() == null) ? 0 : getThumbnail_pic().hashCode());
        result = prime * result + ((getBmiddle_pic() == null) ? 0 : getBmiddle_pic().hashCode());
        result = prime * result + ((getOriginal_pic() == null) ? 0 : getOriginal_pic().hashCode());
        result = prime * result + ((getGeo() == null) ? 0 : getGeo().hashCode());
        result = prime * result + ((getUser_id() == null) ? 0 : getUser_id().hashCode());
        result = prime * result + ((getRetweeted_status() == null) ? 0 : getRetweeted_status().hashCode());
        result = prime * result + ((getReposts_count() == null) ? 0 : getReposts_count().hashCode());
        result = prime * result + ((getComments_count() == null) ? 0 : getComments_count().hashCode());
        result = prime * result + ((getAttitudes_count() == null) ? 0 : getAttitudes_count().hashCode());
        result = prime * result + ((getMlevel() == null) ? 0 : getMlevel().hashCode());
        result = prime * result + ((getVisible() == null) ? 0 : getVisible().hashCode());
        result = prime * result + ((getPic_ids() == null) ? 0 : getPic_ids().hashCode());
        result = prime * result + ((getAd() == null) ? 0 : getAd().hashCode());
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
        sb.append(", in_reply_to_status_id=").append(in_reply_to_status_id);
        sb.append(", in_reply_to_user_id=").append(in_reply_to_user_id);
        sb.append(", in_reply_to_screen_name=").append(in_reply_to_screen_name);
        sb.append(", thumbnail_pic=").append(thumbnail_pic);
        sb.append(", bmiddle_pic=").append(bmiddle_pic);
        sb.append(", original_pic=").append(original_pic);
        sb.append(", geo=").append(geo);
        sb.append(", user_id=").append(user_id);
        sb.append(", retweeted_status=").append(retweeted_status);
        sb.append(", reposts_count=").append(reposts_count);
        sb.append(", comments_count=").append(comments_count);
        sb.append(", attitudes_count=").append(attitudes_count);
        sb.append(", mlevel=").append(mlevel);
        sb.append(", visible=").append(visible);
        sb.append(", pic_ids=").append(pic_ids);
        sb.append(", ad=").append(ad);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}