package xyz.acproject.blogs.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * admin
 * @author 
 */
public class Admin implements Serializable {
    private Integer id;

    private String name;

    private String password;

    private String remark;

    private Integer articlenum;

    private Date logintime;

    private String headimg;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getArticlenum() {
        return articlenum;
    }

    public void setArticlenum(Integer articlenum) {
        this.articlenum = articlenum;
    }

    public Date getLogintime() {
        return logintime;
    }

    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
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
        Admin other = (Admin) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getArticlenum() == null ? other.getArticlenum() == null : this.getArticlenum().equals(other.getArticlenum()))
            && (this.getLogintime() == null ? other.getLogintime() == null : this.getLogintime().equals(other.getLogintime()))
            && (this.getHeadimg() == null ? other.getHeadimg() == null : this.getHeadimg().equals(other.getHeadimg()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getArticlenum() == null) ? 0 : getArticlenum().hashCode());
        result = prime * result + ((getLogintime() == null) ? 0 : getLogintime().hashCode());
        result = prime * result + ((getHeadimg() == null) ? 0 : getHeadimg().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", password=").append(password);
        sb.append(", remark=").append(remark);
        sb.append(", articlenum=").append(articlenum);
        sb.append(", logintime=").append(logintime);
        sb.append(", headimg=").append(headimg);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}