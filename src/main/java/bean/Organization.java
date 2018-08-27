package bean;

/**
 * @author ZJY
 * @ClassName: Organization
 * @Description: Organization
 * @date 2018/5/14 15:27
 */
public class Organization {

    private Integer id;

    private String orgNo;

    private String orgName;

    public Organization() {
    }

    public Organization(Integer id, String orgNo) {
        this.id = id;
        this.orgNo = orgNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", orgNo='" + orgNo + '\'' +
                ", orgName='" + orgName + '\'' +
                '}';
    }
}
