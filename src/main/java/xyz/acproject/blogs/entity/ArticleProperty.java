package xyz.acproject.blogs.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * article_property
 * @author 
 */
public class ArticleProperty implements Serializable {
    private Integer id;

    private String title;

    /**
     * 文章描述
     */
    private String describe;

    private String imgurl;

    private String createman;

    private Timestamp createtime;

    /**
     * 热门文章 1 yes 0 no
     */
    private String top;

    private Integer contentid;

    private Integer bcid;

    private Integer scid;
    
    private ArticleScale articleScale;
    
    private ArticleContent articleContent;
    
    private Bigcategory bigcategory;
    
    private Smallcategory smallcategory;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getCreateman() {
        return createman;
    }

    public void setCreateman(String createman) {
        this.createman = createman;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String times = createtime.toString();
		Date date =null;
		try {
			date = dateFormat.parse(times);
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		String time = dateFormat.format(date);
		Timestamp timestamp = Timestamp.valueOf(time);
        this.createtime = timestamp;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public Integer getContentid() {
        return contentid;
    }

    public void setContentid(Integer contentid) {
        this.contentid = contentid;
    }

    public Integer getBcid() {
        return bcid;
    }

    public void setBcid(Integer bcid) {
        this.bcid = bcid;
    }

    public Integer getScid() {
        return scid;
    }

    public void setScid(Integer scid) {
        this.scid = scid;
    }

    
    public ArticleScale getArticleScale() {
		return articleScale;
	}

	public void setArticleScale(ArticleScale articleScale) {
		this.articleScale = articleScale;
	}

	
	public ArticleContent getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(ArticleContent articleContent) {
		this.articleContent = articleContent;
	}

	public Bigcategory getBigcategory() {
		return bigcategory;
	}

	public void setBigcategory(Bigcategory bigcategory) {
		this.bigcategory = bigcategory;
	}

	public Smallcategory getSmallcategory() {
		return smallcategory;
	}

	public void setSmallcategory(Smallcategory smallcategory) {
		this.smallcategory = smallcategory;
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
        ArticleProperty other = (ArticleProperty) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getDescribe() == null ? other.getDescribe() == null : this.getDescribe().equals(other.getDescribe()))
            && (this.getImgurl() == null ? other.getImgurl() == null : this.getImgurl().equals(other.getImgurl()))
            && (this.getCreateman() == null ? other.getCreateman() == null : this.getCreateman().equals(other.getCreateman()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getTop() == null ? other.getTop() == null : this.getTop().equals(other.getTop()))
            && (this.getContentid() == null ? other.getContentid() == null : this.getContentid().equals(other.getContentid()))
            && (this.getBcid() == null ? other.getBcid() == null : this.getBcid().equals(other.getBcid()))
            && (this.getScid() == null ? other.getScid() == null : this.getScid().equals(other.getScid()))
            && (this.getArticleScale()== null ? other.getArticleScale() == null : this.getArticleScale().equals(other.getArticleScale()))
            && (this.getArticleContent()== null ? other.getArticleContent() == null : this.getArticleContent().equals(other.getArticleContent()))
            && (this.getBigcategory()== null ? other.getBigcategory() == null : this.getBigcategory().equals(other.getBigcategory()))
            && (this.getSmallcategory()== null ? other.getSmallcategory() == null : this.getSmallcategory().equals(other.getSmallcategory()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getDescribe() == null) ? 0 : getDescribe().hashCode());
        result = prime * result + ((getImgurl() == null) ? 0 : getImgurl().hashCode());
        result = prime * result + ((getCreateman() == null) ? 0 : getCreateman().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getTop() == null) ? 0 : getTop().hashCode());
        result = prime * result + ((getContentid() == null) ? 0 : getContentid().hashCode());
        result = prime * result + ((getBcid() == null) ? 0 : getBcid().hashCode());
        result = prime * result + ((getScid() == null) ? 0 : getScid().hashCode());
        result = prime * result + ((getArticleScale() == null) ? 0 : getArticleScale().hashCode());
        result = prime * result + ((getArticleContent() == null) ? 0 : getArticleContent().hashCode());
        result = prime * result + ((getBigcategory() == null) ? 0 : getBigcategory().hashCode());
        result = prime * result + ((getSmallcategory() == null) ? 0 : getSmallcategory().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", describe=").append(describe);
        sb.append(", imgurl=").append(imgurl);
        sb.append(", createman=").append(createman);
        sb.append(", createtime=").append(createtime);
        sb.append(", top=").append(top);
        sb.append(", contentid=").append(contentid);
        sb.append(", bcid=").append(bcid);
        sb.append(", scid=").append(scid);
        sb.append(", articleScale=").append(articleScale);
        sb.append(", articleContent=").append(articleContent);
        sb.append(", bigcategory=").append(bigcategory);
        sb.append(", smallcategory=").append(smallcategory);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}