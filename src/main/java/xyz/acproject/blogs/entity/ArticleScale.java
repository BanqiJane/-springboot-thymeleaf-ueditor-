package xyz.acproject.blogs.entity;

import java.io.Serializable;

/**
 * article_scale
 * @author 
 */
public class ArticleScale implements Serializable {
    private Integer id;

    /**
     * 文章评论量
     */
    private Long commentnum;

    /**
     * 文章访问量
     */
    private Long articlepv;

    /**
     * 点赞量
     */
    private Long articlepraise;

    private Integer apid;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(Long commentnum) {
        this.commentnum = commentnum;
    }

    public Long getArticlepv() {
        return articlepv;
    }

    public void setArticlepv(Long articlepv) {
        this.articlepv = articlepv;
    }

    public Long getArticlepraise() {
        return articlepraise;
    }

    public void setArticlepraise(Long articlepraise) {
        this.articlepraise = articlepraise;
    }

    public Integer getApid() {
        return apid;
    }

    public void setApid(Integer apid) {
        this.apid = apid;
    }

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
        ArticleScale other = (ArticleScale) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCommentnum() == null ? other.getCommentnum() == null : this.getCommentnum().equals(other.getCommentnum()))
            && (this.getArticlepv() == null ? other.getArticlepv() == null : this.getArticlepv().equals(other.getArticlepv()))
            && (this.getArticlepraise() == null ? other.getArticlepraise() == null : this.getArticlepraise().equals(other.getArticlepraise()))
            && (this.getApid() == null ? other.getApid() == null : this.getApid().equals(other.getApid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCommentnum() == null) ? 0 : getCommentnum().hashCode());
        result = prime * result + ((getArticlepv() == null) ? 0 : getArticlepv().hashCode());
        result = prime * result + ((getArticlepraise() == null) ? 0 : getArticlepraise().hashCode());
        result = prime * result + ((getApid() == null) ? 0 : getApid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", commentnum=").append(commentnum);
        sb.append(", articlepv=").append(articlepv);
        sb.append(", articlepraise=").append(articlepraise);
        sb.append(", apid=").append(apid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}