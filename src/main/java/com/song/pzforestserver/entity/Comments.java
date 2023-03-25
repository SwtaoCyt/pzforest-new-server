package com.song.pzforestserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 微博评论表
 * @TableName weibos_comments
 */
@TableName(value ="weibos_comments")
@Data
public class Comments implements Serializable {
    /**
     * 评论的ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 评论创建时间
     */
    @TableField(value = "created_at")
    private Date created_at;

    /**
     * 评论的内容
     */
    @TableField(value = "text")
    private String text;

    /**
     * 评论的来源
     */
    @TableField(value = "source")
    private String source;

    /**
     * 评论的MID
     */
    @TableField(value = "mid")
    private String mid;

    /**
     * 字符串型的评论ID
     */
    @TableField(value = "idstr")
    private String idstr;

    /**
     * 评论的微博ID
     */
    @TableField(value = "status_id")
    private Long status_id;

    /**
     * 评论来源评论的ID，当本评论属于对另一评论的回复时返回此字段
     */
    @TableField(value = "reply_comment_id")
    private Long reply_comment_id;

    /**
     * 评论作者的用户ID
     */
    @TableField(value = "user_id")
    private Long user_id;

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
        Comments other = (Comments) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCreated_at() == null ? other.getCreated_at() == null : this.getCreated_at().equals(other.getCreated_at()))
            && (this.getText() == null ? other.getText() == null : this.getText().equals(other.getText()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getMid() == null ? other.getMid() == null : this.getMid().equals(other.getMid()))
            && (this.getIdstr() == null ? other.getIdstr() == null : this.getIdstr().equals(other.getIdstr()))
            && (this.getStatus_id() == null ? other.getStatus_id() == null : this.getStatus_id().equals(other.getStatus_id()))
            && (this.getReply_comment_id() == null ? other.getReply_comment_id() == null : this.getReply_comment_id().equals(other.getReply_comment_id()))
            && (this.getUser_id() == null ? other.getUser_id() == null : this.getUser_id().equals(other.getUser_id()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreated_at() == null) ? 0 : getCreated_at().hashCode());
        result = prime * result + ((getText() == null) ? 0 : getText().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getMid() == null) ? 0 : getMid().hashCode());
        result = prime * result + ((getIdstr() == null) ? 0 : getIdstr().hashCode());
        result = prime * result + ((getStatus_id() == null) ? 0 : getStatus_id().hashCode());
        result = prime * result + ((getReply_comment_id() == null) ? 0 : getReply_comment_id().hashCode());
        result = prime * result + ((getUser_id() == null) ? 0 : getUser_id().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", created_at=").append(created_at);
        sb.append(", text=").append(text);
        sb.append(", source=").append(source);
        sb.append(", mid=").append(mid);
        sb.append(", idstr=").append(idstr);
        sb.append(", status_id=").append(status_id);
        sb.append(", reply_comment_id=").append(reply_comment_id);
        sb.append(", user_id=").append(user_id);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}