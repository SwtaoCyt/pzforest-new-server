package com.song.pzforestserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName weibos_users
 */
@TableName(value ="weibos_users")
@Data
public class WeibosUsers implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 
     */
    @TableField(value = "idstr")
    private String idstr;

    /**
     * 
     */
    @TableField(value = "screen_name")
    private String screen_name;

    /**
     * 
     */
    @TableField(value = "name")
    private String name;

    /**
     * 
     */
    @TableField(value = "province")
    private Integer province;

    /**
     * 
     */
    @TableField(value = "city")
    private Integer city;

    /**
     * 
     */
    @TableField(value = "location")
    private String location;

    /**
     * 
     */
    @TableField(value = "description")
    private String description;

    /**
     * 
     */
    @TableField(value = "url")
    private String url;

    /**
     * 
     */
    @TableField(value = "profile_image_url")
    private String profile_image_url;

    /**
     * 
     */
    @TableField(value = "profile_url")
    private String profile_url;

    /**
     * 
     */
    @TableField(value = "domain")
    private String domain;

    /**
     * 
     */
    @TableField(value = "weihao")
    private String weihao;

    /**
     * 
     */
    @TableField(value = "gender")
    private Object gender;

    /**
     * 
     */
    @TableField(value = "followers_count")
    private Integer followers_count;

    /**
     * 
     */
    @TableField(value = "friends_count")
    private Integer friends_count;

    /**
     * 
     */
    @TableField(value = "statuses_count")
    private Integer statuses_count;

    /**
     * 
     */
    @TableField(value = "favourites_count")
    private Integer favourites_count;

    /**
     * 
     */
    @TableField(value = "created_at")
    private String created_at;

    /**
     * 
     */
    @TableField(value = "following")
    private Integer following;

    /**
     * 
     */
    @TableField(value = "allow_all_act_msg")
    private Integer allow_all_act_msg;

    /**
     * 
     */
    @TableField(value = "geo_enabled")
    private Integer geo_enabled;

    /**
     * 
     */
    @TableField(value = "verified")
    private Integer verified;

    /**
     * 
     */
    @TableField(value = "verified_type")
    private Integer verified_type;

    /**
     * 
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 
     */
    @TableField(value = "allow_all_comment")
    private Integer allow_all_comment;

    /**
     * 
     */
    @TableField(value = "avatar_large")
    private String avatar_large;

    /**
     * 
     */
    @TableField(value = "avatar_hd")
    private String avatar_hd;

    /**
     * 
     */
    @TableField(value = "verified_reason")
    private String verified_reason;

    /**
     * 
     */
    @TableField(value = "follow_me")
    private Integer follow_me;

    /**
     * 
     */
    @TableField(value = "online_status")
    private Integer online_status;

    /**
     * 
     */
    @TableField(value = "bi_followers_count")
    private Integer bi_followers_count;

    /**
     * 
     */
    @TableField(value = "lang")
    private Object lang;

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
        WeibosUsers other = (WeibosUsers) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getIdstr() == null ? other.getIdstr() == null : this.getIdstr().equals(other.getIdstr()))
            && (this.getScreen_name() == null ? other.getScreen_name() == null : this.getScreen_name().equals(other.getScreen_name()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getLocation() == null ? other.getLocation() == null : this.getLocation().equals(other.getLocation()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getProfile_image_url() == null ? other.getProfile_image_url() == null : this.getProfile_image_url().equals(other.getProfile_image_url()))
            && (this.getProfile_url() == null ? other.getProfile_url() == null : this.getProfile_url().equals(other.getProfile_url()))
            && (this.getDomain() == null ? other.getDomain() == null : this.getDomain().equals(other.getDomain()))
            && (this.getWeihao() == null ? other.getWeihao() == null : this.getWeihao().equals(other.getWeihao()))
            && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
            && (this.getFollowers_count() == null ? other.getFollowers_count() == null : this.getFollowers_count().equals(other.getFollowers_count()))
            && (this.getFriends_count() == null ? other.getFriends_count() == null : this.getFriends_count().equals(other.getFriends_count()))
            && (this.getStatuses_count() == null ? other.getStatuses_count() == null : this.getStatuses_count().equals(other.getStatuses_count()))
            && (this.getFavourites_count() == null ? other.getFavourites_count() == null : this.getFavourites_count().equals(other.getFavourites_count()))
            && (this.getCreated_at() == null ? other.getCreated_at() == null : this.getCreated_at().equals(other.getCreated_at()))
            && (this.getFollowing() == null ? other.getFollowing() == null : this.getFollowing().equals(other.getFollowing()))
            && (this.getAllow_all_act_msg() == null ? other.getAllow_all_act_msg() == null : this.getAllow_all_act_msg().equals(other.getAllow_all_act_msg()))
            && (this.getGeo_enabled() == null ? other.getGeo_enabled() == null : this.getGeo_enabled().equals(other.getGeo_enabled()))
            && (this.getVerified() == null ? other.getVerified() == null : this.getVerified().equals(other.getVerified()))
            && (this.getVerified_type() == null ? other.getVerified_type() == null : this.getVerified_type().equals(other.getVerified_type()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getAllow_all_comment() == null ? other.getAllow_all_comment() == null : this.getAllow_all_comment().equals(other.getAllow_all_comment()))
            && (this.getAvatar_large() == null ? other.getAvatar_large() == null : this.getAvatar_large().equals(other.getAvatar_large()))
            && (this.getAvatar_hd() == null ? other.getAvatar_hd() == null : this.getAvatar_hd().equals(other.getAvatar_hd()))
            && (this.getVerified_reason() == null ? other.getVerified_reason() == null : this.getVerified_reason().equals(other.getVerified_reason()))
            && (this.getFollow_me() == null ? other.getFollow_me() == null : this.getFollow_me().equals(other.getFollow_me()))
            && (this.getOnline_status() == null ? other.getOnline_status() == null : this.getOnline_status().equals(other.getOnline_status()))
            && (this.getBi_followers_count() == null ? other.getBi_followers_count() == null : this.getBi_followers_count().equals(other.getBi_followers_count()))
            && (this.getLang() == null ? other.getLang() == null : this.getLang().equals(other.getLang()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIdstr() == null) ? 0 : getIdstr().hashCode());
        result = prime * result + ((getScreen_name() == null) ? 0 : getScreen_name().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getLocation() == null) ? 0 : getLocation().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getProfile_image_url() == null) ? 0 : getProfile_image_url().hashCode());
        result = prime * result + ((getProfile_url() == null) ? 0 : getProfile_url().hashCode());
        result = prime * result + ((getDomain() == null) ? 0 : getDomain().hashCode());
        result = prime * result + ((getWeihao() == null) ? 0 : getWeihao().hashCode());
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getFollowers_count() == null) ? 0 : getFollowers_count().hashCode());
        result = prime * result + ((getFriends_count() == null) ? 0 : getFriends_count().hashCode());
        result = prime * result + ((getStatuses_count() == null) ? 0 : getStatuses_count().hashCode());
        result = prime * result + ((getFavourites_count() == null) ? 0 : getFavourites_count().hashCode());
        result = prime * result + ((getCreated_at() == null) ? 0 : getCreated_at().hashCode());
        result = prime * result + ((getFollowing() == null) ? 0 : getFollowing().hashCode());
        result = prime * result + ((getAllow_all_act_msg() == null) ? 0 : getAllow_all_act_msg().hashCode());
        result = prime * result + ((getGeo_enabled() == null) ? 0 : getGeo_enabled().hashCode());
        result = prime * result + ((getVerified() == null) ? 0 : getVerified().hashCode());
        result = prime * result + ((getVerified_type() == null) ? 0 : getVerified_type().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getAllow_all_comment() == null) ? 0 : getAllow_all_comment().hashCode());
        result = prime * result + ((getAvatar_large() == null) ? 0 : getAvatar_large().hashCode());
        result = prime * result + ((getAvatar_hd() == null) ? 0 : getAvatar_hd().hashCode());
        result = prime * result + ((getVerified_reason() == null) ? 0 : getVerified_reason().hashCode());
        result = prime * result + ((getFollow_me() == null) ? 0 : getFollow_me().hashCode());
        result = prime * result + ((getOnline_status() == null) ? 0 : getOnline_status().hashCode());
        result = prime * result + ((getBi_followers_count() == null) ? 0 : getBi_followers_count().hashCode());
        result = prime * result + ((getLang() == null) ? 0 : getLang().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", idstr=").append(idstr);
        sb.append(", screen_name=").append(screen_name);
        sb.append(", name=").append(name);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", location=").append(location);
        sb.append(", description=").append(description);
        sb.append(", url=").append(url);
        sb.append(", profile_image_url=").append(profile_image_url);
        sb.append(", profile_url=").append(profile_url);
        sb.append(", domain=").append(domain);
        sb.append(", weihao=").append(weihao);
        sb.append(", gender=").append(gender);
        sb.append(", followers_count=").append(followers_count);
        sb.append(", friends_count=").append(friends_count);
        sb.append(", statuses_count=").append(statuses_count);
        sb.append(", favourites_count=").append(favourites_count);
        sb.append(", created_at=").append(created_at);
        sb.append(", following=").append(following);
        sb.append(", allow_all_act_msg=").append(allow_all_act_msg);
        sb.append(", geo_enabled=").append(geo_enabled);
        sb.append(", verified=").append(verified);
        sb.append(", verified_type=").append(verified_type);
        sb.append(", remark=").append(remark);
        sb.append(", allow_all_comment=").append(allow_all_comment);
        sb.append(", avatar_large=").append(avatar_large);
        sb.append(", avatar_hd=").append(avatar_hd);
        sb.append(", verified_reason=").append(verified_reason);
        sb.append(", follow_me=").append(follow_me);
        sb.append(", online_status=").append(online_status);
        sb.append(", bi_followers_count=").append(bi_followers_count);
        sb.append(", lang=").append(lang);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}