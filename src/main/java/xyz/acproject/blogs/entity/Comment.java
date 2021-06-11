package xyz.acproject.blogs.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * comment
 * @author 
 */
public class Comment implements Serializable {
    private Integer id;

    private String name;

    private String ip;

    private String value;

    private Date time;

    private Integer cid;

    private Long praisenum;

    private Integer apid;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Long getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(Long praisenum) {
        this.praisenum = praisenum;
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
        Comment other = (Comment) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getCid() == null ? other.getCid() == null : this.getCid().equals(other.getCid()))
            && (this.getPraisenum() == null ? other.getPraisenum() == null : this.getPraisenum().equals(other.getPraisenum()))
            && (this.getApid() == null ? other.getApid() == null : this.getApid().equals(other.getApid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getCid() == null) ? 0 : getCid().hashCode());
        result = prime * result + ((getPraisenum() == null) ? 0 : getPraisenum().hashCode());
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
        sb.append(", name=").append(name);
        sb.append(", ip=").append(ip);
        sb.append(", value=").append(value);
        sb.append(", time=").append(time);
        sb.append(", cid=").append(cid);
        sb.append(", praisenum=").append(praisenum);
        sb.append(", apid=").append(apid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}